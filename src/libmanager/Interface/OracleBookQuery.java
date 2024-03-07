package libManager.Interface;

public interface OracleBookQuery {

	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속할 오라클 경로
	public static final String USER = "scott";
	public static final String PASSWORD = "tiger";
	
	public static final String TABLE_BOOK = "BOOK";
	public static final String BOOK_ID = "BOOK_ID"; 
	public static final String NAME = "NAME"; 
	public static final String WRITER = "WRITER"; 
	public static final String CATEGORY = "CATEGORY";
	public static final String PUBLISHER = "PUBLISHER"; 
	public static final String PUB_DATE = "PUB_DATE";
	public static final String STATE = "STATE";
	public static final String IMAGE = "IMAGE"; 
	
	public static final String TABLE_CHECK_OUT = "CHECK_OUT";
	public static final String CHECK_IN_DATE = "CHECK_IN_DATE";	
	
	public static final String BOOK_STATE_OUT = "OUT";
	public static final String BOOK_STATE_SET = "SET";
	public static final String BOOK_STATE_RSV = "RESERVED";
	public static final String BOOK_STATE_LOST = "LOST";
	
	public static final String CATEGORY_FICTION = "FICTION";
	public static final String CATEGORY_SCIENCE = "SCIENCE";
	public static final String CATEGORY_HISTORY = "HISTORY";
	public static final String CATEGORY_NONFICTION = "NONFICTION";
	public static final String CATEGORY_DRAMA = "DRAMA";
	public static final String CATEGORY_POETRY = "POETRY";
	
	// BOOK 테이블의 모든 데이터 검색
	public static final String SQL_SELECT_ALL = 
			"SELECT * FROM " + TABLE_BOOK;
	
	// BOOK 테이블의 이름이 ?인 열 검색(name)
	public static final String SQL_SELECT_BY_NAME = 
			"SELECT * FROM " + TABLE_BOOK + " WHERE " + NAME +" LIKE ?";
	
	// BOOK 테이블의 저자가 ?인 열 검색(writer)
	public static final String SQL_SELECT_BY_WRITER = 
			"SELECT * FROM " + TABLE_BOOK + " WHERE " + WRITER + " LIKE ?";
	
	// BOOK 테이블의 카테고리가 ?인 열 검색(category)
	public static final String SQL_SELECT_BY_CATEGORY = 
			"SELECT * FROM " + TABLE_BOOK + " WHERE " + CATEGORY + " LIKE ?";
	
	
	
	// 신규 도서 등록 쿼리(name, writer, category, publisher, pub_date, state, image)
	public static final String SQL_INSERT_BOOK = "INSERT INTO " + TABLE_BOOK
			+ " VALUES ( BOOK_ID_GEN.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
	
	// 입력 book_id의 도서의 모든 값을 변경(name, writer, category, publisher, pub_date, state, image, book_id)
	public static final String SQL_UPDATE = "UPDATE " + TABLE_BOOK + " SET " + 
				NAME + " = ?, " + WRITER + " = ?, " + CATEGORY + " = ?, " + PUBLISHER + 
				" = ?, " + PUB_DATE + " = TO_DATE(?, 'YYYY-MM-DD HH24-MI-SS'), " + STATE + " = ?, " + IMAGE + " = ? WHERE "
			+ BOOK_ID + " = ?";

	// 입력 book_id의 도서 삭제(book_id)
	public static final String SQL_DELETE = "DELETE " + TABLE_BOOK + " WHERE " + BOOK_ID 
			+ " = ?"; 
	
	// 입력 book_id의 반납 예정일 요청 쿼리(book_id)
	public static final String SQL_SELECT_CHECKIN_DATE = 
			"SELECT " + CHECK_IN_DATE + " FROM " + TABLE_CHECK_OUT + " WHERE "
			+ BOOK_ID + " = ?";
	
	// check_out 테이블에 데이터 추가 쿼리(book_id, user_id, state, check_out_date, check_in_date)
	public static final String SQL_INSERT_CHECK_OUT = 
			"INSERT INTO " + TABLE_CHECK_OUT + " VALUES( ?, ?, ?, SYSDATE, TO_DATE(?, 'YYYY-MM-DD HH24-MI-SS'))";
	
}
