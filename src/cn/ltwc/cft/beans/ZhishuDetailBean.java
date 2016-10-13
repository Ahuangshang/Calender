package cn.ltwc.cft.beans;

public class ZhishuDetailBean {
	private String title;
	private String url;
	private String summary;
	private String source;
	private String[] images;
	private String imageNum;

	public ZhishuDetailBean(String title, String url, String summary,
			String source, String[] images, String imageNum) {
		super();
		this.title = title;
		this.url = url;
		this.summary = summary;
		this.source = source;
		this.images = images;
		this.imageNum = imageNum;
	}

	public ZhishuDetailBean() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public String getImageNum() {
		return imageNum;
	}

	public void setImageNum(String imageNum) {
		this.imageNum = imageNum;
	}

}
