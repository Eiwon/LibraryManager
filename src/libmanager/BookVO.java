package libManager;

public class BookVO {
	private String bookId;
	private String name;
	private String writer;
	private String category;
	private String publisher;
	private String pubDate;
	private String state;
	
	public BookVO() {}
	public BookVO(String bookId, String name, String writer, String category, String publisher, String pubDate,
			String state) {
		super();
		this.bookId = bookId;
		this.name = name;
		this.writer = writer;
		this.category = category;
		this.publisher = publisher;
		this.pubDate = pubDate;
		this.state = state;
	}
	
	public BookVO(String bookId, String name, String writer, String category, String publisher, String pubDate) {
		super();
		this.bookId = bookId;
		this.name = name;
		this.writer = writer;
		this.category = category;
		this.publisher = publisher;
		this.pubDate = pubDate;
		this.state = "정상";
	}

	
	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return "BookVO [bookId=" + bookId + ", name=" + name + ", writer=" + writer + ", category=" + category
				+ ", publisher=" + publisher + ", pubDate=" + pubDate + ", state=" + state + "]";
	}
	
	
}
