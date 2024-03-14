package lib.module.room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import oracle.jdbc.OracleDriver;

public class RoomDAOImple implements RoomDAO, OracleRoomQuery {
	
	private static RoomDAOImple instance = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	
	private RoomDAOImple() {}
	
	public static RoomDAOImple getInstance() {
		if(instance == null) {
			instance = new RoomDAOImple();
		}
		return instance;
	}

	@Override
	public ArrayList<String> selectAllSeatId() {
		System.out.println("roomDAOImple : selectAllSeatId()");
		ArrayList<String> list = new ArrayList<>();
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_ALL_SEAT_ID);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	
	@Override
	public int insertNewSeat(SeatVO seat) {
		System.out.println("roomDaoImple : insertNewSeat()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_INSERT_NEW_SEAT);
			
			pstmt.setString(1, seat.getSeatId());
			pstmt.setInt(2, seat.getX());
			pstmt.setInt(3, seat.getY());
			
			res = pstmt.executeUpdate();
			
			if(res == 1)
				System.out.println(res + "행 추가 성공");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}

	@Override
	public int deleteSeat(String seatId) {
		System.out.println("roomDaoImple : deleteSeat()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_DELETE_SEAT);
			
			pstmt.setString(1, seatId);
			
			res = pstmt.executeUpdate();
			
			if(res == 1)
				System.out.println(res + "행 삭제 성공");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}

	@Override
	public ArrayList<SeatVO> selectAllSeat() {
		System.out.println("roomDAOImple : selectAllSeat()");
		ArrayList<SeatVO> list = new ArrayList<>();
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_ALL_SEAT);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SeatVO vo = new SeatVO(rs.getString(1), rs.getInt(2), rs.getInt(3));
				vo.setUserId(rs.getString(4));
				vo.setStart(rs.getTimestamp(5) != null ? rs.getTimestamp(5).toLocalDateTime() : null);
				vo.setEnd(rs.getTimestamp(6) != null ? rs.getTimestamp(6).toLocalDateTime() : null);
				vo.setState(rs.getString(7));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	@Override
	public int occupySeat(SeatVO seat) {
		System.out.println("roomDaoImple : occupySeat()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_UPDATE_SEAT);
			// userid, starttime, endtime, state
			pstmt.setString(1, seat.getUserId());
			pstmt.setTimestamp(2, Timestamp.valueOf(seat.getStart()));
			pstmt.setTimestamp(3, Timestamp.valueOf(seat.getEnd()));
			pstmt.setString(4, seat.getState());
			pstmt.setString(5, seat.getSeatId());
			
			res = pstmt.executeUpdate();
			
			if(res == 1)
				System.out.println(res + "행 수정 성공");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}

	@Override
	public int emptySeat(String seatId) {
		System.out.println("roomDaoImple : emptySeat()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_UPDATE_SEAT);
			// userid, starttime, endtime, state
			pstmt.setString(1, "");
			pstmt.setTimestamp(2, null);
			pstmt.setTimestamp(3, null);
			pstmt.setString(4, OracleRoomQuery.STATE_EMPTY);
			pstmt.setString(5, seatId);
			
			res = pstmt.executeUpdate();
			
			if(res == 1)
				System.out.println(res + "행 수정 성공");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}

	@Override
	public int extendSeat(String seatId) {
		System.out.println("roomDaoImple : extendSeat()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_EXTEND_SEAT);
			//  endtime, seatId
			pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now().plusHours(4)));
			pstmt.setString(2, seatId);
			
			res = pstmt.executeUpdate();
			
			if(res == 1)
				System.out.println(res + "행 수정 성공");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}

	@Override
	public SeatVO checkSeatByUId(String userId) {
		System.out.println("roomDAOImple : checkSeatById()");
		SeatVO vo = null;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_BY_UID);
			pstmt.setString(1, userId);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new SeatVO(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
						rs.getTimestamp(5) != null ? rs.getTimestamp(5).toLocalDateTime() : null,
						rs.getTimestamp(6) != null ? rs.getTimestamp(6).toLocalDateTime() : null,
						rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}
	
}
