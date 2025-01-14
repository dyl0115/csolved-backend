package store.csolved.csolved.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.question.controller.dto.form.QuestionCreateUpdateForm;
import store.csolved.csolved.domain.tag.Tag;
import store.csolved.csolved.domain.tag.dto.TagNameDTO;
import store.csolved.csolved.domain.tag.mapper.TagMapper;
import store.csolved.csolved.domain.tag.service.dto.QuestionTagNameRecord;
import store.csolved.csolved.domain.tag.service.dto.TagNameRecord;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagService
{
    private final TagMapper tagMapper;

    public Map<Long, List<TagNameDTO>> getTags(List<Long> questionIds)
    {
        Map<Long, QuestionTagNameRecord> tags = tagMapper.getTagsByQuestionIdList(questionIds);
        return TagNameDTO.from(tags);
    }

    public List<TagNameDTO> getTags(Long questionId)
    {
        List<TagNameRecord> tags = tagMapper.getTagsByQuestionId(questionId);
        return TagNameDTO.from(tags);
    }

    // 태그 저장
    @Transactional
    public void saveTags(Long questionId, QuestionCreateUpdateForm form)
    {
        List<Tag> tags = createTags(questionId, form);

        List<Tag> existTags = filterExistTags(tags);
        tagMapper.insertQuestionTags(existTags);

        List<Tag> newTags = filterNewTags(tags);
        tagMapper.insertTags(newTags);
        tagMapper.insertQuestionTags(newTags);
    }

    // 질문글 업데이트 시 태그도 업데이트
    @Transactional
    public void updateTags(Long questionId, QuestionCreateUpdateForm form)
    {
        tagMapper.deleteQuestionTagByQuestionId(questionId);
        saveTags(questionId, form);
    }

    private List<Tag> createTags(Long questionId, QuestionCreateUpdateForm form)
    {
        return form.getTags().stream()
                .map(tagName -> Tag.create(questionId, tagName))
                .toList();
    }

    private List<Tag> filterExistTags(List<Tag> tags)
    {
        List<String> tagNames = tags.stream()
                .map(Tag::getName)
                .toList();

        return tagMapper.getTagsByNameIfExist(tagNames).stream()
                .map(tag -> Tag.create(tag.getId(), tag.getName()))
                .toList();
    }

    private List<Tag> filterNewTags(List<Tag> tags)
    {
        List<Tag> existTags = filterExistTags(tags);

        Set<String> existTagNames = existTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        return tags.stream()
                .filter(tag -> !existTagNames.contains(tag.getName()))
                .toList();
    }
}
