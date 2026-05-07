package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		Map<Integer, String> pointErrors = new HashMap<>();

		// ===== 入力取得 =====
		String subjectCd = req.getParameter("subjectCd");
		String classNum = req.getParameter("classNum");
		String numStr = req.getParameter("num");
		String entYearStr = req.getParameter("entYear");

		String[] studentNos = req.getParameterValues("studentNo");
		String[] pointStrs = req.getParameterValues("point");

		if (studentNos == null || pointStrs == null) {
			req.getRequestDispatcher("test_regist.jsp").forward(req, res);
			return;
		}

		int entYear = (entYearStr != null && !entYearStr.isEmpty())
				? Integer.parseInt(entYearStr)
				: 0;

		int num = Integer.parseInt(numStr);

		boolean hasError = false;

		// ===== バリデーションのみ =====
		for (int i = 0; i < studentNos.length; i++) {

			int point = Integer.parseInt(pointStrs[i]);

			if (point < 0 || point > 100) {
				pointErrors.put(i, "0～100の間で入力してください");
				hasError = true;
			}
		}

		// ===== エラー時 =====
		if (hasError) {

			req.setAttribute("pointErrors", pointErrors);

			// マスタ再取得
			ClassNumDao cNumDao = new ClassNumDao();
			List<String> classList = cNumDao.filter(teacher.getSchool());

			int currentYear = LocalDate.now().getYear();
			List<Integer> yearList = new ArrayList<>();
			for (int i = currentYear - 10; i <= currentYear + 10; i++) {
				yearList.add(i);
			}

			SubjectDao subjectDao = new SubjectDao();
			List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

			req.setAttribute("class_num_set", classList);
			req.setAttribute("ent_year_set", yearList);
			req.setAttribute("subject_list", subjectList);

			// 検索結果再表示
			TestDao dao = new TestDao();
			List<Test> testList = dao.filter(
					teacher.getSchool(),
					entYear,
					classNum,
					subjectCd,
					num
			);

			req.setAttribute("testList", testList);

			// 入力保持
			req.setAttribute("entYear", entYear);
			req.setAttribute("classNum", classNum);
			req.setAttribute("subjectCd", subjectCd);
			req.setAttribute("num", numStr);

			req.getRequestDispatcher("test_regist.jsp").forward(req, res);
			return;
		}

		// ===== 正常時：保存 =====
		TestDao dao = new TestDao();

		for (int i = 0; i < studentNos.length; i++) {

			int point = Integer.parseInt(pointStrs[i]);

			Student student = new Student();
			student.setNo(studentNos[i]);

			Subject subject = new Subject();
			subject.setCd(subjectCd);

			Test test = new Test();
			test.setStudent(student);
			test.setSubject(subject);
			test.setClassNum(classNum);
			test.setNo(num);
			test.setPoint(point);
			test.setSchool(teacher.getSchool());

			dao.save(test);
		}

		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
	}
}