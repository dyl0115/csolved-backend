package store.csolved.csolved.domain.post;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.common.Post;

import java.util.List;

@Mapper
public interface PostMapper
{
    void savePost(int postType, Post post);

    List<Post> getPosts(int postType,
                        Long offset,
                        Long size,
                        String sortType,
                        String filterType,
                        Long filterValue,
                        String searchType,
                        String searchKeyword);

    Post getPost(Long postId);

    void updatePost(Long postId, Post post);

    Long countPosts(int postType,
                    String filterType,
                    Long filterValue,
                    String searchType,
                    String searchKeyword);
}
