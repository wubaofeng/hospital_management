package me.wbf.hospital.util;

import java.util.List;

/**
 * @author Baofeng.Wu
 * @param <T>
 */
public class PageUtil<T> {
	private int page;
	private int size;
	private int total;
	private int prePage;
	private int nextPage;
	private int totalPage;
	private List<T> data;

	public PageUtil(int page, int size) {
		this.page = page;
		this.size = size;
	}

	public void setTotal(int total) {
		if (total <= size) {
			this.totalPage = 1;
		}else {
			this.totalPage = total % size == 0 ? total / size : (total / size) + 1;	
		}
		prePage = page - 1 > 0 ? page - 1 : 1;
		nextPage = page + 1 > totalPage ? totalPage : page + 1;
		this.total = total;
	}

	public int getCurrentPage() {
		return page;
	}

	public int getSize() {
		return size;
	}

	public int getTotal() {
		return total;
	}

	public int getPrePage() {
		return prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public List<T> getData() {
		return data;
	}

	public void setCurrentPage(int currentPage) {
		this.page = currentPage;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
