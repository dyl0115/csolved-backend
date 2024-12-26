package store.csolved.csolved.domain.tag;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper
{
    public List<Tag> findAllTags();
}
