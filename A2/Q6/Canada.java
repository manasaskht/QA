import java.util.ArrayList;
public class Canada implements ICountry
{
	public String getAgriculture()
	{
		return "$50000000 CAD";
	}

	public String getManufacturing()
	{
		return "$100000 CAD";
	}
	public ArrayList<String> GDPList() {

		ArrayList<String> GDPList = new ArrayList<String>();
		System.out.println("- Canada:\n");
		GDPList.add("Agriculture: " + getAgriculture());
		GDPList.add("Manufacturing: " + getManufacturing());

		return GDPList;
	}
}