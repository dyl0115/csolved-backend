package store.csolved.csolved.user.exceptions;

import java.util.HashMap;
import java.util.Map;

public class SignUpFailedException extends RuntimeException
{
    private final Map<String, String> errors;

    public SignUpFailedException()
    {
        this.errors = new HashMap<>();
    }

    public void addError(String field, String message)
    {
        errors.put(field, message);
    }

    public Map<String, String> getErrors()
    {
        return errors;
    }
}
