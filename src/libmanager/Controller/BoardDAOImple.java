package libManager.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.BookVO;
import Model.PostVO;
import Model.ReplyVO;
import libManager.Interface.BoardDAO;
import libManager.Interface.OracleBoardQuery;
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
						rs.getString(5), rs.getInt(6), rs.getString(7)));
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
						rs.getString(5), rs.getInt(6), rs.getString(7));
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
						rs.getString(5), rs.getInt(6), rs.getString(7)));
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
						rs.getString(5), rs.getInt(6), rs.getString(7)));
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
						rs.getString(5), rs.getInt(6), rs.getString(7)));
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
	public ArrayList<ReplyVO> selectReplyById(int replyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertReply(ReplyVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateReply(ReplyVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteReply(ReplyVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
