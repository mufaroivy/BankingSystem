import java.security.SecureRandom;
import java.util.UUID;

import static java.text.NumberFormat.Field.PREFIX;

public class SavingsAccount extends Account {
    private double interestRate;
    private static final String PREFIX = "SAV";
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Alphanumeric characters
    private static final SecureRandom RANDOM = new SecureRandom();

    public SavingsAccount(Customer accountHolder, double initialDeposit, double interestRate) {
        super(generateShortUUID(10), accountHolder, initialDeposit);
        this.interestRate = interestRate;
    }

    @Override
    public void applyInterest() {
        double interest = balance * interestRate / 100;
        deposit(interest);
        System.out.println("Interest of " + interest + " applied to Savings Account " + accountNumber);
    }

    public static String generateShortUUID(int length) {
        StringBuilder sb = new StringBuilder(PREFIX);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }


}
