package store.csolved.csolved.domain.tag.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import store.csolved.csolved.domain.tag.entity.Tag;

import java.util.List;
import java.util.Map;

@Mapper
public interface TagMapper
{
    void saveTags(List<Tag> tags);

    void saveQuestionTags(Long questionId, List<Tag> tags);

    List<Tag> getTagsByNames(List<String> tagNames);

    void deleteQuestionTag(Long questionId);
}
