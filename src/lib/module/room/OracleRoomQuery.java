package lib.module.room;

public interface OracleRoomQuery {
	
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속할 오라클 경로
	public static final String USER = "scott";
	public static final String PASSWORD = "tiger";
	
	public static final String TABLE_SEAT = "SEAT";
	public static final String SEAT_ID = "SEAT_ID";
	public static final String POSITION_X = "POSITION_X";
	public static final String POSITION_Y = "POSITION_Y";
	public static final String USER_ID = "USER_ID";
	public static final String START_TIME = "START_TIME";
	public static final String END_TIME = "END_TIME";
	public static final String STATE = "STATE";
	
	public static final String STATE_EMPTY = "EMPTY";
	public static final String STATE_OCCUPIED = "OCCUPIED";
	
	
	public static final String SQL_INSERT_NEW_SEAT =
			"INSERT INTO " + TABLE_SEAT + " (" + SEAT_ID + ", " + POSITION_X + ", " + POSITION_Y
			+ ") VALUES (?, ?, ?)";
	
	public static final String SQL_SELECT_ALL_SEAT =
			"SELECT * FROM " + TABLE_SEAT;
	public static final String SQL_UPDATE_SEAT = 
			"UPDATE " + TABLE_SEAT + " SET " + USER_ID + " = ?, " + START_TIME + " = ?, " 
			+ END_TIME + " = ?, " + STATE + " = ? WHERE " + SEAT_ID + " = ?";
	public static final String SQL_EXTEND_SEAT = 
			"UPDATE " + TABLE_SEAT + " SET " + END_TIME + " = ? WHERE " + SEAT_ID + " = ?";
	
}
//INSERT INTO SEAT (SEAT_ID, POSITION_X, POSITION_Y)  
//VALUES (?, ?, ?);