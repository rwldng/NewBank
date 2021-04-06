package newbank.server;

import java.util.HashMap;
import java.lang.String;

public class NewBank {

	private static final NewBank bank = new NewBank();
	private Authentication auth = new Authentication();
	private HashMap<String,Customer> customers;
	private Pay pay = new Pay();

	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}

	private void addTestData() {
		String user1 = "Bhagy";
		auth.addNewCustomer(user1, "password1");
		Customer bhagy = auth.getCustomerFromUsername(user1);
		bhagy.addAccount(new Account(user1, "Main", 1000.0));

		String user2 = "Christina";
		auth.addNewCustomer(user2, "password2");
		Customer christina = auth.getCustomerFromUsername(user2);
		christina.addAccount(new Account(user2, "Savings", 1500.0));

		String user3 = "John";
		auth.addNewCustomer(user3, "password3");
		Customer john = auth.getCustomerFromUsername(user3);
		john.addAccount(new Account("John","Checking", 250.0));
	}

	// sources relevant enum for request
	private Action parseAction(String request) {
		String parsedRequest = request.split(" ")[0];
		return Action.valueOf(parsedRequest);
	}

	public static NewBank getBank() {
		return bank;
	}

	public Authentication getAuthData() {
		return auth;
	}

	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(customers.containsKey(userName)) {
			return new CustomerID(userName);
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if(auth.isUsernameValid(customer.getKey()) != null) {
			Action action = parseAction(request);
			switch(action) {
			case SHOWMYACCOUNTS:
				return showMyAccounts(customer);
			case NEWACCOUNT:
			case MOVE:
			case PAY:
				Customer sender = auth.getCustomerFromCustomerID(customer);
				Account senderAccount = sender.getDefaultAccount();
				if(pay.handlePaymentRequest(senderAccount, request)) {
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
		return auth.getCustomerFromCustomerID(customer).accountsToString();
	}
}
