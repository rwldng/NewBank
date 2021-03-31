package newbank.server;

import java.util.ArrayList;

public class Customer {
	private ArrayList<Account> accounts;
	
	public Customer() {
		this.accounts = new ArrayList<Account>();
	}
	
	public String accountsToString() {
		String s = "";
		for(Account a : this.accounts) {
			s += a.toString();
		}
		return s;
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}
}
