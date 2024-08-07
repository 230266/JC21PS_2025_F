// package jp.co.jc21ps.activity_management.repository;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;

// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.stereotype.Repository;

// import jp.co.jc21ps.activity_management.entity.JoinRequestEntity;

// //Db接続クラス
// @Repository
// public class JoinRequestRepository {
//     private final JdbcTemplate jdbcTemplate;

//     public JoinRequestRepository(JdbcTemplate jdbcTemplate) {
//         this.jdbcTemplate = jdbcTemplate;
//     }

//     //初期表示
//     public List<JoinRequestEntity> getJoinRequestByUserId(JoinRequestEntity userId){
        
//         //取得したUserIdをエンティティに変換
//         JoinRequestEntity entity = new JoinRequestEntity();
        
//         String sql = """
//                 SELECT * FROM 
//                     mst_club 
//                 WHERE 
//                     club_id NOT IN 
//                         (SELECT club_id 
//                          FROM trn_join_request 
//                          WHERE user_id = ?) 
//                     AND 
//                     club_id NOT IN 
//                         (SELECT club_id 
//                          FROM trn_club_member 
//                          WHERE user_id = ?)
//                 """;

//                  List<Map<String, Object>> joinRequestList = jdbcTemplate.queryForList(sql, userId.getUserId(), userId.getUserId());
//                  List<JoinRequestEntity> joinRequestEntities = new ArrayList<>();

//                  //空だった場合
//                  if(joinRequestList.isEmpty()){
//                     return joinRequestEntities;
//                  }

//                  for(Map<String, Object> joinRequest : joinRequestList){
//                     JoinRequestEntity joinRequestEntity = new JoinRequestEntity();
                 
//                     //エンティティにセットする
//                     //部署ID
//                     joinRequestEntity.setClubId((String)joinRequest.get("club_id"));
//                     //部署名
//                     joinRequestEntity.setClubName((String)joinRequest.get("club_name"));
//                     //部署説明
//                     joinRequestEntity.setClubDescription((String)joinRequest.get("club_description"));

//                     joinRequestEntities.add(joinRequestEntity);

//                  }
//                  return joinRequestEntities;
//     }

//     //申請処理
//     public JoinRequestSaveEntity 
// }