package codesquard.app.issue.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.errors.errorcode.IssueErrorCode;
import codesquard.app.errors.exception.NoSuchIssueException;
import codesquard.app.issue.dto.response.IssueCommentsResponse;
import codesquard.app.issue.dto.response.IssueLabelResponse;
import codesquard.app.issue.dto.response.IssueMilestoneCountResponse;
import codesquard.app.issue.dto.response.IssueReadResponse;
import codesquard.app.issue.dto.response.IssueUserResponse;
import codesquard.app.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class IssueQueryService {

	private final IssueRepository issueRepository;

	public IssueReadResponse get(Long issueId) {
		existIssue(issueId);
		IssueReadResponse issueReadResponse = issueRepository.findBy(issueId);
		List<IssueUserResponse> assignees = IssueUserResponse.from(issueRepository.findAssigneesBy(issueId));
		List<IssueLabelResponse> labels = IssueLabelResponse.from(issueRepository.findLabelsBy(issueId));
		IssueMilestoneCountResponse issueMilestoneCountResponse = issueRepository.countIssueBy(
			issueReadResponse.getMilestone().getId());
		List<IssueCommentsResponse> issueCommentsResponse = issueRepository.findCommentsBy(issueId);
		return issueReadResponse.from(assignees, labels, issueMilestoneCountResponse, issueCommentsResponse);
	}

	public void existIssue(Long issueId) {
		if (!issueRepository.exist(issueId)) {
			throw new NoSuchIssueException(IssueErrorCode.NOT_FOUND_ISSUE);
		}
	}

	public IssueReadResponse findById(Long issueId) {
		return issueRepository.findBy(issueId);
	}

	public List<IssueUserResponse> findAssigneesById(Long issueId) {
		return IssueUserResponse.from(issueRepository.findAssigneesBy(issueId));
	}

	public List<IssueLabelResponse> findLabelsById(Long issueId) {
		return IssueLabelResponse.from(issueRepository.findLabelsBy(issueId));
	}
}
