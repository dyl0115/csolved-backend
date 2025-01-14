package store.csolved.csolved.domain.tag.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.tag.Tag;
import store.csolved.csolved.domain.tag.service.dto.QuestionTagNameRecord;
import store.csolved.csolved.domain.tag.service.dto.TagNameRecord;

import java.util.List;
import java.util.Map;

@Mapper
public interface TagMapper
{
    List<TagNameRecord> getTagsByQuestionId(Long questionId);

    @MapKey("questionId")
    Map<Long, QuestionTagNameRecord> getTagsByQuestionIdList(List<Long> questionIds);

    List<TagNameRecord> getTagsByNameIfExist(List<String> tagNames);

    void insertTags(List<Tag> tags);

    void insertQuestionTags(List<Tag> tags);

    void deleteQuestionTagByQuestionId(Long questionId);
}
