package GUI;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame {
    private SystemRezerwacji system;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI(SystemRezerwacji system) {
        this.system = system;

        setTitle("System Parkingowy - Logowanie");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Login form
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.add(new JLabel("Login:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Hasło:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton loginButton = new JButton("Zaloguj");
        JButton registerButton = new JButton("Utwórz konto");

        loginButton.addActionListener(e -> performLogin());
        registerButton.addActionListener(e -> openRegistrationScreen());

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Proszę wprowadzić login i hasło", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = system.getUserManager().login(username, password);

        if (user != null) {
            // Open the main application window
            JOptionPane.showMessageDialog(this, "Logowanie pomyślne!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            openMainApplicationWindow(user);
        } else {
            JOptionPane.showMessageDialog(this, "Nieprawidłowy login lub hasło", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openRegistrationScreen() {
        new RegistrationGUI(system);
        dispose(); // Close the login window
    }

    private void openMainApplicationWindow(User user) {
        new ParkingGUI(system, user);
        dispose(); // Close the login window
    }
}