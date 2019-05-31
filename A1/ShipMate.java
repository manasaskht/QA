import java.util.ArrayList;

public class ShipMate implements IShipMate {

	private ArrayList<Address> addr;

	public ShipMate() {
		addr = new ArrayList<>();
		Address a1 = new Address();
		a1.customer = "manasa";
		a1.street = "vernon";
		a1.city = "halifax";
		a1.province = "ns";
		a1.country = "canada";
		a1.postalCode = "hohoho";
		addr.add(a1);
		Address a2 = new Address();
		a2.customer = "rob hawkey";
		a2.street = "123 street";
		a2.city = "halifax";
		a2.province = "nova scotia";
		a2.country = "canada";
		a2.postalCode = "h0h0h0";
		addr.add(a2);
	}

	@Override
	public boolean isKnownAddress(Address address) {

		for (Address adr : addr) {
			if (adr.customer.equals(address.customer) && adr.street.equals(address.street)
					&& adr.city.equals(address.city) && adr.province.equals(address.province)
					&& adr.country.equals(address.country) && adr.postalCode.equals(address.postalCode)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String shipToAddress(Address address, int count, String drugName) throws Exception {

		try {
			if (isKnownAddress(address)) {
				return "29-05-2019";
			}

			throw new Exception("UnknownAddress");

		} catch (Exception e) {
			return e.getMessage();
		}

	}

}
