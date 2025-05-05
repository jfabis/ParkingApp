package GUI;
import model.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ParkingGUI extends JFrame {
    private SystemRezerwacji system;
    private User currentUser;
    private JTextArea outputArea;

    public ParkingGUI(SystemRezerwacji system, User user) {
        this.system = system;
        this.currentUser = user;

        setTitle("System Zarządzania Parkingiem - " + user.getUsername() + " (" + user.getUserType() + ")");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the GUI components based on user type
        createGUI();

        setVisible(true);
    }

    private void createGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Create buttons panel
        JPanel buttonPanel = new JPanel();
        // Use a grid layout for better organization
        buttonPanel.setLayout(new GridLayout(0, 3, 5, 5));

        // Add buttons based on user type
        addUserSpecificButtons(buttonPanel);

        // Add logout button
        JButton logoutButton = new JButton("Wyloguj");
        logoutButton.addActionListener(e -> logout());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(logoutButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Show welcome message
        outputArea.append("Witaj, " + currentUser.getUsername() + "!\n");
        outputArea.append("Typ konta: " + currentUser.getUserType() + "\n\n");
    }

    private void addUserSpecificButtons(JPanel panel) {
        // Common buttons for all users
        JButton btnProfile = new JButton("Mój profil");
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

    private void addOwnerButtons(JPanel panel) {
        JButton btnRaporty = new JButton("Raporty zajętości");
        JButton btnOptCeny = new JButton("Optymalizacja cen");
        JButton btnMonitorowanieMiejsc = new JButton("Monitorowanie miejsc");
        JButton btnOceny = new JButton("Oceny klientów");

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

        btnUsterki.addActionListener(e -> pokazUsterki());
        btnHarmonogram.addActionListener(e -> pokazHarmonogram());

        panel.add(btnUsterki);
        panel.add(btnHarmonogram);
    }

    // Method implementations - we'll create stubs for now

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
            JOptionPane.showMessageDialog(this, "Nie masz żadnych aktywnych rezerwacji.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
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

    // Stub implementations for client features
    private void pokazHistorie() {
        outputArea.setText("");
        outputArea.append("=== Historia i faktury ===\n");
        outputArea.append("Funkcja w trakcie implementacji\n");
    }

    private void ocenUsluge() {
        JOptionPane.showInputDialog(this, "Oceń usługę (1-5):", "Ocena usługi", JOptionPane.QUESTION_MESSAGE);
        outputArea.append("Dziękujemy za ocenę!\n");
    }

    private void uslugiDodatkowe() {
        outputArea.setText("");
        outputArea.append("=== Usługi dodatkowe ===\n");
        outputArea.append("1. Czyszczenie samochodu - 50 PLN\n");
        outputArea.append("2. Detailing - 150 PLN\n");
        outputArea.append("Funkcja w trakcie implementacji\n");
    }

    // Stub implementations for owner features
    private void generujRaporty() {
        outputArea.setText("");
        outputArea.append("=== Raport zajętości parkingu ===\n");
        outputArea.append("Całkowita liczba miejsc: 100\n");
        outputArea.append("Zajęte miejsca: " + new Random().nextInt(80) + "\n");
        outputArea.append("Funkcja w trakcie implementacji\n");
    }

    private void optymalizujCeny() {
        outputArea.setText("");
        outputArea.append("=== Optymalizacja cen ===\n");
        outputArea.append("Funkcja w trakcie implementacji\n");
    }

    private void monitorujMiejsca() {
        outputArea.setText("");
        outputArea.append("=== Monitorowanie miejsc parkingowych ===\n");
        outputArea.append("Funkcja w trakcie implementacji\n");
    }

    private void pokazOceny() {
        outputArea.setText("");
        outputArea.append("=== Oceny klientów ===\n");
        outputArea.append("Średnia ocena: 4.5/5\n");
        outputArea.append("Funkcja w trakcie implementacji\n");
    }

    // Stub implementations for security features
    private void pokazMonitoring() {
        outputArea.setText("");
        outputArea.append("=== Monitoring na żywo ===\n");
        outputArea.append("Funkcja w trakcie implementacji\n");
    }

    private void pokazPowiadomienia() {
        outputArea.setText("");
        outputArea.append("=== Powiadomienia ===\n");
        outputArea.append("Brak nowych powiadomień\n");
    }

    private void pokazHistorieNagran() {
        outputArea.setText("");
        outputArea.append("=== Historia nagrań ===\n");
        outputArea.append("Funkcja w trakcie implementacji\n");
    }

    // Stub implementations for admin features
    private void zarzadzajUzytkownikami() {
        outputArea.setText("");
        outputArea.append("=== Zarządzanie użytkownikami ===\n");
        outputArea.append("Lista użytkowników:\n");

        for (User user : system.getUserManager().getUsers()) {
            outputArea.append("ID: " + user.getId() + ", Login: " + user.getUsername() +
                    ", Email: " + user.getEmail() + ", Typ: " + user.getUserType() + "\n");
        }
    }

    private void aktualizujSystem() {
        outputArea.setText("");
        outputArea.append("=== Aktualizacja systemu ===\n");
        outputArea.append("Funkcja w trakcie implementacji\n");
    }

    private void monitorujPlatnosci() {
        outputArea.setText("");
        outputArea.append("=== Monitoring płatności ===\n");
        outputArea.append("Funkcja w trakcie implementacji\n");
    }

    private void testujSystemAwaryjny() {
        outputArea.setText("");
        outputArea.append("=== Test systemu awaryjnego ===\n");
        outputArea.append("Test rozpoczęty...\n");
        outputArea.append("Funkcja w trakcie implementacji\n");
    }

    // Stub implementations for maintenance features
    private void pokazUsterki() {
        outputArea.setText("");
        outputArea.append("=== Zgłoszone usterki ===\n");
        outputArea.append("Brak zgłoszonych usterek\n");
    }

    private void pokazHarmonogram() {
        outputArea.setText("");
        outputArea.append("=== Harmonogram przeglądów ===\n");
        outputArea.append("Funkcja w trakcie implementacji\n");
    }

    private void logout() {
        system.getUserManager().logout(currentUser);
        new LoginGUI(system);
        dispose(); // Close the current window
    }
}