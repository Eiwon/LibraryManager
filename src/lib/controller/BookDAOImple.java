package lib.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import lib.Interface.BookDAO;
import lib.Interface.OracleBookQuery;
import lib.model.BookVO;
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
			pstmt.setTimestamp(5, Timestamp.valueOf(vo.getPubDate()));
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
			pstmt.setTimestamp(5, Timestamp.valueOf(vo.getPubDate()));
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
	public ArrayList<BookVO> selectAll(int page) {
		
		System.out.println("bookDaoImple : selectByAll()");
		ArrayList<BookVO> list = new ArrayList<>();
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_ALL);
			pstmt.setInt(1, (page-1) *10 +1);
			pstmt.setInt(2, page *10);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new BookVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getTimestamp(6).toLocalDateTime(), rs.getString(7), rs.getString(8)));
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
	public ArrayList<BookVO> selectByValue(String tag, String word, int page) {
		System.out.println("bookDaoImple : selectByValue()");
		ArrayList<BookVO> list = new ArrayList<>();
		String searchWord = '%' + word + '%';
		String sql_query;
		if(tag.equals(NAME)) {
			sql_query = SQL_SELECT_BY_NAME;
		}else if(tag.equals(WRITER)) {
			sql_query = SQL_SELECT_BY_WRITER;
		}else {
			sql_query = SQL_SELECT_BY_CATEGORY;
		}
		
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			pstmt = conn.prepareStatement(sql_query);
			pstmt.setString(1, searchWord);
			pstmt.setInt(2, (page -1) *10 +1 );
			pstmt.setInt(3, page *10);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new BookVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getTimestamp(6).toLocalDateTime(), rs.getString(7), rs.getString(8)));
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

	
	public LocalDateTime selectCheckinDate(int bookId) {
		
		System.out.println("bookDaoImple : selectCheckinDate()");
		LocalDateTime res = null;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			pstmt = conn.prepareStatement(OracleBookQuery.SQL_SELECT_CHECKIN_DATE);
			pstmt.setInt(1, bookId);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				res = rs.getTimestamp(1).toLocalDateTime();
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
	public int insertCheckoutBook(int bookId, String userId, String state, LocalDateTime checkoutDate, LocalDateTime checkinDate) {
		System.out.println("bookDaoImple : checkoutBook()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_INSERT_CHECK_OUT);
			
			pstmt.setInt(1, bookId);
			pstmt.setString(2, userId);
			pstmt.setString(3, state);
			pstmt.setTimestamp(4, Timestamp.valueOf(checkoutDate));
			pstmt.setTimestamp(5, Timestamp.valueOf(checkinDate));
			
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
			//c.state, c.bookid, b.name, b.writer, b.category, b.state, c.checkoutdate, c.chechindate, b.image
			while(rs.next()) {
				ArrayList<String> l = new ArrayList<>(); 
				l.add(rs.getString(1));
				l.add(String.valueOf(rs.getInt(2)));
				l.add(rs.getString(3));
				l.add(rs.getString(4));
				l.add(rs.getString(5));
				l.add(rs.getString(6));
				l.add(rs.getTimestamp(7).toLocalDateTime().toString());
				l.add(rs.getTimestamp(8).toLocalDateTime().toString());
				l.add(rs.getString(9));
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
	public int updateCheckinDate(int bookId, LocalDateTime checkinDate) {
		System.out.println("bookDaoImple : updateCheckinDate()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_UPDATE_CHECK_IN_DATE);
			System.out.println(checkinDate);
			pstmt.setTimestamp(1, Timestamp.valueOf(checkinDate));
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
