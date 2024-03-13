package lib.module.room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import lib.model.BookVO;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.OraclePreparedStatement;

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
	public int insertNewSeat(SeatVO seat) {
		System.out.println("roomDaoImple : insertNewSeat()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_INSERT_NEW_SEAT);
			
			pstmt.setInt(1, seat.seatId);
			pstmt.setInt(2, seat.x);
			pstmt.setInt(3, seat.y);
			
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
	public ArrayList<SeatVO> selectAllSeat() {
		System.out.println("roomDAOImple : selectAllSeat()");
		ArrayList<SeatVO> list = new ArrayList<>();
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_ALL_SEAT);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SeatVO su = new SeatVO(rs.getInt(1), rs.getInt(2), rs.getInt(3));
				su.setUserId(rs.getString(4));
				su.setStart(rs.getTimestamp(5) != null ? rs.getTimestamp(5).toLocalDateTime() : null);
				su.setEnd(rs.getTimestamp(6) != null ? rs.getTimestamp(6).toLocalDateTime() : null);
				su.setState(rs.getString(7));
				list.add(su);
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
			pstmt.setString(1, seat.userId);
			pstmt.setTimestamp(2, Timestamp.valueOf(seat.start));
			pstmt.setTimestamp(3, Timestamp.valueOf(seat.end));
			pstmt.setString(4, seat.state);
			pstmt.setInt(5, seat.seatId);
			
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
	public int emptySeat(int seatId) {
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
			pstmt.setInt(5, seatId);
			
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
	public int extendSeat(int seatId) {
		System.out.println("roomDaoImple : extendSeat()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_EXTEND_SEAT);
			//  endtime, seatId
			pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now().plusHours(4)));
			pstmt.setInt(2, seatId);
			
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
	
	
}
