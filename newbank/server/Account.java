//Import
package newbank.server;

import java.util.ArrayList;
import java.time.LocalDate;

public class Account {
	private String accountName;
	private double openingBalance;
	private double totalBalance;
	private ArrayList<String> bankStatement;
	private String customer;

	public Account(String customer, String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.totalBalance = openingBalance;
		this.bankStatement = new ArrayList<String>();
		this.bankStatement.add("Opening Balance is " + openingBalance);
		this.customer = customer;
	}

	// Method that returns string accountName and openingBalance
	public String toString() {
		return (accountName + ": " + openingBalance);
	}

	// Add amount
	public boolean addAmount(double amount) throws Exception {
		if (amount <= 0) {
			throw new Exception("Amount should be greater than 0.");
		}
		this.totalBalance += amount;
		this.bankStatement.add("Amount added to the account is " + amount + "on date: " + LocalDate.now());
		return true;
	}

	// Subtract amount
	public boolean subtractAmount(double amount) throws Exception {
		if (amount <= 0) {
			throw new Exception("Amount should be greater than 0");
		}
		if ((this.totalBalance - amount) < 0) {
			throw new Exception("There is no sufficient amount in the account to be debited");
		}
		this.totalBalance -= amount;
		this.bankStatement.add("Amount subtracted from the account is " + "on date: " + LocalDate.now());
		return true;
	}

	// Get total balance
	public double getTotalBalance() {
		return this.totalBalance;
	}

	// Retrieve bank statement
	public ArrayList<String> retrieveBankStatement() {
		return this.bankStatement;
	}

	// Print bank statement
	public void printBankStatement() {
		System.out.println("Customer Name" + this.customer);
		System.out.println("Account Name" + this.accountName);
		for (int i = 0; i < this.bankStatement.size(); i++) {
			System.out.println(this.bankStatement.get(i));
		}
	}

	// Check Account Name
	public boolean checkAccountExist(String accountName) {
		if (this.accountName == accountName) {
			return true;
		}
		return false;
	}
}
