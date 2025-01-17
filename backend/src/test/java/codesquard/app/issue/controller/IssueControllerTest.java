package codesquard.app.issue.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import codesquard.app.ControllerTestSupport;
import codesquard.app.api.errors.errorcode.IssueErrorCode;
import codesquard.app.api.errors.exception.IllegalIssueStatusException;
import codesquard.app.api.errors.exception.NoSuchIssueException;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.api.errors.handler.GlobalExceptionHandler;
import codesquard.app.api.errors.handler.IssueExceptionHandler;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.issue.dto.request.IssueModifyAssigneesRequest;
import codesquard.app.issue.dto.request.IssueModifyContentRequest;
import codesquard.app.issue.dto.request.IssueModifyLabelsRequest;
import codesquard.app.issue.dto.request.IssueModifyMilestoneRequest;
import codesquard.app.issue.dto.request.IssueModifyStatusesRequest;
import codesquard.app.issue.dto.request.IssueModifyTitleRequest;
import codesquard.app.issue.dto.request.IssueSaveRequest;
import codesquard.app.issue.fixture.FixtureFactory;

class IssueControllerTest extends ControllerTestSupport {

	static final int TITLE_MAX_LENGTH = 50;
	static final int CONTENT_MAX_LENGTH = 10000;
	public static final String INVALID_ISSUE_STATUS_NAME = "OPEN";

	@DisplayName("이슈의 상세 내용을 조회한다.")
	@Test
	void getIssueDetail() throws Exception {
		// mocking
		mockingAuthenticateUser();

		// given
		int id = 1;
		Long userId = 1L;
		given(issueQueryService.get((long)id, userId)).willReturn(FixtureFactory.createIssueReadResponse((long)id));

		// when & then
		mockMvc.perform(get("/api/issues/" + id))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.title").exists())
			.andExpect(jsonPath("$.data.content").exists())
			.andDo(print());
	}

	@DisplayName("이슈의 상세 내용 조회에 실패한다.")
	@Test
	void getIssueDetail_Fail() throws Exception {
		// mocking
		mockingAuthenticateUser();

		// given
		willThrow(new NoSuchIssueException())
			.given(issueQueryService).get(anyLong(), anyLong());

		// when & then
		mockMvc.
			perform(get("/api/issues/" + anyLong()))
			.andExpect(status().isNotFound())
			.andDo(print());
	}

