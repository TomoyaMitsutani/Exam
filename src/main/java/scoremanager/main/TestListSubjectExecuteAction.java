package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
		
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		String entYearStr = req.getParameter("f1");
		String classNum = req.getParameter("f2");
		String subjectStr= req.getParameter("f3");
		
		// error
		
		int entYear = Integer.parseInt(entYearStr);	
		
		// Subject subject = new Subject();
		
		List<TestListSubject> testListSubject = null;
		
		TestListSubjectDao dao = new TestListSubjectDao();

		// 検索
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
		
	    // 入力値保持
		req.setAttribute("class_num_set", classList);
	    req.setAttribute("ent_year_set", yearList);
	    req.setAttribute("subject_set", subjectList);
		req.setAttribute("f1", entYearStr);
	    req.setAttribute("f2", classNum);
	    req.setAttribute("f3", subjectStr);
	    
	    // 画面表示
		req.getRequestDispatcher("test_list.jsp").forward(req, res);
		
	}	
		
}
		
	


