package codesquard.app.issue.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IssueCommentsResponse {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("userId")
	private String userId;
	@JsonProperty("avatarUrl")
	private String avatarUrl;
	@JsonProperty("content")
	private String content;
	@JsonProperty("createdAt")
	private LocalDateTime createdAt;
	@JsonProperty("modifiedAt")
	private LocalDateTime modifiedAt;
	@JsonProperty("reactions")
	private final List<userReactionResponse> reactions;

	public Long getId() {
		return id;
	}
}