	@DisplayName("이슈를 등록한다.")
	@Test
	void create() throws Exception {
		// mocking
		mockingAuthenticateUser();

		// given
		IssueSaveRequest issueSaveRequest = FixtureFactory.createIssueRegisterRequest("Controller", "내용", 1L, 1L);

		// when & then
		mockMvc.perform(post("/api/issues")
				.content(objectMapper.writeValueAsString(issueSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.savedIssueId").exists())
			.andDo(print());
	}

	@DisplayName("제목이 1글자 미만이라면 400 에러를 반환한다.")
	@Test
	void create_InputZeroTitle_Response400() throws Exception {
		// given
		String zeroTitle = "";
		IssueSaveRequest zero = FixtureFactory.createIssueRegisterRequest(zeroTitle, "내용", 1L, 1L);

		// when & then
		mockMvc.perform(post("/api/issues")
				.content(objectMapper.writeValueAsString(zero))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("제목이 50글자 초과라면 400 에러를 반환한다.")
	@Test
	void create_Input51Title_Response400() throws Exception {
		// given
		String title = generateExceedingMaxLengthContent(TITLE_MAX_LENGTH);

		IssueSaveRequest over = FixtureFactory.createIssueRegisterRequest(title, "내용", 1L, 1L);

		// when & then
		mockMvc.perform(post("/api/issues")
				.content(objectMapper.writeValueAsString(over))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("제목이 공백이라면 400 에러를 반환한다.")
	@Test
	void create_InputBlankTitle_Response400() throws Exception {
		// given
		String blankTitle = " ";
		IssueSaveRequest blank = FixtureFactory.createIssueRegisterRequest(blankTitle, "내용", 1L, 1L);

		// when & then
		mockMvc.perform(post("/api/issues")
				.content(objectMapper.writeValueAsString(blank))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("내용이 10000글자 초과라면 400 에러를 반환한다.")
	@Test
	void create_Input10000Content_Response400() throws Exception {
		// given
		String content = generateExceedingMaxLengthContent(CONTENT_MAX_LENGTH);

		IssueSaveRequest over = FixtureFactory.createIssueRegisterRequest("Controller", content, 1L, 1L);

		// when & then
		mockMvc.perform(post("/api/issues")
				.content(objectMapper.writeValueAsString(over))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("이슈들의 상태를 수정한다.")
	@Test
	void modifyStatuses() throws Exception {
		// mocking
		mockingAuthenticateUser();

		// given
		List<Long> issueId = List.of(1L, 2L);
		String status = "CLOSED";
		IssueModifyStatusesRequest issueModifyStatusesRequest = new IssueModifyStatusesRequest(issueId, status);

		// when & then
		mockMvc.perform(patch("/api/issues/status")
				.content(objectMapper.writeValueAsString(issueModifyStatusesRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.modifiedIssueId[0]").value(issueId.get(0)))
			.andExpect(jsonPath("$.data.modifiedIssueId[1]").value(issueId.get(1)))
			.andDo(print());
	}

	@DisplayName("이슈들 상태 수정시 유효하지 않은 status 값이 오면 400 에러를 반환한다.")
	@Test
	void modifyInvalidStatus_Response400() throws Exception {
		// mocking
		mockingAuthenticateUser();

		// given
		List<Long> issueId = List.of(1L, 2L);
		IssueModifyStatusesRequest issueModifyStatusesRequest = new IssueModifyStatusesRequest(issueId,
			INVALID_ISSUE_STATUS_NAME);
		willThrow(new IllegalIssueStatusException(IssueErrorCode.INVALID_ISSUE_STATUS))
			.given(issueService).modifyStatuses(any(IssueModifyStatusesRequest.class), anyLong());

		// when & then
		mockMvc.perform(patch("/api/issues/status")
				.content(objectMapper.writeValueAsString(issueModifyStatusesRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("이슈 제목을 수정한다.")
	@Test
	void modifyTitle() throws Exception {
		// mocking
		mockingAuthenticateUser();

		// given
		int issueId = 1;
		IssueModifyTitleRequest issueModifyTitleRequest = new IssueModifyTitleRequest("수정된 제목");

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/title")
				.content(objectMapper.writeValueAsString(issueModifyTitleRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.modifiedIssueId").value(issueId))
			.andDo(print());
	}

	@DisplayName("이슈 제목 수정시 제목이 1글자 미만이라면 400 에러를 반환한다.")
	@Test
	void modify_InputZeroTitle_Response400() throws Exception {
		// given
		int issueId = 1;
		String zeroTitle = "";
		IssueModifyTitleRequest issueModifyTitleRequest = new IssueModifyTitleRequest(zeroTitle);

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/title")
				.content(objectMapper.writeValueAsString(issueModifyTitleRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("이슈 제목 수정시 제목이 50글자 초과라면 400 에러를 반환한다.")
	@Test
	void modify_Input51Title_Response400() throws Exception {
		// given
		int issueId = 1;
		String title = generateExceedingMaxLengthContent(TITLE_MAX_LENGTH);
		IssueModifyTitleRequest issueModifyTitleRequest = new IssueModifyTitleRequest(title);

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/title")
				.content(objectMapper.writeValueAsString(issueModifyTitleRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("이슈 제목 수정시 제목이 공백이라면 400 에러를 반환한다.")
	@Test
	void modify_InputBlankTitle_Response400() throws Exception {
		// given
		int issueId = 1;
		String blankTitle = " ";
		IssueModifyTitleRequest issueModifyTitleRequest = new IssueModifyTitleRequest(blankTitle);

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/title")
				.content(objectMapper.writeValueAsString(issueModifyTitleRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("이슈 내용을 수정한다.")
	@Test
	void modifyContent() throws Exception {
		// mocking
		mockingAuthenticateUser();

		// given
		int issueId = 1;
		IssueModifyContentRequest issueModifyContentRequest = new IssueModifyContentRequest("수정된 내용");

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/content")
				.content(objectMapper.writeValueAsString(issueModifyContentRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.modifiedIssueId").value(issueId))
			.andDo(print());
	}

	@DisplayName("이슈 내용 수정시 내용이 10000글자 초과라면 400 에러를 반환한다.")
	@Test
	void modify_Input10000Content_Response400() throws Exception {
		// given
		int issueId = 1;
		String title = generateExceedingMaxLengthContent(CONTENT_MAX_LENGTH);
		IssueModifyContentRequest issueModifyContentRequest = new IssueModifyContentRequest(title);

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/content")
				.content(objectMapper.writeValueAsString(issueModifyContentRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("이슈 마일스톤을 수정한다.")
	@Test
	void modifyMilestone() throws Exception {
		// mocking
		mockingAuthenticateUser();

		// given
		int issueId = 1;
		Long milestoneId = 2L;
		IssueModifyMilestoneRequest issueModifyMilestoneRequest = new IssueModifyMilestoneRequest(milestoneId);

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/milestones")
				.content(objectMapper.writeValueAsString(issueModifyMilestoneRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.modifiedIssueId").value(issueId))
			.andDo(print());
	}

	@DisplayName("이슈 책임자를 수정한다.")
	@Test
	void modifyAssignees() throws Exception {
		// mocking
		mockingAuthenticateUser();

		// given
		int issueId = 1;
		List<Long> assigneesId = List.of(1L, 2L);
		IssueModifyAssigneesRequest issueModifyAssigneesRequest = new IssueModifyAssigneesRequest(assigneesId);

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/assignees")
				.content(objectMapper.writeValueAsString(issueModifyAssigneesRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.modifiedIssueId").value(issueId))
			.andDo(print());
	}

	@DisplayName("이슈 라벨을 수정한다.")
	@Test
	void modifyLabels() throws Exception {
		// mocking
		mockingAuthenticateUser();

		// given
		int issueId = 1;
		List<Long> labelsId = List.of(1L, 2L);
		IssueModifyLabelsRequest issueModifyLabelsRequest = new IssueModifyLabelsRequest(labelsId);

		// when & then
		mockMvc.perform(patch("/api/issues/" + issueId + "/labels")
				.content(objectMapper.writeValueAsString(issueModifyLabelsRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.modifiedIssueId").value(issueId))
			.andDo(print());
	}

	@DisplayName("이슈를 삭제한다.")
	@Test
	void deleteIssue() throws Exception {
		// mocking
		mockingAuthenticateUser();

		// given
		int issueId = 1;

		// when & then
		mockMvc.perform(delete("/api/issues/" + issueId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.deletedIssueId").value(issueId))
			.andDo(print());
	}

	@DisplayName("이슈를 삭제할 때 이슈 작성자와 유저가 다를 시 403에러를 반환한다.")
	@Test
	void delete_IfNotSameAuthor_Response403() throws Exception {
		// mocking
		mockingAuthenticateUser();

		// given
		int issueId = 1;
		willThrow(new RestApiException(IssueErrorCode.FORBIDDEN_ISSUE))
			.given(issueService).delete(anyLong(), anyLong());

		// when & then
		mockMvc.perform(delete("/api/issues/" + issueId))
			.andExpect(status().isForbidden())
			.andDo(print());
	}

	private String generateExceedingMaxLengthContent(int maxLength) {
		StringBuilder builder = new StringBuilder();
		while (builder.length() < maxLength) {
			builder.append("Talk is cheap. Show me the code. ");
		}
		return builder.toString();
	}

	private void mockingAuthenticateUser() {
		mockMvc = MockMvcBuilders.standaloneSetup(new IssueController(issueService, issueQueryService))
			.setControllerAdvice(new IssueExceptionHandler(), new GlobalExceptionHandler())
			.setCustomArgumentResolvers(loginUserArgumentResolver)
			.build();

		AuthenticateUser authenticateUser = new AuthenticateUser(1L, "wis123", "wis123@naver.com", null);
		when(loginUserArgumentResolver.supportsParameter(any()))
			.thenReturn(true);
		when(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
			.thenReturn(authenticateUser);
	}
}
