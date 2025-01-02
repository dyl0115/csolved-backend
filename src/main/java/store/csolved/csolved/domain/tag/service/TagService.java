package store.csolved.csolved.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.csolved.csolved.domain.tag.Tag;
import store.csolved.csolved.domain.tag.mapper.TagMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService
{
    private final TagMapper tagMapper;

    public List<Tag> saveAndGetTags(String tagString)
    {
        List<String> tagNames = splitTagNames(tagString);

        List<Tag> oldTags = getAlreadyExistTags(tagNames);
        List<Tag> newTags = getNewTags(tagNames);

        newTags.forEach(tagMapper::insertTag);
        oldTags.addAll(newTags);
        return oldTags;
    }

    private List<String> splitTagNames(String tagNames)
    {
        return Arrays.stream(tagNames.split(","))
                .map(String::toLowerCase)
                .toList();
    }

    private List<Tag> getAlreadyExistTags(List<String> tagNames)
    {
        return tagMapper.findTagsByNames(tagNames);
    }

    private List<Tag> getNewTags(List<String> tagNames)
    {
        HashSet<String> tagNameSet = new HashSet<>(tagMapper.findAllTagNames());

        return tagNames.stream()
                .filter(name -> !tagNameSet.contains(name))
                .map(Tag::create)
                .toList();
    }
}
