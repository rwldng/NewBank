package newbank.server;

import java.util.ArrayList;

public class Customer {
	private ArrayList<Account> accounts;

	public Customer() {
		this.accounts = new ArrayList<Account>();
	}

	public String accountsToString() {
		String s = "";
		for (Account a : this.accounts) {
			s += a.toString();
		}
		return s;
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}

	public Account getDefaultAccount() {
		if (!accounts.isEmpty()) {
			return accounts.get(0);
		}
		return null;
	}

	public boolean checkingIfAccountExist(String accountName) {
		if (accounts.isEmpty()) {
			return false;
		}

		for (int i = 0; i < this.accounts.size(); i++) {
			Account act = this.accounts.get(i);
			act.checkAccountExist(accountName);
		}
		return false;
	}
}
