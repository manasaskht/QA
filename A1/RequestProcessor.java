
// YOU ARE ALLOWED TO MODIFY THIS FILE
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RequestProcessor implements IRequestProcessor {

	/*
	 * This is dependency injection. Everything the class and this method needs to
	 * do their job is passed to it. This allows you to perfectly test every aspect
	 * of your class by writing mock objects that implement these interfaces such
	 * that you can test every possible path through your code.
	 */
	public String processRequest(String json, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String response = "";
		try {

			JSONObject obj = (JSONObject) new JSONParser().parse(json);
			String apikey = (obj.get("apikey").toString()).toLowerCase();
			String username = (obj.get("username").toString()).toLowerCase();
			String drugName = (obj.get("drug").toString()).toLowerCase();
			String action = (obj.get("action").toString()).toUpperCase();

			boolean isAuthenticated = authentication.authenticate(apikey);
			if (isAuthenticated) {
				if (action.equals("QUERY")) {
					if (authentication.authorize(username, RequestAction.QUERY)) {
						if (database.drugSearch(drugName)) {
							response = "{\"status\":200,\"count\":" + database.drugCount(drugName) + "}";
						} else {
							response = "{\"status\":500,\"error\":\"Unknown Drug\"}";
						}
					} else {
						response = "{\"status\":500,\"error\":\"Not Authorized\"}";
					}
				} else if (action.equals("SHIP")) {

					if (authentication.authorize(username, RequestAction.SHIP)) {
						long drugInLong;
						int drugQuantity;
						if (obj.get("quantity").toString().equals("")) {
							drugQuantity = 0;
						} else {
							drugInLong = (long) obj.get("quantity");
							drugQuantity = (int) (long) drugInLong;
						}
						JSONObject address = (JSONObject) obj.get("address");
						Address requestedAddress = new Address();
						requestedAddress.city = (address.get("city").toString()).toLowerCase();
						requestedAddress.country = (address.get("country").toString()).toLowerCase();
						requestedAddress.customer = (address.get("customer").toString()).toLowerCase();
						requestedAddress.street = (address.get("street").toString()).toLowerCase();
						requestedAddress.province = (address.get("province").toString()).toLowerCase();
						requestedAddress.postalCode = (address.get("postalCode").toString()).toLowerCase();
						if (database.drugSearch(drugName)) {

							if (shipMate.isKnownAddress(requestedAddress)) {
								if (database.drugClaim(drugQuantity, drugName)) {

									response = "{\"status\":200,\"estimateddeliverydate\":" + "\""
											+ shipMate.shipToAddress(requestedAddress, drugQuantity, drugName) + "\"}";
								} else {
									response = "{\"status\":500,\"error\":\"Insufficient Stock\"}";
								}
							} else {
								response = "{\"status\":500,\"error\":\"Unknown Address\"}";
							}

						} else {
							response = "{\"status\":500,\"error\":\"Unknown Drug\"}";
						}
					} else {
						response = "{\"status\":500,\"error\":\"Not Authorized\"}";
					}
				} else {
					response = "{\"status\":500,\"error\":\"Not Authorized\"}";
				}
			} else {
				response = "{\"status\":500,\"error\":\"Authentication Failure\"}";
			}

		} catch (Exception e) {
			System.out.println("exception");
		}
		return response;
	}

	/*
	 * Insert all of your instantiation of mock objects and RequestProcessor(s)
	 * here. Then insert calls to all of your unit tests for the RequestProcessor
	 * class. These tests should send different combinations of JSON strings to your
	 * class with mock objects such that you test all paths through the API. Write
	 * one test function per "path" you are testing. For example, to test
	 * authentication you would write two unit tests: authenticateSuccess() that
	 * passes JSON with a known API key that should be authenticated by your mock
	 * security object and tests for the correct JSON response from
	 * processRequest(), and authenticateFailure() that passes JSON with a bad API
	 * key that should fail to be authenticated by your mock security object and
	 * tests for the correct JSON response from processRequest().
	 * 
	 * The runUnitTests() method will be called by Main.java. It must run your unit
	 * tests. All of your unit tests should System.out.println() one line indicating
	 * pass or failure with the following format: PASS - <Name of test> FAIL - <Name
	 * of test>
	 */
	static public void runUnitTests() {
		IShipMate shipMateMock = new ShipMate();
		IAuthentication authentication = new Authentication();
		IDatabase databaseMock = new Database();
		RequestProcessor requestProcessor = new RequestProcessor();
		requestProcessor.queryWithAPIkeyempty(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.querySuccessWithUsernameCaseInsensitive(authentication, databaseMock, shipMateMock,
				requestProcessor);
		requestProcessor.queryWithAPIkeyNumber(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.queryWithAPIkeyIncorrect(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.queryWithIncorrectUsername(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.queryWithUsernameEmpty(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.querySuccessWithAPIkeyCaseInsensitive(authentication, databaseMock, shipMateMock,
				requestProcessor);
		requestProcessor.queryWithUnknownAction(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.queryWithActionEmpty(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.querySuccessWithActionCaseInsensitive(authentication, databaseMock, shipMateMock,
				requestProcessor);
		requestProcessor.queryCheckWithActionASShip(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.queryWithDrugnameEmpty(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.queryWithunknownDrug(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.querySuccessWithDrugWithoutStock(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.queryDrugValidScenario(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithApikeyCaseinsensitive(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithActionAsQuery(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithApikeyEmpty(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithApikeyIncorrect(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithActionEmpty(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipSuccessWithActionCaseinsensitive(authentication, databaseMock, shipMateMock,
				requestProcessor);
		requestProcessor.shipWithUnknownAction(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithUnknownDrug(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithDrugEmpty(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithUsernameEmpty(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithIncorrectUsername(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipSuccessUsernameCaseinsensitive(authentication, databaseMock, shipMateMock,
				requestProcessor);
		requestProcessor.shipTylenolNotinStock(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipTylenolClaimInstock(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.queryDrugReducedCount(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithCustomernameEmpty(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithStreetnameEmpty(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithCitynameEmpty(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithProvincenameEmpty(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithCountrynameEmpty(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithPostalcodeEmpty(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.incorrectStreetAddress(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.streetAddressInNumber(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.successWithStreetAddressCaseInsensitive(authentication, databaseMock, shipMateMock,
				requestProcessor);
		requestProcessor.incorrectProvince(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.incorrectCity(authentication, databaseMock, shipMateMock, requestProcessor);
		requestProcessor.shipWithQuantityEmpty(authentication, databaseMock, shipMateMock, requestProcessor);
		
		requestProcessor.shipValidScenario(authentication, databaseMock, shipMateMock, requestProcessor);

	}

	public void queryWithAPIkeyempty(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {

		String json = "{\"apikey\":\"\",\"username\":\"manasaQUERY\",\"action\":\"QUERY\",\"drug\":\"tylenol\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Authentication Failure\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- queryWithAPIkeyempty");
		} else {
			System.out.println("Fail- queryWithAPIkeyempty");
		}
	}

	public void queryWithAPIkeyNumber(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {

		String json = "{\"apikey\":12,\"username\":\"manasaQUERY\",\"action\":\"QUERY\",\"drug\":\"Tylenol\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Authentication Failure\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- queryWithAPIkeyNumber");
		} else {
			System.out.println("Fail- queryWithAPIkeyNumber");
		}
	}

	public void querySuccessWithAPIkeyCaseInsensitive(IAuthentication authentication, IDatabase databaseMock,
			IShipMate shipMateMock, RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"ManasaTrUE\",\"username\":\"manasaQUERY\",\"action\":\"QUERY\",\"drug\":\"Tylenol\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":200,\"count\":10}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- querySuccessWithAPIkeyCaseInsensitive");
		} else {
			System.out.println("Fail- querySuccessWithAPIkeyCaseInsensitive");
		}
	}

	public void queryWithAPIkeyIncorrect(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"Manasa123\",\"username\":\"manasaQUERY\",\"action\":\"QUERY\",\"drug\":\"Tylenol\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Authentication Failure\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- queryWithAPIkeyIncorrect");
		} else {
			System.out.println("Fail- queryWithAPIkeyIncorrect");
		}
	}

	public void queryWithIncorrectUsername(IAuthentication authentication, IDatabase databaseMock,
			IShipMate shipMateMock, RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"ManasaTRUE\",\"username\":\"manasa\",\"action\":\"QUERY\",\"drug\":\"Tylenol\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);
		String expectedResponse = "{\"status\":500,\"error\":\"Not Authorized\"}";

		if (response.equals(expectedResponse)) {
			System.out.println("Pass- queryWithIncorrectUsername");
		} else {
			System.out.println("Fail- queryWithIncorrectUsername");
		}
	}

	public void querySuccessWithUsernameCaseInsensitive(IAuthentication authentication, IDatabase databaseMock,
			IShipMate shipMateMock, RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"ManasaTRUE\",\"username\":\"manasaQUErY\",\"action\":\"QUERY\",\"drug\":\"Tylenol\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);
		String expectedResponse = "{\"status\":200,\"count\":10}";

		if (response.equals(expectedResponse)) {
			System.out.println("Pass- querySuccessWithUsernameCaseInsensitive");
		} else {
			System.out.println("Fail- querySuccessWithUsernameCaseInsensitive");
		}
	}

	public void queryCheckWithActionASShip(IAuthentication authentication, IDatabase databaseMock,
			IShipMate shipMateMock, RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"ManasaTRUE\",\"username\":\"manasaQUERY\",\"action\":\"SHIP\",\"drug\":\"Tylenol\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Not Authorized\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- queryCheckWithActionASShip");
		} else {
			System.out.println("Fail- queryCheckWithActionASShip");
		}
	}

	public void queryWithUsernameEmpty(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"ManasaTRUE\",\"username\":\"\",\"action\":\"QUERY\",\"drug\":\"Tylenol\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);
		String expectedResponse = "{\"status\":500,\"error\":\"Not Authorized\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- queryWithUsernameEmpty");
		} else {
			System.out.println("Fail- queryWithUsernameEmpty");
		}
	}

	public void queryWithActionEmpty(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"ManasaTRUE\",\"username\":\"manasaQUERY\",\"action\":\"\",\"drug\":\"Tylenol\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Not Authorized\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- queryWithActionEmpty");
		} else {
			System.out.println("Fail- queryWithActionEmpty");
		}
	}

	public void querySuccessWithActionCaseInsensitive(IAuthentication authentication, IDatabase databaseMock,
			IShipMate shipMateMock, RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"ManasaTrUE\",\"username\":\"manasaQUERY\",\"action\":\"QUeRY\",\"drug\":\"Tylenol\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":200,\"count\":10}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- querySuccessWithActionCaseInsensitive");
		} else {
			System.out.println("Fail- querySuccessWithActionCaseInsensitive");
		}
	}

	public void queryWithUnknownAction(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"ManasaTRUE\",\"username\":\"manasaQUERY\",\"action\":\"Delivery\",\"drug\":\"Tylenol\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);
		String expectedResponse = "{\"status\":500,\"error\":\"Not Authorized\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- queryWithUnknownAction");
		} else {
			System.out.println("Fail- queryWithUnknownAction");
		}
	}

	public void queryWithDrugnameEmpty(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"ManasaTRUE\",\"username\":\"manasaQUERY\",\"action\":\"QUERY\",\"drug\":\"\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Unknown Drug\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- queryWithDrugnameEmpty");
		} else {
			System.out.println("Fail- queryWithDrugnameEmpty");
		}
	}

	public void queryWithunknownDrug(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"ManasaTRUE\",\"username\":\"manasaQUERY\",\"action\":\"QUERY\",\"drug\":\"Muciprocin\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Unknown Drug\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- queryWithunknownDrug");
		} else {
			System.out.println("Fail- queryWithunknownDrug");
		}
	}

	public void querySuccessWithDrugWithoutStock(IAuthentication authentication, IDatabase databaseMock,
			IShipMate shipMateMock, RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"ManasaTRUE\",\"username\":\"manasaQUERY\",\"action\":\"QUERY\",\"drug\":\"Moov\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":200,\"count\":0}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- querySuccessWithDrugWithoutStock");
		} else {
			System.out.println("Fail- querySuccessWithDrugWithoutStock");
		}
	}

	public void queryDrugValidScenario(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"ManasaTRUE\",\"username\":\"manasaQUERY\",\"action\":\"QUERY\",\"drug\":\"CoughSyrup\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":200,\"count\":5}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- drugValidScenario");
		} else {
			System.out.println("Fail- drugValidScenario");
		}

	}

	public void shipWithApikeyEmpty(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"\",\"username\":\"manasaSHIP\",\"action\":\"QUERY\",\"drug\":\"Tylenol\",\"quantity\":6,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Authentication Failure\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithApikeyEmpty");
		} else {
			System.out.println("Fail- shipWithApikeyEmpty");
		}

	}

	public void shipWithApikeyIncorrect(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasa\",\"username\":\"manasaSHIP\",\"action\":\"QUERY\",\"drug\":\"Tylenol\",\"quantity\":6,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Authentication Failure\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithApikeyIncorrect");
		} else {
			System.out.println("Fail- shipWithApikeyIncorrect");
		}

	}

	public void shipWithApikeyCaseinsensitive(IAuthentication authentication, IDatabase databaseMock,
			IShipMate shipMateMock, RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTrUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Vicks\",\"quantity\":1,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":200,\"estimateddeliverydate\":\"29-05-2019\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithApikeyCaseinsensitive");
		} else {
			System.out.println("Fail- shipWithApikeyCaseinsensitive");
		}

	}

	public void shipWithActionAsQuery(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"QUERY\",\"drug\":\"Tylenol\",\"quantity\":6,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Not Authorized\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithActionAsQuery");
		} else {
			System.out.println("Fail- shipWithActionAsQuery");
		}

	}

	public void shipWithActionEmpty(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"\",\"drug\":\"Tylenol\",\"quantity\":6,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Not Authorized\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithActionEmpty");
		} else {
			System.out.println("Fail- shipWithActionEmpty");
		}

	}

	public void shipWithUnknownAction(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"Deliver\",\"drug\":\"Tylenol\",\"quantity\":6,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Not Authorized\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithUnknownAction");
		} else {
			System.out.println("Fail- shipWithUnknownAction");
		}

	}

	public void shipSuccessWithActionCaseinsensitive(IAuthentication authentication, IDatabase databaseMock,
			IShipMate shipMateMock, RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"ShIP\",\"drug\":\"Vicks\",\"quantity\":1,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":200,\"estimateddeliverydate\":\"29-05-2019\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipSuccessWithActionCaseinsensitive");
		} else {
			System.out.println("Fail- shipSuccessWithActionCaseinsensitive");
		}

	}

	public void shipWithIncorrectUsername(IAuthentication authentication, IDatabase databaseMock,
			IShipMate shipMateMock, RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasa\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":6,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Not Authorized\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithIncorrectUsername");
		} else {
			System.out.println("Fail- shipWithIncorrectUsername");
		}
	}

	public void shipWithUsernameEmpty(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":6,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Not Authorized\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithUsernameEmpty");
		} else {
			System.out.println("Fail- shipWithUsernameEmpty");
		}
	}

	public void shipSuccessUsernameCaseinsensitive(IAuthentication authentication, IDatabase databaseMock,
			IShipMate shipMateMock, RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaShIp\",\"action\":\"SHIP\",\"drug\":\"Vicks\",\"quantity\":1,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":200,\"estimateddeliverydate\":\"29-05-2019\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipSuccessUsernameCaseinsensitive");
		} else {
			System.out.println("Fail- shipSuccessUsernameCaseinsensitive");
		}
	}

	public void shipWithDrugEmpty(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"\",\"quantity\":6,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Unknown Drug\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithDrugEmpty");
		} else {
			System.out.println("Fail- shipWithDrugEmpty");
		}
	}

	public void shipWithUnknownDrug(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Muciprocin\",\"quantity\":6,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Unknown Drug\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithUnknownDrug");
		} else {
			System.out.println("Fail- shipWithUnknownDrug");
		}

	}

	public void shipTylenolNotinStock(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":11,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Insufficient Stock\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipTylenolNotinStock");
		} else {
			System.out.println("Fail- shipTylenolNotinStock");
		}

	}

	public void shipTylenolClaimInstock(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":5,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":200,\"estimateddeliverydate\":\"29-05-2019\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipTylenolClaimInstock");
		} else {
			System.out.println("Fail- shipTylenolClaimInstock");
		}

	}

	public void queryDrugReducedCount(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"ManasaTRUE\",\"username\":\"manasaQUERY\",\"action\":\"QUERY\",\"drug\":\"Tylenol\"}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":200,\"count\":5}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- queryTylenolReducedCount");
		} else {
			System.out.println("Fail- queryTylenolReducedCount");
		}

	}

	public void shipWithCustomernameEmpty(IAuthentication authentication, IDatabase databaseMock,
			IShipMate shipMateMock, RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":3,\"address\":{\"customer\":\"\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Unknown Address\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithCustomernameEmpty");
		} else {
			System.out.println("Fail- shipWithCustomernameEmpty");
		}

	}

	public void shipWithStreetnameEmpty(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":3,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Unknown Address\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithStreetnameEmpty");
		} else {
			System.out.println("Fail- shipWithStreetnameEmpty");
		}

	}

	public void shipWithCitynameEmpty(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":3,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Unknown Address\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithCitynameEmpty");
		} else {
			System.out.println("Fail- shipWithCitynameEmpty");
		}

	}

	public void shipWithCountrynameEmpty(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":3,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Unknown Address\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithCountrynameEmpty");
		} else {
			System.out.println("Fail- shipWithCountrynameEmpty");
		}

	}

	public void shipWithProvincenameEmpty(IAuthentication authentication, IDatabase databaseMock,
			IShipMate shipMateMock, RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":3,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Unknown Address\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithProvincenameEmpty");
		} else {
			System.out.println("Fail- shipWithProvincenameEmpty");
		}

	}

	public void shipWithPostalcodeEmpty(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":3,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Unknown Address\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithPostalcodeEmpty");
		} else {
			System.out.println("Fail- shipWithPostalcodeEmpty");
		}

	}

	public void streetAddressInNumber(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":3,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":12,\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Unknown Address\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- streetAddressInNumber");
		} else {
			System.out.println("Fail- streetAddressInNumber");
		}

	}

	public void incorrectStreetAddress(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":3,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123456 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Unknown Address\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- incorrectStreetAddress");
		} else {
			System.out.println("Fail- incorrectStreetAddress");
		}

	}

	public void successWithStreetAddressCaseInsensitive(IAuthentication authentication, IDatabase databaseMock,
			IShipMate shipMateMock, RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Vicks\",\"quantity\":1,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":200,\"estimateddeliverydate\":\"29-05-2019\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- successWithStreetAddressCaseInsensitive");
		} else {
			System.out.println("Fail- successWithStreetAddressCaseInsensitive");
		}

	}

	public void incorrectProvince(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":3,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123456 Street\",\"city\":\"Halifax\",\"province\":\"NS\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Unknown Address\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- incorrectProvince");
		} else {
			System.out.println("Fail- incorrectProvince");
		}

	}

	public void incorrectCity(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":3,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123456 Street\",\"city\":\"Brunswick\",\"province\":\"NS\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Unknown Address\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- incorrectCity");
		} else {
			System.out.println("Fail- incorrectCity");
		}

	}

	public void incorrectCountry(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":3,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123456 Street\",\"city\":\"Halifax\",\"province\":\"NS\",\"country\":\"India\",\"postalCode\":\"hohoho\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":500,\"error\":\"Unknown Address\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- incorrectProvince");
		} else {
			System.out.println("Fail- incorrectProvince");
		}

	}



	public void shipWithQuantityEmpty(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Vicks\",\"quantity\":\"\",\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":200,\"estimateddeliverydate\":\"29-05-2019\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipWithQuantityEmpty");
		} else {
			System.out.println("Fail- shipWithQuantityEmpty");
		}

	}

	public void shipValidScenario(IAuthentication authentication, IDatabase databaseMock, IShipMate shipMateMock,
			RequestProcessor requestProcessor) {
		String json = "{\"apikey\":\"manasaTRUE\",\"username\":\"manasaSHIP\",\"action\":\"SHIP\",\"drug\":\"Tylenol\",\"quantity\":3,\"address\":{\"customer\":\"Rob Hawkey\",\"street\":\"123 Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String response = requestProcessor.processRequest(json, authentication, shipMateMock, databaseMock);

		String expectedResponse = "{\"status\":200,\"estimateddeliverydate\":\"29-05-2019\"}";
		if (response.equals(expectedResponse)) {
			System.out.println("Pass- shipValidScenario");
		} else {
			System.out.println("Fail- shipValidScenario");
		}

	}

}
