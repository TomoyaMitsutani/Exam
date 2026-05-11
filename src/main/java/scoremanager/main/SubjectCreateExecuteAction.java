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

        // リクエストパラメータの取得 2
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        Map<String, String> errors = new HashMap<>();

        // 科目コード重複チェック
        SubjectDao subjectDao = new SubjectDao();
        Subject subject_check = subjectDao.get(cd, school);
        if (subject_check != null) {
            errors.put("cd", "科目コードが重複しています");
        }

        // 科目コード未入力チェック
        if (cd == null || cd.isEmpty()) {
            errors.put("cd", "科目コードを入力してください");
        }

        // 科目名未入力チェック
        if (name == null || name.isEmpty()) {
            errors.put("name", "科目名を入力してください");
        }

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