package store.csolved.csolved.domain.post.exception;

public class PostNotFoundException extends RuntimeException
{
    public PostNotFoundException()
    {
        super(PostExceptionType.POST_NOT_FOUND.getCode());
    }
}
