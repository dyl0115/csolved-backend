package store.csolved.csolved.domain.question.controller.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.question.domain.Question;
import store.csolved.csolved.domain.question.service.dto.record.QuestionDetailRecord;
import store.csolved.csolved.domain.tag.service.dto.TagNameRecord;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class QuestionCreateUpdateForm
{
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 2, max = 50, message = "제목은 최소 2글자에서 50자까지 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotNull(message = "실명/익명 여부를 선택해주세요.")
    private boolean anonymous;

    @NotNull(message = "카테고리를 하나 선택해주세요.")
    private Long categoryId;

    @NotEmpty(message = "태그는 반드시 하나 이상 있어야 합니다.")
    private List<String> tags;

    public static QuestionCreateUpdateForm empty()
    {
        return new QuestionCreateUpdateForm(
                "",
                "",
                false,
                null,
                new ArrayList<>());
    }

    public Question toQuestion(Long userId)
    {
        return Question.create(
                null,
                title,
                content,
                userId,
                anonymous,
                categoryId);
    }

    public static QuestionCreateUpdateForm from(QuestionDetailRecord question, List<TagNameRecord> tags)
    {
        return QuestionCreateUpdateForm.builder()
                .title(question.getTitle())
                .content(question.getContent())
                .anonymous(question.isAnonymous())
                .categoryId(question.getCategoryId())
                .tags(tags.stream()
                        .map(TagNameRecord::getName)
                        .toList())
                .build();
    }
}
