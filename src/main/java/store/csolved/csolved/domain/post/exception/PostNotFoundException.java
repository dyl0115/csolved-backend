package store.csolved.csolved.domain.post.exception;

public class PostNotFoundException extends RuntimeException
{
    public PostNotFoundException()
    {
        super(CommunityExceptionType.POST_NOT_FOUND.getCode());
    }
}
