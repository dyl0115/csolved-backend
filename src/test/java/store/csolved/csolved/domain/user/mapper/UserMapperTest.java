package store.csolved.csolved.domain.user.mapper;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import store.csolved.csolved.domain.user.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;


@MybatisTest
class UserMapperTest
{
    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    void beforeEach()
    {
        userMapper.deleteAll();
    }

    User createTestUser(String email, String nickname, String password)
    {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .build();
    }

    @Test
    @DisplayName("회원가입이 정상적으로 된다.")
    void insertUser()
    {
        //given
        String testEmail = "testEmail@example.com";
        String testNickname = "testNickname";
        String testPassword = "testPassword";

        User user = createTestUser(testEmail,
                testNickname,
                testPassword);

        //when
        userMapper.insertUser(user);

        //then
        User foundUser = userMapper.findUserByEmail(user.getEmail());
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(testEmail);
        assertThat(foundUser.getNickname()).isEqualTo(testNickname);
    }

    @Test
    @DisplayName("중복된 이메일은 회원가입 시 예외를 던진다.")
    void insertUser_Duplicate_Email()
    {
        //given
        String testEmail1 = "duplicateEmail@example.com";
        String testNickname1 = "david";
        String testPassword1 = "david123!";

        String testEmail2 = "duplicateEmail@example.com";
        String testNickname2 = "marry";
        String testPassword2 = "marry123!";

        User user1 = createTestUser(testEmail1, testNickname1, testPassword1);
        User user2 = createTestUser(testEmail2, testNickname2, testPassword2);

        //when
        userMapper.insertUser(user1);

        //then
        assertThatThrownBy(() -> userMapper.insertUser(user2))
                .isInstanceOf(DuplicateKeyException.class);

        assertThat(userMapper.findUserByEmail(testEmail1).getEmail())
                .isEqualTo(user1.getEmail());

        assertThat(userMapper.findUserByEmail(testEmail1).getNickname())
                .isEqualTo(user1.getNickname());
    }

    @Test
    @DisplayName("중복된 닉네임은 회원가입 시 예외를 던진다.")
    void insertUser_Duplicate_Nickname()
    {
        //given
        String testEmail1 = "david@example.com";
        String testNickname1 = "duplicateNickname";
        String testPassword1 = "david123!";

        String testEmail2 = "marry@example.com";
        String testNickname2 = "duplicateNickname";
        String testPassword2 = "marry123!";

        User user1 = createTestUser(testEmail1, testNickname1, testPassword1);
        User user2 = createTestUser(testEmail2, testNickname2, testPassword2);

        //when
        userMapper.insertUser(user1);

        //then
        assertThatThrownBy(() -> userMapper.insertUser(user2))
                .isInstanceOf(DuplicateKeyException.class);

        assertThat(userMapper.findUserByEmail(testEmail1).getEmail())
                .isEqualTo(user1.getEmail());

        assertThat(userMapper.findUserByEmail(testEmail1).getNickname())
                .isEqualTo(user1.getNickname());

        assertThat(userMapper.findUserByEmail(testEmail2)).isNull();
    }

    @Test
    @DisplayName("이메일로 비밀번호 찾기 테스트")
    void findPasswordByEmail()
    {
        //given
        String email1 = "david@example.com";
        String nickname1 = "david";
        String password1 = "david123!";

        String email2 = "marry@example.com";
        String nickname2 = "marry";
        String password2 = "marry123!";

        userMapper.insertUser(createTestUser(email1, nickname1, password1));
        userMapper.insertUser(createTestUser(email2, nickname2, password2));

        //when then
        assertThat(userMapper.findPasswordByEmail(email1)).isEqualTo(password1);
        assertThat(userMapper.findPasswordByEmail(email2)).isNotEqualTo(password1);
    }

    @Test
    void updateProfileImage()
    {
    }

    @Test
    void updateNickname()
    {
    }

    @Test
    void deleteAll()
    {
        //given
        String email1 = "david@example.com";
        String nickname1 = "david";
        String password1 = "david123!";

        String email2 = "marry@example.com";
        String nickname2 = "marry";
        String password2 = "marry123!";

        userMapper.insertUser(createTestUser(email1, nickname1, password1));
        userMapper.insertUser(createTestUser(email2, nickname2, password2));

        //when
        userMapper.deleteAll();

        //then
        assertThat(userMapper.findUserByEmail(email1)).isNull();
        assertThat(userMapper.findUserByEmail(email2)).isNull();
    }

    @Test
    @DisplayName("유저의 아이디를 입력하면 유저의 관리자 여부를 알 수 있다.")
    void checkUserAdmin()
    {
        //given
        User david = User.builder()
                .admin(true)
                .email("david@naver.com")
                .password("david123!!")
                .nickname("david")
                .company("davidCompany")
                .createdAt(LocalDateTime.now())
                .build();

        User marry = User.builder()
                .admin(false)
                .email("marry@naver.com")
                .password("marry123!!")
                .nickname("marry")
                .company("marryCompany")
                .createdAt(LocalDateTime.now())
                .build();

        //when
        userMapper.insertUser(david);
        userMapper.insertUser(marry);

        //then
        boolean isDavidAdmin = userMapper.checkAdmin(david.getId());
        boolean isMarryAdmin = userMapper.checkAdmin(marry.getId());

        assertThat(isDavidAdmin).isTrue();
        assertThat(isMarryAdmin).isFalse();
    }
}