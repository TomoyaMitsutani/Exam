package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の宣言 1
        List<Subject> subjects = null;

        // DBからデータ取得 
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        SubjectDao subjectDao = new SubjectDao();
        subjects = subjectDao.filter(teacher.getSchool());

        // レスポンス値をセット 
        req.setAttribute("subjects", subjects);

        // JSPへフォワード 
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}