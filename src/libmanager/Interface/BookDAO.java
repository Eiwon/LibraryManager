package libManager.Interface;

import java.util.ArrayList;

import Model.BookVO;

public interface BookDAO {
	
	public int insertBook(BookVO vo);
	
	public int updateBook(BookVO vo);
	
	public ArrayList<BookVO> selectAll();
	
	public ArrayList<BookVO> selectByValue(String tag, String word);
	
	public String selectCheckinDate(int bookId);
	
	public int checkoutBook(int bookId, String userId, String state, String checkinDate);
}
