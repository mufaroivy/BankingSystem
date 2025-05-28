import java.util.ArrayList;

public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected Customer accountHolder;
    protected ArrayList<Transaction> transactionLog;

    public Account(String accountNumber, Customer accountHolder, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialDeposit;
        this.transactionLog = new ArrayList<>();
        logTransaction("Deposit", initialDeposit);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getAccountHolder() { 
        return accountHolder;
    }

    public void deposit(double amount) {
        balance += amount;
        logTransaction("Deposit", amount);
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Your withdrawal exceeded your balance. Current balance: " + balance);
        }
        balance -= amount;
        logTransaction("Withdrawal", amount);
    }
    public abstract void applyInterest();

    public void logTransaction(String type, double amount) {
        String transactionID = java.util.UUID.randomUUID().toString();
        Transaction transaction = new Transaction(transactionID, accountNumber, type, amount, new java.util.Date());
        transactionLog.add(transaction);
        System.out.println("Transaction Logged: " + type + " of " + amount + " on account " + accountNumber);
    }
}