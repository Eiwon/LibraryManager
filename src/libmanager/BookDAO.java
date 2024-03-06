package libManager;

import java.util.ArrayList;

public interface BookDAO {
	
	public int insertBook(BookVO vo);
	
	public ArrayList<BookVO> selectByCategory();
}
