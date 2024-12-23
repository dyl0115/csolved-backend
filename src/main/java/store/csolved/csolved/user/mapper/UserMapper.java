package store.csolved.csolved.user.mapper;

import org.apache.ibatis.annotations.*;
import store.csolved.csolved.user.User;

@Mapper
public interface UserMapper
{
    void insertUser(User user);

    User findUserById(Long id);

    Long getUserCount();

    String isEmailDuplicate(String email);

    void updateUser(User user);

    void deleteUserById(Long id);

    void hardDeleteAllUsers();
}
