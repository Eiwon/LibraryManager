package libManager.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.UserVO;
import libManager.Interface.OracleBookQuery;
import libManager.Interface.OracleUserQuery;
import libManager.Interface.UserDAO;
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
	public int registerBlackList(String userId, String banDate, String releaseDate) {
		System.out.println("userdaoimple : insertBlackList");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_INSERT_BLACK);
			
			pstmt.setString(1, userId);
			pstmt.setString(2, banDate);
			pstmt.setString(3, releaseDate);
			
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
		String[] res = new String[3];
		int index = 0;
		
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_BY_USERID_FROM_BLACK);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				res[index] = rs.getString(index + 1);
				index++;
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
		if(index == 0)
			res = null;
		
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
	public String getCheckinDate(String userId) {
		System.out.println("userdaoimple : getCheckinDate");
		String res = null;
		
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_EARLIEST_CHECK_IN);
			pstmt.setString(1, userId);
			pstmt.setString(2, OracleBookQuery.BOOK_STATE_OUT);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				res = rs.getString(1);
			}
			System.out.println("가장 빠른 반납일 검색 성공");
			
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
