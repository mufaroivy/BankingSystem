import java.security.SecureRandom;
import java.util.UUID;

import static java.text.NumberFormat.Field.PREFIX;

public class CurrentAccount extends Account {
    private double overdraftLimit;
    private static final String PREFIX = "ACC";
    private static final String ALPHABET = "0123456789012334567890123123457678ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Alphanumeric characters
    private static final SecureRandom RANDOM = new SecureRandom();

    public CurrentAccount(Customer accountHolder, double initialDeposit, double overdraftLimit) {
        super(generateShortUUID(10), accountHolder, initialDeposit);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (balance + overdraftLimit >= amount) {
            balance -= amount;
            logTransaction("Withdrawal", amount);
        } else {
            throw new IllegalArgumentException("You have exceeded the overdraft limit.");
        }
    }
    public static String generateShortUUID(int length) {
        StringBuilder sb = new StringBuilder(PREFIX);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }

    @Override
    public void applyInterest() {
        // No interest for Current Account
    }
}
