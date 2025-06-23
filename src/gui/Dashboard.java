package gui;

import javax.swing.*;
import java.awt.event.*;
import model.User;
import dao.UserDAO;

public class Dashboard {
    private JFrame frame;
    private JLabel balanceLabel;
    private User user;
    private UserDAO userDAO;

    public Dashboard(User user) {
        this.user = user;
        this.userDAO = new UserDAO();

        frame = new JFrame("User Dashboard");
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        balanceLabel = new JLabel("Balance: ₹" + user.getBalance());
        balanceLabel.setBounds(100, 30, 200, 25);
        frame.add(balanceLabel);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(50, 80, 100, 30);
        frame.add(depositBtn);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(160, 80, 100, 30);
        frame.add(withdrawBtn);

        depositBtn.addActionListener(e -> {
            String amtStr = JOptionPane.showInputDialog("Enter deposit amount:");
            double amt = Double.parseDouble(amtStr);
            user.setBalance(user.getBalance() + amt);
            userDAO.updateBalance(user);
            updateBalance();
        });

        withdrawBtn.addActionListener(e -> {
            String amtStr = JOptionPane.showInputDialog("Enter withdrawal amount:");
            double amt = Double.parseDouble(amtStr);
            if (amt <= user.getBalance()) {
                user.setBalance(user.getBalance() - amt);
                userDAO.updateBalance(user);
                updateBalance();
            } else {
                JOptionPane.showMessageDialog(frame, "Insufficient balance.");
            }
        });

        frame.setVisible(true);
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: ₹" + user.getBalance());
    }
}

