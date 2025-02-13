package store.csolved.csolved.domain.post.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.post.entity.CodeReview;

import java.util.List;

@Mapper
public interface CodeReviewMapper
{
    Long saveCodeReview(CodeReview codeReview);

    void saveGithubUrl(Long postId, String githubUrl);

    void updateGithubUrl(Long postId, String githubUrl);

    Long countCodeReviews(int postType,
                          String filterType,
                          Long filterValue,
                          String searchType,
                          String searchKeyword);

    List<CodeReview> getCodeReviews(int postType,
                                    Long offset,
                                    Long size,
                                    String sortType,
                                    String filterType,
                                    Long filterValue,
                                    String searchType,
                                    String searchKeyword);

    CodeReview getCodeReview(Long postId);
}
