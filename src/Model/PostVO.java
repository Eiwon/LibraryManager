package Model;

import java.time.LocalDateTime;

public class PostVO {
	private int id;
	private String title;
	private String content;
	private String userId;
	private String tag;
	private int views;
	private String writeDate;
	
	public PostVO(int id, String title, String content, String userId, String tag, int views, String writeDate) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.userId = userId;
		this.tag = tag;
		this.views = views;
		this.writeDate = writeDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	
	
}
