import java.util.ArrayList;
import java.util.List;

public class Bank implements IBank {
    public String bankName;
    private List<Customer> customers;
    private List<Account> accounts;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.customers = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    @Override
    public void addCustomer(Customer c) {
        customers.add(c);
    }

    @Override
    public void openAccount(Customer c, Account a) {
        accounts.add(a);
        c.openAccount(a);
    }

    @Override
    public Account findAccountByNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public void calculateMonthlyInterest() {
        for (Account account : accounts) {
            if (account instanceof SavingsAccount || account instanceof LoanAccount) {
                account.applyInterest();
            }
        }
    }

    public Customer getCustomerById(String customerID) {
        for (Customer c : customers) {
            if (c.getCustomerID().equals(customerID)) {
                return c;
            }
        }
        return null;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

}