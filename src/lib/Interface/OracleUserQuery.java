package lib.Interface;

public interface OracleUserQuery {
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속할 오라클 경로
	public static final String USER = "scott";
	public static final String PASSWORD = "tiger";
	
	// 테이블 명
	public static final String TABLE_USER_INFO = "USER_INFO";
	public static final String TABLE_CHECK_OUT = "CHECK_OUT";
	public static final String TABLE_BLACK_LIST = "BLACK_LIST";
	
	// USER_INFO 테이블
	public static final String USER_ID = "USER_ID";
	public static final String PW = "PW";
	public static final String NAME = "NAME";
	public static final String PHONE = "PHONE";
	public static final String EMAIL = "EMAIL";
	public static final String AUTH = "AUTH";
	
	// CHECK_OUT 테이블
	public static final String STATE = "STATE";
	public static final String CHECK_IN_DATE = "CHECK_IN_DATE";	
	public static final String CHECK_OUT_DATE = "CHECK_OUT_DATE";
	
	// BLACK_LIST 테이블
	public static final String BAN_DATE = "BAN_DATE";
	public static final String RELEASE_DATE = "RELEASE_DATE";
	
	// 권한 설정값
	public static final String AUTH_USER = "USER";
	public static final String AUTH_ADMIN = "ADMIN";
	
	
	// 새 유저 추가
	public static final String SQL_INSERT_USER = 
			"INSERT INTO " + TABLE_USER_INFO + " VALUES(?, ?, ?, ?, ?, 'USER')";
	// User ID로 검색 (입력한 User ID가 있는지 확인)
	public static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_USER_INFO
			+ " WHERE " + USER_ID + " = ?";
	// User ID와 PW로 검색 (등록된 ID와 PW인지 확인)
	public static final String SQL_SELECT_WITH_PW = "SELECT * FROM " + TABLE_USER_INFO + 
			" WHERE " + USER_ID + " = ? and " + PW + " = ?";

	
	// check_out 에서 userId로 반납일자 검색
	public static final String SQL_SELECT_EARLIEST_CHECK_IN = "SELECT MIN(" + CHECK_IN_DATE + ") FROM "
			+ TABLE_CHECK_OUT + " WHERE " + USER_ID + " = ? AND " + STATE + " = ?";
	
	
	// 블랙리스트 등록(userid, ban 날짜, 해제 날짜)
	public static final String SQL_INSERT_BLACK = "INSERT INTO " + TABLE_BLACK_LIST + 
			" VALUES ( ?, ?, ?)";
	// 블랙리스트에서 userId로 검색
	public static final String SQL_SELECT_BY_USERID_FROM_BLACK = "SELECT * FROM " + TABLE_BLACK_LIST
			+ " WHERE " + USER_ID + " = ?";
	// 블랙리스트에서 userId로 삭제
	public static final String SQL_DELETE_BY_USERID = "DELETE " + TABLE_BLACK_LIST + 
			" WHERE " + USER_ID + " = ?";
			
}
