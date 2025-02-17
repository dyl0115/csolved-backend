package store.csolved.csolved.domain.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.common.Post;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends Post
{
    private Long views;
    private Long likes;
    private Long answerCount;
}
