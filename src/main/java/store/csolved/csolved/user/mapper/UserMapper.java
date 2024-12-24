package store.csolved.csolved.user.mapper;

import org.apache.ibatis.annotations.*;
import store.csolved.csolved.user.User;

@Mapper
public interface UserMapper
{
    void insertUser(User user);

    User findUserById(Long id);

    User findUserByEmail(String email);

    String findPasswordByEmail(String Email);

    long getUserCount();

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    void updateUser(User user);

    void deleteUserById(Long id);

    void hardDeleteAllUsers();
}
