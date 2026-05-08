package bean;

import java.io.Serializable;

/**
 * 成績一覧（学生別）情報を保持するクラス
 */
public class TestListStudent implements Serializable {

    // --- フィールド ---
    private String subjectName; // 科目名
    private String subjectCd;   // 科目コード
    private int num;            // 回数
    private int point;          // 得点



    // --- ゲッター / セッター ---

    public String getSubjctName() { // 図の綴り（Subjct）に合わせています
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCd() {
        return subjectCd;
    }

    public void setSubjectCd(String subjectCd) {
        this.subjectCd = subjectCd;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
