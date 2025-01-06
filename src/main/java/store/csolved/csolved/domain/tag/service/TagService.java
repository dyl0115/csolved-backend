package store.csolved.csolved.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.tag.Tag;
import store.csolved.csolved.domain.tag.mapper.TagMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService
{
    private final TagMapper tagMapper;

    @Transactional
    public void saveQuestionTags(Long questionId, String tagString)
    {
        List<Tag> tags = getOrCreateTags(tagString);
        tags.forEach(tag -> tagMapper.insertQuestionAndTag(questionId, tag.getId()));
    }

    @Transactional
    public void updateQuestionTags(Long questionId, String tagString)
    {
        tagMapper.deleteQuestionAndTagByQuestionId(questionId);

        List<Tag> tags = getOrCreateTags(tagString);
        tags.forEach(tag -> tagMapper.insertQuestionAndTag(questionId, tag.getId()));
    }

    private List<Tag> getOrCreateTags(String tagString)
    {
        List<String> tags = splitTagNames(tagString);
        List<Tag> existTags = filterExistTags(tags);
        List<Tag> newTags = filterNewTags(tags);

        // 새로운 태그만 DB에 저장합니다.
        newTags.forEach(tagMapper::insertTag);

        return mergeTags(existTags, newTags);
    }

    private List<Tag> mergeTags(List<Tag> existTags, List<Tag> newTags)
    {
        List<Tag> allTags = new ArrayList<>(existTags);
        allTags.addAll(newTags);
        return allTags;
    }

    private List<String> splitTagNames(String tagNames)
    {
        return Arrays.stream(tagNames.split(","))
                .map(String::toLowerCase)
                .toList();
    }

    private List<Tag> filterExistTags(List<String> tagNames)
    {
        return tagMapper.findTagsByNames(tagNames);
    }

    private List<Tag> filterNewTags(List<String> tagNames)
    {
        HashSet<String> tagNameSet = new HashSet<>(tagMapper.findAllTagNames());

        return tagNames.stream()
                .filter(name -> !tagNameSet.contains(name))
                .map(Tag::create)
                .toList();
    }
}
