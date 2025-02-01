package store.csolved.csolved.domain.free_board.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.free_board.entity.Article;

@Mapper
public interface ArticleMapper
{
    void save(Article article);
}
