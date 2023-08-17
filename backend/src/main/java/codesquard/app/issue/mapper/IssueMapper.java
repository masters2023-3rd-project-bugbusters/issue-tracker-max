package codesquard.app.issue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import codesquard.app.issue.mapper.request.IssueFilterRequest;
import codesquard.app.issue.mapper.response.IssueCount;
import codesquard.app.issue.mapper.response.IssuesResponse;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterAssignee;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterAuthor;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterLabel;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterMilestone;
import codesquard.app.util.Page;

@Mapper
public interface IssueMapper {

	List<IssueCount> countIssues(IssueFilterRequest request);

	List<IssuesResponse> getIssues(@Param("request") IssueFilterRequest request, @Param("page") Page page);

	List<MultiFilterAssignee> getMultiFiltersAssignees(@Param("check") boolean check,
		@Param("request") IssueFilterRequest request);

	List<MultiFilterAuthor> getMultiFiltersAuthors(@Param("check") boolean check,
		@Param("request") IssueFilterRequest request);

	List<MultiFilterLabel> getMultiFiltersLabels(@Param("check") boolean check,
		@Param("request") IssueFilterRequest request);

	List<MultiFilterMilestone> getMultiFiltersMilestones(@Param("check") boolean check,
		@Param("request") IssueFilterRequest request);

}
