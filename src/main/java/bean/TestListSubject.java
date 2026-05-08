package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 成績一覧（科目別）情報を保持するクラス
 */
public class TestListSubject implements Serializable {

    // --- フィールド ---
    private int entYear;                        // 入学年度
    private String studentNo;                  // 学生番号
    private String studentName;                // 氏名
    private String classNum;                   // クラス番号
    private Map<Integer, Integer> points;      // 回数ごとの得点 (回, 点数)

    // --- コンストラクタ ---
    public TestListSubject() {
        // pointsがnullにならないよう初期化
        this.points = new HashMap<>();
    }

    // --- ゲッター / セッター ---

    public int getEntYear() {
        return entYear;
    }

    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public Map<Integer, Integer> getPoints() {
        return points;
    }

    public void setPoints(Map<Integer, Integer> points) {
        this.points = points;
    }

    // --- 追加のメソッド ---

    /**
     * 指定した回の得点を文字列で取得する
     * @param key 回数
     * @return 得点（存在しない場合はハイフンなど）
     */
    public String getPoint(int key) {
        Integer point = points.get(key);
        return (point == null) ? "-" : point.toString();
    }
    

    /**
     * 指定した回の得点を登録する
     * @param key 回数
     * @param value 得点
     */
    public void putPoint(int key, int value) {
        this.points.put(key, value);
    }
}