package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		String entYearStr = req.getParameter("entYear");
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String classNum = req.getParameter("classNum");
		
		Map<String, String> errors = new HashMap<>();

		// 学生番号チェック
		StudentDao sDao = new StudentDao();
		Student student_check = sDao.get(no);
		if (student_check != null) {
		    errors.put("no", "学生番号が重複しています");
		}
		
		// 入学年度チェック
		if (entYearStr == null || entYearStr.isEmpty() || "0".equals(entYearStr)) {
		    errors.put("entYear", "入学年度を選択してください");
		}
		
		if (!errors.isEmpty()) {
		    req.setAttribute("errors", errors);

		    // 入力値保持
		    req.setAttribute("entYear", entYearStr);
		    req.setAttribute("no", no);
		    req.setAttribute("name", name);
		    req.setAttribute("classNum", classNum);

			// クラス一覧再取得
			ClassNumDao cNumDao = new ClassNumDao();
			List<String> classList = cNumDao.filter(teacher.getSchool());
			req.setAttribute("class_num_set", classList);
		    
		    // 入学年度リスト再作成
		    int currentYear = LocalDate.now().getYear();
		    List<Integer> yearList = new ArrayList<>();
		    for (int i = currentYear - 10; i <= currentYear + 10; i++) {
		        yearList.add(i);
		    }
		    req.setAttribute("ent_year_set", yearList);
		    
		    req.getRequestDispatcher("student_create.jsp").forward(req, res);
		    return;
		}
		
		int entYear = Integer.parseInt(entYearStr);

		// Studentオブジェクト作成
		Student student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setSchool(teacher.getSchool());

		// 登録
		sDao.save(student);
		
		req.getRequestDispatcher("student_create_done.jsp").forward(req, res);
		
	}
}
