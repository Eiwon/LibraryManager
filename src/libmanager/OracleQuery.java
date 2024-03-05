package libManager;

public interface OracleQuery {

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
	
	
	
	
	
}
