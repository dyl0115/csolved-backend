package store.csolved.csolved.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.csolved.csolved.BaseTest;
import store.csolved.csolved.user.mapper.UserMapper;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserMapperTest extends BaseTest
{
    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    public void setUp()
    {
        userMapper.hardDeleteAllUsers();
    }

    @Test
    @DisplayName("insertUser() 유저를 데이터베이스에 저장합니다.")
    public void insertUserTest()
    {
        User user1 = createTestUser(1, false);
        userMapper.insertUser(user1);

        User findUser1 = userMapper.findUserById(user1.getId());

        assertThat(user1.getEmail()).isEqualTo(findUser1.getEmail());
    }

    @Test
    @DisplayName("getUserCount() 전체 유저의 수를 검색합니다.")
    public void getUserCountTest()
    {
        User user1 = createTestUser(1, false);
        userMapper.insertUser(user1);
        User user2 = createTestUser(2, true);
        userMapper.insertUser(user2);

        Long userCount = userMapper.getUserCount();

        assertThat(userCount).isEqualTo(2);
    }

    @Test
    @DisplayName("updateUser() 유저를 업데이트 합니다.")
    public void updateUserTest()
    {
        User user1 = createTestUser(1, false);
        userMapper.insertUser(user1);
        User user2 = createTestUser(2, false);
        userMapper.insertUser(user2);

        // user1의 정보를 업데이트 합니다.
        user1.setNickname("new_nickname");
        user1.setEmail("new_email");
        user1.setCompany("new_company");
        user1.setPassword("new_password");
        userMapper.updateUser(user1);

        // user1과 user2를 데이터베이스에서 가져옵니다.
        User userFind1 = userMapper.findUserById(user1.getId());
        User userFind2 = userMapper.findUserById(user2.getId());

        // user1의 정보는 업데이트 됩니다.
        assertThat(userFind1.getNickname()).isEqualTo("new_nickname");
        assertThat(userFind1.getEmail()).isEqualTo("new_email");
        assertThat(userFind1.getCompany()).isEqualTo("new_company");
        assertThat(userFind1.getPassword()).isEqualTo("new_password");

        // user2의 정보는 업데이트 되면 안됩니다.
        assertThat(userFind2.getNickname().contains(TEST_NICK_NAME_FORMAT)).isTrue();
        assertThat(userFind2.getEmail().contains(TEST_EMAIL_FORMAT)).isTrue();
        assertThat(userFind2.getCompany().contains(TEST_COMPANY_FORMAT)).isTrue();
        assertThat(userFind2.getPassword().contains(TEST_PASSWORD_FORMAT)).isTrue();
    }

    @Test
    @DisplayName("deleteUserById() 유저를 soft delete 합니다.")
    public void deleteUserById()
    {
        User user1 = createTestUser(1, false);
        userMapper.insertUser(user1);
        User user2 = createTestUser(2, false);
        userMapper.insertUser(user2);

        Long userCount1 = userMapper.getUserCount();
        assertThat(userCount1).isEqualTo(2);

        userMapper.deleteUserById(user1.getId());
        Long userCount2 = userMapper.getUserCount();
        assertThat(userCount2).isEqualTo(1);
    }
}