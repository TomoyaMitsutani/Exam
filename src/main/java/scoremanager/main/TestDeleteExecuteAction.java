package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

public class TestDeleteExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		// パラメータ取得
		String studentNo = req.getParameter("studentNo");
		String subjectCd = req.getParameter("subjectCd");
		String numStr = req.getParameter("num");
		int num = Integer.parseInt(numStr);
		
		// Test生成
		Test test = new Test();
		
		// Subject生成
		Subject subject = new Subject();
		subject.setCd(subjectCd);
		
		test.setSubject(subject);
		test.setNo(num);
		
		// DAO
		TestDao tDao = new TestDao();
		tDao.delete(studentNo, subjectCd, num);
		
		// メッセージ添付
		req.setAttribute("message_dlt", "成績情報は正常に削除されました。");
		
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
		
		// JSPへ渡す
		req.setAttribute("class_num_set", classList);
		req.setAttribute("ent_year_set", yearList);
		req.setAttribute("subject_list", subjectList);
		
		// 元画面へ戻す
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}