package libManager.Interface;

import java.util.ArrayList;

import Model.BookVO;

public interface BookDAO {
	
	public int insertBook(BookVO vo);
	
	public int updateBook(BookVO vo);
	
	public ArrayList<BookVO> selectAll();
	
	public ArrayList<BookVO> selectByValue(String tag, String word);
	
	public String selectCheckinDate(int bookId);
	
	public int insertCheckoutBook(int bookId, String userId, String state, String checkoutDate,String checkinDate);
	
	public String selectByBookState(int bookId, String state);
	
	public ArrayList<ArrayList<String>> selectAllInfoById(String userId);
	
	public int deleteByBookId(int bookId, String state);
	
	public int updateByBookId(String state, int bookId);
	
	public int updateCheckinDate(int bookId, String checkinDate);
}
