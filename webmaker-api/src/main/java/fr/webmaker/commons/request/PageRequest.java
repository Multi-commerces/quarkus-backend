package fr.webmaker.commons.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class PageRequest {

	@QueryParam("page")
	@DefaultValue("1")
	@Positive
	@Min(1)
	private int page;

	@QueryParam("size")
	@DefaultValue("10")
	@Positive
	@Min(1)
	private int size;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "PageRequest [page=" + page + ", size=" + size + "]";
	}

}