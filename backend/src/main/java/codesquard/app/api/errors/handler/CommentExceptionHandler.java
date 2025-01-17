package codesquard.app.api.errors.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import codesquard.app.api.errors.exception.CommentAuthorMismatchException;
import codesquard.app.api.errors.exception.CommentMaxLengthExceededException;
import codesquard.app.api.errors.exception.NoSuchCommentException;
import codesquard.app.api.response.ApiResponse;

@RestControllerAdvice
public class CommentExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(CommentExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CommentMaxLengthExceededException.class)
	public ApiResponse<Object> handleCommentMaxLengthExceededException(CommentMaxLengthExceededException e) {
		logger.debug("CommentMaxLengthExceededException handling : {}", e.getMessage());
		return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NoSuchCommentException.class)
	public ApiResponse<Object> handleNoSuchCommentException(NoSuchCommentException e) {
		logger.debug("NoSuchCommentException handling : {}", e.getMessage());
		return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CommentAuthorMismatchException.class)
	public ApiResponse<Object> handleCommentAuthorMismatchException(CommentAuthorMismatchException e) {
		logger.debug("CommentAuthorMismatchException handling : {}", e.getMessage());
		return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
	}

}
