package store.csolved.csolved.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.tag.entity.Tag;
import store.csolved.csolved.domain.tag.mapper.TagMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TagService
{
    private final TagMapper tagMapper;

    // 태그 저장
    @Transactional
    public void saveTags(Long questionId, List<Tag> tags)
    {
        List<Tag> existTags = filterExistTags(tags);
        if (!existTags.isEmpty())
        {
            tagMapper.saveQuestionTags(questionId, existTags);
        }

        List<Tag> newTags = filterNewTags(tags);
        if (!newTags.isEmpty())
        {
            tagMapper.saveTags(newTags);
            tagMapper.saveQuestionTags(questionId, newTags);
        }
    }

    // 질문글 업데이트 시 태그도 업데이트
    @Transactional
    public void updateTags(Long questionId, List<Tag> tags)
    {
        tagMapper.deleteQuestionTag(questionId);
        saveTags(questionId, tags);
    }

    private List<String> extractTagNames(List<Tag> tags)
    {
        return tags.stream()
                .map(Tag::getName)
                .toList();
    }

    private List<Tag> filterExistTags(List<Tag> tags)
    {
        List<String> tagNames = extractTagNames(tags);
        return tagMapper.getTagsByNames(tagNames);
    }

    private List<Tag> filterNewTags(List<Tag> tags)
    {
        List<String> tagNames = extractTagNames(tags);

        List<Tag> existTags = filterExistTags(tags);
        Set<String> existTagNames = new HashSet<>(extractTagNames(existTags));

        return tagNames.stream()
                .filter(name -> !existTagNames.contains(name))
                .map(Tag::from)
                .toList();
    }
}
