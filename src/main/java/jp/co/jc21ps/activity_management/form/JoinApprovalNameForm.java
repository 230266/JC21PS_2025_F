package jp.co.jc21ps.activity_management.form;

import java.util.List;

public class JoinApprovalNameForm {

    // 参加承認リスト
    private List<JoinApprovalForm> joinApprovalList;

    // 部署名
    private String clubName;

    public JoinApprovalNameForm() {

    }

    public List<JoinApprovalForm> getJoinApprovalList() {
        return joinApprovalList;
    }

    public void setJoinApprovalList(List<JoinApprovalForm> joinApprovalList) {
        this.joinApprovalList = joinApprovalList;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

}
