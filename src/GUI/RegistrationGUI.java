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

        // Ustawienie wyglądu systemowego
        AppTheme.setSystemLookAndFeel();

        setTitle("System Parkingowy - Rejestracja");
        setSize(450, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Główny panel z gradientem
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, AppTheme.SECONDARY, 0, h, AppTheme.PRIMARY);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Panel nagłówka
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel titleLabel = new JLabel("REJESTRACJA", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel formularza
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));

        // Komponenty formularza
        formPanel.add(createFormField("Login:", usernameField = new JTextField()));
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        formPanel.add(createFormField("Hasło:", passwordField = new JPasswordField()));
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        formPanel.add(createFormField("Potwierdź hasło:", confirmPasswordField = new JPasswordField()));
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        formPanel.add(createFormField("Email:", emailField = new JTextField()));
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel userTypePanel = new JPanel(new BorderLayout());
        userTypePanel.setOpaque(false);
        userTypePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        userTypePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel userTypeLabel = new JLabel("Typ użytkownika:");
        userTypeLabel.setForeground(Color.WHITE);
        userTypeLabel.setFont(AppTheme.NORMAL_FONT);

        String[] userTypes = {"Klient", "Właściciel", "Ochrona", "Administrator", "Konserwator"};
        userTypeComboBox = new JComboBox<>(userTypes);
        userTypeComboBox.setFont(AppTheme.NORMAL_FONT);

        userTypePanel.add(userTypeLabel, BorderLayout.WEST);
        userTypePanel.add(userTypeComboBox, BorderLayout.EAST);

        formPanel.add(userTypePanel);

        // Panel przycisków
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonPanel.setOpaque(false);

        JButton registerButton = new JButton("Zarejestruj");
        AppTheme.styleButton(registerButton, true);

        JButton backButton = new JButton("Powrót do logowania");
        AppTheme.styleButton(backButton, false);

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        // Dodawanie paneli do głównego panelu
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Dodawanie akcji
        registerButton.addActionListener(e -> performRegistration());
        backButton.addActionListener(e -> backToLogin());

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createFormField(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        label.setFont(AppTheme.NORMAL_FONT);

        AppTheme.styleTextField(field);

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private void performRegistration() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String email = emailField.getText();
        String userType = userTypeComboBox.getSelectedItem().toString().toLowerCase();

        // Validate fields
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            showErrorMessage("Wszystkie pola są wymagane");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showErrorMessage("Hasła nie pasują do siebie");
            return;
        }

        // Simple email validation
        if (!email.contains("@") || !email.contains(".")) {
            showErrorMessage("Podaj prawidłowy adres email");
            return;
        }

        // Register user
        boolean registrationSuccessful = system.getUserManager().registerUser(username, password, email, userType);

        if (registrationSuccessful) {
            showSuccessMessage("Rejestracja zakończona pomyślnie!");
            backToLogin();
        } else {
            showErrorMessage("Nazwa użytkownika jest już zajęta");
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Błąd rejestracji", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Sukces", JOptionPane.INFORMATION_MESSAGE);
    }

    private void backToLogin() {
        new LoginGUI(system);
        dispose(); // Close the registration window
    }
}
