
public class ContactDetails {
	private String areaCode;
    private String phoneNumber;
    
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getPhoneNumber() {
		if (areaCode != null && areaCode != "") {

			return "(" + areaCode + ") " + phoneNumber;

		}
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	

}
