package store.csolved.csolved.utils;


import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtils
{
    private static final int logRound = 12;

    public static String hashPassword(String plainPassword)
    {
        String salt = BCrypt.gensalt(logRound);
        return BCrypt.hashpw(plainPassword, salt);
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword)
    {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
