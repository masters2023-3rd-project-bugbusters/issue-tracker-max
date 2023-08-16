package codesquard.app.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Page {

	private static final int ISSUES_PER_PAGE = 15;
	private static final int PAGES_BY_GROUP = 5;

	private int currentPage;
	private int totalPageCount;
	private int pageGroup;
	private int startPage;
	private int endPage;
	private int limitIndex;
	private boolean hasPrevious;
	private boolean hasNext;

	public Page(int currentPage, int total) {
		this.currentPage = currentPage;
		calculateTotalPageCount(total);
		calculatePageGroup();
		calculateStartPage();
		calculateEndPage();
		calculatePrevious();
		calculateNext();
		calculateLimitIndex();
	}

	private void calculateTotalPageCount(int total) {
		totalPageCount = total / ISSUES_PER_PAGE;

		if (total % ISSUES_PER_PAGE > 0) {
			totalPageCount++;
		}
	}

	private void calculatePageGroup() {
		pageGroup = (int)Math.ceil((double)currentPage / PAGES_BY_GROUP);
	}

	private void calculateStartPage() {
		startPage = (pageGroup - 1) * ISSUES_PER_PAGE + 1;

		if (startPage < 0) {
			startPage = 0;
		}
	}

	private void calculateEndPage() {
		endPage = pageGroup * PAGES_BY_GROUP;

		if (totalPageCount < endPage) {
			endPage = totalPageCount;
		}
	}

	private void calculatePrevious() {
		hasPrevious = startPage > 1;
	}

	private void calculateNext() {
		hasNext = endPage < totalPageCount;
	}

	private void calculateLimitIndex() {
		limitIndex = (currentPage - 1) * ISSUES_PER_PAGE;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public int getPageGroup() {
		return pageGroup;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public int getLimitIndex() {
		return limitIndex;
	}

}
