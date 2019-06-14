public class Main {

	public static void main(String[] args) {
	
		CountryGDPReport c = new CountryGDPReport();

		ICountry[] countries = new ICountry[2];
		countries[0] = new Canada();
		countries[1] = new Mexico();
		
		c.printCountryGDPReport(countries);
	}

}
