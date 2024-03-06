package libManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleDriver;

public class BookDAOImple implements BookDAO, OracleBookQuery{

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private static BookDAOImple instance = null;
	
	private BookDAOImple() {}
	
	public static BookDAOImple getInstance() {
		if(instance == null)
			instance = new BookDAOImple();
		return instance;
	}
	
	
	@Override
	public int insertBook(BookVO vo) {
		System.out.println("bookDaoImple : inserBook()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_INSERT_BOOK);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getWriter());
			pstmt.setString(3, vo.getCategory());
			pstmt.setString(4, vo.getPublisher());
			pstmt.setString(5, vo.getPubDate());
			pstmt.setString(6, vo.getState());
			pstmt.setString(7, vo.getImg());
			
			res = pstmt.executeUpdate();
			System.out.println(res + "권 추가 성공");
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
	} // end insertBook

	@Override
	public ArrayList<BookVO> selectByCategory() {
		
		System.out.println("bookDaoImple : selectByCategory()");
		ArrayList<BookVO> list = new ArrayList<>();
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_BY_CATEGORY);
			pstmt.setString(1, "IMAGE");
			pstmt.setString(2, "NULL");
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new BookVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
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

}
