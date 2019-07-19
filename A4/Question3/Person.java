public class Person
{
	public String name;
	public Address address;

	public Person()
	{
		name = "Rob";
		address = new Address("Rob street", "Rob city", "Rob province", "Rob postalcode");
	}

	public boolean isPersonRob()
	{
		return name.equals("Rob") && isRobsAddress(address);
	}

	private boolean isRobsAddress(Address address)
	{
		return address.addressValidator(address);
	}
}