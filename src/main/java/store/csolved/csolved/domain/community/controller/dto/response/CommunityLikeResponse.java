package store.csolved.csolved.domain.community.controller.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommunityLikeResponse
{
    int status;
    String message;
    LocalDateTime timestamp;

    public static CommunityLikeResponse success()
    {
        return CommunityLikeResponse.builder()
                .status(200)
                .message("좋아요 증가 완료")
                .timestamp(LocalDateTime.now())
                .build();
    }
}
