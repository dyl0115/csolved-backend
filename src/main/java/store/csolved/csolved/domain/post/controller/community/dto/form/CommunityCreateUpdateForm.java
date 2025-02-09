package store.csolved.csolved.domain.post.controller.community.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.entity.Post;
import store.csolved.csolved.domain.tag.entity.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static store.csolved.csolved.domain.post.entity.PostType.COMMUNITY;

@Getter
@Builder
public class CommunityCreateUpdateForm
{
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 2, max = 50, message = "제목은 최소 2글자에서 50자까지 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotNull
    private Long authorId;

    @NotNull(message = "실명/익명 여부를 선택해주세요.")
    private boolean anonymous;

    @NotNull(message = "카테고리를 하나 선택해주세요.")
    private Long categoryId;

    @NotEmpty(message = "태그는 반드시 하나 이상 있어야 합니다.")
    private String tags;

    public static CommunityCreateUpdateForm from(Post post)
    {
        return CommunityCreateUpdateForm.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .authorId(post.getAuthorId())
                .anonymous(post.isAnonymous())
                .categoryId(post.getCategoryId())
                .tags(post.getTags().stream()
                        .map(Tag::getName)
                        .collect(Collectors.joining(",")))
                .build();
    }

    public static CommunityCreateUpdateForm empty()
    {
        return CommunityCreateUpdateForm.builder()
                .anonymous(false)
                .build();
    }

    public Post getCommunity()
    {
        return Post.builder()
                .postType(COMMUNITY.getCode())
                .title(title)
                .content(content)
                .authorId(authorId)
                .anonymous(anonymous)
                .views(0L)
                .likes(0L)
                .answerCount(0L)
                .categoryId(categoryId)
                .build();
    }

    public List<Tag> getTagList()
    {
        return Arrays.stream(tags.split(","))
                .map(name -> Tag.builder().name(name).build())
                .toList();
    }
}