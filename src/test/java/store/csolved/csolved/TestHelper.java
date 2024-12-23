package store.csolved.csolved;

import store.csolved.csolved.user.User;

public class TestHelper
{
    protected static final String TEST_USER_FORMAT = "test";
    protected static final String TEST_EMAIL_FORMAT = "@test.com";
    protected static final String TEST_PASSWORD_FORMAT = "A123!!";
    protected static final String TEST_NICKNAME_FORMAT = "_nickname";
    protected static final String TEST_COMPANY_FORMAT = "_company";

    public User createTestUser(int testId)
    {
        String username = TEST_USER_FORMAT + testId;

        return User.create(createEmail(username),
                createPassword(username),
                createNickname(username),
                createCompany(username),
                false);
    }

    private String createEmail(String username)
    {
        return username + TEST_EMAIL_FORMAT;
    }

    private String createPassword(String username)
    {
        return username + TEST_PASSWORD_FORMAT;
    }

    private String createNickname(String username)
    {
        return username + TEST_NICKNAME_FORMAT;
    }

    private String createCompany(String username)
    {
        return username + TEST_COMPANY_FORMAT;
    }
}
