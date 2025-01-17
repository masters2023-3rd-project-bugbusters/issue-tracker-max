package codesquard.app.label.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.label.entity.Label;

public class LabelSaveRequest {
	@NotNull(message = "제목 입력은 필수입니다.")
	@Size(min = 1, max = 20, message = "제목은 1글자 이상, 20글자 이하여야 합니다.")
	@JsonProperty("name")
	private String name;
	@NotNull(message = "글자색 입력은 필수입니다.")
	@JsonProperty("color")
	private String color;
	@NotNull(message = "배경색 입력은 필수입니다.")
	@Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "올바른 배경색 형식이어야 합니다. (예: #RRGGBB)")
	@JsonProperty("background")
	private String background;
	@Size(max = 50, message = "내용은 0글자 이상, 50글자 이하여야 합니다.")
	@JsonProperty("description")
	private String description;

	private LabelSaveRequest() {
	}

	public LabelSaveRequest(String name, String color, String background, String description) {
		this.name = name;
		this.color = color;
		this.background = background;
		this.description = description;
	}

	public static Label toEntity(final LabelSaveRequest labelSaveRequest, final Long userId) {
		return new Label(labelSaveRequest.name, labelSaveRequest.color, labelSaveRequest.background,
			labelSaveRequest.description, userId);
	}
}
