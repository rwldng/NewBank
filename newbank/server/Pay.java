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
        String[] recipientParts = Arrays.copyOfRange(requestParts, start, end);
        String recipient = String.join(" ", recipientParts);

        return makePayment(sender, paymentAmount, recipient);
    }

    public boolean makePayment(Account sender, double paymentAmount, String recipient) {
        try {
            sender.subtractAmount(paymentAmount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
