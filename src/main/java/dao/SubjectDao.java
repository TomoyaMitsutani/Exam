package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {
	
	// ─────────────────────────────────────────────
    // 1件取得
    // ─────────────────────────────────────────────
	public Subject get(String cd, School school) throws Exception {
		
		Subject subject = null;
		Connection con = getConnection();
		PreparedStatement st = null;
		
		try {
			st = con.prepareStatement("SELECT * FROM subject WHERE cd = ? and school_cd = ?");
			st.setString(1, cd);
			st.setString(2, school.getCd());
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				subject = new Subject();
				subject.setCd(rs.getString("cd"));
				subject.setName(rs.getString("name"));
			}
			
		} finally {
			if(st != null) st.close();
			if(con != null) con.close();
		}
		
		return subject;
		
	}
	
	// ─────────────────────────────────────────────
    // 登録・更新（upsert）
    // ─────────────────────────────────────────────
	public void save(Subject subject) throws Exception {
		
		Connection con = getConnection();
		PreparedStatement st = null;
		
		boolean exists = (get(subject.getCd(), subject.getSchool()) != null);
		
		try {
			if(exists) {
				st = con.prepareStatement("UPDATE subject SET name = ? WHERE cd = ?");
				st.setString(1, subject.getName());
				st.setString(2, subject.getCd());
			} else {
				st = con.prepareStatement("INSERT INTO subject (cd, name, school_cd) VALUES (?, ?, ?)");
				st.setString(1, subject.getCd());
				st.setString(2, subject.getName());
				st.setString(3, subject.getSchool().getCd());
			}
			
			st.executeUpdate();
			
		} finally {
			if(st != null) st.close();
			if(con != null) con.close();
		}
	}
	
	// ─────────────────────────────────────────────
    // 絞り込み（filter）
    // ─────────────────────────────────────────────
	public List<Subject> filter(School school) throws Exception {
		
		List<Subject> list = new ArrayList<>();
		Connection con = getConnection();
		PreparedStatement st = null;
		
		try {
			st = con.prepareStatement("SELECT * FROM subject WHERE school_cd = ?");
			st.setString(1, school.getCd());
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Subject subject = new Subject();
				subject.setCd(rs.getString("cd"));
				subject.setName(rs.getString("name"));
				list.add(subject);
			}
		
		} finally {
			if(st != null) st.close();
			if(con != null) con.close();		
		}
		
		return list;
	}

	// ─────────────────────────────────────────────
    // 削除
    // ─────────────────────────────────────────────
	public void delete(Subject subject) throws Exception {

	    Connection con = getConnection();
	    PreparedStatement st = null;

	    boolean exists = (get(subject.getCd(), subject.getSchool()) != null);

	    try {
	        if (exists) {
	            st = con.prepareStatement(
	                "DELETE FROM subject WHERE cd = ?");
	            st.setString(1, subject.getCd());

	            st.executeUpdate();
	        }

	    } finally {
	        if (st != null) st.close();
	        if (con != null) con.close();
	    }
    }
}
