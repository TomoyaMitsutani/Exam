package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Test;

public class TestDao extends Dao {
	
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {

		List<Test> list = new ArrayList<>();
	
		StudentDao studentDao = new StudentDao();
		SubjectDao subjectDao = new SubjectDao();
	
		try {
			
			while (rSet.next()) {
			
				Test test = new Test();
			
				// Student取得
				test.setStudent(studentDao.get(rSet.getString("student_no")));
			
				// Subject取得
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"), school));
			
				// その他
				test.setClassNum(rSet.getString("class_num"));
				test.setSchool(school);
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
			
				list.add(test);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	
			return list;
	}
		
	public List<Test> filter(School school, int entYear, String classNum, String subjectCd, int no) throws Exception {
	
		List<Test> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;
	
		String sql =
				"select * from test t " +
				"join student s on t.student_no = s.no " +
				"where t.school_cd = ? " +
				"and s.ent_year = ? " +
				"and s.class_num = ? " +
				"and t.subject_cd = ? " +
				"and t.no = ? " +
				"order by s.no asc";
		
		try {
			statement = connection.prepareStatement(sql);
		
			// バインド
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			statement.setString(4, subjectCd);
			statement.setInt(5, no);
		
			rSet = statement.executeQuery();
		
			list = postFilter(rSet, school);
	
		} finally {
			if (rSet != null) rSet.close();
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		
		return list;
	}

	public boolean save(Test test) throws Exception {

		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {

			// 既に存在するかチェック（UPDATEかINSERTか判断）
			String checkSql =
				"select count(*) from test " +
				"where student_no=? and subject_cd=? and school_cd=? and no=?";

			statement = connection.prepareStatement(checkSql);

			statement.setString(1, test.getStudent().getNo());
			statement.setString(2, test.getSubject().getCd());
			statement.setString(3, test.getSchool().getCd());
			statement.setInt(4, test.getNo());

			ResultSet rs = statement.executeQuery();

			rs.next();
			int count = rs.getInt(1);

			rs.close();
			statement.close();

			// UPDATE
			if (count > 0) {

				String updateSql =
					"update test set point=? " +
					"where student_no=? and subject_cd=? and school_cd=? and no=?";

				statement = connection.prepareStatement(updateSql);

				statement.setInt(1, test.getPoint());
				statement.setString(2, test.getStudent().getNo());
				statement.setString(3, test.getSubject().getCd());
				statement.setString(4, test.getSchool().getCd());
				statement.setInt(5, test.getNo());

				statement.executeUpdate();

			}
			// INSERT
			else {

				String insertSql =
					"insert into test " +
					"(student_no, subject_cd, school_cd, class_num, no, point) " +
					"values (?, ?, ?, ?, ?, ?)";

				statement = connection.prepareStatement(insertSql);

				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getCd());
				statement.setString(4, test.getClassNum());
				statement.setInt(5, test.getNo());
				statement.setInt(6, test.getPoint());

				statement.executeUpdate();
			}

			return true;

		} finally {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
	}
	
	public void delete(String studentNo, String subjectCd, int num) throws Exception {

		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(
				"delete from test "
				+ "where student_no = ? "
				+ "and subject_cd = ? "
				+ "and no = ?"
			);

			statement.setString(1, studentNo);
			statement.setString(2, subjectCd);
			statement.setInt(3, num);

			statement.executeUpdate();

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		}
	}
	
	public Test get(String studentNo, String subjectCd, int num, School school) throws Exception {

		Test test = null;

		Connection con = getConnection();
		PreparedStatement st = null;

		try {

			String sql =
				"SELECT * FROM test WHERE student_no = ? AND subject_cd = ? AND no = ?";

			st = con.prepareStatement(sql);

			st.setString(1, studentNo);
			st.setString(2, subjectCd);
			st.setInt(3, num);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				test = new Test();

				// Student取得
				StudentDao studentDao = new StudentDao();
				test.setStudent(studentDao.get(studentNo));

				// Subject取得
				SubjectDao subjectDao = new SubjectDao();
				test.setSubject(subjectDao.get(subjectCd, school));

				// その他
				test.setNo(rs.getInt("no"));
				test.setPoint(rs.getInt("point"));
				test.setClassNum(rs.getString("class_num"));

			}

		} finally {

			if (st != null) st.close();
			if (con != null) con.close();

		}

		return test;
	}

}
