package com.bank.db;

import dao.TransactionDAO;
import dao.UserDAO;
import model.Transaction;
import model.User;

import java.util.List;
import java.util.Scanner;

import service.AdminService;


public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        TransactionDAO txnDAO = new TransactionDAO();
        
        while (true) {
        	System.out.println("\n=== Online Banking System ===");
        	System.out.println("1. Register");
        	System.out.println("2. Login");
        	System.out.println("3. Admin Login");
        	System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String regUser = sc.nextLine();
                    System.out.print("Enter password: ");
                    String regPass = sc.nextLine();

                    User newUser = new User();
                    newUser.setUsername(regUser);
                    newUser.setPassword(regPass);
                    newUser.setBalance(0.0);

                    boolean registered = userDAO.registerUser(newUser);
                    System.out.println(registered ? "Registration successful!" : "Registration failed.");
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    String logUser = sc.nextLine();
                    System.out.print("Enter password: ");
                    String logPass = sc.nextLine();

                    User loggedIn = userDAO.login(logUser, logPass);

                    if (loggedIn != null) {
                        System.out.println("Login successful!");
                        boolean loggedInFlag = true;

                        while (loggedInFlag) {
                            System.out.println("\n=== User Dashboard ===");
                            System.out.println("1. View Balance");
                            System.out.println("2. Deposit Money");
                            System.out.println("3. Withdraw Money");
                            System.out.println("4. View Transaction History");
                            System.out.println("5. Logout");
                            System.out.print("Choose an option: ");
                            int userChoice = sc.nextInt();

                            switch (userChoice) {
                                case 1:
                                    System.out.println("Your current balance is: ₹" + loggedIn.getBalance());
                                    break;

                                case 2:
                                    System.out.print("Enter amount to deposit: ");
                                    double depositAmount = sc.nextDouble();
                                    loggedIn.setBalance(loggedIn.getBalance() + depositAmount);
                                    userDAO.updateBalance(loggedIn);

                                    txnDAO.saveTransaction(new Transaction(
                                            loggedIn.getId(),
                                            "deposit",
                                            depositAmount,
                                            java.time.LocalDateTime.now()
                                    ));

                                    System.out.println("Deposit successful!");
                                    break;

                                case 3:
                                    System.out.print("Enter amount to withdraw: ");
                                    double withdrawAmount = sc.nextDouble();
                                    if (withdrawAmount <= loggedIn.getBalance()) {
                                        loggedIn.setBalance(loggedIn.getBalance() - withdrawAmount);
                                        userDAO.updateBalance(loggedIn);

                                        txnDAO.saveTransaction(new Transaction(
                                                loggedIn.getId(),
                                                "withdraw",
                                                withdrawAmount,
                                                java.time.LocalDateTime.now()
                                        ));

                                        System.out.println("Withdrawal successful!");
                                    } else {
                                        System.out.println("Insufficient balance.");
                                    }
                                    break;

                                case 4:
                                    List<Transaction> txns = txnDAO.getTransactionsByUserId(loggedIn.getId());
                                    if (txns.isEmpty()) {
                                        System.out.println("No transactions found.");
                                    } else {
                                        System.out.println("=== Transaction History ===");
                                        for (Transaction t : txns) {
                                            System.out.printf("%s | %s | ₹%.2f\n",
                                                    t.getTimestamp(), t.getType(), t.getAmount());
                                        }
                                    }
                                    break;

                                case 5:
                                    loggedInFlag = false;
                                    System.out.println("Logged out successfully.");
                                    break;

                                default:
                                    System.out.println("Invalid option.");
                            }
                        }
                    } else {
                        System.out.println("Login failed. Invalid credentials.");
                    }
                    break;

                case 3:
                    System.out.print("Enter admin username: ");
                    String adminUser = sc.nextLine();
                    System.out.print("Enter admin password: ");
                    String adminPass = sc.nextLine();

                    if (adminUser.equals("admin") && adminPass.equals("admin")) {
                        System.out.println("Admin login successful!");
                        AdminService adminService = new AdminService();
                        boolean adminFlag = true;

                        while (adminFlag) {
                            System.out.println("\n=== Admin Panel ===");
                            System.out.println("1. View All Users");
                            System.out.println("2. View All Transactions");
                            System.out.println("3. Logout");
                            System.out.print("Choose an option: ");
                            int adminChoice = sc.nextInt();

                            switch (adminChoice) {
                                case 1:
                                    adminService.viewAllUsers();
                                    break;
                                case 2:
                                    adminService.viewAllTransactions();
                                    break;
                                case 3:
                                    adminFlag = false;
                                    System.out.println("Logged out of admin panel.");
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                            }
                        }
                    } else {
                        System.out.println("Invalid admin credentials.");
                    }
                    break;
                case 4:
                    System.out.println("Thank you for using the system!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
