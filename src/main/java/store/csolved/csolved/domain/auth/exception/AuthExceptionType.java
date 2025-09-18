package store.csolved.csolved.domain.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthExceptionType
{
    DUPLICATE_EMAIL(400, "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(400, "이미 존재하는 닉네임입니다."),
    INVALID_PASSWORD(400, "비밀번호가 올바르지 않습니다."),
    USER_NOT_FOUND(404, "등록되지 않은 사용자입니다.");

    private final int code;
    private final String message;
}
