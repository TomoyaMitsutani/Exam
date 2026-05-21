package scoremanager.main;

import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestDeleteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		// セッション取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		// パラメータ取得
		String studentNo = req.getParameter("studentNo");
		String subjectCd = req.getParameter("subjectCd");
		int num = Integer.parseInt(req.getParameter("num"));

		// 削除対象取得
		TestDao dao = new TestDao();
		Test test = dao.get(studentNo, subjectCd, num, teacher.getSchool());

		// JSPへ渡す
		req.setAttribute("test", test);

		// フォワード
		req.getRequestDispatcher("test_delete.jsp").forward(req, res);
	}
}