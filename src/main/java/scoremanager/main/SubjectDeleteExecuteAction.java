package scoremanager.main;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	
    	// セッション取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // リクエストパラメータの取得
        String cd = req.getParameter("cd");

        // Subjectオブジェクト作成
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool(school);

        // 削除処理
        SubjectDao subjectDao = new SubjectDao();
        subjectDao.delete(subject);

        // フォワード
        req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
    }
}