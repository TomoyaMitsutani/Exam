package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao{

	public Subject get(String cd, School school) throws Exception {

		Connection con = getConnection();
		PreparedStatement st = null;
		Subject subject = null;

		try {
			st = con.prepareStatement("select * from subject where cd=? and school_cd=?");
			st.setString(1, cd);
			st.setString(2, school.getCd());

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				subject = new Subject();
				subject.setCd(rs.getString("cd"));
				subject.setName(rs.getString("name"));
			}

		} finally {
			if (st != null) st.close();
			if (con != null) con.close();
		}

		return subject;
	}
	
	public List<Subject> filter(School school) throws Exception {

		List<Subject> list = new ArrayList<>();
		Connection con = getConnection();
		PreparedStatement st = null;

		try {
			st = con.prepareStatement("select * from subject where school_cd=?");
			st.setString(1, school.getCd());

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Subject subject = new Subject();
				subject.setCd(rs.getString("cd"));
				subject.setName(rs.getString("name"));
				list.add(subject);
			}

		} finally {
			if (st != null) st.close();
			if (con != null) con.close();
		}

		return list;
	}
	
	
}
