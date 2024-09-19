package jp.co.jc21ps.activity_management.dto;

//初期表示画面
public class JoinRequestDto {

    // ユーザーID
    private String userId;

    // 部署ID
    private String clubId;

    // 部署名
    private String clubName;

    // 部署説明
    private String clubDescription;

    public JoinRequestDto() {

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public String getClubDescription() {
        return clubDescription;
    }

}
