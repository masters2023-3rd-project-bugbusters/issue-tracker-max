package codesquard.app.user.service.request;

import java.util.Objects;

import codesquard.app.user.entity.User;
import codesquard.app.user.password.PasswordEncoder;
import lombok.ToString;

@ToString
public class UserSaveServiceRequest {
	private final String loginId;
	private final String email;
	private final String password;
	private final String passwordConfirm;
	private final String avatarUrl;

	public UserSaveServiceRequest(String loginId, String email, String password, String passwordConfirm,
		String avatarUrl) {
		this.loginId = loginId;
		this.email = email;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.avatarUrl = avatarUrl;
	}

	public User toEntity() {
		return new User(null, loginId, email, password, avatarUrl);
	}

	public boolean matchPasswordAndPasswordConfirm() {
		return Objects.equals(password, passwordConfirm);
	}

	public UserSaveServiceRequest encryptPassword() {
		PasswordEncoder passwordEncoder = new PasswordEncoder();
		String encryptPassword = passwordEncoder.encrypt(password);
		return new UserSaveServiceRequest(loginId, email, encryptPassword, passwordConfirm, avatarUrl);
	}
}
