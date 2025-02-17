package store.csolved.csolved.domain.code_review.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.code_review.CodeReview;

import java.util.List;

@Mapper
public interface CodeReviewMapper
{
    void saveCodeReview(int postType, CodeReview codeReview);

    void updateCodeReview(Long codeReviewId, CodeReview codeReview);

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

    CodeReview getCodeReview(Long codeReviewId);

    void deleteCodeReview(Long codeReviewId);

    boolean hasUserLiked(Long codeReviewId, Long authorId);

    void increaseLikes(Long codeReviewId);

    void addUserLike(Long codeReviewId, Long authorId);

    void increaseView(Long codeReviewId);
}
