package scoremanager.main;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentDeleteExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // リクエストパラメータの取得
        String no = req.getParameter("no");

        // Subjectオブジェクト作成
        Student student = new Student();
        student.setNo(no);
        student.setSchool(school);

        // 削除
        StudentDao studentDao = new StudentDao();
        studentDao.delete(student,school);

        req.getRequestDispatcher("student_delete_done.jsp").forward(req, res);
    }
}