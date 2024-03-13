package lib.module.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import lib.model.BookVO;
import oracle.jdbc.OracleDriver;

public class BoardDAOImple implements BoardDAO, OracleBoardQuery{
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private static BoardDAOImple instance = null;
	
	private BoardDAOImple() {};
	
	public static BoardDAOImple getInstance() {
		if(instance == null) {
			instance = new BoardDAOImple();
		}
		return instance;
	}

	@Override
	public int insertPost(PostVO vo) {
		System.out.println("boardDaoImple : inserPost()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_INSERT_POST);
		
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getUserId());
			pstmt.setString(4, vo.getTag());
			
			res = pstmt.executeUpdate();
			if(res == 1)
				System.out.println(res + "글 추가 성공");
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
	} // end insertPost

	@Override
	public int updatePost(PostVO vo) {
		System.out.println("boardDaoImple : updatePost()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_UPDATE_POST);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getTag());
			pstmt.setInt(4, vo.getId());
			
			res = pstmt.executeUpdate();
			if(res == 1)
				System.out.println(res + "글 수정 성공");
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
	public int deletePost(int postId) {
		System.out.println("boardDaoImple : deletePost()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_DELETE_POST);
			
			pstmt.setInt(1, postId);
			
			res = pstmt.executeUpdate();
			if(res == 1)
				System.out.println(res + "글 삭제 성공");
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
	public ArrayList<PostVO> selectPage(int page) {
		System.out.println("boardDaoImple : selectPage()");
		ArrayList<PostVO> list = new ArrayList<>();
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_PARTIAL);
			pstmt.setInt(1, (page -1) *10 +1);
			pstmt.setInt(2, page *10);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new PostVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getInt(6), rs.getTimestamp(7).toLocalDateTime()));
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
	} // end selectPage

	@Override
	public PostVO selectPostById(int postId) {
		System.out.println("boardDaoImple : selectPostById()");
		PostVO vo = null;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_POST_BY_ID);
			pstmt.setInt(1, postId);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new PostVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getTimestamp(7).toLocalDateTime());
				System.out.println("글 읽어오기 성공");
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

	@Override
	public ArrayList<PostVO> selectPostByUid(String userId, int page) {
		System.out.println("boardDaoImple : selectPostByUid()");
		ArrayList<PostVO> list = new ArrayList<>();
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_POST_BY_UID);
			pstmt.setString(1, '%' + userId + '%');
			pstmt.setInt(2, (page -1) *10 +1);
			pstmt.setInt(3, page *10);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new PostVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getInt(6), rs.getTimestamp(7).toLocalDateTime()));
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
	} // selectPostByUid

	@Override
	public ArrayList<PostVO> selectPostByTitle(String title, int page) {
		System.out.println("boardDaoImple : selectPostByTitle()");
		ArrayList<PostVO> list = new ArrayList<>();
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_POST_BY_TITLE);
			pstmt.setString(1, '%' + title + '%');
			pstmt.setInt(2, (page -1) *10 +1);
			pstmt.setInt(3, page *10);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new PostVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getInt(6), rs.getTimestamp(7).toLocalDateTime()));
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
	public ArrayList<PostVO> selectPostByTag(String tag, int page) {
		System.out.println("boardDaoImple : selectPostByTag()");
		ArrayList<PostVO> list = new ArrayList<>();
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_POST_BY_TAG);
			pstmt.setString(1, tag);
			pstmt.setInt(2, (page -1) *10 +1);
			pstmt.setInt(3, page *10);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new PostVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getInt(6), rs.getTimestamp(7).toLocalDateTime()));
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
	public ArrayList<ReplyVO> selectReplyById(int postId, int page) {
		System.out.println("boardDaoImple : selectReplyById()");
		ArrayList<ReplyVO> list = new ArrayList<>();
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_REPLY_BY_PID);
			pstmt.setInt(1, postId);
			pstmt.setInt(2, (page -1) *10 +1);
			pstmt.setInt(3, page *10);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new ReplyVO(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
						rs.getTimestamp(5).toLocalDateTime()));
			}//int id, int postId, String userId, String content, String writeDate
			
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
	public int insertReply(ReplyVO vo) {
		System.out.println("boardDaoImple : insertReply()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_INSERT_REPLY);
			System.out.println(vo.getWriteDate());
			pstmt.setInt(1, vo.getPostId());
			pstmt.setString(2, vo.getUserId());
			pstmt.setString(3, vo.getContent());
			pstmt.setTimestamp(4, Timestamp.valueOf(vo.getWriteDate()));
			
			res = pstmt.executeUpdate();
			if(res == 1)
				System.out.println(res + "댓글 추가 성공");
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
	} // insertReply

	@Override
	public int updateReply(ReplyVO vo) {
		System.out.println("boardDaoImple : updateReply()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_UPDATE_REPLY);
		
			pstmt.setString(1, vo.getContent());
			pstmt.setInt(2, vo.getId());
			
			res = pstmt.executeUpdate();
			if(res == 1)
				System.out.println(res + "댓글 수정 성공");
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
	} // updateReply

	@Override
	public int deleteReply(int replyId) {
		System.out.println("boardDaoImple : deleteReply()");
		int res = 0;
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_DELETE_REPLY);

			pstmt.setInt(1, replyId);
			
			res = pstmt.executeUpdate();
			if(res == 1)
				System.out.println(res + "댓글 삭제 성공");
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
	} // deleteReply
	
}
