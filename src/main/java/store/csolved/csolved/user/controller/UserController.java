package store.csolved.csolved.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/db-insert")
    public String testInsert()
    {
        try
        {
            String sql = "INSERT INTO users (email, password, nickname) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql,
                    "test" + System.currentTimeMillis() + "@test.com",
                    "testpass123",
                    "테스트유저" + System.currentTimeMillis()
            );
            return "사용자 추가 성공!";
        }
        catch (Exception e)
        {
            return "DB 에러: " + e.getMessage();
        }
    }
}
