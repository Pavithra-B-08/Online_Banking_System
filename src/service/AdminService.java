package service;

import dao.TransactionDAO;
import dao.UserDAO;
import model.Transaction;
import model.User;

import java.util.List;

public class AdminService {
    private UserDAO userDAO = new UserDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public void viewAllUsers() {
        List<User> users = userDAO.getAllUsers();
        System.out.println("\n=== All Users ===");
        for (User u : users) {
            System.out.printf("ID: %d | Username: %s | Balance: ₹%.2f\n", u.getId(), u.getUsername(), u.getBalance());
        }
    }

    public void viewAllTransactions() {
        List<Transaction> txns = transactionDAO.getAllTransactions();
        System.out.println("\n=== All Transactions ===");
        for (Transaction t : txns) {
            System.out.printf("UserID: %d | Type: %s | Amount: ₹%.2f | Time: %s\n",
                    t.getUserId(), t.getType(), t.getAmount(), t.getTimestamp());
        }
    }
}

