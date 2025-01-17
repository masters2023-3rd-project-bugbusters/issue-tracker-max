package codesquard.app.milestone.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.milestone.entity.Milestone;

public class MilestonesResponse {
	@JsonProperty("id")
	private Long id; // 등록번호
	@JsonProperty("name")
	private String name; // 이름
	@JsonProperty("description")
	private String description; // 설명
	@JsonProperty("createdAt")
	private LocalDateTime createdAt; // 생성 시간
	@JsonProperty("modifiedAt")
	private LocalDateTime modifiedAt; // 수정 시간
	@JsonProperty("deadline")
	private LocalDate deadline; // 완료일
	@JsonProperty("issues")
	private Map<String, Long> issues; // 이슈 카운트

	private MilestonesResponse(final Long id, final String name, final String description,
		final LocalDateTime createdAt, final LocalDateTime modifiedAt, final LocalDate deadline,
		final Map<String, Long> issues) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.deadline = deadline;
		this.issues = issues;
	}

	public static MilestonesResponse fromEntity(final Milestone milestone, final Map<String, Long> issues) {
		return new MilestonesResponse(milestone.getId(), milestone.getName(),
			milestone.getDescription(), milestone.getCreatedAt(), milestone.getModifiedAt(), milestone.getDeadline(),
			issues);
	}
}
