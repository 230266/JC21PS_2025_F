package jp.co.jc21ps.activity_management.entity;

//初期画面
public class ClubInfoRegisterEntity {
    // 部署ID
    private String leaderClubId;

    // 部署名
    private String clubName;

    // 部署説明
    private String clubDescription;

    public ClubInfoRegisterEntity() {

    }

    // public ClubInfoRegisterEntity(String leaderClubId, String clubName, String
    // clubDescription) {
    // this.leaderClubId = leaderClubId;s
    // this.clubName = clubName;
    // this.clubDescription = clubDescription;
    // }
    // 引数なしでnewしてあげる場合、↑ のコンストラクタはいらない
    // ClubInfoRegisterEntity entity = new ClubInfoRegisterEntity(null, null, null);

    public String getLeaderClubId() {
        return leaderClubId;
    }

    public void setLeaderClubId(String leaderClubId) {
        this.leaderClubId = leaderClubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }
}
