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

		// フォワード
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// エラーメッセージ格納用Map
		Map<Integer, String> pointErrors = new HashMap<>();

		// パラメータ取得
		String subjectCd = req.getParameter("subjectCd");
		String classNum = req.getParameter("classNum");
		String numStr = req.getParameter("num");
		String entYearStr = req.getParameter("entYear");
		
		String[] studentNos = req.getParameterValues("studentNo");
		String[] pointStrs = req.getParameterValues("point");

		System.out.println("studentNos.length = " + studentNos.length);
		System.out.println("pointStrs.length = " + pointStrs.length);
		
		for (int i = 0; i < pointStrs.length; i++) {

			System.out.println(
				"pointStrs[" + i + "] = " + pointStrs[i]
			);
		}
		
		for (int i = 0; i < studentNos.length; i++) {

			System.out.println(
				"studentNos[" + i + "] = " + studentNos[i]
			);
		}
		
		// 必須入力チェック
		if (studentNos == null || pointStrs == null) {
			req.getRequestDispatcher("test_regist.jsp").forward(req, res);
			return;
		}

		int entYear = (entYearStr != null && !entYearStr.isEmpty())
				? Integer.parseInt(entYearStr)
				: 0;

		// 型変換
		int num = Integer.parseInt(numStr);

		boolean hasError = false;

		// バリデーションのみ
		for (int i = 0; i < studentNos.length; i++) {

			int point = Integer.parseInt(pointStrs[i]);

			if (point < 0 || point > 100) {
				pointErrors.put(i, "0～100の間で入力してください");
				hasError = true;
			}
		}

		// エラー時
		if (hasError) {

			// エラー文セット
			req.setAttribute("pointErrors", pointErrors);

			// クラス一覧取得
			ClassNumDao cNumDao = new ClassNumDao();
			List<String> classList = cNumDao.filter(teacher.getSchool());

			// 入学年度リスト取得
			int currentYear = LocalDate.now().getYear();
			List<Integer> yearList = new ArrayList<>();
			for (int i = currentYear - 10; i <= currentYear + 10; i++) {
				yearList.add(i);
			}

			// 科目一覧取得
			SubjectDao subjectDao = new SubjectDao();
			List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

			// フォーム情報再送
			req.setAttribute("class_num_set", classList);
			req.setAttribute("ent_year_set", yearList);
			req.setAttribute("subject_list", subjectList);

			// 検索結果再表示
			TestDao dao = new TestDao();
			List<Test> testList = dao.filter(teacher.getSchool(), entYear, classNum, subjectCd, num);
			req.setAttribute("testList", testList);

			// 入力保持
			req.setAttribute("entYear", entYear);
			req.setAttribute("classNum", classNum);
			req.setAttribute("subjectCd", subjectCd);
			req.setAttribute("num", numStr);

			// フォワード（差し戻し）
			req.getRequestDispatcher("test_regist.jsp").forward(req, res);
			return;
		}

		TestDao dao = new TestDao();

		// 登録処理
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

		// フォワード
		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
	}
}