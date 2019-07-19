
public class Address {
	
	String street;
    String city;
	String province;
	String postalCode;
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	public Address(String street, String city, String province, String postalCode) {
	
		this.street = street;
		this.city = city;
		this.province = province;
		this.postalCode = postalCode;
	}
	public boolean addressValidator(Address address)

	{

		if( address.getStreet().equals("Rob street") && address.getCity().equals("Rob city") && address.getProvince().equals("Rob province") && address.getPostalCode().equals("Rob postalcode"))
		{
			return true;
		}
		return false;

	}


}
