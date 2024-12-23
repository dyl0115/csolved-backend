package store.csolved.csolved.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.csolved.csolved.TestHelper;
import store.csolved.csolved.user.mapper.UserMapper;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserMapperTest extends TestHelper
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
        //User는 이메일, 비밀번호, 닉네임 형식을 지켜야 합니다.
        User user1 = createTestUser(1);
        userMapper.insertUser(user1);

        User findUser1 = userMapper.findUserById(user1.getId());
        assertThat(user1.getEmail()).isEqualTo(findUser1.getEmail());
    }

    @Test
    @DisplayName("getUserCount() 전체 유저의 수를 검색합니다.")
    public void getUserCountTest()
    {
        User user1 = createTestUser(1);
        userMapper.insertUser(user1);
        User user2 = createTestUser(2);
        userMapper.insertUser(user2);

        Long userCount = userMapper.getUserCount();

        assertThat(userCount).isEqualTo(2);
    }

    @Test
    @DisplayName("isEmailDuplicate() 중복되는 이메일이 있다면 해당 이메일을 반환합니다.")
    public void isEmailDuplicate()
    {
        User user1 = createTestUser(1);
        userMapper.insertUser(user1);

        String emailDuplicate = userMapper.isEmailDuplicate(user1.getEmail());
        Assertions.assertThat(emailDuplicate).isEqualTo(user1.getEmail());
    }

    @Test
    @DisplayName("updateUser() 유저를 업데이트하고 데이터베이스에 저장합니다.")
    public void updateUserTest()
    {
        User user1 = createTestUser(1);
        userMapper.insertUser(user1);
        User user2 = createTestUser(2);
        userMapper.insertUser(user2);

        // user1의 정보를 업데이트하고 데이터베이스에 저장합니다.
        user1.updateNickname("new_nickname");
        user1.updateEmail("new_email@example.com");
        user1.updateCompany("new_company");
        user1.updatePassword("password123!");
        userMapper.updateUser(user1);

        // user1과 user2를 데이터베이스에서 가져옵니다.
        User userFind1 = userMapper.findUserById(user1.getId());
        User userFind2 = userMapper.findUserById(user2.getId());

        // user1의 정보는 업데이트 됩니다.
        assertThat(userFind1.getEmail()).isEqualTo("new_email@example.com");
        assertThat(userFind1.getPassword()).isEqualTo("password123!");
        assertThat(userFind1.getNickname()).isEqualTo("new_nickname");
        assertThat(userFind1.getCompany()).isEqualTo("new_company");

        // user2의 정보는 업데이트 되면 안됩니다.
        assertThat(userFind2.getEmail()).isEqualTo(user2.getEmail());
        assertThat(userFind2.getPassword()).isEqualTo(user2.getPassword());
        assertThat(userFind2.getNickname()).isEqualTo(user2.getNickname());
        assertThat(userFind2.getCompany()).isEqualTo(user2.getCompany());
    }

    @Test
    @DisplayName("deleteUserById() 유저를 soft delete 합니다.")
    public void deleteUserById()
    {
        User user1 = createTestUser(1);
        userMapper.insertUser(user1);
        User user2 = createTestUser(2);
        userMapper.insertUser(user2);

        Long userCount1 = userMapper.getUserCount();
        assertThat(userCount1).isEqualTo(2);

        userMapper.deleteUserById(user1.getId());
        Long userCount2 = userMapper.getUserCount();
        assertThat(userCount2).isEqualTo(1);
    }
}