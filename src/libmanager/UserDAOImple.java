package libManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleDriver;

public class UserDAOImple implements UserDAO, OracleUserQuery{
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	private static UserVO currentUser = null;
	private static UserDAOImple instance = null;
	
	//싱글톤
	private UserDAOImple() {}
	
	public static UserDAOImple getInstance() {
		if(instance == null) {
			instance = new UserDAOImple();
		}
		return instance;
	}
	
	
	public static UserVO getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(UserVO currentUser) {
		UserDAOImple.currentUser = currentUser;
	}

	@Override
	public int insertUser(UserVO vo) {
		System.out.println("userdaoimple : insertUser");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_INSERT);
			
			pstmt.setString(1, vo.userId);
			pstmt.setString(2, vo.pw);
			pstmt.setString(3, vo.name);
			pstmt.setString(4, vo.phone);
			pstmt.setString(5, vo.email);
			
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

}
