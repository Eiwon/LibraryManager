package lib.Interface;

public interface OracleUserQuery {
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속할 오라클 경로
	public static final String USER = "scott";
	public static final String PASSWORD = "tiger";
	
	public static final String TABLE_NAME = "USER_INFO";
	public static final String TABLE_CHECK_OUT = "CHECK_OUT";
	public static final String TABLE_BLACK_LIST = "BLACK_LIST";
	
	public static final String USER_ID = "USER_ID";
	public static final String PW = "PW";
	public static final String NAME = "NAME";
	public static final String PHONE = "PHONE";
	public static final String EMAIL = "EMAIL";
	public static final String AUTH = "AUTH";
	
	public static final String STATE = "STATE";
	public static final String CHECK_IN_DATE = "CHECK_IN_DATE";	
	public static final String CHECK_OUT_DATE = "CHECK_OUT_DATE";
	
	public static final String BAN_DATE = "BAN_DATE";
	public static final String RELEASE_DATE = "RELEASE_DATE";
	
	
	public static final String AUTH_USER = "USER";
	public static final String AUTH_ADMIN = "ADMIN";
	
	
	
	public static final String SQL_INSERT = 
			"INSERT INTO " + TABLE_NAME + " VALUES(?, ?, ?, ?, ?, 'USER')";
	
	public static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME
			+ " WHERE " + USER_ID + " = ?";
	public static final String SQL_SELECT_WITH_PW = "SELECT * FROM " + TABLE_NAME + 
			" WHERE " + USER_ID + " = ? and " + PW + " = ?";
	
	// 블랙리스트 등록(userid, ban 날짜, 해제 날짜)
	public static final String SQL_INSERT_BLACK = "INSERT INTO " + TABLE_BLACK_LIST + 
			" VALUES ( ?, ?, ?)";
	// 블랙리스트에서 userId로 검색
	public static final String SQL_SELECT_BY_USERID_FROM_BLACK = "SELECT * FROM " + TABLE_BLACK_LIST
			+ " WHERE " + USER_ID + " = ?";
	// 블랙리스트에서 userId로 삭제
	public static final String SQL_DELETE_BY_USERID = "DELETE " + TABLE_BLACK_LIST + 
			" WHERE " + USER_ID + " = ?";
	// check_out 에서 userId로 반납일자 검색
	public static final String SQL_SELECT_EARLIEST_CHECK_IN = "SELECT MIN(" + CHECK_IN_DATE + ") FROM "
			+ TABLE_CHECK_OUT + " WHERE " + USER_ID + " = ? AND " + STATE + " = ?";
			
}
