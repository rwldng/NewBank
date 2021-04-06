package newbank.server;
import java.lang.String;
import java.util.Arrays;

public class Pay {
    public Pay() {
    }

    // stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
    private static boolean isValidPaymentAmount(String input) {
        return input.chars().allMatch(Character::isDigit);
    }

    // parses payment request
    public boolean handlePaymentRequest(Account sender, String request) {
        String[] requestParts = request.split(" ");
        int length = requestParts.length;
        int start = 1;
        int end = length - 1;

        if(!isValidPaymentAmount(requestParts[end])) {
            return false;
        }
        if(isValidPaymentAmount(requestParts[start])) {
            return false;
        }

        double paymentAmount = Double.parseDouble(requestParts[end]);

        return makePayment(sender, paymentAmount);
    }

    // deducts amount from account
    public boolean makePayment(Account sender, double paymentAmount) {
        try {
            sender.subtractAmount(paymentAmount);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
