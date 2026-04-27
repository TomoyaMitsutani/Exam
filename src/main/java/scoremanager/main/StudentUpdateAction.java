package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		// 一覧から学生番号を受け取る
		String no = req.getParameter("no");
		
		// 学生番号から登録情報を探す
		StudentDao sDao = new StudentDao();
		Student student = sDao.get(no);
		
		// クラス一覧取得
		ClassNumDao cNumDao = new ClassNumDao();
		List<String> classList = cNumDao.filter(teacher.getSchool());

		// jspに渡す
		req.setAttribute("class_num_set", classList);
		req.setAttribute("student", student);
		
		// フォワード
		req.getRequestDispatcher("student_update.jsp").forward(req, res);
		
	}
}