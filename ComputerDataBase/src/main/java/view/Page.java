package view;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
	private List<T> data;
	private int elementPerPage;
	private int totalPage;
	private int currentPage;

	public Page(List<T> data, int elementPerPage) {
		this.data = new ArrayList<T>();
		this.data.addAll(data);
		this.elementPerPage = elementPerPage;
		this.currentPage = 1;
		this.totalPage = 1 + this.data.size() / this.elementPerPage;
	}

	public List<T> getByPageNumber(int nb) {
		List<T> ret = new ArrayList<T>();
		if (nb > 0 && nb <= totalPage) {
			for (int i = (nb - 1) * elementPerPage; i < nb * elementPerPage; i++) {
				if (i >= data.size()) {
					break;
				}
				ret.add(data.get(i));
			}
		}
		return ret;
	}

	public List<T> getCurrentPage() {
		return getByPageNumber(currentPage);
	}

	public void moveToNextPage() {
		if (currentPage < totalPage) {
			currentPage++;
		}
	}

	public void moveToPreviousPage() {
		if (currentPage >= 1) {
			currentPage--;
		}
	}
	
	public void moveToPageNumber(int nb) {
		if (nb > 0 && nb <= totalPage) {
			currentPage = nb;
		}
	}
}
