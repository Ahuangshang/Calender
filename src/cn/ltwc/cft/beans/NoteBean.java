package cn.ltwc.cft.beans;
/**
 * 
 * TODO:记事本的JavaBean
 * @author huangshang
 * 2015-11-24 下午5:50:56
 * @Modified_By:
 */
public class NoteBean {
	private String noteTitle;
	private String noteContent;
	private String completeTime;

	public String getNoteTitle() {
		return noteTitle;
	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	public String getNoteContent() {
		return noteContent;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public NoteBean(String noteTitle, String noteContent, String completeTime) {
		super();
		this.noteTitle = noteTitle;
		this.noteContent = noteContent;
		this.completeTime = completeTime;
	}

	public NoteBean() {
		super();
	}

	@Override
	public String toString() {
		return "NoteBean [noteTitle=" + noteTitle + ", noteContent="
				+ noteContent + ", completeTime=" + completeTime + "]";
	}

}
