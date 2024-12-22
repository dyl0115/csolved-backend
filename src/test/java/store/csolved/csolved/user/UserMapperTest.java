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
        userMapper.deleteAllUsers();
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
    @DisplayName("updateUser() 유저를 업데이트 합니다.")
    public void updateUser()
    {
        User user1 = createTestUser(1, false);
        userMapper.insertUser(user1);

        user1.setNickname("test2");
        userMapper.updateUser(user1);

        assertThat(user1.getNickname()).isEqualTo("test2");
    }

}