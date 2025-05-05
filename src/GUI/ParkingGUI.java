package GUI;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ParkingGUI extends JFrame {
    private SystemRezerwacji system;
    private User currentUser;
    private JTextArea outputArea;

    // Kolory aplikacji
    private static final Color PRIMARY_COLOR = new Color(25, 118, 210);
    private static final Color SECONDARY_COLOR = new Color(66, 165, 245);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color TEXT_COLOR = new Color(33, 33, 33);
    private static final Color ACCENT_COLOR = new Color(76, 175, 80);

    public ParkingGUI(SystemRezerwacji system, User user) {
        this.system = system;
        this.currentUser = user;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("System Zarządzania Parkingiem - " + user.getUsername() + " (" + user.getUserType() + ")");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the GUI components based on user type
        createGUI();

        setVisible(true);
    }

    private void createGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Dodanie panelu nagłówka
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel centralny z obszarem tekstowym
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(BACKGROUND_COLOR);

        outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        outputArea.setBackground(Color.WHITE);
        outputArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                BorderFactory.createLineBorder(new Color(200, 200, 200))
        ));

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel przycisków użytkownika
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 10, 0),
                BorderFactory.createLineBorder(new Color(200, 200, 200))
        ));
        buttonPanel.setLayout(new GridLayout(0, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Dodaj przyciski specyficzne dla typu użytkownika
        addUserSpecificButtons(buttonPanel);

        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Panel dolny z przyciskiem wylogowania
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(BACKGROUND_COLOR);

        JButton logoutButton = new JButton("Wyloguj");
        styleButton(logoutButton, true, "OptionPane.warningIcon");
        logoutButton.setBackground(new Color(211, 47, 47)); // Czerwony kolor dla przycisku wylogowania
        logoutButton.addActionListener(e -> logout());

        bottomPanel.add(logoutButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Pokaż wiadomość powitalną
        outputArea.append("Witaj, " + currentUser.getUsername() + "!\n");
        outputArea.append("Typ konta: " + currentUser.getUserType() + "\n\n");
    }
    private ImageIcon createIcon(String path) {
        try {
            return new ImageIcon(getClass().getResource(path));
        } catch (Exception e) {
            System.err.println("Nie można załadować ikony: " + path);
            return null;
        }
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("System Zarządzania Parkingiem");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel userLabel = new JLabel("Zalogowany jako: " + currentUser.getUsername() +
                " (" + currentUser.getUserType() + ")");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(userLabel, BorderLayout.EAST);

        return headerPanel;
    }

    private void addUserSpecificButtons(JPanel panel) {
        // Common buttons for all users
        JButton btnProfile = new JButton("Mój profil");
        styleButton(btnProfile, false, "OptionPane.informationIcon");
        btnProfile.addActionListener(e -> showUserProfile());
        panel.add(btnProfile);

        // Add buttons based on user type
        switch (currentUser.getUserType()) {
            case "klient":
                addClientButtons(panel);
                break;
            case "właściciel":
                addOwnerButtons(panel);
                break;
            case "ochrona":
                addSecurityButtons(panel);
                break;
            case "administrator":
                addAdminButtons(panel);
                break;
            case "konserwator":
                addMaintenanceButtons(panel);
                break;
            default:
                // Default buttons for all users
                break;
        }
    }

    private void addClientButtons(JPanel panel) {
        JButton btnRezerwuj = new JButton("Zarezerwuj miejsce");
        JButton btnPokazRezerwacje = new JButton("Moje rezerwacje");
        JButton btnAnuluj = new JButton("Anuluj rezerwację");
        JButton btnHistoria = new JButton("Historia i faktury");
        JButton btnOcena = new JButton("Oceń usługę");
        JButton btnUslugiDodatkowe = new JButton("Usługi dodatkowe");

        styleButton(btnRezerwuj, false, "FileView.directoryIcon");
        styleButton(btnPokazRezerwacje, false, "FileView.fileIcon");
        styleButton(btnAnuluj, false, "OptionPane.errorIcon");
        styleButton(btnHistoria, false, "Table.descendingSortIcon");
        styleButton(btnOcena, false, "OptionPane.questionIcon");
        styleButton(btnUslugiDodatkowe, false, "FileChooser.detailsViewIcon");

        btnRezerwuj.addActionListener(e -> zarezerwuj());
        btnPokazRezerwacje.addActionListener(e -> pokazMojeRezerwacje());
        btnAnuluj.addActionListener(e -> anulujRezerwacje());
        btnHistoria.addActionListener(e -> pokazHistorie());
        btnOcena.addActionListener(e -> ocenUsluge());
        btnUslugiDodatkowe.addActionListener(e -> uslugiDodatkowe());

        panel.add(btnRezerwuj);
        panel.add(btnPokazRezerwacje);
        panel.add(btnAnuluj);
        panel.add(btnHistoria);
        panel.add(btnOcena);
        panel.add(btnUslugiDodatkowe);
    }
    private void styleButton(JButton button, boolean isPrimary, String iconKey) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Dodanie ikony systemowej ze zmniejszonym rozmiarem
        if (iconKey != null) {
            Icon originalIcon = UIManager.getIcon(iconKey);
            if (originalIcon instanceof ImageIcon) {
                ImageIcon icon = (ImageIcon) originalIcon;
                Image img = icon.getImage();
                // Zmniejsz rozmiar ikony do 14x14 pikseli (możesz dostosować te wartości)
                Image smallerImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(smallerImg));
                button.setIconTextGap(8); // Zmniejsz odstęp między ikoną a tekstem
            } else if (originalIcon != null) {
                button.setIcon(originalIcon);
                button.setIconTextGap(10);
            }
        }

        if (isPrimary) {
            button.setBackground(PRIMARY_COLOR);
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(PRIMARY_COLOR);
            button.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
        }

        // Zmniejsz marginesy przycisku, aby ikona była bliżej tekstu
        button.setMargin(new Insets(2, 4, 2, 4));
        Dimension currentSize = button.getPreferredSize();
        button.setPreferredSize(new Dimension(currentSize.width, 45));
    }

    private void addOwnerButtons(JPanel panel) {
        JButton btnRaporty = new JButton("Raporty zajętości");
        JButton btnOptCeny = new JButton("Optymalizacja cen");
        JButton btnMonitorowanieMiejsc = new JButton("Monitorowanie miejsc");
        JButton btnOceny = new JButton("Oceny klientów");

        styleButton(btnRaporty, false, "Table.ascendingSortIcon");
        styleButton(btnOptCeny, false, "FileChooser.upFolderIcon");
        styleButton(btnMonitorowanieMiejsc, false, "FileView.computerIcon");
        styleButton(btnOceny, false, "OptionPane.warningIcon");

        btnRaporty.addActionListener(e -> generujRaporty());
        btnOptCeny.addActionListener(e -> optymalizujCeny());
        btnMonitorowanieMiejsc.addActionListener(e -> monitorujMiejsca());
        btnOceny.addActionListener(e -> pokazOceny());

        panel.add(btnRaporty);
        panel.add(btnOptCeny);
        panel.add(btnMonitorowanieMiejsc);
        panel.add(btnOceny);
    }

    private void addSecurityButtons(JPanel panel) {
        JButton btnMonitoring = new JButton("Monitoring na żywo");
        JButton btnPowiadomienia = new JButton("Powiadomienia");
        JButton btnHistoria = new JButton("Historia nagrań");

        styleButton(btnMonitoring, false, "FileView.computerIcon");
        styleButton(btnPowiadomienia, false, "OptionPane.informationIcon");
        styleButton(btnHistoria, false, "Table.descendingSortIcon");

        btnMonitoring.addActionListener(e -> pokazMonitoring());
        btnPowiadomienia.addActionListener(e -> pokazPowiadomienia());
        btnHistoria.addActionListener(e -> pokazHistorieNagran());

        panel.add(btnMonitoring);
        panel.add(btnPowiadomienia);
        panel.add(btnHistoria);
    }

    private void addAdminButtons(JPanel panel) {
        JButton btnUzytkownicy = new JButton("Zarządzaj użytkownikami");
        JButton btnAktualizacje = new JButton("Aktualizacje systemu");
        JButton btnMonitoring = new JButton("Monitoring płatności");
        JButton btnBezpieczenstwo = new JButton("System awaryjny");

        styleButton(btnUzytkownicy, false, "FileChooser.homeFolderIcon");
        styleButton(btnAktualizacje, false, "FileChooser.upFolderIcon");
        styleButton(btnMonitoring, false, "FileView.fileIcon");
        styleButton(btnBezpieczenstwo, false, "OptionPane.warningIcon");

        btnUzytkownicy.addActionListener(e -> zarzadzajUzytkownikami());
        btnAktualizacje.addActionListener(e -> aktualizujSystem());
        btnMonitoring.addActionListener(e -> monitorujPlatnosci());
        btnBezpieczenstwo.addActionListener(e -> testujSystemAwaryjny());

        panel.add(btnUzytkownicy);
        panel.add(btnAktualizacje);
        panel.add(btnMonitoring);
        panel.add(btnBezpieczenstwo);
    }

    private void addMaintenanceButtons(JPanel panel) {
        JButton btnUsterki = new JButton("Zgłoszone usterki");
        JButton btnHarmonogram = new JButton("Harmonogram przeglądów");

        styleButton(btnUsterki, false, "OptionPane.errorIcon");
        styleButton(btnHarmonogram, false, "FileChooser.listViewIcon");

        btnUsterki.addActionListener(e -> pokazUsterki());
        btnHarmonogram.addActionListener(e -> pokazHarmonogram());

        panel.add(btnUsterki);
        panel.add(btnHarmonogram);
    }


    private void showUserProfile() {
        outputArea.setText("");
        outputArea.append("=== Profil użytkownika ===\n");
        outputArea.append("ID: " + currentUser.getId() + "\n");
        outputArea.append("Nazwa użytkownika: " + currentUser.getUsername() + "\n");
        outputArea.append("Email: " + currentUser.getEmail() + "\n");
        outputArea.append("Typ użytkownika: " + currentUser.getUserType() + "\n");
    }

    private void zarezerwuj() {
        // Simple implementation for now
        Rezerwacja r = system.createRezerwacja(currentUser.getId(), new Date());
        outputArea.append("Dodano rezerwację: ID = " + r.idRezerwacji + "\n");
    }

    private void pokazMojeRezerwacje() {
        outputArea.setText("");
        outputArea.append("=== Moje rezerwacje ===\n");
        List<Rezerwacja> mojeRezerwacje = system.getRezerwacjeUzytkownika(currentUser.getId());

        if (mojeRezerwacje.isEmpty()) {
            outputArea.append("Brak aktywnych rezerwacji\n");
            return;
        }

        for (Rezerwacja r : mojeRezerwacje) {
            outputArea.append("ID: " + r.idRezerwacji + ", Data: " + r.data + ", Status: " + r.status + "\n");
        }
    }

    private void anulujRezerwacje() {
        List<Rezerwacja> mojeRezerwacje = system.getRezerwacjeUzytkownika(currentUser.getId());

        if (mojeRezerwacje.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nie masz żadnych aktywnych rezerwacji.",
                    "Informacja",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create a list of reservation IDs for selection
        String[] options = new String[mojeRezerwacje.size()];
        for (int i = 0; i < mojeRezerwacje.size(); i++) {
            Rezerwacja r = mojeRezerwacje.get(i);
            options[i] = "ID: " + r.idRezerwacji + ", Data: " + r.data;
        }

        String selected = (String) JOptionPane.showInputDialog(this,
                "Wybierz rezerwację do anulowania:",
                "Anuluj rezerwację",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (selected != null) {
            // Extract ID from the selected string
            int id = Integer.parseInt(selected.substring(4, selected.indexOf(",")));
            system.anulujRezerwacje(id);
            outputArea.append("Anulowano rezerwację ID: " + id + "\n");
        }
    }
    private void pokazHistorie() {
        outputArea.setText("");
        outputArea.append("=== Historia i faktury ===\n");
        outputArea.append("\nOstatnie transakcje:\n");
        outputArea.append("- 2023-05-01: Rezerwacja #1234 - 50.00 PLN\n");
        outputArea.append("- 2023-04-15: Rezerwacja #1122 - 75.00 PLN\n");
        outputArea.append("- 2023-03-22: Rezerwacja #0987 - 120.00 PLN\n");
    }

    private void ocenUsluge() {
        String[] options = {"1", "2", "3", "4", "5"};
        String ocena = (String) JOptionPane.showInputDialog(this,
                "Jak oceniasz naszą usługę?",
                "Ocena usługi",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[4]);

        if (ocena != null) {
            outputArea.append("Dziękujemy za ocenę " + ocena + "/5!\n");
        }
    }

    private void uslugiDodatkowe() {
        outputArea.setText("");
        outputArea.append("=== Usługi dodatkowe ===\n\n");

        String[] uslugi = {
                "Czyszczenie samochodu - 50 PLN",
                "Detailing - 150 PLN",
                "Wymiana opon - 120 PLN",
                "Kontrola techniczna - 80 PLN",
                "Tankowanie - wg cennika stacji"
        };

        for (String usluga : uslugi) {
            outputArea.append("• " + usluga + "\n");
        }

        outputArea.append("\nAby zamówić usługę, skontaktuj się z obsługą parkingu.\n");
    }

    // Stub implementations for owner features
    private void generujRaporty() {
        outputArea.setText("");
        outputArea.append("=== Raport zajętości parkingu ===\n\n");

        int totalSpaces = 100;
        int occupiedSpaces = new Random().nextInt(80);
        int availableSpaces = totalSpaces - occupiedSpaces;
        double occupancyRate = (double) occupiedSpaces / totalSpaces * 100;

        outputArea.append("Całkowita liczba miejsc: " + totalSpaces + "\n");
        outputArea.append("Zajęte miejsca: " + occupiedSpaces + "\n");
        outputArea.append("Dostępne miejsca: " + availableSpaces + "\n");
        outputArea.append(String.format("Stopień zajętości: %.1f%%\n\n", occupancyRate));

        outputArea.append("Szczegółowe statystyki:\n");
        outputArea.append("- Miejsca standardowe: " + (occupiedSpaces * 7 / 10) + "/" + (totalSpaces * 7 / 10) + "\n");
        outputArea.append("- Miejsca dla niepełnosprawnych: " + (occupiedSpaces / 10) + "/" + (totalSpaces / 10) + "\n");
        outputArea.append("- Miejsca premium: " + (occupiedSpaces * 2 / 10) + "/" + (totalSpaces * 2 / 10) + "\n");
    }

    private void optymalizujCeny() {
        outputArea.setText("");
        outputArea.append("=== Optymalizacja cen ===\n\n");

        outputArea.append("Aktualne ceny:\n");
        outputArea.append("- Pierwsza godzina: 5.00 PLN\n");
        outputArea.append("- Każda kolejna godzina: 3.00 PLN\n");
        outputArea.append("- Całodniowy bilet: 25.00 PLN\n\n");

        outputArea.append("Sugerowane ceny (na podstawie analizy obłożenia):\n");
        outputArea.append("- Pierwsza godzina: 6.00 PLN\n");
        outputArea.append("- Każda kolejna godzina: 3.50 PLN\n");
        outputArea.append("- Całodniowy bilet: 28.00 PLN\n\n");

        outputArea.append("Prognozowany wzrost przychodu: 12%\n");
    }

    private void monitorujMiejsca() {
        outputArea.setText("");
        outputArea.append("=== Monitorowanie miejsc parkingowych ===\n\n");

        outputArea.append("Status miejsc parkingowych:\n\n");

        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 1; i <= 10; i++) {
            sb.append("Rząd ").append(i).append(": ");
            for (int j = 1; j <= 10; j++) {
                int status = random.nextInt(3);
                if (status == 0) {
                    sb.append("□ "); // wolne
                } else if (status == 1) {
                    sb.append("■ "); // zajęte
                } else {
                    sb.append("▢ "); // zarezerwowane
                }
            }
            sb.append("\n");
        }

        outputArea.append(sb.toString());
        outputArea.append("\nLegenda: □ - wolne, ■ - zajęte, ▢ - zarezerwowane\n");
    }

    private void pokazOceny() {
        outputArea.setText("");
        outputArea.append("=== Oceny klientów ===\n\n");

        outputArea.append("Średnia ocena: 4.5/5 ★★★★☆\n\n");

        outputArea.append("Ostatnie opinie:\n");
        outputArea.append("- Jan K. (5/5): \"Świetna obsługa i czyste miejsce parkingowe.\"\n");
        outputArea.append("- Anna M. (4/5): \"Dobra lokalizacja, czasem brakuje miejsc w godzinach szczytu.\"\n");
        outputArea.append("- Piotr W. (5/5): \"Profesjonalna obsługa i konkurencyjne ceny.\"\n");
        outputArea.append("- Marta S. (4/5): \"Bardzo dobry parking, polecam!\"\n");
    }

    // Stub implementations for security features
    private void pokazMonitoring() {
        outputArea.setText("");
        outputArea.append("=== Monitoring na żywo ===\n\n");

        outputArea.append("Status kamer:\n");
        outputArea.append("- Kamera 1 (Wjazd): Online\n");
        outputArea.append("- Kamera 2 (Wyjazd): Online\n");
        outputArea.append("- Kamera 3 (Poziom -1): Online\n");
        outputArea.append("- Kamera 4 (Poziom -2): Online\n");
        outputArea.append("- Kamera 5 (Schody): Offline - wymaga serwisu\n\n");

        outputArea.append("Podgląd z kamer dostępny w aplikacji monitoringu.\n");
    }

    private void pokazPowiadomienia() {
        outputArea.setText("");
        outputArea.append("=== Powiadomienia ===\n\n");

        outputArea.append("Nowe powiadomienia:\n");
        outputArea.append("- [10:15] Alarm przeciwpożarowy - test systemu\n");
        outputArea.append("- [09:30] Wjazd pojazdu bez identyfikacji\n");
        outputArea.append("- [08:45] Rozpoczęcie zmiany - Jan Kowalski\n\n");

        outputArea.append("Wczorajsze powiadomienia:\n");
        outputArea.append("- [22:30] Zakończenie zmiany - Anna Nowak\n");
        outputArea.append("- [18:15] Awaria oświetlenia na poziomie -1\n");
    }

    private void pokazHistorieNagran() {
        outputArea.setText("");
        outputArea.append("=== Historia nagrań ===\n\n");

        outputArea.append("Dostępne nagrania:\n");
        outputArea.append("- 2023-05-01 (Wszystkie kamery)\n");
        outputArea.append("- 2023-04-30 (Wszystkie kamery)\n");
        outputArea.append("- 2023-04-29 (Wszystkie kamery)\n");
        outputArea.append("- 2023-04-28 (Wszystkie kamery)\n");
        outputArea.append("- 2023-04-27 (Wszystkie kamery)\n\n");

        outputArea.append("Wybierz datę i kamerę w aplikacji monitoringu, aby odtworzyć nagranie.\n");
    }

    // Stub implementations for admin features
    private void zarzadzajUzytkownikami() {
        outputArea.setText("");
        outputArea.append("=== Zarządzanie użytkownikami ===\n\n");

        // Wyświetl listę użytkowników
        refreshUserList();

        // Panel z przyciskami do zarządzania
        JPanel adminPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        adminPanel.setBackground(Color.WHITE);
        adminPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addButton = new JButton("Dodaj użytkownika");
        JButton editButton = new JButton("Edytuj użytkownika");
        JButton deleteButton = new JButton("Usuń użytkownika");

        styleButton(addButton, false, "FileChooser.newFolderIcon");
        styleButton(editButton, false, "FileView.fileIcon");
        styleButton(deleteButton, false, "FileChooser.upFolderIcon");

        addButton.addActionListener(e -> dodajUzytkownika());
        editButton.addActionListener(e -> edytujUzytkownika());
        deleteButton.addActionListener(e -> usunUzytkownika());

        adminPanel.add(addButton);
        adminPanel.add(editButton);
        adminPanel.add(deleteButton);

        // Jeśli nie możesz dodać panelu do interfejsu, możesz utworzyć nowe okno dialogowe
        JDialog adminDialog = new JDialog(this, "Panel administracyjny", false);
        adminDialog.setLayout(new BorderLayout());
        adminDialog.add(adminPanel, BorderLayout.CENTER);
        adminDialog.pack();
        adminDialog.setLocationRelativeTo(this);
        adminDialog.setVisible(true);
    }

    private void refreshUserList() {
        outputArea.setText("");
        outputArea.append("=== Zarządzanie użytkownikami ===\n\n");
        outputArea.append("Lista użytkowników:\n");

        for (User user : system.getUserManager().getUsers()) {
            outputArea.append("ID: " + user.getId() + ", Login: " + user.getUsername() +
                    ", Email: " + user.getEmail() + ", Typ: " + user.getUserType() + "\n");
        }
    }

    private void dodajUzytkownika() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField emailField = new JTextField();
        String[] userTypes = {"klient", "właściciel", "ochrona", "administrator", "konserwator"};
        JComboBox<String> userTypeCombo = new JComboBox<>(userTypes);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nazwa użytkownika:"));
        panel.add(usernameField);
        panel.add(new JLabel("Hasło:"));
        panel.add(passwordField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Typ użytkownika:"));
        panel.add(userTypeCombo);

        int result = JOptionPane.showConfirmDialog(this, panel, "Dodaj użytkownika",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String email = emailField.getText();
            String userType = (String) userTypeCombo.getSelectedItem();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Wszystkie pola są wymagane", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = system.getUserManager().registerUser(username, password, email, userType);

            if (success) {
                JOptionPane.showMessageDialog(this, "Użytkownik został dodany pomyślnie", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                refreshUserList();
            } else {
                JOptionPane.showMessageDialog(this, "Nie można dodać użytkownika. Nazwa użytkownika może być już zajęta.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void edytujUzytkownika() {
        // Najpierw wybierz użytkownika do edycji
        List<User> users = system.getUserManager().getUsers();
        if (users.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Brak użytkowników do edycji", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] userNames = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            userNames[i] = user.getId() + ": " + user.getUsername() + " (" + user.getUserType() + ")";
        }

        String selectedUserName = (String) JOptionPane.showInputDialog(this,
                "Wybierz użytkownika do edycji:", "Edytuj użytkownika",
                JOptionPane.QUESTION_MESSAGE, null, userNames, userNames[0]);

        if (selectedUserName == null) return;

        // Znajdź wybranego użytkownika
        int selectedId = Integer.parseInt(selectedUserName.split(":")[0].trim());
        User selectedUser = null;
        for (User user : users) {
            if (user.getId() == selectedId) {
                selectedUser = user;
                break;
            }
        }

        if (selectedUser == null) return;

        // Formularz edycji
        JTextField emailField = new JTextField(selectedUser.getEmail());
        JPasswordField passwordField = new JPasswordField();
        String[] userTypes = {"klient", "właściciel", "ochrona", "administrator", "konserwator"};
        JComboBox<String> userTypeCombo = new JComboBox<>(userTypes);
        userTypeCombo.setSelectedItem(selectedUser.getUserType());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Nowe hasło (pozostaw puste, aby nie zmieniać):"));
        panel.add(passwordField);
        panel.add(new JLabel("Typ użytkownika:"));
        panel.add(userTypeCombo);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edytuj użytkownika: " + selectedUser.getUsername(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String newEmail = emailField.getText();
            String newPassword = new String(passwordField.getPassword());
            String newUserType = (String) userTypeCombo.getSelectedItem();

            if (newEmail.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Email jest wymagany", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Aktualizuj dane użytkownika
            selectedUser.setEmail(newEmail);
            selectedUser.setUserType(newUserType);
            if (!newPassword.isEmpty()) {
                selectedUser.setPassword(newPassword);
            }

            // Zapisz zmiany do pliku
            system.getUserManager().saveUsersToFile();

            JOptionPane.showMessageDialog(this, "Użytkownik został zaktualizowany pomyślnie", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            refreshUserList();
        }
    }

    private void usunUzytkownika() {
        // Najpierw wybierz użytkownika do usunięcia
        List<User> users = system.getUserManager().getUsers();
        if (users.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Brak użytkowników do usunięcia", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] userNames = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            userNames[i] = user.getId() + ": " + user.getUsername() + " (" + user.getUserType() + ")";
        }

        String selectedUserName = (String) JOptionPane.showInputDialog(this,
                "Wybierz użytkownika do usunięcia:", "Usuń użytkownika",
                JOptionPane.QUESTION_MESSAGE, null, userNames, userNames[0]);

        if (selectedUserName == null) return;

        // Znajdź wybranego użytkownika
        int selectedId = Integer.parseInt(selectedUserName.split(":")[0].trim());
        User selectedUser = null;
        for (User user : users) {
            if (user.getId() == selectedId) {
                selectedUser = user;
                break;
            }
        }

        if (selectedUser == null) return;

        // Potwierdź usunięcie
        int confirm = JOptionPane.showConfirmDialog(this,
                "Czy na pewno chcesz usunąć użytkownika " + selectedUser.getUsername() + "?",
                "Potwierdź usunięcie", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // Usuń użytkownika
            boolean success = system.getUserManager().removeUser(selectedUser.getUsername());

            if (success) {
                JOptionPane.showMessageDialog(this, "Użytkownik został usunięty pomyślnie", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                refreshUserList();
            } else {
                JOptionPane.showMessageDialog(this, "Nie można usunąć użytkownika", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void aktualizujSystem() {
        outputArea.setText("");
        outputArea.append("=== Aktualizacja systemu ===\n\n");

        outputArea.append("Aktualna wersja: 2.3.1\n");
        outputArea.append("Dostępna aktualizacja: 2.4.0\n\n");

        outputArea.append("Zmiany w nowej wersji:\n");
        outputArea.append("- Ulepszony interfejs użytkownika\n");
        outputArea.append("- Optymalizacja wydajności\n");
        outputArea.append("- Nowe funkcje raportowania\n");
        outputArea.append("- Poprawki bezpieczeństwa\n\n");

        outputArea.append("Aby zaktualizować system, kliknij przycisk 'Aktualizuj' w panelu administracyjnym.\n");
    }

    private void monitorujPlatnosci() {
        outputArea.setText("");
        outputArea.append("=== Monitoring płatności ===\n\n");

        outputArea.append("Statystyki płatności (bieżący miesiąc):\n");
        outputArea.append("- Łączna kwota: 12,450.00 PLN\n");
        outputArea.append("- Liczba transakcji: 487\n");
        outputArea.append("- Średnia wartość transakcji: 25.56 PLN\n\n");

        outputArea.append("Metody płatności:\n");
        outputArea.append("- Gotówka: 35%\n");
        outputArea.append("- Karta płatnicza: 55%\n");
        outputArea.append("- Aplikacja mobilna: 10%\n");
    }

    private void testujSystemAwaryjny() {
        outputArea.setText("");
        outputArea.append("=== Test systemu awaryjnego ===\n\n");
        outputArea.append("Test rozpoczęty...\n");

        // Użyj SwingWorker do wykonania zadania w tle
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Tablica komunikatów do wyświetlenia
                String[] komunikaty = {
                        "Sprawdzanie zasilania awaryjnego: OK",
                        "Sprawdzanie systemu przeciwpożarowego: OK",
                        "Sprawdzanie systemu wentylacji: OK",
                        "Sprawdzanie systemu oświetlenia awaryjnego: OK",
                        "Sprawdzanie systemu komunikacji: OK"
                };

                for (String komunikat : komunikaty) {
                    // Symulacja czasu przetwarzania
                    Thread.sleep(1000); // 1 sekunda opóźnienia

                    // Aktualizuj interfejs użytkownika w wątku EDT
                    SwingUtilities.invokeLater(() -> {
                        outputArea.append(komunikat + "\n");
                        // Przewiń do końca, aby pokazać najnowsze wyniki
                        outputArea.setCaretPosition(outputArea.getDocument().getLength());
                    });
                }

                // Dodatkowe opóźnienie przed podsumowaniem
                Thread.sleep(1000);

                // Wyświetl podsumowanie
                SwingUtilities.invokeLater(() -> {
                    outputArea.append("\nWszystkie systemy działają prawidłowo.\n");
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());
                });

                return null;
            }
        }.execute();
    }

    private void pokazUsterki() {
        outputArea.setText("");
        outputArea.append("=== Zgłoszone usterki ===\n\n");

        outputArea.append("Aktywne zgłoszenia:\n");
        outputArea.append("1. ID: #2345 - Uszkodzona bariera na wjeździe (Priorytet: Wysoki)\n");
        outputArea.append("2. ID: #2346 - Awaria oświetlenia na poziomie -2 (Priorytet: Średni)\n");
        outputArea.append("3. ID: #2347 - Niedziałający terminal płatniczy #3 (Priorytet: Wysoki)\n\n");

        outputArea.append("Ostatnio rozwiązane zgłoszenia:\n");
        outputArea.append("- ID: #2344 - Wyciek wody na poziomie -1 (Rozwiązano: 2023-05-01)\n");
        outputArea.append("- ID: #2343 - Uszkodzony znak parkingowy (Rozwiązano: 2023-04-30)\n");
    }

    private void pokazHarmonogram() {
        outputArea.setText("");
        outputArea.append("=== Harmonogram przeglądów ===\n\n");

        outputArea.append("Zaplanowane przeglądy:\n");
        outputArea.append("- 2023-05-10: Przegląd systemu przeciwpożarowego\n");
        outputArea.append("- 2023-05-15: Konserwacja wind\n");
        outputArea.append("- 2023-05-20: Przegląd instalacji elektrycznej\n");
        outputArea.append("- 2023-05-25: Konserwacja systemu wentylacji\n");
        outputArea.append("- 2023-06-01: Przegląd barier i szlabanów\n\n");

        outputArea.append("Ostatnie przeglądy:\n");
        outputArea.append("- 2023-04-15: Przegląd systemu monitoringu (Status: Zakończony)\n");
        outputArea.append("- 2023-04-01: Konserwacja terminali płatniczych (Status: Zakończony)\n");
    }

    private void logout() {
        system.getUserManager().logout(currentUser);
        new LoginGUI(system);
        dispose(); // Close the current window
    }
}
