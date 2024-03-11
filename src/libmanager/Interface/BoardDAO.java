package libManager.Interface;

import java.util.ArrayList;

import Model.PostVO;
import Model.ReplyVO;

public interface BoardDAO {
	
	// 글쓰기
	public int insertPost(PostVO vo);
	
	// 글수정
	public int updatePost(PostVO vo);
	
	// 글삭제
	public int deletePost(int postId);
	
	// 글 지정 구간 검색
	public ArrayList<PostVO> selectPage(int page);
	
	// 글 id 지정 검색
	public PostVO selectPostById(int postId);
	
	// 유저 id 지정 검색
	public ArrayList<PostVO> selectPostByUid(String userId, int page);
	
	// 제목 지정 검색
	public ArrayList<PostVO> selectPostByTitle(String title, int page);
	
	// 태그 지정 검색
	public ArrayList<PostVO> selectPostByTag(String tag, int page);
	
	// 글 id 지정 댓글 검색
	public ArrayList<ReplyVO> selectReplyById(int replyId);
	
	// 댓글 쓰기
	public int insertReply(ReplyVO vo);
	
	// 댓글 수정
	public int updateReply(ReplyVO vo);
	
	// 댓글 삭제
	public int deleteReply(ReplyVO vo);
}
