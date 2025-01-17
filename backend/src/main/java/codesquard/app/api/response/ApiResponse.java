package codesquard.app.api.response;

import org.springframework.http.HttpStatus;

import codesquard.app.api.errors.errorcode.ErrorCode;

public class ApiResponse<T> {

	private int code;
	private HttpStatus status;
	private String message;
	private T data;

	public ApiResponse(HttpStatus status, String message, T data) {
		this.code = status.value();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
		return new ApiResponse<>(httpStatus, message, data);
	}

	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>(HttpStatus.OK, HttpStatus.OK.name(), data);
	}

	public static <T> ApiResponse<T> error(ErrorCode errorCode) {
		return new ApiResponse<>(errorCode.getHttpStatus(), errorCode.getMessage(), null);
	}

	public int getCode() {
		return code;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}

}
