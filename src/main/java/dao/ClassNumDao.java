package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;

public class ClassNumDao extends Dao {

    // 指定された学校に所属するクラス番号一覧を取得するメソッド
    public List<String> filter(School school) throws Exception {

        // クラス番号を格納するリストを初期化
        List<String> list = new ArrayList<>();

        // データベース接続を取得
        Connection con = getConnection();

        // SQL実行用オブジェクト
        PreparedStatement st = null;

        // 実行結果を格納するオブジェクト
        ResultSet rs = null;

        try {
            // studentテーブルから重複しないクラス番号を取得
            // schoolテーブルと結合し、指定された学校コードで絞り込む
            String sql = "SELECT DISTINCT s.class_num "
                       + "FROM student s "
                       + "JOIN school sc ON s.school_cd = sc.cd "
                       + "WHERE sc.cd = ?";

            // SQLをセット
            st = con.prepareStatement(sql);

            // プレースホルダに学校コードをバインド
            st.setString(1, school.getCd());

            // SQLを実行
            rs = st.executeQuery();

            // 結果を1行ずつ取得
            while (rs.next()) {
                // クラス番号をリストに追加
                list.add(rs.getString("class_num"));
            }

        } finally {
        	
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return list;
    }
}