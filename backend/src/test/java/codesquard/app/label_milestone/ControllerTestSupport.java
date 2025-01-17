package codesquard.app.label_milestone;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.authenticate_user.resolver.LoginUserArgumentResolver;
import codesquard.app.authenticate_user.service.AuthenticateUserService;
import codesquard.app.issue.controller.IssueController;
import codesquard.app.issue.service.IssueQueryService;
import codesquard.app.issue.service.IssueService;
import codesquard.app.jwt.JwtProvider;
import codesquard.app.label.controller.LabelController;
import codesquard.app.label.service.LabelService;
import codesquard.app.milestone.controller.MilestoneController;
import codesquard.app.milestone.service.MilestoneService;
import codesquard.app.oauth.service.OauthService;
import codesquard.app.user.controller.UserRestController;
import codesquard.app.user.service.UserQueryService;
import codesquard.app.user.service.UserService;
import codesquard.app.user_reaction.controller.UserReactionController;
import codesquard.app.user_reaction.service.UserReactionService;

@WebMvcTest(controllers = {
	MilestoneController.class,
	LabelController.class,
	IssueController.class,
	UserRestController.class,
	UserReactionController.class
})
@Import(value = {JwtProvider.class})
public abstract class ControllerTestSupport {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected JwtProvider jwtProvider;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockBean
	protected IssueService issueService;

	@MockBean
	protected IssueQueryService issueQueryService;

	@MockBean
	protected MilestoneService milestoneService;

	@MockBean
	protected LabelService labelService;

	@MockBean
	protected UserService userService;

	@MockBean
	protected UserQueryService userQueryService;

	@MockBean
	protected AuthenticateUserService authenticateUserService;

	@MockBean
	protected UserReactionService userReactionService;

	@MockBean
	protected OauthService oauthService;

	@Mock
	protected LoginUserArgumentResolver loginUserArgumentResolver;

}
