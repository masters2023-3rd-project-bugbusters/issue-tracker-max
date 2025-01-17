package codesquard.app.api.errors.errorcode;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum JwtTokenErrorCode implements ErrorCode {

	NOT_MATCH_REFRESHTOKEN(HttpStatus.BAD_REQUEST, "Refreshtoken이 일치하지 않습니다."),
	FAIL_PARSE_JSON(HttpStatus.BAD_REQUEST, "Json 파싱에 실패하였습니다."),
	FAIL_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다."),
	EXPIRE_TOKEN(HttpStatus.FORBIDDEN, "토큰이 만료되었습니다."),
	NOT_HAVE_AUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");

	private HttpStatus httpStatus;
	private String message;

	JwtTokenErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	@Override
	public String getName() {
		return name();
	}
}
