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

    @GetMapping("/db-check")
    public String hello()
    {
        try
        {
            // 테스트 데이터 삽입 시도
            jdbcTemplate.execute("INSERT INTO test_table (name) VALUES ('test-data-" + System.currentTimeMillis() + "')");
            return "DB 저장 성공!";
        }
        catch (Exception e)
        {
            return "DB 에러: " + e.getMessage();
        }
    }
}
