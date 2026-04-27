package scoremanager.main;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		String entYearStr = req.getParameter("entYear");
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String classNum = req.getParameter("classNum");
		String isAttendStr = req.getParameter("isAttend");
		int entYear = Integer.parseInt(entYearStr);
		boolean isAttend = false;
		if (isAttendStr != null) {
			isAttend = true;
		}
		
		StudentDao sDao = new StudentDao();

		// Studentオブジェクト作成
		Student student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setAttend(isAttend);
		student.setSchool(teacher.getSchool());

		// 登録
		sDao.save(student);
		
		req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
		
	}
}
