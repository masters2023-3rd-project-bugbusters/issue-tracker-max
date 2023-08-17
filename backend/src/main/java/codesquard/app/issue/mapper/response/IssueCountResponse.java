package codesquard.app.issue.mapper.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class IssueCountResponse {

	List<IssueCount> issueCounts;

	public List<IssueCount> getIssueCounts() {
		return issueCounts;
	}

	public int getOpenedIssueCount() {
		if (issueCounts.get(0).isStatus("OPENED")) {
			return issueCounts.get(0).getCount();
		} else if (issueCounts.size() < 2) {
			return 0;
		} else {
			return issueCounts.get(1).getCount();
		}
	}

	public int getClosedIssueCount() {
		if (issueCounts.get(0).isStatus("CLOSED")) {
			return issueCounts.get(0).getCount();
		} else if (issueCounts.size() < 2) {
			return 0;
		} else {
			return issueCounts.get(1).getCount();
		}
	}

}
