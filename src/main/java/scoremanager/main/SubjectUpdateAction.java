package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 一覧から科目コードを受け取る
        String cd = req.getParameter("cd");

        // 科目コードから登録情報を探す
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(cd, teacher.getSchool());

        // JSPに渡す
        req.setAttribute("subject", subject);

        // フォワード
        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}