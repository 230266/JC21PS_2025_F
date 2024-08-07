package jp.co.jc21ps.activity_management.repository;

import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import jp.co.jc21ps.activity_management.entity.RegisterActivityEntity;
import jp.co.jc21ps.activity_management.entity.RegisterActivitySaveEntity;

//DB接続クラス
@Repository
public class RegisterActivityRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String ACTIVITY_ID_PREFIX = "A";

    public RegisterActivityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //クラブIDから活動情報を取得するメソッド
    public RegisterActivityEntity getActivityByClubId(RegisterActivityEntity clubId) {

        //取得したデータをRegisterActivityEntityに変換、エンティティに引数なしのコンストラクタ作ってる
        RegisterActivityEntity entity = new RegisterActivityEntity();

        String sql = """
                SELECT
                    club_name
                FROM
                    mst_club
                WHERE
                    club_id = ?
                """; 

            //clubIdに対応する活動情報を取得　
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, clubId.getClubId());

            //エンティティにclubNameをセットする
            entity.setClubName((String) result.get("club_name")); //List場合は、ListにEntityをaddしてあげて返す。List.add(Entity)
            return entity;                                            //Listで返さない場合は、Listにaddせずに返す。
    }

    //入力された値を登録するメソッド
    public void insertActivity(RegisterActivitySaveEntity activitySaveEntity) {
        String sql = """
                INSERT INTO 
                    trn_activity (activity_id, 
                                  club_id, 
                                  activity_name, 
                                  activity_place, 
                                  activity_start_time, 
                                  activity_end_time, 
                                  activity_description, 
                                  max_participant) 
                VALUES (?,?,?,?,?,?,?,?)
                """;
            
            //パラメータの設定  
            Object[] paramList = {
                activitySaveEntity.getActivityId(),
                activitySaveEntity.getClubId(),
                activitySaveEntity.getActivityName(),
                activitySaveEntity.getActivityPlace(),
                activitySaveEntity.getActivityStartTime(), //LocalDateTime型
                activitySaveEntity.getActivityEndTime(),   //LocalDateTime型
                activitySaveEntity.getActivityDescription(),
                activitySaveEntity.getMaxParticipant(), //int型
            };

            //DBに挿入
            jdbcTemplate.update(sql, paramList);
    }

    //シーケンスからactivityIdを取得するメソッド
    public String getNextActivityId() throws Exception {
        
        //SQLクエリの定義
        String sql = "SELECT nextval('activity_id_sequence') AS id";
        
        try{
            //クエリを実行して次のシーケンスの値を取得
            Integer sequenceValue = jdbcTemplate.queryForObject(sql, Integer.class);

            if (sequenceValue != null){
                //フォーマット指定
                return ACTIVITY_ID_PREFIX + String.format("%07d", sequenceValue);
            }else{
                throw new Exception();
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

}
