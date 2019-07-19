public class Person
{
	private String name;
	private ContactDetails phoneNumber;
	private UserValidation validation;

	public Person(String name)
	{
		this.name = name;
		validation = new UserValidation();
     	phoneNumber = new ContactDetails();
	}

	public void setAreaCode(String areaCode)
	{
		phoneNumber.setAreaCode(areaCode);
	}
	public String getAreaCode()
	{
		return phoneNumber.getAreaCode();
	}
	public void setPhoneNumber(String PhoneNumber)
	{
		phoneNumber.setPhoneNumber(PhoneNumber);
	}
	public String getPhoneNumber()
	{
		
		return phoneNumber.getPhoneNumber();
	}

	public void setLoginCredentials(String userName, String password)
	{
		validation.setUserName(userName);
		validation.setPassword(password);
	}
	public boolean authenticateUser()
	{
		return validation.AuthenticateUser();
	}
}