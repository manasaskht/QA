import java.util.ArrayList;
public class Mexico  implements ICountry
{
	public String getAgriculture()
	{
		return "$50000000 MXN";
	}

	public String getTourism()
	{
		return "$100000 MXN";
	}
	public ArrayList<String> GDPList() {

		ArrayList<String> GDPList = new ArrayList<String>();
		System.out.println("- Mexico:\n");
		GDPList.add("Agriculture: " + getAgriculture());
		GDPList.add("Tourism: " + getTourism());

		return GDPList;
	}
	
}