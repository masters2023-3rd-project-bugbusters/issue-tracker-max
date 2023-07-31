package codesquard.app.milestone.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class Milestone {
	private Long id; // 등록번호
	private boolean status; // true: open, false: close
	private LocalDateTime statusModifiedAt; // 상태(open/close)변경시간
	private String name; // 이름
	private String description; // 설명
	private LocalDateTime createdAt; // 생성시간
	private LocalDateTime modifiedAt; // 수정시간
	private LocalDate deadline; // 완료일
}