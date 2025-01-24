package store.csolved.csolved.domain.user.mapper;

import org.apache.ibatis.annotations.*;
import store.csolved.csolved.domain.user.User;

@Mapper
public interface UserMapper
{
    void insertUser(User user);

    User findUserById(Long id);

    User findUserByEmail(String email);

    String findPasswordByEmail(String Email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    void update(User user);

    void delete(Long id);
}
