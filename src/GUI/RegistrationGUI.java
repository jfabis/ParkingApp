package GUI;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrationGUI extends JFrame {
    private SystemRezerwacji system;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;
    private JComboBox<String> userTypeComboBox;

    public RegistrationGUI(SystemRezerwacji system) {
        this.system = system;

        setTitle("System Parkingowy - Rejestracja");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Registration form
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.add(new JLabel("Login:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Hasło:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Potwierdź hasło:"));
        confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Typ użytkownika:"));
        String[] userTypes = {"Klient", "Właściciel", "Ochrona", "Administrator", "Konserwator"};
        userTypeComboBox = new JComboBox<>(userTypes);
        formPanel.add(userTypeComboBox);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton registerButton = new JButton("Zarejestruj");
        JButton backButton = new JButton("Powrót do logowania");

        registerButton.addActionListener(e -> performRegistration());
        backButton.addActionListener(e -> backToLogin());

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void performRegistration() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String email = emailField.getText();
        String userType = userTypeComboBox.getSelectedItem().toString().toLowerCase();

        // Validate fields
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Wszystkie pola są wymagane", "Błąd rejestracji", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Hasła nie pasują do siebie", "Błąd rejestracji", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Simple email validation
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Podaj prawidłowy adres email", "Błąd rejestracji", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Register user
        User newUser = system.getUserManager().registerUser(username, password, email, userType);

        if (newUser != null) {
            JOptionPane.showMessageDialog(this, "Rejestracja zakończona pomyślnie!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            backToLogin();
        } else {
            JOptionPane.showMessageDialog(this, "Nazwa użytkownika jest już zajęta", "Błąd rejestracji", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void backToLogin() {
        new LoginGUI(system);
        dispose(); // Close the registration window
    }
}