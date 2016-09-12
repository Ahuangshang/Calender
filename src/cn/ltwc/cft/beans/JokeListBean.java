package cn.ltwc.cft.beans;

/**
 * 
 * TODO:内涵段子返回数据的Bean 笑话的内容
 * 
 * @author huangshang 2015-11-24 下午3:00:45
 * @Modified_By:
 */
public class JokeListBean {
	private String BillNo;// 201511050046
	private String JokeTitle;// 买衣服的理由
	private String JokeContent;// 女：“你是喜欢我走时尚路线呢还是走复古路线呢？”\r\n男：“时尚路线吧！”\r\n女：“那你TM还不陪我去逛街，今年冬天的新款衣服还没买呢！”\r\n男...
	// // ... \r\n
	// private String Count;// 0,
	// private String Collect;// 0,
	private String Type;// 26,

	// private String IsHtml;// false,
	// private String AddTime;// /Date(-62135596800000)/

	public String getBillNo() {
		return BillNo;
	}

	public void setBillNo(String billNo) {
		BillNo = billNo;
	}

	public String getJokeTitle() {
		return JokeTitle;
	}

	public void setJokeTitle(String jokeTitle) {
		JokeTitle = jokeTitle;
	}

	public String getJokeContent() {
		return JokeContent;
	}

	public void setJokeContent(String jokeContent) {
		JokeContent = jokeContent;
	}

	// public String getCount() {
	// return Count;
	// }
	//
	// public void setCount(String count) {
	// Count = count;
	// }
	//
	// public String getCollect() {
	// return Collect;
	// }
	//
	// public void setCollect(String collect) {
	// Collect = collect;
	// }
	//
	public String getType() {
		return Type;
	}

	public void setType(String jokeType) {
		Type = jokeType;
	}

	//
	// public String getIsHtml() {
	// return IsHtml;
	// }
	//
	// public void setIsHtml(String isHtml) {
	// IsHtml = isHtml;
	// }
	//
	// public String getAddTime() {
	// return AddTime;
	// }
	//
	// public void setAddTime(String addTime) {
	// AddTime = addTime;
	// }
	//

	@Override
	public String toString() {
		return "JokeListBean [BillNo=" + BillNo + ", JokeTitle=" + JokeTitle + ", JokeContent=" + JokeContent
				+ ", Type=" + Type + "]";
	}

	public JokeListBean(String billNo, String jokeTitle, String jokeContent, String type) {
		super();
		BillNo = billNo;
		JokeTitle = jokeTitle;
		JokeContent = jokeContent;
		Type = type;
	}

}
