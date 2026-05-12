package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // リクエストパラメータの取得
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        Map<String, String> errors = new HashMap<>();

        // 科目コード重複チェック
        SubjectDao subjectDao = new SubjectDao();
        Subject subject_check = subjectDao.get(cd, school);
        if (subject_check != null) {
            errors.put("subject_cd_duplicate", "科目コードが重複しています");
        }
        
        // 科目コード文字数チェック
        if (cd.length() != 3) {
        	errors.put("subject_cd_lengthover", "科目コードは3文字で入力してください");
        }

        // エラー発生時の差し返し
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
            return;
        }

        // Subjectオブジェクト作成
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(school);

        // 登録
        subjectDao.save(subject);

        req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
    }
}