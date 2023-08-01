package codesquard.app.user.service;

import org.springframework.stereotype.Service;

import codesquard.app.user.dto.request.UserSaveRequest;
import codesquard.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public Long save(UserSaveRequest userSaveRequest) {
		return userRepository.save(userSaveRequest.toEntity());
	}
}
