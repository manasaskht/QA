// YOU ARE ALLOWED TO MODIFY THIS FILE
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class RequestProcessor implements IRequestProcessor
{
	/*
		This is dependency injection. Everything the class and this method needs to do their job is
		passed to it. This allows you to perfectly test every aspect of your class by writing mock
		objects that implement these interfaces such that you can test every possible path through
		your code.
	*/
	public String processRequest(String json,
								 IAuthentication authentication,
								 IShipMate shipMate,
								 IDatabase database)
	{
		String response = "";
		try
		{
			Object obj = new JSONParser().parse(json);
		}
		catch (Exception e)
		{}
		return response;
	}

	/*
		Insert all of your instantiation of mock objects and RequestProcessor(s)
		here. Then insert calls to all of your unit tests for the RequestProcessor
		class.  These tests should send different combinations of JSON strings
		to your class with mock objects such that you test all paths through the
		API.  Write one test function per "path" you are testing.  For example,
		to test authentication you would write two unit tests: authenticateSuccess()
		that passes JSON with a known API key that should be authenticated by your
		mock security object and tests for the correct JSON response from processRequest(),
		and authenticateFailure() that passes JSON with a bad API key that should fail to
		be authenticated by your mock security object and tests for the correct JSON
		response from processRequest().

		The runUnitTests() method will be called by Main.java. It must run your unit tests.
		All of your unit tests should System.out.println() one line indicating pass or
		failure with the following format:
		PASS - <Name of test>
		FAIL - <Name of test>
	*/
	static public void runUnitTests()
	{
	}
}
