package store.csolved.csolved.domain.post.controller.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostLikeResponse
{
    int status;
    String message;
    LocalDateTime timestamp;

    public static PostLikeResponse success()
    {
        return PostLikeResponse.builder()
                .status(200)
                .message("좋아요 증가 완료")
                .timestamp(LocalDateTime.now())
                .build();
    }
}
