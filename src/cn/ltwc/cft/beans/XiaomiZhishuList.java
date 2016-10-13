package cn.ltwc.cft.beans;

public class XiaomiZhishuList {
	private String image;
	private String summary;
	private String title;
	private String headPic;
	private String channelId;
	private String indexType;

	public XiaomiZhishuList() {
		super();
	}

	public XiaomiZhishuList(String image, String summary, String title,
			String headPic, String channelId, String indexType) {
		super();
		this.image = image;
		this.summary = summary;
		this.title = title;
		this.headPic = headPic;
		this.channelId = channelId;
		this.indexType = indexType;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

}
