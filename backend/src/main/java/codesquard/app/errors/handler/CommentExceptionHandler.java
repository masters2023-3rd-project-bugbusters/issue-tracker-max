package codesquard.app.errors.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import codesquard.app.errors.exception.CommentMaxLengthExceededException;

@RestControllerAdvice
public class CommentExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException exception) {
		Map<String, String> errors = new HashMap<>();
		exception.getBindingResult()
			.getAllErrors()
			.forEach(error -> errors.put(((FieldError)error).getField(), error.getDefaultMessage()));

		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(CommentMaxLengthExceededException.class)
	public ResponseEntity<String> handleCommentMaxLengthExceededException(CommentMaxLengthExceededException exception) {
		return ResponseEntity.badRequest().body(exception.getMessage());
	}

}