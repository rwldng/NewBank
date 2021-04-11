package newbank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NewBankClientHandler extends Thread{

	private NewBank bank;
	private BufferedReader in;
	private PrintWriter out;
	private Authentication auth;

	public NewBankClientHandler(Socket s) throws IOException {
		bank = NewBank.getBank();
		auth = bank.getAuthData();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}

	public void run() {
		// keep getting requests from the client and processing them
		try {
			// ask for user name
			out.println("Enter Username");
			String userName = in.readLine();
			CustomerID customer = auth.isUsernameValid(userName);
			// ask for password
			if(customer != null) {
				out.println("Enter Password");
				String password = in.readLine();
				out.println("Checking Details...");
				if(auth.isPasswordValid(userName, password)) {
					out.println("Log In Successful. What do you want to do?");
					while(true) {
						String request = in.readLine();
						System.out.println("Request from " + customer.getKey());
						String responce = bank.processRequest(customer, request);
						out.println(responce);
					}
				} else {
					out.println("Log In Failed");
				}
			} else {
				out.println("Customer not found!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

}
