package store.csolved.csolved.domain.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthExceptionType
{
    DUPLICATE_EMAIL("DUPLICATE_EMAIL", "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME("DUPLICATE_NICKNAME", "이미 존재하는 닉네임입니다."),
    INVALID_PASSWORD("INVALID_PASSWORD", "비밀번호가 올바르지 않습니다."),
    USER_NOT_FOUND("USER_NOT_FOUND", "등록되지 않은 사용자입니다.");

    private final String code;
    private final String message;
}
