import java.util.ArrayList;

public class User extends Person {
    private int accountNumber;
    private double balance = 0;
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public User(String name, String email, String address, int accountNumber) {
        super(name, email, address); 
        this.accountNumber = accountNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit: " + amount));
        System.out.println(amount + " deposited successfully.");
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance for withdrawal!");
        } else {
            balance -= amount;
            transactions.add(new Transaction("Withdrawal: " + amount));
            System.out.println(amount + " withdrawn successfully.");
        }
    }

    public void transferMoney(double amount, User recipient) {
        if (amount > balance) {
            System.out.println("Insufficient balance for transfer!");
        } else {
            balance -= amount;
            recipient.balance += amount;
            transactions.add(new Transaction("Transferred: " + amount + " to " + recipient.getName()));
            recipient.transactions.add(new Transaction("Received: " + amount + " from " + getName()));
            System.out.println(amount + " transferred successfully to " + recipient.getName());
        }
    }

    public void checkBalance() {
        System.out.println("Available balance: " + balance);
    }

    public void viewTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction t : transactions) {
            System.out.println(t.getDetails());
        }
    }
}



