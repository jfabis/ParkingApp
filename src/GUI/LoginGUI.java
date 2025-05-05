package GUI;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame {
    private SystemRezerwacji system;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox rememberMeCheckbox;

    public LoginGUI(SystemRezerwacji system) {
        this.system = system;

        // Ustawienie wyglądu systemowego
        AppTheme.setSystemLookAndFeel();

        setTitle("System Parkingowy - Logowanie");
        setSize(400, 300);
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

        JLabel titleLabel = new JLabel("SYSTEM PARKINGOWY", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel formularza
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));

        // Komponenty formularza
        JLabel usernameLabel = new JLabel("Nazwa użytkownika");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(AppTheme.NORMAL_FONT);
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        usernameField = new JTextField(20);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        AppTheme.styleTextField(usernameField);

        JLabel passwordLabel = new JLabel("Hasło");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(AppTheme.NORMAL_FONT);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        AppTheme.styleTextField(passwordField);

        rememberMeCheckbox = new JCheckBox("Zapamiętaj mnie");
        rememberMeCheckbox.setForeground(Color.WHITE);
        rememberMeCheckbox.setFont(AppTheme.SMALL_FONT);
        rememberMeCheckbox.setOpaque(false);
        rememberMeCheckbox.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel przycisków
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        JButton loginButton = new JButton("Zaloguj");
        AppTheme.styleButton(loginButton, true);
        loginButton.setBackground(new Color(0, 200, 255));
        loginButton.setForeground(AppTheme.PRIMARY);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBorderPainted(true);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        Dimension loginSize = loginButton.getPreferredSize();
        loginButton.setPreferredSize(new Dimension(loginSize.width + 20, loginSize.height + 5));


        JButton registerButton = new JButton("Utwórz konto");
        AppTheme.styleButton(registerButton, false);

        // Dodawanie komponentów do formularza
        formPanel.add(usernameLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(usernameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(passwordLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(passwordField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(rememberMeCheckbox);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Dodawanie paneli do głównego panelu
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Dodawanie akcji
        loginButton.addActionListener(e -> performLogin());
        registerButton.addActionListener(e -> openRegistrationScreen());

        // Obsługa klawisza Enter
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Proszę wprowadzić login i hasło",
                    "Błąd logowania",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = system.getUserManager().login(username, password);

        if (user != null) {
            if (rememberMeCheckbox.isSelected()) {
                // Tutaj można dodać kod do zapisywania danych logowania
            }
            JOptionPane.showMessageDialog(this,
                    "Logowanie pomyślne!",
                    "Sukces",
                    JOptionPane.INFORMATION_MESSAGE);
            openMainApplicationWindow(user);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Nieprawidłowy login lub hasło",
                    "Błąd logowania",
                    JOptionPane.ERROR_MESSAGE);
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
