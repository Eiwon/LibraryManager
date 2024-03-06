package libManager;

public interface OracleUserQuery {
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속할 오라클 경로
	public static final String USER = "scott";
	public static final String PASSWORD = "tiger";
	
	public static final String TABLE_NAME = "USER_INFO";
	public static final String USER_ID = "USER_ID";
	public static final String PW = "PW";
	public static final String NAME = "NAME";
	public static final String PHONE = "PHONE";
	public static final String EMAIL = "EMAIL";
	public static final String AUTH = "AUTH";
	
	public static final String SQL_INSERT = 
			"INSERT INTO " + TABLE_NAME + " VALUES(?, ?, ?, ?, ?, 'USER')";
	
	public static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME
			+ " WHERE " + USER_ID + " = ?";
	public static final String SQL_SELECT_WITH_PW = "SELECT * FROM " + TABLE_NAME + 
			" WHERE " + USER_ID + " = ? and " + PW + " = ?";
}
