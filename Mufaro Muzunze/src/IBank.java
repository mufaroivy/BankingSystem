public interface IBank {
    void addCustomer(Customer c);
    void openAccount(Customer c, Account a);
    Account findAccountByNumber(String accountNumber);
    void calculateMonthlyInterest();
}
