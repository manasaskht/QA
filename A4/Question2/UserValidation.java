
public class UserValidation {

	private String userName;
	private String password;
	
	public UserValidation() {

		super();

	}
	public boolean AuthenticateUser() {

		return (userName.equals("joe") && password.equals("joepass"));

	}
	public void setUserName(String tUserName) {

		this.userName = tUserName;
	}
	public void setPassword(String tPassword) {

		this.password = tPassword;

	}
}
