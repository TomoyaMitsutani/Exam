package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	
		// セッション取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		School school = teacher.getSchool();
		
		// リクエストパラメータの取得
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");
		
		SubjectDao dao = new SubjectDao();
		
		
		// エラー格納用リスト作成
		Map<String, String> errors = new HashMap<>();
		
		// 科目存在確認
		Subject subject_check = dao.get(cd, teacher.getSchool());
		// 科目が存在しないならTrue
		if (subject_check == null) {
        	
			errors.put("subject_not_found", "科目が存在していません");
			
			// 入力値保持
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);
			
			req.setAttribute("errors", errors);
			
			// フォワード（差し戻し）
			req.getRequestDispatcher("subject_update.jsp").forward(req, res);
			return;
		}
		
		// 更新用Subjectオブジェクト作成
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(school);
		
		// 更新処理
		SubjectDao subjectDao = new SubjectDao();
		subjectDao.save(subject);
		
		// フォワード
		req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    }
}