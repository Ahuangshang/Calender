package cn.ltwc.cft.beans;

import java.util.List;

/**
 * 
 * TODO:内涵段子返回数据的Bean 返回的数据体
 * 
 * @author huangshang 2015-11-24 下午3:00:35
 * @Modified_By:
 */
public class ResBodyBean {
	private String Counts;// 11050,
	private String PageCount;// 553,
	private List<JokeListBean> JokeList;// 笑话的集合

	public ResBodyBean() {
		super();
	}

	public ResBodyBean(String counts, String pageCount, List<JokeListBean> jokeList) {
		super();
		Counts = counts;
		PageCount = pageCount;
		JokeList = jokeList;
	}

	public String getCounts() {
		return Counts;
	}

	public void setCounts(String counts) {
		Counts = counts;
	}

	public String getPageCount() {
		return PageCount;
	}

	public void setPageCount(String pageCount) {
		PageCount = pageCount;
	}

	public List<JokeListBean> getJokeList() {
		return JokeList;
	}

	public void setJokeList(List<JokeListBean> jokeList) {
		JokeList = jokeList;
	}

	@Override
	public String toString() {
		return "ResBodyBean [Counts=" + Counts + ", PageCount=" + PageCount + ", JokeList=" + JokeList + "]";
	}

}
