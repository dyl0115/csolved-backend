package store.csolved.csolved.domain.post.question.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.post.question.Question;

import java.util.List;

@Mapper
public interface QuestionMapper
{
    void saveQuestion(int postType, Question question);

    void updateQuestion(Long questionId, Question question);

    // 게시글 개수 조회
    Long countQuestions(int postType,
                        String filterType,
                        Long filterValue,
                        String searchType,
                        String searchKeyword);

    // 질문글들 조회
    List<Question> getQuestions(int postType,
                                Long offset,
                                Long size,
                                String sortType,
                                String filterType,
                                Long filterValue,
                                String searchType,
                                String searchKeyword);

    // 질문글 조회
    Question getQuestion(Long questionId);

    // 논리적으로 게시글을 삭제
    void deleteQuestion(Long questionId);

    void increaseAnswerCount(Long questionId);

    void decreaseAnswerCount(Long questionId);

    // 질문-좋아요 테이블에 저장된 유저인지 체크
    boolean hasUserLiked(Long questionId, Long authorId);

    // 질문 테이블의 Likes 1증가
    void increaseLikes(Long questionId);

    // 질문-좋아요 테이블에 questionId, userId 저장 (중복 좋아요 방지)
    void addUserLike(Long questionId, Long authorId);

    // 질문 테이블의 Views 1증가
    void increaseView(Long questionId);
}
