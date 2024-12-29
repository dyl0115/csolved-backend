package store.csolved.csolved.domain.tag.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.tag.Tag;

@Mapper
public interface TagMapper
{
    void insertTag(Tag tag);

    Tag findTagByName(String name);

    void insertQuestionAndTag(Long questionId, Long tagId);
}
