package store.csolved.csolved.domain.tag.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.tag.Tag;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface TagMapper
{
    void insertTag(Tag tag);

    List<Tag> findTagsByNames(List<String> name);

    List<String> findAllTagNames();
}
