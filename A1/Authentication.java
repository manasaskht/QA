
public class Authentication implements IAuthentication{

	@Override
	public boolean authenticate(String apiKey) {
		if(apiKey.contains("TRUE".toLowerCase())) {
			return true;
		}
			
			return false;
		
		
	}

	@Override
	public boolean authorize(String username, RequestAction action) {
		if(username.contains("SHIP".toLowerCase()) && action==RequestAction.SHIP) {
			return true;
		}
		
		else 
		{
			if(username.contains("QUERY".toLowerCase()) && action==RequestAction.QUERY) {
				return true;
			}
		}
	return false;
	}
	

}
