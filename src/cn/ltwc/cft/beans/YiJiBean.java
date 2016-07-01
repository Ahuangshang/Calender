package cn.ltwc.cft.beans;

/**
 * 宜忌bean
 * 
 * @author LZL
 *
 */
public class YiJiBean {
	private String yi;
	private String ji;

	public YiJiBean() {
		super();
	}

	public YiJiBean(String yi, String ji) {
		this.yi = yi;
		this.ji = ji;
	}

	public String getYi() {
		return yi;
	}

	public void setYi(String yi) {
		this.yi = yi;
	}

	public String getJi() {
		return ji;
	}

	public void setJi(String ji) {
		this.ji = ji;
	}

	@Override
	public String toString() {
		return "YiJiBean [yi=" + yi + ", ji=" + ji + "]";
	}

}
