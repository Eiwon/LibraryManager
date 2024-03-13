package lib.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lib.Interface.UserDAO;
import lib.model.UserVO;

public class UserManager {

	private static UserManager instance = null;
	private static UserVO currentUser = new UserVO("Guest", "guest", "guest", "0", "guest");
	
	
	private UserManager() {}
	
	public UserManager getInstance() {
		if(instance == null) {
			instance = new UserManager();
		}
		return instance;
	}

	public static void initUser() {
		currentUser = new UserVO("Guest", "guest", "guest", "0", "guest");
	}
	
	public static String getUserId() {
		return currentUser.getUserId();
	}
	
	public static String getUserAuth() {
		return currentUser.getAuth();
	}
	
	public static UserVO getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(UserVO currentUser) {
		UserManager.currentUser = currentUser;
	}
	
	public static boolean isBan() {
		/*블랙 리스트에 등록되었는지? 등록되었다면 해제해야하는지? 등록 해야하는지?
블랙리스트로부터 userid에 해당하는 열 select
ㄴ 결과가 있다면 해제일과 현재 시간을 비교
	ㄴ 해제일이 현재시간보다 큰 경우 -> 대여 불가
	ㄴ 해제일이 현재시간보다 작은 경우 -> 블랙리스트에서 해당 유저id 열 delete, 대여 가능
ㄴ 결과가 없다면 checkout으로부터 userid에 해당하고 state가 out인 열의 반납일 select
	ㄴ 결과 중 현재시간보다 작은 값 존재시 -> 블랙리스트에 insert, 대여 불가
	ㄴ 아니면 대여 가능*/
		String userId = currentUser.getUserId();
		UserDAO dao = UserDAOImple.getInstance();
		String[] banInfo = dao.searchBlackList(userId);
		
		if(banInfo != null) { // 블랙리스트에 이미 등록된 유저라면
			LocalDateTime releaseDate = LocalDateTime.parse(banInfo[3]);
			if(LocalDateTime.now().isBefore(releaseDate)) { // 아직 해제일 전이라면 
				return true;
			}else {
				dao.deleteFromBlackList(userId);
				return false;
			}
		}else { // 블랙리스트 미등록 유저라면
			LocalDateTime earliestCheckinDate = dao.getCheckinDate(userId);
			if(earliestCheckinDate != null) {
				if(LocalDateTime.now().isBefore(earliestCheckinDate)) { // 아직 가장 빠른 반납일 전이라면
					return false;
				}else {
					dao.registerBlackList(currentUser.getUserId(), LocalDateTime.now(), 
						LocalDateTime.now().plusDays(14));
					return true;
				}
			}else return false;
		}
	} // end isBan
	
	public String[] getBanInfo() {
		// 현재 유저의 userId, 블랙리스트 등록일, 해제일
		return UserDAOImple.getInstance().searchBlackList(currentUser.getUserId());
	} // end getBanInfo
	
}
