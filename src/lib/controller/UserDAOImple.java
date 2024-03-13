package lib.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import lib.Interface.OracleBookQuery;
import lib.Interface.OracleUserQuery;
import lib.Interface.UserDAO;
import lib.model.UserVO;
import oracle.jdbc.OracleDriver;

public class UserDAOImple implements UserDAO, OracleUserQuery{
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	private static UserDAOImple instance = null;
	
	//싱글톤
	private UserDAOImple() {}
	
	public static UserDAOImple getInstance() {
		if(instance == null) {
			instance = new UserDAOImple();
		}
		return instance;
	}

	@Override
	public int insertUser(UserVO vo) {
		System.out.println("userdaoimple : insertUser");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_INSERT);
			
			pstmt.setString(1, vo.getUserId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getPhone());
			pstmt.setString(5, vo.getEmail());
			
			res = pstmt.executeUpdate();
			if(res == 1) {
				System.out.println("유저 등록 성공");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	} // end insertUser

	
	@Override
	public UserVO selectByID(String userId) {
		System.out.println("userdaoimple : selectByID");
		UserVO vo = null;
		
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_BY_ID);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
				vo.setAuth(rs.getString(6));
			}
			System.out.println("유저 지정 검색 성공");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return vo;
	} // end selectById

	@Override
	public UserVO selectWithPw(String userId, String pw) {
		System.out.println("userdaoimple : selectWithPw");
		UserVO vo = null;
		
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_WITH_PW);
			pstmt.setString(1, userId);
			pstmt.setString(2, pw);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
				vo.setAuth(rs.getString(6));
			}
			System.out.println("유저 지정 검색 성공");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return vo;
	}

	@Override
	public int registerBlackList(String userId, LocalDateTime banDate, LocalDateTime releaseDate) {
		System.out.println("userdaoimple : insertBlackList");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_INSERT_BLACK);
			
			pstmt.setString(1, userId);
			pstmt.setTimestamp(2, Timestamp.valueOf(banDate));
			pstmt.setTimestamp(3, Timestamp.valueOf(releaseDate));
			
			res = pstmt.executeUpdate();
			if(res == 1) {
				System.out.println("블랙리스트 등록 성공");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	} // end insertBlackList

	@Override
	public String[] searchBlackList(String userId) {
		System.out.println("userdaoimple : searchBlackList");
		String[] res = null;
		
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_BY_USERID_FROM_BLACK);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			LocalDateTime temp;
			if(rs.next()) {
				res = new String[3];
				res[0] = rs.getString(1);
				res[1] = rs.getTimestamp(2).toLocalDateTime().toString();
				res[2] = rs.getTimestamp(3).toLocalDateTime().toString();
			}
			System.out.println("블랙리스트로부터 유저 검색 성공");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return res;
	}

	@Override
	public int deleteFromBlackList(String userId) {
		System.out.println("userdaoimple : deleteFromBlackList");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_DELETE_BY_USERID);
			
			pstmt.setString(1, userId);
			
			res = pstmt.executeUpdate();
			if(res == 1) {
				System.out.println("블랙리스트에서 유저 삭제 성공");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	} // deleteFromBlackList

	@Override
	public LocalDateTime getCheckinDate(String userId) {
		System.out.println("userdaoimple : getCheckinDate");
		LocalDateTime res = null;
		
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_EARLIEST_CHECK_IN);
			pstmt.setString(1, userId);
			pstmt.setString(2, OracleBookQuery.BOOK_STATE_OUT);
			ResultSet rs = pstmt.executeQuery();
			// 검색된 열이 없으면 MIN() 결과값은 ResultSet {null} 반환
			if(rs.next()) { 
				if(rs.getTimestamp(1) != null) {
					res = rs.getTimestamp(1).toLocalDateTime();
				}
				System.out.println("가장 빠른 반납일 검색 성공");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	} // getCheckinDate


}
