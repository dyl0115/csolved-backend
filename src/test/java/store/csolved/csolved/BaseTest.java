package store.csolved.csolved;

import store.csolved.csolved.user.User;

import java.time.LocalDateTime;

public class BaseTest
{
    private static final String TEST_USER_FORMAT = "test";
    private static final String TEST_EMAIL_FORMAT = "@example.com";
    private static final String TEST_PASSWORD_FORMAT = "_password";
    private static final String TEST_NICK_NAME_FORMAT = "_nickname";
    private static final String TEST_COMPANY_FORMAT = "_company";

    public User createTestUser(int testId, boolean isAdmin)
    {
        String testUser = TEST_USER_FORMAT + testId;

        return User.builder()
                .email(testUser + TEST_EMAIL_FORMAT)
                .password(testUser + TEST_PASSWORD_FORMAT)
                .nickname(testUser + TEST_NICK_NAME_FORMAT)
                .company(testUser + TEST_COMPANY_FORMAT)
                .createdAt(LocalDateTime.now())
                .isAdmin(isAdmin)
                .build();
    }
}
