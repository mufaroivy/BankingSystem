import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer; 
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Date;

public class Main {
    private static Bank bank;
    private static JFrame mainFrame;

    public static void main(String[] args) {
        bank = new Bank("Ivy's Bank");
        mainFrame = new JFrame("Banking System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 400); // Adjusted window size
        mainFrame.setLocationRelativeTo(null); // Center the window
        mainFrame.setLayout(new BorderLayout());

        // Create and add a welcome message
        JLabel welcomeLabel = new JLabel("Ivy's Banking System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.ITALIC, 36)); 
        welcomeLabel.setForeground(new Color(255, 69, 0)); // Changed color to a vibrant orange-red
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); 
        welcomeLabel.setOpaque(true); 
        welcomeLabel.setBackground(new Color(240, 248, 255)); 
        mainFrame.add(welcomeLabel, BorderLayout.NORTH); 

        JMenuBar menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);

        // Create main menu
        JMenu customerMenu = new JMenu("Customer");
        createMenuItem(customerMenu, "Create Customer", e -> openCustomerForm());
        createMenuItem(customerMenu, "View Transaction History", e -> openTransactionHistoryForm());

        JMenu accountMenu = new JMenu("Account");
        createMenuItem(accountMenu, "Open Account", e -> openAccountForm());
        createMenuItem(accountMenu, "Deposit Money", e -> openDepositForm());
        createMenuItem(accountMenu, "Withdraw Money", e -> openWithdrawForm());
        createMenuItem(accountMenu, "Check Balance", e -> openBalanceForm());

        JMenu loanMenu = new JMenu("Loan");
        createMenuItem(loanMenu, "Repay Loan", e -> openRepayLoanForm());


        JMenu databaseMenu = new JMenu("Database");
        createMenuItem(databaseMenu, "Access Bank Database", e -> openDatabaseAccessForm());

        // Add menus to the menu bar
        menuBar.add(customerMenu);
        menuBar.add(accountMenu);
        menuBar.add(loanMenu);
        menuBar.add(databaseMenu);

        mainFrame.setVisible(true);
    }

