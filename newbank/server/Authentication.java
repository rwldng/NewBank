package newbank.server;

import java.util.HashMap;

public class Authentication {
    private HashMap<String, String> userInfo;
    private HashMap<String,Customer> customers;

    public Authentication() {
        this.userInfo = new HashMap<>();
        this.customers = new HashMap<>();
    }

    // checks if customer is registered by searching by username and returns CustomerID
     public synchronized CustomerID isUsernameValid(String username) {
         if(customers.containsKey(username)) {
 			return new CustomerID(username);
 		}
 		return null;
     }

     // checks existing user's password
     public boolean isPasswordValid(String username, String password) {
         return userInfo.get(username).equals(password);
     }

     // adds new customer info
     public void addNewCustomer(String username, String password) {
         userInfo.put(username, password);
         customers.put(username, new Customer());
         return;
     }

     // get customer from username string
     public Customer getCustomerFromUsername(String username) {
         return customers.get(username);
     }

     // get customer from customerID
     public Customer getCustomerFromCustomerID(CustomerID customer) {
         return customers.get(customer.getKey());
     }
}
