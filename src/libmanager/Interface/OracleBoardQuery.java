package libManager.Interface;

public interface OracleBoardQuery {
	
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속할 오라클 경로
	public static final String USER = "scott";
	public static final String PASSWORD = "tiger";
	
	// 테이블 명
	public static final String TABLE_POST = "POST";
	public static final String TABLE_REPLY = "REPLY";
	
	// post 테이블 
	public static final String USER_ID = "USER_ID";
	public static final String POST_ID = "POST_ID";
	public static final String POST_TITLE = "POST_TITLE";
	public static final String POST_CONTENT = "POST_CONTENT";
	public static final String TAG = "TAG";
	public static final String VIEWS = "VIEWS";
	public static final String WRITE_DATE = "WRITE_DATE";
	
	// reply 테이블
	public static final String REPLY_ID = "REPLY_ID";
	public static final String REPLY_CONTENT = "REPLY_CONTENT";
	
	// 게시글 추가
	public static final String SQL_INSERT_POST = "INSERT INTO " + TABLE_POST 
			+ " VALUES (POST_ID_SEQ.NEXTVAL, ?, ?, ?, ?, 0, SYSDATE)";
	// 댓글 추가
	public static final String SQL_INSERT_REPLY = "INSERT INTO " + TABLE_REPLY + 
			" VALUES (REPLY_ID_SEQ.NEXTVAL, ?, ?, ?, ?)";

	// 게시글 수정
	public static final String SQL_UPDATE_POST = "UPDATE " + TABLE_POST + " SET " + POST_TITLE + " = ?, " + POST_CONTENT
			+ " = ?, " + TAG + " = ? WHERE " + POST_ID + " = ?";

	// 댓글 수정
	public static final String SQL_UPDATE_REPLY = "UPDATE " + TABLE_REPLY + " SET " + REPLY_CONTENT + " = ? WHERE "
			+ REPLY_ID + " = ?";

	// 게시글 삭제
	public static final String SQL_DELETE_POST = "DELETE " + TABLE_POST + " WHERE " + POST_ID + " = ?";

	// 댓글 삭제
	public static final String SQL_DELETE_REPLY = "DELETE " + TABLE_REPLY + " WHERE " + REPLY_ID + " = ?";

	// 게시글 전체 검색
	public static final String SQL_SELECT_PARTIAL = "SELECT * FROM ( SELECT " + TABLE_POST
			+ ".*, ROW_NUMBER() OVER (ORDER BY " + WRITE_DATE + " DESC) AS RN FROM " + TABLE_POST
			+ ") WHERE RN BETWEEN ? AND ?";

	// 게시글 ID 지정 검색
	public static final String SQL_SELECT_POST_BY_ID = "SELECT * FROM " + TABLE_POST + " WHERE " + POST_ID
			+ " = ?";

	// 게시글 USER ID 지정 검색
	public static final String SQL_SELECT_POST_BY_UID = "SELECT * FROM ( SELECT " + TABLE_POST
			+ ".*, ROW_NUMBER() OVER (ORDER BY " + WRITE_DATE + " DESC) AS RN FROM " + TABLE_POST
			+ " WHERE " + USER_ID + " = %?%) WHERE RN BETWEEN ? AND ?";
	// 게시글 TAG 지정 검색
	public static final String SQL_SELECT_POST_BY_TAG = "SELECT * FROM ( SELECT " + TABLE_POST
			+ ".*, ROW_NUMBER() OVER (ORDER BY " + WRITE_DATE + " DESC) AS RN FROM " + TABLE_POST
			+ " WHERE " + TAG + " = ?) WHERE RN BETWEEN ? AND ?";
	// 게시글 TITLE 지정 검색
	public static final String SQL_SELECT_POST_BY_TITLE = "SELECT * FROM ( SELECT " + TABLE_POST
			+ ".*, ROW_NUMBER() OVER (ORDER BY " + WRITE_DATE + " DESC) AS RN FROM " + TABLE_POST
			+ " WHERE " + POST_TITLE + " = %?%) WHERE RN BETWEEN ? AND ?";

	// 게시글 ID 지정 댓글 검색
	public static final String SQL_SELECT_REPLY_BY_PID = "SELECT * FROM ( SELECT " + TABLE_REPLY
			+ ".*, ROW_NUMBER() OVER (ORDER BY " + WRITE_DATE + " DESC) AS RN FROM " + TABLE_REPLY
			+ " WHERE " + POST_ID + " = ?) WHERE RN BETWEEN ? AND ?";
}
