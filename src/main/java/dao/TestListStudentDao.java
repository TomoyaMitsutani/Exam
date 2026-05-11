package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {

		List<TestListStudent> list = new ArrayList<>();

		while (rSet.next()) {

			TestListStudent test = new TestListStudent();

			// 科目名
			test.setSubjectName(rSet.getString("subject_name"));

			// 科目コード
			test.setSubjectCd(rSet.getString("subject_cd"));

			// 回数
			test.setNum(rSet.getInt("no"));

			// 点数
			test.setPoint(rSet.getInt("point"));

			list.add(test);
		}

		return list;
	}

	// 学生番号検索
	public List<TestListStudent> filter(String studentNo, School school) throws Exception {

		List<TestListStudent> list = new ArrayList<>();

		Connection con = getConnection();

		String sql =
			"select " +
			"s.name as subject_name, " +
			"s.cd as subject_cd, " +
			"t.no, " +
			"t.point " +
			"from test t " +
			"join subject s " +
			"on t.subject_cd = s.cd " +
			"and t.school_cd = s.school_cd " +
			"where t.student_no = ? " +
			"and t.school_cd = ? " +
			"order by s.cd asc, t.no asc";
		
		// SQLセット
		PreparedStatement st = con.prepareStatement(sql);
		// フィールドセット
		st.setString(1, studentNo);
		st.setString(2,school.getCd());
		// 実行
		ResultSet rSet = st.executeQuery();

		list = postFilter(rSet);

		st.close();
		con.close();

		return list;
	}

}