package lib.Interface;

import java.time.LocalDateTime;
import java.util.ArrayList;

import lib.model.UserVO;

public interface UserDAO {
	
	public int insertUser(UserVO vo);
	
	public UserVO selectByID(String userId);
	
	public UserVO selectWithPw(String userId, String pw);
	
	public int registerBlackList(String userId, LocalDateTime banDate, LocalDateTime releaseDate);
	
	public String[] searchBlackList(String userId);
	
	public int deleteFromBlackList(String userId);
	
	public LocalDateTime getCheckinDate(String userId);
}
