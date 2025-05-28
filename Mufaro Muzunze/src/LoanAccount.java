import java.security.SecureRandom;
import java.util.UUID;

import static java.text.NumberFormat.Field.PREFIX;

public class LoanAccount extends Account {
    private double loanAmount;
    private double interestRate;
    private static final String PREFIX = "LOA";
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Alphanumeric characters
    private static final SecureRandom RANDOM = new SecureRandom();

    public LoanAccount(Customer accountHolder, double loanAmount, double interestRate) {
        super(generateShortUUID(10), accountHolder, -loanAmount);  // Negative balance for loans
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
    }

    @Override
    public void applyInterest() {
        double interest = -balance * interestRate / 100; // Interest increases the debt
        balance -= interest;
        logTransaction("Interest Charge", -interest);
        System.out.println("An interest of " + -interest + " applied to Loan Account " + accountNumber);
    }

    public static String generateShortUUID(int length) {
        StringBuilder sb = new StringBuilder(PREFIX);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }

    public void repayLoan(double amount) {
        if (amount <= -balance) {
            balance += amount;
            logTransaction("Loan Repayment", amount);
        } else {
            throw new IllegalArgumentException("Repayment exceeded the loan amount.");
        }
    }
}
