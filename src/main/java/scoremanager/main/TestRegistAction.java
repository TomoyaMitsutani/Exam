package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class TestRegistAction extends Action {
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		String entYearStr = req.getParameter("entYear");
		String classNum = req.getParameter("classNum");
		String subjectCd = req.getParameter("subjectCd");
		String numStr = req.getParameter("num");
		
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
		SubjectDao subjectDao = new SubjectDao();
		List<Subject> subjectList = subjectDao.filter(teacher.getSchool());
		
		// エラーメッセージ格納用Map
		Map<String, String> errors = new HashMap<>();
		
		// 「検索ボタンが押されたか」の判定
		boolean isSearch = req.getParameter("search") != null;
		
		// 「未入力があるか」の判定
		// 1つでも未入力があれば true
		boolean isEmpty =
			entYearStr == null || entYearStr.isEmpty() ||
			classNum == null || classNum.isEmpty() ||
			subjectCd == null || subjectCd.isEmpty() ||
			numStr == null || numStr.isEmpty();

		// 入力チェック（バリデーション）
		// 「検索された」かつ「未入力がある」場合のみエラー
		// （初回表示ではエラーが出ないようにするための処置）
		if (isSearch && isEmpty) {
			
			errors.put("search", "入学年度とクラスと科目と回数を選択してください");
			req.setAttribute("errors", errors);
			
			req.setAttribute("class_num_set", classList);
			req.setAttribute("ent_year_set", yearList);
			req.setAttribute("subject_list", subjectList);
			
			// 入力値の保持
			req.setAttribute("entYear", entYearStr);
			req.setAttribute("classNum", classNum);
			req.setAttribute("subjectCd", subjectCd);
			req.setAttribute("num", numStr);
			
			req.getRequestDispatcher("test_regist.jsp").forward(req, res);
			return;

		}
		
		// 型変換
		Integer entYear = null;
		Integer num = null;
		
		// nullや空文字でなければ数値変換
		if (entYearStr != null && !entYearStr.isEmpty()) {
			entYear = Integer.parseInt(entYearStr);
		}
		
		if (numStr != null && !numStr.isEmpty()) {
			num = Integer.parseInt(numStr);
		}
		
		// 検索結果格納用
		List<Test> testList = null;
		
		// DAOによる検索処理
		
		// 全て入力されている場合のみ検索を実行（上で未入力を弾いているけど一応分岐）
		if (!isEmpty) {
		
			// DAO生成
			TestDao dao = new TestDao();
		
			// 条件を指定して検索
			testList = dao.filter(teacher.getSchool(), entYear, classNum, subjectCd, num);
			
		}
		
		// JSPへ渡す
		req.setAttribute("class_num_set", classList);
		req.setAttribute("ent_year_set", yearList);
		req.setAttribute("subject_list", subjectList);
		
		// 検索結果
		req.setAttribute("testList", testList);
		
		// 入力値の保持
		req.setAttribute("entYear", entYearStr);
		req.setAttribute("classNum", classNum);
		req.setAttribute("subjectCd", subjectCd);
		req.setAttribute("num", numStr);
		
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}