package scoremanager.main;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentDeleteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	
    	// セッション
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 一覧から学生番号を受け取る
        String no = req.getParameter("no");

        // 学生番号から登録情報を探す
        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(no);

        // JSPに渡す
        req.setAttribute("student", student);

        // フォワード
        req.getRequestDispatcher("student_delete.jsp").forward(req, res);
    }
}