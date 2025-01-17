package codesquard.app.issue.mapper.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IssuesResponse {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("title")
	private String title;

	@JsonProperty("status")
	private String status;

	@JsonProperty("createdAt")
	private LocalDateTime createdAt;

	@JsonProperty("modifiedAt")
	private LocalDateTime modifiedAt;

	@JsonProperty("statusModifiedAt")
	private LocalDateTime statusModifiedAt;

	@JsonProperty("assignees")
	private List<IssueAssigneeResponse> assignees;

	@JsonProperty("labels")
	private List<IssueLabelResponse> labels;

	@JsonProperty("milestones")
	private IssueMilestoneResponse milestone;

	@JsonProperty("author")
	private IssueAuthorResponse author;

	@JsonProperty("commentCount")
	private int commentCount;

}
