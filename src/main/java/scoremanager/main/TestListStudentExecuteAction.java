package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// セッション取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// パラメータ取得
		String studentNo = req.getParameter("f4");

		// 検索処理
		TestListStudentDao dao = new TestListStudentDao();
		List<TestListStudent> testListStudent = dao.filter(studentNo, teacher.getSchool());		
		
		// 検索結果をJSPへ送る
		req.setAttribute("testListStudent", testListStudent);

		// クラス一覧
		ClassNumDao cNumDao = new ClassNumDao();
		List<String> classList = cNumDao.filter(teacher.getSchool());

		// 入学年度一覧
		int currentYear = LocalDate.now().getYear();
		List<Integer> yearList = new ArrayList<>();
		for (int i = currentYear - 10; i <= currentYear + 10; i++) {
			yearList.add(i);
		}

		// 科目一覧
		SubjectDao subjectDao = new SubjectDao();
		List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

		// JSPへ送る
		req.setAttribute("class_num_set", classList);
		req.setAttribute("ent_year_set", yearList);
		req.setAttribute("subject_set", subjectList);

		// 入力値保持
		req.setAttribute("f4", studentNo);
		
		// フォワード
		req.getRequestDispatcher("test_list.jsp").forward(req, res);
	}
}