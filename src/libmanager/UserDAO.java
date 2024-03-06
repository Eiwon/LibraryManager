package libManager;

public interface UserDAO {
	
	public int insertUser(UserVO vo);
	
	public UserVO selectByID(String userId);
	
	public UserVO selectWithPw(String userId, String pw);
}
