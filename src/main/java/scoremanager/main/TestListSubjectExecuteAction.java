package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		// セッション取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		String entYearStr = req.getParameter("f1");
		String classNum = req.getParameter("f2");
		String subjectStr= req.getParameter("f3");
		
		// エラーメッセージ格納用Map
		Map<String, String> errors = new HashMap<>();

		// 入力必須チェック（どれか１項目でも未入力ならTrue）
		if (
			entYearStr == null || entYearStr.equals("0") ||
			classNum == null || classNum.equals("0") ||
			subjectStr == null || subjectStr.equals("0")
		) {

			// エラー文セット
			errors.put("error_1", "入学年度とクラスと科目を選択してください");
			req.setAttribute("errors", errors);

			// クラス一覧
			ClassNumDao cNumDao = new ClassNumDao();
			List<String> classList = cNumDao.filter(teacher.getSchool());

			// 入学年度
			int currentYear = LocalDate.now().getYear();
			List<Integer> yearList = new ArrayList<>();
			for (int i = currentYear - 10; i <= currentYear + 10; i++) {
				yearList.add(i);
			}

			// 科目一覧
			SubjectDao subjectDao = new SubjectDao();
			List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

			// JSPへ再セット
			req.setAttribute("class_num_set", classList);
			req.setAttribute("ent_year_set", yearList);
			req.setAttribute("subject_set", subjectList);

			// 入力保持
			req.setAttribute("f1", entYearStr);
			req.setAttribute("f2", classNum);
			req.setAttribute("f3", subjectStr);

			// フォワード（差し戻し）
			req.getRequestDispatcher("test_list.jsp").forward(req, res);

			return;
		}
		
		// 型変換
		int entYear = Integer.parseInt(entYearStr);	
		
		// 検索処理
		List<TestListSubject> testListSubject = null;
		TestListSubjectDao dao = new TestListSubjectDao();
		testListSubject = dao.filter(entYear, classNum, subjectStr, teacher.getSchool());

		// 検索結果をJSPへ送る
	    req.setAttribute("testListSubject", testListSubject);
		
		// クラス一覧
		ClassNumDao cNumDao = new ClassNumDao();
		List<String> classList = cNumDao.filter(teacher.getSchool());
		
		// 入学年度
		int currentYear = LocalDate.now().getYear();
		List<Integer>yearList = new ArrayList<>();
		for (int i = currentYear - 10; i <= currentYear + 10; i++) {
			yearList.add(i);
		}
		
		// 科目
		SubjectDao subjectDao = new SubjectDao();
		List<Subject> subjectList = subjectDao.filter(teacher.getSchool());
		
		// フォーム情報再送
		req.setAttribute("class_num_set", classList);
	    req.setAttribute("ent_year_set", yearList);
	    req.setAttribute("subject_set", subjectList);
		
	    // 入力値保持
		req.setAttribute("f1", entYearStr);
	    req.setAttribute("f2", classNum);
	    req.setAttribute("f3", subjectStr);
	    
	    // フォワード
		req.getRequestDispatcher("test_list.jsp").forward(req, res);
		
	}	
		
}