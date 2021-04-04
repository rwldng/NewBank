package newbank.server;

import java.util.HashMap;
import java.lang.String;

public class NewBank {

	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	private Pay pay = new Pay();

	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}

	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Bhagy","Main", 1000.0));
		customers.put("Bhagy", bhagy);

		Customer christina = new Customer();
		christina.addAccount(new Account("Christina", "Savings", 1500.0));
		customers.put("Christina", christina);

		Customer john = new Customer();
		john.addAccount(new Account("John","Checking", 250.0));
		customers.put("John", john);
	}

	// sources relevant enum for request
	private Action parseAction(String request) {
		String parsedRequest = request.split(" ")[0];
		return Action.valueOf(parsedRequest);
	}

	public static NewBank getBank() {
		return bank;
	}

	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(customers.containsKey(userName)) {
			return new CustomerID(userName);
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if(customers.containsKey(customer.getKey())) {
			Action action = parseAction(request);
			switch(action) {
			case SHOWMYACCOUNTS: return showMyAccounts(customer);
			case NEWACCOUNT:
			case MOVE:
			case PAY:
				Account sender = customers.get(customer.getKey()).getDefaultAccount();
				if(pay.handlePaymentRequest(sender, request)) {
					return "SUCCESS";
				} else {
					return "FAIL";
				}
			default : return "FAIL";
			}
		}
		return "FAIL";
	}

	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}
}
