import java.util.ArrayList;

public class Bank {
    private String name;
    private ArrayList<User> users = new ArrayList<>();


    public Bank(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void createAccount(String name, String email, String address, int accountNumber) {
        User user = new User(name, email, address, accountNumber);
        users.add(user);
        System.out.println("Account " + accountNumber + " created successfully!");
    }


    public void viewAllAccounts() {
        for (User user : users) {
            System.out.println("Account Number: " + user.getAccountNumber());
        }
    }

   

    public User findUser(int accountNumber) {
        for (User user : users) {
            if (user.getAccountNumber() == accountNumber) {
                return user;
            }
        }
        return null;
    }

   
}



