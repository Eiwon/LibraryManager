package libManager.Interface;

import java.util.ArrayList;

import Model.UserVO;

public interface UserDAO {
	
	public int insertUser(UserVO vo);
	
	public UserVO selectByID(String userId);
	
	public UserVO selectWithPw(String userId, String pw);
	
	public int registerBlackList(String userId, String banDate, String releaseDate);
	
	public String[] searchBlackList(String userId);
	
	public int deleteFromBlackList(String userId);
	
	public String getCheckinDate(String userId);
}
