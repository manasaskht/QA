import java.util.ArrayList;
public class CountryGDPReport
{
	public void printCountryGDPReport(ICountry[] countries)
	{
		ArrayList<String> list = new ArrayList<String>();
		System.out.println("GDP By Country:\n");
		for (int i = 0; i < countries.length; i++) {
			
			list = countries[i].GDPList();
			for (int j = 0; j < list.size(); j++) {

				System.out.println(list.get(j));
			}
			
		}	
	}
}