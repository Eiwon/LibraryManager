package lib.Interface;

import java.time.LocalDateTime;
import java.util.ArrayList;

import lib.model.BookVO;

public interface BookDAO {
	
	public int insertBook(BookVO vo);
	
	public int updateBook(BookVO vo);
	
	public ArrayList<BookVO> selectAll(int page);
	
	public ArrayList<BookVO> selectByValue(String tag, String word, int page);
	
	public LocalDateTime selectCheckinDate(int bookId);
	
	public int insertCheckoutBook(int bookId, String userId, String state, LocalDateTime checkoutDate, LocalDateTime checkinDate);
	
	public String selectByBookState(int bookId, String state);
	
	public ArrayList<ArrayList<String>> selectAllInfoById(String userId);
	
	public int deleteByBookId(int bookId, String state);
	
	public int updateByBookId(String state, int bookId);
	
	public int updateCheckinDate(int bookId, LocalDateTime checkinDate);
}
