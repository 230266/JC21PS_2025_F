package jp.co.jc21ps.activity_management.entity;


//初期表示画面のエンティティ
public class JoinRequestEntity {
    //ユーザーID
    private String userId;

    //部署ID
    private String clubId;

    //部署名
    private String clubName;

    //部署説明
    private String clubDescription;

    //デフォルトコンストラクタ
    public JoinRequestEntity() {

    }
    
    public JoinRequestEntity(String userId, String clubId, String clubName, String clubDescription) {
        this.userId = userId;
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubDescription = clubDescription;
    }

    //ユーザーID
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    //部署ID
    public void setClubId(String clubId) {
        this.clubId = clubId;
    }
    public String getClubId() {
        return clubId;
    }

    //部署名
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubName() {
        return clubName;
    }

    //部署説明
    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public String getClubDescription() {
        return clubDescription;
    }

}
