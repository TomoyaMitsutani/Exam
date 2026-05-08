package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {
	
	
	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
	    // リストを初期化
	    List<TestListSubject> list = new ArrayList<>();

	    try {
	        // 学生番号ごとにまとめるためのマップを初期化
	        Map<String, TestListSubject> map = new LinkedHashMap<>();

	        // リザルトセットを全件走査
	        while (rSet.next()) {

	            // 学生番号を取得
	            String studentNo = rSet.getString("student_no");

	            // 初めての学生番号なら TestListSubject を生成
	            if (!map.containsKey(studentNo)) {

	                // インスタンスを初期化
	                TestListSubject tls = new TestListSubject();

	                // インスタンスに検索結果をセット
	                tls.setEntYear(rSet.getInt("ent_year"));          // 入学年度
	                tls.setStudentNo(studentNo);                      // 学生番号
	                tls.setStudentName(rSet.getString("name"));       // 氏名
	                tls.setClassNum(rSet.getString("class_num"));     // クラス番号
	                tls.setPoints(new LinkedHashMap<>());             // 回数ごとの得点マップ

	                // マップに追加
	                map.put(studentNo, tls);
	            }

	            // 既存の TestListSubject を取得
	            TestListSubject tls = map.get(studentNo);

	            // 回数と点数を取得
	            int no = rSet.getInt("no");     // テスト回数
	            int point = rSet.getInt("point");     // 得点
	            System.out.println(studentNo + "の" + no + "回目に点数" + point + "を格納しました");
	            // マップに追加
	            tls.getPoints().put(no, point);
	        }

	        // マップの値をリストに変換
	        list.addAll(map.values());

	    } catch (SQLException | NullPointerException e) {
	        e.printStackTrace();
	    }

	    return list;
	}


    // を取得するメソッド
    public List<TestListSubject> filter(int entYear,String classNum,String subject,School school) throws Exception {
    
    	// リストを初期化
    			List<TestListSubject> list = new ArrayList<>();
    			// コネクションを確立
    			Connection connection = getConnection();
    			// プリペアードステートメント
    			PreparedStatement statement = null;
    			// リザルトセット
    			ResultSet rSet = null;
    			
    	 String sql = "select * from test t "+
    			 "join student s on t.student_no = s.no "+
    			 "where t.school_cd = ? "+
    	         "and s.ent_year = ? "+
    			 "and s.class_num = ? "+
    	         "and t.subject_cd = ? "+
    			 "order by s.no asc";

 		try {
 			// プリペアードステートメントにSQL文にセット
 			statement = connection.prepareStatement(sql);
 			// プリペアードステートメントに学校コードをバインド
 			statement.setString(1, school.getCd());
 			// プリペアードステートメントに入学年度をバインド
 			statement.setInt(2, entYear);
 			// プリペアードステートメントにクラス番号をバインド
 			statement.setString(3, classNum);
 			
 			statement.setString(4, subject);
 			// プリペアードステートメントを実行
 			rSet = statement.executeQuery();
 			// リストへの格納処理を実行
 			list = postFilter(rSet);
 		} catch (Exception e) {
 			throw e;
 		} finally {
 			// プリペアードステートメントを閉じる
 			if (statement != null) {
 				try {
 					statement.close();
 				} catch (SQLException sqle) {
 					throw sqle;
 				}
    	  
 			
    }
   
 		}
 		
    return list;
    }
    


}