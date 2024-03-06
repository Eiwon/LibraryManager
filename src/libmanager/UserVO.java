package libManager;

public class UserVO {
	String userId;
	String pw;
	String name;
	String phone;
	String email;
	String auth;
	
	public UserVO() {}
	public UserVO(String userId, String pw, String name, String phone, String email) {
		super();
		this.userId = userId;
		this.pw = pw;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.auth = "USER";
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	@Override
	public String toString() {
		return "UserVO [userId=" + userId + ", pw=" + pw + ", name=" + name + ", phone=" + phone + ", email=" + email
				+ ", auth=" + auth + "]";
	}
	
	
}
