package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ユーザーデータを取得
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		//クラス一覧
		ClassNumDao cNumDao = new ClassNumDao();
		List<String> classList = cNumDao.filter(teacher.getSchool());
		
		//入学年度
		int currentYear = LocalDate.now().getYear();
		List<Integer>yearList = new ArrayList<>();
		for (int i = currentYear - 10; i <= currentYear + 10; i++) {
			yearList.add(i);
		}
		
		//科目
		SubjectDao subjectDao = new SubjectDao();
		List<Subject> subjectList = subjectDao.filter(teacher.getSchool());
		
		// JSPに渡す
		req.setAttribute("class_num_set", classList);
	    req.setAttribute("ent_year_set", yearList);
	    req.setAttribute("subject_set", subjectList);
		// 画面表示
		req.getRequestDispatcher("test_list.jsp").forward(req, res);
	}
}
