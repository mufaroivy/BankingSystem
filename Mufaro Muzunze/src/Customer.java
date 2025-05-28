import java.util.ArrayList;

public class Customer  {
    private String customerID;
    private String name;
    private String address;
    private ArrayList<Account> accounts;

    public Customer(String customerID, String name, String address) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.accounts = new ArrayList<>();
    }

    public void openAccount(Account a) {
        accounts.add(a);
    }

    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    // Getters
    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}