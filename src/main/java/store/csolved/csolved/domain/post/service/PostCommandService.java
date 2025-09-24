package store.csolved.csolved.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.post.mapper.entity.Post;
import store.csolved.csolved.domain.post.exception.DeleteDeniedException;
import store.csolved.csolved.domain.post.exception.PostNotFoundException;
import store.csolved.csolved.domain.post.exception.UpdateDeniedException;
import store.csolved.csolved.domain.post.mapper.PostMapper;
import store.csolved.csolved.domain.post.service.command.PostCreateCommand;
import store.csolved.csolved.domain.post.service.command.PostUpdateCommand;
import store.csolved.csolved.domain.tag.service.TagService;

import java.util.Objects;

import static store.csolved.csolved.domain.post.PostType.COMMUNITY;

@RequiredArgsConstructor
@Service
public class PostCommandService
{
    private final TagService tagService;
    private final PostMapper postMapper;

    // 커뮤니티 글, 태그 저장
    @Transactional
    public void save(PostCreateCommand command)
    {
        Post post = Post.from(command);
        postMapper.savePost(COMMUNITY.getCode(), post);
        tagService.saveTags(post.getId(), command.getTags());
    }

    // 커뮤니티글 업데이트
    @Transactional
    public void update(Long userId, Long postId, PostUpdateCommand command)
    {
        Post post = Post.from(command);

        Long authorId = postMapper.getAuthorId(postId);

        if (authorId == null)
        {
            throw new PostNotFoundException();
        }

        if (!authorId.equals(userId))
        {
            throw new UpdateDeniedException();
        }

        postMapper.updatePost(postId, post);
        tagService.updateTags(postId, command.getTags());
    }

    // 커뮤니티글 삭제
    @Transactional
    public void delete(Long userId, Long postId)
    {
        Long authorId = postMapper.getAuthorId(postId);

        if (authorId == null)
        {
            throw new PostNotFoundException();
        }

        if (!Objects.equals(userId, authorId))
        {
            throw new DeleteDeniedException();
        }

        postMapper.deletePost(postId);
    }
}
