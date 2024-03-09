package libManager.Interface;

import java.util.ArrayList;

import Model.UserVO;

public interface UserDAO {
	
	public static final UserVO defaultUser = new UserVO("Guest", "guest", "guest", "0", "guest"); 
	
	public int insertUser(UserVO vo);
	
	public UserVO selectByID(String userId);
	
	public UserVO selectWithPw(String userId, String pw);
	
	public int insertBlackList(String userId, String banDate, String releaseDate);
}
