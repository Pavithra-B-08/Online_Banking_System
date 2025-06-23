package gui;

import javax.swing.*;
import java.awt.event.*;
import dao.UserDAO;
import model.User;

public class LoginForm {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;
    private UserDAO userDAO;

    public LoginForm() {
        userDAO = new UserDAO();
        frame = new JFrame("Bank Login");
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 80, 25);
        frame.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(120, 30, 160, 25);
        frame.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 80, 25);
        frame.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 70, 160, 25);
        frame.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 120, 100, 30);
        frame.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(160, 120, 100, 30);
        frame.add(registerButton);

        statusLabel = new JLabel("");
        statusLabel.setBounds(30, 160, 280, 25);
        frame.add(statusLabel);

        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> register());

        frame.setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = userDAO.login(username, password);
        if (user != null) {
            statusLabel.setText("Login successful. Balance: ₹" + user.getBalance());
            new Dashboard(user);  // Open user dashboard
            frame.dispose(); // Close login form
        } else {
            statusLabel.setText("Invalid credentials.");
        }
    }

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setBalance(0.0);

        boolean success = userDAO.registerUser(newUser);
        statusLabel.setText(success ? "Registration successful!" : "Registration failed.");
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
