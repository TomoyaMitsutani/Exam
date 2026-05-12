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

public class SubjectUpdateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        
		Teacher teacher = (Teacher) session.getAttribute("user");
		School school = teacher.getSchool();

        // リクエストパラメータの取得
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");
	
		SubjectDao dao = new SubjectDao();

        // 科目存在確認
        Subject subject_check = dao.get(cd, teacher.getSchool());

        Map<String, String> errors = new HashMap<>();

        if (subject_check == null) {
        	
        	errors.put("subject_not_found", "科目が存在していません");

        	// 入力値保持
        	req.setAttribute("cd", cd);
        	req.setAttribute("name", name);

        	req.setAttribute("errors", errors);

        	req.getRequestDispatcher("subject_update.jsp").forward(req, res);

        	return;
        
        }

        // 更新用Subjectオブジェクト作成
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(school);

        // 更新
        SubjectDao subjectDao = new SubjectDao();
        subjectDao.save(subject);

        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    }
}