    private static void createMenuItem(JMenu menu, String title, ActionListener action) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(action);
        menu.add(menuItem);
    }

    private static void openCustomerForm() {
        JFrame customerFrame = new JFrame("Create Customer");
        customerFrame.setSize(400, 250); 
        customerFrame.setLocationRelativeTo(null);
        customerFrame.setLayout(new GridLayout(5, 2, 10, 10)); 
    
        // Set a soft background color for the frame
        customerFrame.getContentPane().setBackground(new Color(245, 245, 245)); // Light gray background
    
        // Create text fields with rounded border
        JTextField customerIdField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField addressField = new JTextField();
    
        // Set background color for text fields
        Color textFieldColor = new Color(255, 255, 255); // White background for text fields
        customerIdField.setBackground(textFieldColor);
        nameField.setBackground(textFieldColor);
        addressField.setBackground(textFieldColor);
    
        // Set font for labels
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        JLabel customerIdLabel = new JLabel("Customer ID:");
        customerIdLabel.setFont(labelFont);
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(labelFont);
    
        // Add labels and fields to the frame
        customerFrame.add(customerIdLabel);
        customerFrame.add(customerIdField);
        customerFrame.add(nameLabel);
        customerFrame.add(nameField);
        customerFrame.add(addressLabel);
        customerFrame.add(addressField);
    
        // Create submit button and style it
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(70, 130, 180)); // Steel blue
        submitButton.setForeground(Color.WHITE); // White text
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Bold font for emphasis
        submitButton.setFocusPainted(false); // Remove focus paint
        submitButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Border to match color
        submitButton.addActionListener(e -> {
            String customerID = customerIdField.getText();
            String name = nameField.getText();
            String address = addressField.getText();
            Customer newCustomer = new Customer(customerID, name, address);
            bank.addCustomer(newCustomer);
            saveCustomerToCSV(newCustomer); // Save customer details to CSV
            JOptionPane.showMessageDialog(customerFrame, "Customer created successfully!");
            customerFrame.dispose();
        });
    
        // Add components to the frame
        customerFrame.add(new JLabel()); // Empty cell for layout
        customerFrame.add(submitButton);
    
        // Set a rounded border for text fields - using a custom painter
        customerIdField.setBorder(BorderFactory.createCompoundBorder());
        nameField.setBorder(BorderFactory.createCompoundBorder());
        addressField.setBorder(BorderFactory.createCompoundBorder());
    
        customerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the frame when done
        customerFrame.setVisible(true);
    }

    private static void saveCustomerToCSV(Customer customer) {
        String fileName = "customers.csv";
        try (FileWriter fw = new FileWriter(fileName, true); // Append mode
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(customer.getCustomerID() + "," + customer.getName() + "," + customer.getAddress() + "," + new Date()); // Ensure the Customer class has a toCSV() method for CSV format
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error saving customer: " + e.getMessage());
        }
    }


    private static void openAccountForm() {
        JFrame accountFrame = new JFrame("Open Account");
        accountFrame.setSize(400, 300);
        accountFrame.setLocationRelativeTo(null);
        accountFrame.setLayout(new GridLayout(6, 2, 10, 10)); // Added gaps for better spacing
    
        // Set a soft background color for the frame
        accountFrame.getContentPane().setBackground(new Color(245, 245, 245)); // Light gray background
    
        // Create text fields with a white background
        JTextField customerIdField = new JTextField();
        JTextField initialDepositField = new JTextField();
        JTextField overdraftLimitField = new JTextField();
    
        // Set background color for text fields
        Color textFieldColor = new Color(255, 255, 255); // White background for text fields
        customerIdField.setBackground(textFieldColor);
        initialDepositField.setBackground(textFieldColor);
        overdraftLimitField.setBackground(textFieldColor);
    
        // Set font for labels
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        accountFrame.add(new JLabel("Customer ID:"));
        accountFrame.add(customerIdField);
        accountFrame.add(new JLabel("Account Type (Savings/Current/Loan):"));
        JComboBox<String> accountTypeCombo = new JComboBox<>(new String[]{"Savings", "Current", "Loan"});
        accountFrame.add(accountTypeCombo);
        accountFrame.add(new JLabel("Initial Deposit:"));
        accountFrame.add(initialDepositField);
        accountFrame.add(new JLabel("Overdraft Limit:"));
        accountFrame.add(overdraftLimitField);
    
        // Create and style the submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(70, 130, 180)); // Steel blue
        submitButton.setForeground(Color.WHITE); // White text
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Bold font for emphasis
        submitButton.setFocusPainted(false); // Remove focus paint
        submitButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Border to match color
        submitButton.addActionListener(e -> {
            String customerID = customerIdField.getText();
            double initialDeposit = getDoubleFromField(initialDepositField);
            String accountType = (String) accountTypeCombo.getSelectedItem();
            double overdraftLimit = getDoubleFromField(overdraftLimitField);
    
            if (initialDeposit < 0 || (accountType.equals("Loan"))) {
                JOptionPane.showMessageDialog(accountFrame, "Invalid input! Please enter positive numbers.");
                return;
            }
    
            Customer customer = bank.getCustomerById(customerID);
            if (customer != null) {
                Account newAccount;
                switch (accountType) {
                    case "Savings":
                        newAccount = new SavingsAccount(customer, initialDeposit, overdraftLimit);
                        break;
                    case "Current":
                        newAccount = new CurrentAccount(customer, initialDeposit, overdraftLimit);
                        break;
                    case "Loan":
                        newAccount = new LoanAccount(customer, initialDeposit, overdraftLimit);
                        break;
                    default:
                        JOptionPane.showMessageDialog(accountFrame, "Invalid account type.");
                        return;
                }
                bank.openAccount(customer, newAccount);
                saveAccountToCSV(newAccount); // Save the account details to CSV
                JOptionPane.showMessageDialog(accountFrame, "Account opened successfully, Your Account Number: " + newAccount.getAccountNumber());
                accountFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(accountFrame, "Customer not found.");
            }
        });
    
        // Add the submit button to the frame
        accountFrame.add(new JLabel()); // Empty cell for layout
        accountFrame.add(submitButton);
    
        // Set a rounded border for text fields
        customerIdField.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Steel blue
initialDepositField.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Steel blue
overdraftLimitField.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Steel blue
    
        accountFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the frame when done
        accountFrame.setVisible(true);
    }


    private static void saveAccountToCSV(Account account) {
        String fileName = "accounts.csv";
        try (FileWriter fw = new FileWriter(fileName, true); // Append mode
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(account.getAccountNumber() + "," + account.getAccountHolder().getCustomerID() +
                    "," + account.getBalance() + "," + account.getClass().getSimpleName() + "," + new Date()); // Ensure the Account class has a toCSV() method for CSV format
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error saving account: " + e.getMessage());
        }
    }


    private static void openDepositForm() {
        JFrame depositFrame = new JFrame("Deposit Money");
        depositFrame.setSize(300, 150);
        depositFrame.setLocationRelativeTo(null);
        depositFrame.setLayout(new GridLayout(4, 2, 10, 10)); // Added gaps for better spacing

        // Create text fields
        JTextField accountNumberField = new JTextField();
        JTextField depositAmountField = new JTextField();

        // Set background color for text fields
        accountNumberField.setBackground(new Color(240, 240, 240)); // Light gray
        depositAmountField.setBackground(new Color(240, 240, 240)); // Light gray

        // Add components to the frame
        depositFrame.add(new JLabel("Account Number:"));
        depositFrame.add(accountNumberField);
        depositFrame.add(new JLabel("Deposit Amount:"));
        depositFrame.add(depositAmountField);

        // Create and style the submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(70, 130, 180)); 
        submitButton.setForeground(Color.WHITE); 
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
        submitButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        submitButton.setBorderPainted(true);
        submitButton.setOpaque(true); // Ensure the background color is shown
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand on hover
        submitButton.setPreferredSize(new Dimension(100, 40)); // Set preferred size for better appearance
        submitButton.addActionListener(e -> {
            String accountNumber = accountNumberField.getText();
            double depositAmount = getDoubleFromField(depositAmountField);
            if (depositAmount < 0) {
                JOptionPane.showMessageDialog(depositFrame, "Invalid input! Please enter a positive number.");
                return;
            }
            Account account = bank.findAccountByNumber(accountNumber);
            if (account != null) {
                account.deposit(depositAmount);
                writeTransactionToCSV(account, "Deposit", depositAmount); // Log the transaction
                JOptionPane.showMessageDialog(depositFrame, "You've made a successful deposit");
                depositFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(depositFrame, "Account not found.");
            }
        });

        // Add an empty label for layout purposes and then the submit button
        depositFrame.add(new JLabel()); // Empty cell for layout
        depositFrame.add(submitButton);

        // Style the frame
        depositFrame.getContentPane().setBackground(Color.WHITE); // White background for the frame
        depositFrame.setVisible(true);
    }

    private static void writeTransactionToCSV(Account account, String type, double amount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            writer.write(account.getAccountNumber() + "," + type + "," + amount + "," + new java.util.Date());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing transaction log to CSV: " + e.getMessage());
        }
    }

    private static void openWithdrawForm() {
        JFrame withdrawFrame = new JFrame("Withdraw Money");
        withdrawFrame.setSize(300, 150);
        withdrawFrame.setLocationRelativeTo(null);
        withdrawFrame.setLayout(new GridLayout(4, 2, 10, 10)); // Added gaps for better spacing
    
        // Set a soft background color for the frame
        withdrawFrame.getContentPane().setBackground(new Color(245, 245, 245)); // Light gray background
    
        // Create text fields with a white background
        JTextField accountNumberField = new JTextField();
        JTextField withdrawAmountField = new JTextField();
    
        // Set background color for text fields
        Color textFieldColor = new Color(255, 255, 255); // White background
        accountNumberField.setBackground(textFieldColor);
        withdrawAmountField.setBackground(textFieldColor);
    
        // Set border for text fields
        accountNumberField.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Steel blue
        withdrawAmountField.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Steel blue
    
        // Set font for labels
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        withdrawFrame.add(new JLabel("Account Number:"));
        withdrawFrame.add(accountNumberField);
        withdrawFrame.add(new JLabel("Withdraw Amount:"));
        withdrawFrame.add(withdrawAmountField);
    
        // Create and style the submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(70, 130, 180)); // Steel blue
        submitButton.setForeground(Color.WHITE); // White text
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Bold font for emphasis
        submitButton.setFocusPainted(false); // Remove focus paint
        submitButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Matching border color
        submitButton.setOpaque(true); // Ensure the background color is shown
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand on hover
        submitButton.setPreferredSize(new Dimension(100, 40)); // Set preferred size for better appearance
    
        submitButton.addActionListener(e -> {
            String accountNumber = accountNumberField.getText();
            double withdrawAmount = getDoubleFromField(withdrawAmountField);
            if (withdrawAmount < 0) {
                JOptionPane.showMessageDialog(withdrawFrame, "Invalid amount! Please enter a positive number.");
                return;
            }
            Account account = bank.findAccountByNumber(accountNumber);
            if (account != null) {
                if (account.getBalance() >= withdrawAmount) {
                    account.withdraw(withdrawAmount);
                    writeTransactionToCSV(account, "Withdraw", withdrawAmount); // Log the transaction
                    JOptionPane.showMessageDialog(withdrawFrame, "Withdrawal successful!");
                    withdrawFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(withdrawFrame, "Insufficient funds.");
                }
            } else {
                JOptionPane.showMessageDialog(withdrawFrame, "Account not found.");
            }
        });
    
        // Add an empty label for layout purposes and then the submit button
        withdrawFrame.add(new JLabel()); // Empty cell for layout
        withdrawFrame.add(submitButton);
    
        withdrawFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the frame when done
        withdrawFrame.setVisible(true);
    }

    private static void openBalanceForm() {
        JFrame balanceFrame = new JFrame("Check Balance");
        balanceFrame.setSize(300, 150);
        balanceFrame.setLocationRelativeTo(null);
        balanceFrame.setLayout(new GridLayout(3, 2, 10, 10)); // Added gaps for better spacing
    
        // Set a soft background color for the frame
        balanceFrame.getContentPane().setBackground(new Color(245, 245, 245)); // Light gray background
    
        // Create text field for account number with a white background
        JTextField accountNumberField = new JTextField();
        accountNumberField.setBackground(Color.WHITE); // White background for text field
        accountNumberField.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Steel blue border
    
        // Set font for labels
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        balanceFrame.add(new JLabel("Account Number:"));
        balanceFrame.add(accountNumberField);
    
        // Create and style the check balance button
        JButton checkButton = new JButton("Check Balance");
        checkButton.setBackground(new Color(70, 130, 180)); // Steel blue
        checkButton.setForeground(Color.WHITE); // White text
        checkButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Bold font for emphasis
        checkButton.setFocusPainted(false); // Remove focus paint
        checkButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Matching border color
        checkButton.setOpaque(true); // Ensure the background color is shown
        checkButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand on hover
        checkButton.setPreferredSize(new Dimension(120, 40)); // Set preferred size for better appearance

        // Add components to the frame
        balanceFrame.add(new JLabel("Account Number:"));
        balanceFrame.add(accountNumberField);
        balanceFrame.add(checkButton);

        // Action listener for the check button
        checkButton.addActionListener(e -> {
            String accountNumber = accountNumberField.getText();
            Account account = bank.findAccountByNumber(accountNumber);
            if (account != null) {
                JOptionPane.showMessageDialog(balanceFrame, "Balance: " + account.getBalance());
            } else {
                JOptionPane.showMessageDialog(balanceFrame, "Account not found.");
            }
        });

        // Style the frame
        balanceFrame.getContentPane().setBackground(Color.WHITE); // White background for the frame
        balanceFrame.setVisible(true);
    }




    private static void loadTransactionHistory(Account account, JTable table) {
        String fileName = "transactions.csv"; // Path to the transaction file
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            DefaultTableModel model = new DefaultTableModel(new String[]{"Account", "Type", "Amount", "Date"}, 0);
            String line;
            boolean accountFound = false;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4 && data[0].trim().equals(account.getAccountNumber())) { // Match account number
                    model.addRow(data);
                    accountFound = true;
                }
            }

            if (!accountFound) {
                JOptionPane.showMessageDialog(table, "No transactions found for the account.");
            }

            table.setModel(model);
            table.setDefaultEditor(Object.class, null); // Make table read-only
        } catch (IOException e) {
            JOptionPane.showMessageDialog(table, "Error loading transaction history: " + e.getMessage());
        }
    }

    private static void openRepayLoanForm() {
        JFrame repayLoanFrame = new JFrame("Repay Loan");
        repayLoanFrame.setSize(300, 150);
        repayLoanFrame.setLocationRelativeTo(null);
        repayLoanFrame.setLayout(new GridLayout(4, 2, 10, 10)); // Added gaps for better spacing
    
        // Set a soft background color for the frame
        repayLoanFrame.getContentPane().setBackground(new Color(245, 245, 245)); // Light gray background
    
        // Create text fields for account number and repayment amount with a white background
        JTextField accountNumberField = new JTextField();
        accountNumberField.setBackground(Color.WHITE); // White background for text field
        accountNumberField.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Steel blue border
    
        JTextField repaymentAmountField = new JTextField();
        repaymentAmountField.setBackground(Color.WHITE); // White background for text field
        repaymentAmountField.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Steel blue border
    
        // Set font for labels
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        repayLoanFrame.add(new JLabel("Account Number:"));
        repayLoanFrame.add(accountNumberField);
        repayLoanFrame.add(new JLabel("Repayment Amount:"));
        repayLoanFrame.add(repaymentAmountField);
    
        // Create and style the submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(70, 130, 180)); // Steel blue
        submitButton.setForeground(Color.WHITE); // White text
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Bold font for emphasis
        submitButton.setFocusPainted(false); // Remove focus paint
        submitButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Matching border color
        submitButton.setOpaque(true); // Ensure the background color is shown
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand on hover
        submitButton.setPreferredSize(new Dimension(100, 40)); // Set preferred size for better appearance
    
        submitButton.addActionListener(e -> {
            String accountNumber = accountNumberField.getText();
            double repaymentAmount = getDoubleFromField(repaymentAmountField);
            if (repaymentAmount <= 0) {
                JOptionPane.showMessageDialog(repayLoanFrame, "Invalid amount! Please enter a positive number.");
                return;
            }
            Account account = bank.findAccountByNumber(accountNumber);
            if (account instanceof LoanAccount) {
                try {
                    ((LoanAccount) account).repayLoan(-repaymentAmount); // Repay loan by passing negative amount
                    writeTransactionToCSV(account, "Loan Repayment", repaymentAmount); // Log the transaction
                    JOptionPane.showMessageDialog(repayLoanFrame, "Loan repayment successful!");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(repayLoanFrame, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(repayLoanFrame, "Account is not a Loan Account.");
            }
            repayLoanFrame.dispose();
        });

        // Add an empty label for layout purposes and then the submit button
        repayLoanFrame.add(new JLabel()); // Empty cell for layout
        repayLoanFrame.add(submitButton);

        // Style the frame
        repayLoanFrame.getContentPane().setBackground(Color.WHITE); // White background for the frame
        repayLoanFrame.setVisible(true);
    }



    private static void openTransactionHistoryForm() {
        JFrame historyFrame = new JFrame("Transaction History");
        historyFrame.setSize(500, 300);
        historyFrame.setLocationRelativeTo(null);
        historyFrame.setLayout(new BorderLayout());

        // Panel for user input
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Added gaps for better spacing
        inputPanel.setBackground(Color.WHITE); // Set background color to white

        JTextField accountNumberField = new JTextField();
accountNumberField.setBackground(Color.WHITE); // Changed to white for a cleaner look
accountNumberField.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Steel blue border

JButton viewButton = new JButton("View History");
viewButton.setBackground(new Color(70, 130, 180)); // Steel blue
viewButton.setForeground(Color.WHITE); // White text
viewButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Bold font for emphasis

        inputPanel.add(new JLabel("Account Number:"));
        inputPanel.add(accountNumberField);
        inputPanel.add(viewButton);

        // Table for displaying transaction history
        JTable transactionTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setPreferredSize(new Dimension(450, 200));

        viewButton.addActionListener(e -> {
            String accountNumber = accountNumberField.getText();
            Account account = bank.findAccountByNumber(accountNumber);
            if (account != null) {
                loadTransactionHistory(account, transactionTable);
            } else {
                JOptionPane.showMessageDialog(historyFrame, "Account not found.");
            }
        });

        // Add components to the main frame
        historyFrame.add(inputPanel, BorderLayout.NORTH);
        historyFrame.add(scrollPane, BorderLayout.CENTER);

        // Style the frame
        historyFrame.getContentPane().setBackground(Color.WHITE); // White background for the frame
        historyFrame.setVisible(true);
    }


    private static double getDoubleFromField(JTextField field) {
        try {
            return Double.parseDouble(field.getText());
        } catch (NumberFormatException e) {
            return -1; // Indicate an error
        }
    }

    //database in csv

    private static void openDatabaseAccessForm() {
        JFrame databaseFrame = new JFrame("Access Bank Database");
        databaseFrame.setSize(400, 300);
        databaseFrame.setLocationRelativeTo(null);
        databaseFrame.setLayout(new GridLayout(3, 1));

        JButton openAccountsButton = new JButton("Open Accounts Data");
        JButton openCustomersButton = new JButton("Open Customers Data");
        JButton openTransactionsButton = new JButton("Open Transactions Data");

        openAccountsButton.addActionListener(e -> openDataTable("accounts.csv", true));
        openCustomersButton.addActionListener(e -> openDataTable("customers.csv", true));
        openTransactionsButton.addActionListener(e -> openDataTable("transactions.csv", false));

        databaseFrame.add(openAccountsButton);
        databaseFrame.add(openCustomersButton);
        databaseFrame.add(openTransactionsButton);

        databaseFrame.setVisible(true);
    }

    private static void openDataTable(String csvFileName, boolean editable) {
        JFrame dataFrame = new JFrame("Data Table");
        dataFrame.setSize(600, 400);
        dataFrame.setLocationRelativeTo(null);
        dataFrame.setLayout(new BorderLayout());

        JTable table = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel();
        table.setModel(tableModel);

        // Set column headers based on the file being opened
        if (csvFileName.equals("transactions.csv")) {
            tableModel.setColumnIdentifiers(new String[]{"Account", "Transaction Type", "Amount", "Time"});
        } else if (csvFileName.equals("accounts.csv")) {
            tableModel.setColumnIdentifiers(new String[]{"Account", "Time Account Opened"});
        } else if (csvFileName.equals("customers.csv")) {
            tableModel.setColumnIdentifiers(new String[]{"Customer ID", "Name", "Address", "Date"});
        }

        loadDataFromCSV(csvFileName, tableModel);

        // Set the table's appearance
        table.setFillsViewportHeight(true);
        table.setBackground(Color.WHITE); // White background for the table
        table.setForeground(Color.BLACK); // Black text for contrast
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Set a consistent font
        table.setSelectionBackground(new Color(70, 130, 180)); // Steel blue selection color
        table.setSelectionForeground(Color.WHITE); // White selection text color

        JScrollPane scrollPane = new JScrollPane(table);
        dataFrame.add(scrollPane, BorderLayout.CENTER);

        if (editable) {
            // Make table editable and add Save/Delete buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.WHITE); // Set background color for button panel

            JButton saveButton = new JButton("Save Changes");
            saveButton.setBackground(new Color(70, 130, 180)); // Steel blue
            saveButton.setForeground(Color.WHITE); // White text
            saveButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Bold font for emphasis
            
            JButton deleteButton = new JButton("Delete Selected Row");
            deleteButton.setBackground(new Color(255, 69, 58)); // Bright red for delete
            deleteButton.setForeground(Color.WHITE); // White text
            deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Bold font for emphasis

            saveButton.addActionListener(e -> saveTableDataToCSV(csvFileName, tableModel));
            deleteButton.addActionListener(e -> deleteSelectedRow(tableModel, table));

            buttonPanel.add(saveButton);
            buttonPanel.add(deleteButton);
            dataFrame.add(buttonPanel, BorderLayout.SOUTH);
        } else {
            table.setDefaultEditor(Object.class, null); // Make table read-only for transactions
        }

        // Style the frame
        dataFrame.getContentPane().setBackground(Color.WHITE); // White background for the frame
        dataFrame.setVisible(true);
    }


    private static void loadDataFromCSV(String fileName, DefaultTableModel tableModel) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            String[] columnNames = line.split(",");
            tableModel.setColumnIdentifiers(columnNames);

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                tableModel.addRow(data);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error loading data: " + e.getMessage());
        }
    }

    private static void saveTableDataToCSV(String fileName, DefaultTableModel tableModel) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            // Write header
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                bw.write(tableModel.getColumnName(i));
                if (i < tableModel.getColumnCount() - 1) bw.write(",");
            }
            bw.newLine();

            // Write data
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    bw.write(tableModel.getValueAt(i, j).toString());
                    if (j < tableModel.getColumnCount() - 1) bw.write(",");
                }
                bw.newLine();
            }
            JOptionPane.showMessageDialog(mainFrame, "Data saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error saving data: " + e.getMessage());
        }
    }

    private static void deleteSelectedRow(DefaultTableModel tableModel, JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(table, "Please select a row to delete.");
        }
    }

}