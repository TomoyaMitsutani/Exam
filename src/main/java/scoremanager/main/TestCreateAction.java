package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.StudentDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestCreateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// セッション取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// studentテーブルに登録されている学生番号をリストで取得する
		StudentDao studentDao = new StudentDao();
		List<Student> studentList = studentDao.filter(teacher.getSchool(), true);

		// subjectテーブルに登録されている科目をリストで取得する
		SubjectDao subjectDao = new SubjectDao();
		List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

		// JSPへ送る
		req.setAttribute("student_list", studentList);
		req.setAttribute("subject_list", subjectList);

		// フォワード
		req.getRequestDispatcher("test_create.jsp").forward(req, res);

	}

}