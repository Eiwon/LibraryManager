package lib.module.room;

public interface OracleRoomQuery {
	
	public static final String URL = "jdbc:oracle:thin:@192.168.0.146:1521:xe"; // 접속할 오라클 경로
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
	
	// 좌석 상태
	public static final String STATE_EMPTY = "EMPTY";
	public static final String STATE_OCCUPIED = "OCCUPIED";
	
	// 모든 좌석의 SEAT_ID 검색
	public static final String SQL_SELECT_ALL_SEAT_ID = 
			"SELECT " + SEAT_ID + " FROM " + TABLE_SEAT;
	// 열람실 좌석 추가
	public static final String SQL_INSERT_NEW_SEAT =
			"INSERT INTO " + TABLE_SEAT + " (" + SEAT_ID + ", " + POSITION_X + ", " + POSITION_Y
			+ ") VALUES (?, ?, ?)";
	// 열람식 좌석 삭제
	public static final String SQL_DELETE_SEAT = 
			"DELETE " + TABLE_SEAT + " WHERE " + SEAT_ID + " = ?";
	
	// 열람실 좌석 배치 불러오기
	public static final String SQL_SELECT_ALL_SEAT =
			"SELECT * FROM " + TABLE_SEAT;
	// 열람실 좌석 선택, 반납시 좌석 상태 업데이트
	public static final String SQL_UPDATE_SEAT = 
			"UPDATE " + TABLE_SEAT + " SET " + USER_ID + " = ?, " + START_TIME + " = ?, " 
			+ END_TIME + " = ?, " + STATE + " = ? WHERE " + SEAT_ID + " = ?";
	// seatId에 해당하는 좌석의 반납기한 변경(연장)
	public static final String SQL_EXTEND_SEAT = 
			"UPDATE " + TABLE_SEAT + " SET " + END_TIME + " = ? WHERE " + SEAT_ID + " = ?";
	// 해당 userId로 선택된 좌석이 이미 있는지 확인
	public static final String SQL_SELECT_BY_UID = 
			"SELECT * FROM " + TABLE_SEAT + " WHERE " + USER_ID + " = ?";
	
}