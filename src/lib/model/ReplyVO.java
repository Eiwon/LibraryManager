package lib.model;

import java.time.LocalDateTime;

public class ReplyVO {
	private int id;
	private int postId;
	private String userId;
	private String content;
	private LocalDateTime writeDate;
	
	public ReplyVO(int id, int postId, String userId, String content, LocalDateTime writeDate) {
		super();
		this.id = id;
		this.postId = postId;
		this.userId = userId;
		this.content = content;
		this.writeDate = writeDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(LocalDateTime writeDate) {
		this.writeDate = writeDate;
	}
	
	
}
