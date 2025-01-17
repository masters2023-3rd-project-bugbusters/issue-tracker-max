package codesquard.app.issue.mapper.response.filters;

import codesquard.app.issue.mapper.response.filters.response.MultiFilterAssignees;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterAuthors;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterLabels;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterMilestones;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class MultiFilters {

	private MultiFilterAssignees assignee;

	private MultiFilterLabels label;

	private MultiFilterMilestones milestone;

	private MultiFilterAuthors author;

	public MultiFilterAssignees getAssignee() {
		return assignee;
	}

	public MultiFilterLabels getLabel() {
		return label;
	}

	public MultiFilterMilestones getMilestone() {
		return milestone;
	}

	public MultiFilterAuthors getAuthor() {
		return author;
	}

}
