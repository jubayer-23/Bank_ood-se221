import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class LoginUser {
    private String username;
    private String password;
    private String role; 

    public LoginUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

public class BankApplicationMain {
    private static ArrayList<LoginUser> loginUsers = new ArrayList<>();
    private static Bank bank = new Bank("WWE Bank");

    public static void main(String[] args) {
        
        loginUsers.add(new LoginUser("admin", "admin123", "admin")); 
        loginUsers.add(new LoginUser("user1", "user123", "user"));   

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to the Banking Management System!");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 1) {
                adminLogin(scanner);
            } else if (choice == 2) {
                userLogin(scanner);
            } else if (choice == 3) {
                System.out.println("Exiting the system. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice! Please try again.");
            }
        }

        scanner.close();
    }

    private static void adminLogin(Scanner scanner) {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        LoginUser loggedInAdmin = authenticate(username, password, "admin");

        if (loggedInAdmin != null) {
            System.out.println("Admin login successful!");
            adminInterface(scanner);
        } else {
            System.out.println("Invalid Admin credentials! Returning to the main menu.");
        }
    }

    private static void userLogin(Scanner scanner) {
        System.out.print("Enter User Username (Account Number): ");
        String username = scanner.nextLine();
        System.out.print("Enter User Password: ");
        String password = scanner.nextLine();

        LoginUser loggedInUser = authenticate(username, password, "user");

        if (loggedInUser != null) {
            System.out.println("User login successful!");
            userMenu(scanner);
        } else {
            System.out.println("Invalid User credentials! Returning to the main menu.");
        }
    }

    private static LoginUser authenticate(String username, String password, String role) {
        for (LoginUser user : loginUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password) && user.getRole().equals(role)) {
                return user;
            }
        }
        return null; 
    }

    private static void adminInterface(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Interface:");
            System.out.println("1. Admin Menu");
            System.out.println("2. Switch to User Mode");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 1) {
                adminMenu(scanner);
            } else if (choice == 2) {
                userLogin(scanner);
            } else if (choice == 3) {
                System.out.println("Returning to the main menu...");
                break;
            } else {
                System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void adminMenu(Scanner scanner) {
        while (true) {
            try {
                System.out.println("\nAdmin Menu:");
                System.out.println("1. Create Account for user");
                System.out.println("2. Set Password for User");
                System.out.println("3. View All Accounts");
                System.out.println("4. Back to Admin Interface");
                System.out.print("Enter your choice: ");
                int adminChoice = scanner.nextInt();

                switch (adminChoice) {
                    case 1:
                        System.out.print("Enter Name: ");
                        String name = scanner.next();
                        System.out.print("Enter Email: ");
                        String email = scanner.next();
                        System.out.print("Enter Address: ");
                        String address = scanner.next();
                        System.out.print("Enter Account Number: ");
                        int accNum = scanner.nextInt();
                        bank.createAccount(name, email, address, accNum);

                        loginUsers.add(new LoginUser(String.valueOf(accNum), "default", "user"));
                        System.out.println("Account created. Default password is 'default'. Please set a password for the user.");
                        break;
                    case 2:
                        System.out.print("Enter Account Number to set password for: ");
                        String accountUsername = scanner.next();
                        LoginUser user = findLoginUser(accountUsername);
                        if (user != null) {
                            System.out.print("Enter New Password: ");
                            String newPassword = scanner.next();
                            user.setPassword(newPassword);
                            System.out.println("Password set successfully!");
                        } else {
                            System.out.println("User not found!");
                        }
                        break;
                    case 3:
                        bank.viewAllAccounts();
                        break;
                    case 4:
                        System.out.println("Returning to Admin Interface...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine(); 
            }
        }
    }

    private static void userMenu(Scanner scanner) {
        while (true) {
            try {
                System.out.println("\nUser Menu:");
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Check Balance");
                System.out.println("4. Transfer Money");
                System.out.println("5. View Transaction History");
                System.out.println("6. Logout");
                System.out.print("Enter your choice: ");
                int userChoice = scanner.nextInt();

                System.out.print("Enter your Account Number: ");
                int accountNumber = scanner.nextInt();
                User user = bank.findUser(accountNumber);

                if (user == null) {
                    System.out.println("Account not found!");
                    continue;
                }

                switch (userChoice) {
                    case 1:
                        System.out.print("Enter Amount to Deposit: ");
                        double depAmount = scanner.nextDouble();
                        user.deposit(depAmount);
                        break;
                    case 2:
                        System.out.print("Enter Amount to Withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        user.withdraw(withdrawAmount);
                        break;
                    case 3:
                        user.checkBalance();
                        break;
                    case 4:
                        System.out.print("Enter Recipient Account Number: ");
                        int recipientAccNum = scanner.nextInt();
                        User recipient = bank.findUser(recipientAccNum);
                        if (recipient == null) {
                            System.out.println("Recipient account not found!");
                        } else {
                            System.out.print("Enter Transfer Amount: ");
                            double transferAmount = scanner.nextDouble();
                            user.transferMoney(transferAmount, recipient);
                        }
                        break;
                    case 5:
                        user.viewTransactionHistory();
                        break;
                    case 6:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine(); 
            }
        }
    }

    private static LoginUser findLoginUser(String username) {
        for (LoginUser user : loginUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}




