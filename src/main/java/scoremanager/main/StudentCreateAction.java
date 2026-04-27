package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		// クラス一覧取得
		ClassNumDao cNumDao = new ClassNumDao();
		List<String> classList = cNumDao.filter(teacher.getSchool());

	    // 入学年度リスト作成
	    int currentYear = LocalDate.now().getYear();
	    List<Integer> yearList = new ArrayList<>();
	    for (int i = currentYear - 10; i <= currentYear + 10; i++) {
	        yearList.add(i);
	    }
	    
		// JSPに渡す
		req.setAttribute("class_num_set", classList);
	    req.setAttribute("ent_year_set", yearList);
		
		// 画面表示
		req.getRequestDispatcher("student_create.jsp").forward(req, res);
		
	}
}