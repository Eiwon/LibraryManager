package libManager;

public interface OracleBookQuery {

	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속할 오라클 경로
	public static final String USER = "scott";
	public static final String PASSWORD = "tiger";
	
	public static final String TABLE_NAME = "BOOK";
	public static final String BOOK_ID = "BOOK_ID"; 
	public static final String NAME = "NAME"; 
	public static final String WRITER = "WRITER"; 
	public static final String CATEGORY = "CATEGORY";
	public static final String PUBLISHER = "PUBLISHER"; 
	public static final String PUB_DATE = "PUB_DATE";
	public static final String STATE = "STATE";
	public static final String IMAGE = "IMAGE"; 
	
	public static final String BOOK_STATE_OUT = "OUT";
	public static final String BOOK_STATE_STD = "STANDARD";
	public static final String BOOK_STATE_RSV = "RESERVED";
	public static final String BOOK_STATE_LOST = "LOST";
	
	public static final String CATEGORY_FICTION = "FICTION";
	public static final String CATEGORY_SCIENCE = "SCIENCE";
	public static final String CATEGORY_HISTORY = "HISTORY";
	public static final String CATEGORY_NONFICTION = "NONFICTION";
	public static final String CATEGORY_DRAMA = "DRAMA";
	public static final String CATEGORY_POETRY = "POETRY";
	
	
	// INSERT INTO BOOK VALUES ( book_id_gen.nextval, ?, ?, ?, ?, ?, ?, ?)
	// 도서 등록 : 도서ID(PK), 도서명, 저자, 출판사, 출판일, 카테고리, (표지 이미지)
	public static final String SQL_INSERT_BOOK = "INSERT INTO " + TABLE_NAME
			+ " VALUES ( BOOK_ID_GEN.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String SQL_SELECT_BY_CATEGORY = 
			"SELECT * FROM " + TABLE_NAME + " WHERE ? = ?";
	
	// - 도서 검색
	// 1. 도서명, 저자, 카테고리 중 하나를 선택하여 관련 도서 검색
	// 2. 지정한 도서의 정보와 CHECK_OUT테이블 정보 검색
	
	
	
}
