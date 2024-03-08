package libManager.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.BookVO;
import libManager.Interface.BookDAO;
import libManager.Interface.OracleBookQuery;
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
	public int updateBook(BookVO vo) {
		System.out.println("bookDaoImple : updateBook()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_UPDATE);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getWriter());
			pstmt.setString(3, vo.getCategory());
			pstmt.setString(4, vo.getPublisher());
			pstmt.setString(5, vo.getPubDate());
			pstmt.setString(6, vo.getState());
			pstmt.setString(7, vo.getImg());
			pstmt.setInt(8, vo.getBookId());
			System.out.println(SQL_UPDATE);
			System.out.println(vo.toString());
			res = pstmt.executeUpdate();
			
			if(res == 1)
				System.out.println("수정 성공");
			
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
	} // end updateBook
	
	public int deleteBook(int book_id) {
		System.out.println("bookDaoImple : updateBook()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_DELETE);
			
			pstmt.setInt(1, book_id);
			res = pstmt.executeUpdate();
			
			if(res == 1)
				System.out.println("삭제 성공");
			
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
	} // end deleteBook
	
	
	@Override
	public ArrayList<BookVO> selectAll() {
		
		System.out.println("bookDaoImple : selectByAll()");
		ArrayList<BookVO> list = new ArrayList<>();
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_ALL);
			//pstmt.setString(1, "IMAGE");
			//pstmt.setString(2, "NULL");
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new BookVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
			}
			System.out.println(SQL_SELECT_ALL);
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
	public ArrayList<BookVO> selectByValue(String tag, String word) {
		System.out.println("bookDaoImple : selectByValue()");
		ArrayList<BookVO> list = new ArrayList<>();
		String searchWord = '%' + word + '%';
		String sql_query;
		if(tag.equals(NAME)) {
			sql_query = SQL_SELECT_BY_NAME;
		}else if(tag.equals(WRITER)) {
			sql_query = SQL_SELECT_BY_NAME;
		}else {
			sql_query = SQL_SELECT_BY_CATEGORY;
		}
		
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			pstmt = conn.prepareStatement(sql_query);
			pstmt.setString(1, searchWord);
			
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

	
	public String selectCheckinDate(int bookId) {
		
		System.out.println("bookDaoImple : selectCheckinDate()");
		String res = "";
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			pstmt = conn.prepareStatement(OracleBookQuery.SQL_SELECT_CHECKIN_DATE);
			pstmt.setInt(1, bookId);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				res = rs.getString(1);
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
		
		return res;
	}

	@Override
	public int insertCheckoutBook(int bookId, String userId, String state, String checkoutDate, String checkinDate) {
		System.out.println("bookDaoImple : checkoutBook()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_INSERT_CHECK_OUT);
			
			pstmt.setInt(1, bookId);
			pstmt.setString(2, userId);
			pstmt.setString(3, state);
			pstmt.setString(4, checkoutDate);
			pstmt.setString(5, checkinDate);
			
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
	
	public String selectByBookState(int bookId, String state) {
		System.out.println("bookDaoImple : selectByBookState()");
		String userId = "";
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			pstmt = conn.prepareStatement(OracleBookQuery.SQL_SELECT_USER_BY_BOOK_STATE);
			pstmt.setInt(1, bookId);
			pstmt.setString(2, state);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userId = rs.getString(1);
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
		
		return userId;
	}

	@Override
	public ArrayList<ArrayList<String>> selectAllInfoById(String userId) {
		System.out.println("bookDaoImple : selectAllInfoById()");
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			pstmt = conn.prepareStatement(OracleBookQuery.SQL_SELECT_ALL_INFO_BY_USER);
			pstmt.setString(1, userId);
			
			ResultSet rs = pstmt.executeQuery();
			//C.BOOK_ID, B.STATE, C.CHECK_OUT_DATE, C.CHECK_IN_DATE, B.NAME, B.WRITER, B.CATEGORY, C.STATE, B.IMAGE 
			
			while(rs.next()) {
				ArrayList<String> l = new ArrayList<>(); 
				l.add(rs.getString(1));
				l.add(String.valueOf(rs.getInt(2)));
				for(int i = 3; i < 9; i++) {
					l.add(rs.getString(i));
				}
				list.add(l);
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
	public int deleteByBookId(int bookId, String state) {
		System.out.println("bookDaoImple : deleteByBookId()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_DELETE_BY_BOOK_ID);
			
			pstmt.setInt(1, bookId);
			pstmt.setString(2, state);
			res = pstmt.executeUpdate();
			
			if(res == 1)
				System.out.println("삭제 성공");
			
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
	public int updateByBookId(String state, int bookId) {
		System.out.println("bookDaoImple : updateByBookId()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_UPDATE_STATE_BY_BOOK_ID);
			
			pstmt.setString(1, state);
			pstmt.setInt(2, bookId);
			res = pstmt.executeUpdate();
			
			if(res == 1)
				System.out.println("수정 성공");
			
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
	public int updateCheckinDate(int bookId, String checkinDate) {
		System.out.println("bookDaoImple : updateCheckinDate()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_UPDATE_CHECK_IN_DATE);
			System.out.println(checkinDate);
			pstmt.setString(1, checkinDate);
			pstmt.setInt(2, bookId);
			res = pstmt.executeUpdate();
			
			if(res == 1)
				System.out.println("수정 성공");
			
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
