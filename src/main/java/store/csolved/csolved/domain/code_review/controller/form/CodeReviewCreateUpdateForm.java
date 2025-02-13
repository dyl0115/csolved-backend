package store.csolved.csolved.domain.code_review.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.code_review.CodeReview;
import store.csolved.csolved.domain.tag.Tag;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CodeReviewCreateUpdateForm
{
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 2, max = 50, message = "제목은 최소 2글자에서 50자까지 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotBlank(message = "깃허브 주소를 입력해주세요.")
    private String githubUrl;

    @NotNull
    private Long authorId;

    @NotNull(message = "실명/익명 여부를 선택해주세요.")
    private boolean anonymous;

    @NotNull(message = "카테고리를 하나 선택해주세요.")
    private Long categoryId;

    @NotEmpty(message = "태그는 반드시 하나 이상 있어야 합니다.")
    private String tags;

    public static CodeReviewCreateUpdateForm empty()
    {
        return CodeReviewCreateUpdateForm.builder()
                .anonymous(false)
                .build();
    }

    public static CodeReviewCreateUpdateForm from(CodeReview codeReview)
    {
        return CodeReviewCreateUpdateForm.builder()
                .title(codeReview.getTitle())
                .content(codeReview.getContent())
                .authorId(codeReview.getAuthorId())
                .anonymous(codeReview.isAnonymous())
                .categoryId(codeReview.getCategoryId())
                .tags(codeReview.getTags().stream()
                        .map(Tag::getName)
                        .collect(Collectors.joining(",")))
                .build();
    }

    public CodeReview getCodeReview()
    {
        return CodeReview.builder()
                .title(title)
                .content(content)
                .githubUrl(githubUrl)
                .authorId(authorId)
                .anonymous(anonymous)
                .categoryId(categoryId)
                .views(0L)
                .likes(0L)
                .answerCount(0L)
                .build();
    }

    public List<Tag> getTagList()
    {
        return Arrays.stream(tags.split(","))
                .map(Tag::from)
                .toList();
    }
}
