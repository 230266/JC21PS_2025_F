// package jp.co.jc21ps.activity_management.service;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.MessageSource;
// import org.springframework.data.annotation.AccessType;
// import org.springframework.data.relational.core.sql.Join;

// import jp.co.jc21ps.activity_management.entity.JoinRequestEntity;
// import jp.co.jc21ps.activity_management.repository.JoinRequestRepository;
// import jp.co.jc21ps.activity_management.repository.RegisterActivityRepository;
// import jp.co.jc21ps.dto.JoinRequestDto;

// public class JoinRequestService {
//     private final JoinRequestRepository joinRequestRepository;
//     @Autowired
//     private MessageSource messageSource;
    
//     //リポジトリをセットする
//      public JoinRequestService(JoinRequestRepository joinRequestRepository) {
//         this.joinRequestRepository = joinRequestRepository;
//     }

//     //dto型のメソッドで返す
//     public JoinRequestDto findRequest(JoinRequestDto joinRequestDto) {

//         //エンティティのインスタンス化
//         JoinRequestEntity joinRequestEntity = new JoinRequestEntity();

//         //エンティティのUserIdを詰め替える
//         joinRequestEntity.setUserId(joinRequestDto.getUserId());

//         //リポジトリのメソッドにエンティティに詰め替えたuserIdを渡す
//         List<JoinRequestEntity> joinRequestList = joinRequestRepository.getJoinRequestByUserId(joinRequestEntity);

//         //部署名、部署説明をdtoに渡す
//         joinRequestDto.setClubName(joinRequestList.getClubName());
//         joinRequestDto.setClubDescription(joinRequestList.getClubDescription());
//         return joinRequestDto;
//     }
// }
