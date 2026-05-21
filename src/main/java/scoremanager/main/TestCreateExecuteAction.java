package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// セッション取得
		HttpSession session = req.getSession();

		// ログイン教員取得
		Teacher teacher = (Teacher)session.getAttribute("user");
		School school = teacher.getSchool();

		// パラメータ取得
		String studentNo = req.getParameter("studentNo");
		String subjectCd = req.getParameter("subjectCd");
		String numStr = req.getParameter("num");
		String pointStr = req.getParameter("point");

		Map<String, String> errors = new HashMap<>();
		
		// 入力必須チェック（どこか１箇所でも未入力ならTrue）
		if (
			studentNo == null || studentNo.isEmpty() ||
			subjectCd == null || subjectCd.isEmpty() ||
			numStr == null || numStr.isEmpty() ||
			pointStr == null || pointStr.isEmpty()
		) {

			errors.put("create", "未入力の項目があります");

			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();

			// フォーム情報再セット
			List<Student> studentList = studentDao.filter(teacher.getSchool(), true);
			List<Subject> subjectList = subjectDao.filter(teacher.getSchool());
			req.setAttribute("student_list", studentList);
			req.setAttribute("subject_list", subjectList);

			// 入力値保持
			req.setAttribute("studentNo", studentNo);
			req.setAttribute("subjectCd", subjectCd);
			req.setAttribute("num", numStr);
			req.setAttribute("point", pointStr);

			// エラー文セット
			req.setAttribute("errors", errors);

			// フォワード（差し戻し）
			req.getRequestDispatcher("test_create.jsp").forward(req, res);
			return;
		}
		
		int num = Integer.parseInt(numStr);
		int point = Integer.parseInt(pointStr);

		// DAO
		StudentDao studentDao = new StudentDao();
		SubjectDao subjectDao = new SubjectDao();
		TestDao testDao = new TestDao();

		// 学生取得
		Student student = studentDao.get(studentNo);
		String classNum = student.getClassNum();
		
		// 科目取得
		Subject subject = subjectDao.get(subjectCd, school);

		// 重複チェック
		Test testCheck = testDao.get(studentNo, subjectCd, num, school);

		// 既に存在する場合
		if (testCheck != null) {

			errors.put("create", "既に登録されている成績情報です");
			
			// studentテーブルに登録されている学生番号をリストで取得する
			List<Student> studentList = studentDao.filter(teacher.getSchool(), true);
			// subjectテーブルに登録されている科目をリストで取得する
			List<Subject> subjectList = subjectDao.filter(teacher.getSchool());
			// JSPへ送る
			req.setAttribute("student_list", studentList);
			req.setAttribute("subject_list", subjectList);
			
			// 入力値保持
			req.setAttribute("studentNo", studentNo);
			req.setAttribute("subjectCd", subjectCd);
			req.setAttribute("num", num);
			req.setAttribute("point", point);
			
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("test_create.jsp").forward(req, res);

			return;

		}

		// Test生成
		Test test = new Test();
		test.setStudent(student);
		test.setSubject(subject);
		test.setSchool(school);
		test.setNo(num);
		test.setPoint(point);
		test.setClassNum(classNum);

		// 登録
		testDao.save(test);

		// メッセージ添付
		req.setAttribute("message_crt", "成績情報は正常に登録されました。");
		
		// クラス一覧取得
		ClassNumDao cNumDao = new ClassNumDao();
		List<String> classList = cNumDao.filter(teacher.getSchool());
		
		// 入学年度リスト作成
		int currentYear = LocalDate.now().getYear();
		List<Integer> yearList = new ArrayList<>();
		for (int i = currentYear - 10; i <= currentYear + 10; i++) {
		    yearList.add(i);
		}
		
		// 科目一覧（DBから取得）
		List<Subject> subjectList = subjectDao.filter(teacher.getSchool());
		
		// JSPへ渡す
		req.setAttribute("class_num_set", classList);
		req.setAttribute("ent_year_set", yearList);
		req.setAttribute("subject_list", subjectList);
		
		// 完了画面
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);

	}

}