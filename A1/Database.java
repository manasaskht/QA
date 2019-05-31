import java.util.HashMap;
import java.util.Map;

public class Database implements IDatabase {

	private Map<String, Integer> drugs;

	public Database() {
		drugs = new HashMap<String, Integer>();
		drugs.put("tylenol", 10);
		drugs.put("vicks", 4);
		drugs.put("coughsyrup", 5);
		drugs.put("moov", 0);
		
	}

	@Override
	public int drugCount(String drugname) {

		for (String drug : drugs.keySet()) {
			if (drug.equals(drugname)) {
				return drugs.get(drug);
			}
		}

		return 0;
	}

	@Override
	public boolean drugSearch(String drugname) {
		for (String drug : drugs.keySet()) {
			if (drug.equals(drugname)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean drugClaim(int quantity, String drugname) {

	
		if (drugs.get(drugname) >= quantity) {
			drugs.put(drugname, (drugs.get(drugname) - quantity));
			
			return true;
		} else {
			return false;
		}
	}

}
