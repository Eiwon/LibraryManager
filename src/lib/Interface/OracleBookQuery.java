package lib.Interface;

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
	public static final String CHECK_OUT_DATE = "CHECK_OUT_DATE";
	
	public static final String BOOK_STATE_OUT = "OUT";
	public static final String BOOK_STATE_SET = "SET";
	public static final String BOOK_STATE_RSV = "RESERVED";
	public static final String BOOK_STATE_RSVSET = "SET(RESERVED)";
	public static final String BOOK_STATE_LOST = "LOST";
	
	public static final String CATEGORY_FICTION = "FICTION";
	public static final String CATEGORY_SCIENCE = "SCIENCE";
	public static final String CATEGORY_HISTORY = "HISTORY";
	public static final String CATEGORY_NONFICTION = "NONFICTION";
	public static final String CATEGORY_DRAMA = "DRAMA";
	public static final String CATEGORY_POETRY = "POETRY";
	
	// BOOK 테이블의 모든 데이터를 페이지별 검색
	public static final String SQL_SELECT_ALL = 
			"SELECT * FROM ( SELECT " + TABLE_BOOK + ".*, ROW_NUMBER() OVER (ORDER BY " + BOOK_ID + " DESC) AS RN FROM " 
			+ TABLE_BOOK + " ) WHERE RN BETWEEN ? AND ?";
			
	// BOOK 테이블의 이름이 ?인 열 검색(name)
	public static final String SQL_SELECT_BY_NAME = 
			"SELECT * FROM ( SELECT " + TABLE_BOOK + ".*, ROW_NUMBER() OVER (ORDER BY " + BOOK_ID + " DESC) AS RN FROM " 
					+ TABLE_BOOK + " WHERE " + NAME + " LIKE ?) WHERE RN BETWEEN ? AND ?";
	
	// BOOK 테이블의 저자가 ?인 열 검색(writer)
	public static final String SQL_SELECT_BY_WRITER = 
			"SELECT * FROM ( SELECT " + TABLE_BOOK + ".*, ROW_NUMBER() OVER (ORDER BY " + BOOK_ID + " DESC) AS RN FROM " 
					+ TABLE_BOOK + " WHERE " + WRITER + " LIKE ?) WHERE RN BETWEEN ? AND ?";
	
	// BOOK 테이블의 카테고리가 ?인 열 검색(category)
	public static final String SQL_SELECT_BY_CATEGORY = 
			"SELECT * FROM ( SELECT " + TABLE_BOOK + ".*, ROW_NUMBER() OVER (ORDER BY " + BOOK_ID + " DESC) AS RN FROM " 
					+ TABLE_BOOK + " WHERE " + CATEGORY + " LIKE ?) WHERE RN BETWEEN ? AND ?";
	
	
	
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
			"INSERT INTO " + TABLE_CHECK_OUT + " VALUES( ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD HH24-MI-SS'), TO_DATE(?, 'YYYY-MM-DD HH24-MI-SS'))";
	
	// check_out 테이블로부터, 특정 book을 대출 또는 예약한 유저의 user_id 확인
	public static final String SQL_SELECT_USER_BY_BOOK_STATE = 
			"SELECT " + OracleUserQuery.USER_ID + " FROM " + TABLE_CHECK_OUT + " WHERE " + BOOK_ID + " = ? AND " + STATE + " = ?" ;
	
	// check_out 테이블로부터, 지정한 userId의 행 검색
	
	//c.state, c.bookid, b.name, b.writer, b.category, b.state, c.checkoutdate, c.chechindate, b.image
	public static final String SQL_SELECT_ALL_INFO_BY_USER =
				"SELECT " + TABLE_CHECK_OUT + "." + STATE + ", " + TABLE_CHECK_OUT + "." + BOOK_ID +
				", " + TABLE_BOOK + "." + NAME + ", " + TABLE_BOOK + "." + WRITER + ", " + TABLE_BOOK + "."
				+ CATEGORY + ", " + TABLE_BOOK + "." + STATE + ", " + TABLE_CHECK_OUT + "." + CHECK_OUT_DATE 
				+ ", " + TABLE_CHECK_OUT + "." + CHECK_IN_DATE + ", "+ TABLE_BOOK + "." + IMAGE + " FROM " + TABLE_CHECK_OUT + " JOIN " + TABLE_BOOK + " ON " + 
				TABLE_CHECK_OUT + "." + BOOK_ID + " = " + TABLE_BOOK + "." + BOOK_ID + " WHERE " + OracleUserQuery.USER_ID + " = ?";
		//"대출/예약", "도서 코드", "제목", "저자", "카테고리", "상태", "대출/예약일", "반납/예약만료일"
		
	public static final String SQL_DELETE_BY_BOOK_ID = 
			"DELETE " + TABLE_CHECK_OUT + " WHERE " + BOOK_ID + " = ? AND " + STATE + " = ?";
	public static final String SQL_UPDATE_STATE_BY_BOOK_ID = 
			"UPDATE " + TABLE_BOOK + " SET " + STATE + " = ? WHERE " + BOOK_ID + " = ?";  
	
	// book_id로 선택하여 반납기한 재지정 
	public static final String SQL_UPDATE_CHECK_IN_DATE =
			"UPDATE " + TABLE_CHECK_OUT + " SET " + CHECK_IN_DATE + " = ? WHERE " + BOOK_ID + " = ?";
	
}
