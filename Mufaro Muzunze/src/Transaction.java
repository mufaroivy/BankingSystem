import java.util.Date;

public class Transaction {
    private String transactionID;
    private String accountNumber;
    private String transactionType;
    private double amount;
    private Date date;

    public Transaction(String transactionID, String accountNumber, String transactionType, double amount, Date date) {
        this.transactionID = transactionID;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
    }

    // Getters and toString method for logging
    @Override
    public String toString() {
        return transactionID + ", " + accountNumber + ", " + transactionType + ", " + amount + ", " + date;
    }
}
