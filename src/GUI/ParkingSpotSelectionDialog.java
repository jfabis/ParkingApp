package GUI;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ParkingSpotSelectionDialog extends JDialog {
    private static final int TOTAL_SPOTS = 40; // 4 sekcje po 10 miejsc

    private SystemRezerwacji system;
    private int selectedSpot = -1;
    private boolean confirmed = false;
    private JButton[] spotButtons;

    public ParkingSpotSelectionDialog(JFrame parent, SystemRezerwacji system) {
        super(parent, "Wybierz miejsce parkingowe", true);
        this.system = system;

        initializeDialog();
        createParkingLayout();
        updateSpotStatus();
    }

    private void initializeDialog() {
        setSize(800, 500);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel nagłówka
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 118, 210));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Wybierz wolne miejsce parkingowe");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void createParkingLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));

        // Panel z layoutem parkingu - używamy prostego GridLayout
        JPanel parkingPanel = new JPanel();
        parkingPanel.setLayout(null); // Absolute positioning dla lepszej kontroli
        parkingPanel.setBackground(new Color(150, 150, 150)); // Kolor asfaltu
        parkingPanel.setBorder(BorderFactory.createTitledBorder("Layout parkingu - widok z lotu ptaka"));
        parkingPanel.setPreferredSize(new Dimension(700, 300));

        spotButtons = new JButton[TOTAL_SPOTS];
        int spotIndex = 0;

        // Mały wjazd na górze
        JLabel entrance = new JLabel("WJAZD", JLabel.CENTER);
        entrance.setBounds(300, 10, 100, 20);
        entrance.setOpaque(true);
        entrance.setBackground(new Color(80, 80, 80));
        entrance.setForeground(Color.WHITE);
        entrance.setFont(new Font("Segoe UI", Font.BOLD, 10));
        entrance.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        parkingPanel.add(entrance);

        // Główna droga pionowa
        JPanel mainRoad = new JPanel();
        mainRoad.setBounds(325, 30, 50, 220);
        mainRoad.setBackground(new Color(120, 120, 120));
        mainRoad.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));
        JLabel roadText = new JLabel("<html><center>D<br>R<br>O<br>G<br>A</center></html>");
        roadText.setForeground(Color.WHITE);
        roadText.setFont(new Font("Segoe UI", Font.BOLD, 8));
        roadText.setHorizontalAlignment(JLabel.CENTER);
        mainRoad.add(roadText);
        parkingPanel.add(mainRoad);

        // Lewa górna sekcja (miejsca 1-10)
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 5; col++) {
                spotButtons[spotIndex] = createParkingSpot(spotIndex + 1);
                int x = 50 + col * 50;
                int y = 50 + row * 30;
                spotButtons[spotIndex].setBounds(x, y, 45, 25);
                parkingPanel.add(spotButtons[spotIndex]);
                spotIndex++;
            }
        }

        // Prawa górna sekcja (miejsca 11-20)
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 5; col++) {
                spotButtons[spotIndex] = createParkingSpot(spotIndex + 1);
                int x = 400 + col * 50;
                int y = 50 + row * 30;
                spotButtons[spotIndex].setBounds(x, y, 45, 25);
                parkingPanel.add(spotButtons[spotIndex]);
                spotIndex++;
            }
        }

        // Pozioma droga środkowa
        JPanel horizontalRoad = new JPanel();
        horizontalRoad.setBounds(50, 130, 600, 20);
        horizontalRoad.setBackground(new Color(120, 120, 120));
        horizontalRoad.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));
        parkingPanel.add(horizontalRoad);

        // Lewa dolna sekcja (miejsca 21-30)
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 5; col++) {
                spotButtons[spotIndex] = createParkingSpot(spotIndex + 1);
                int x = 50 + col * 50;
                int y = 170 + row * 30;
                spotButtons[spotIndex].setBounds(x, y, 45, 25);
                parkingPanel.add(spotButtons[spotIndex]);
                spotIndex++;
            }
        }

        // Prawa dolna sekcja (miejsca 31-40)
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 5; col++) {
                spotButtons[spotIndex] = createParkingSpot(spotIndex + 1);
                int x = 400 + col * 50;
                int y = 170 + row * 30;
                spotButtons[spotIndex].setBounds(x, y, 45, 25);
                parkingPanel.add(spotButtons[spotIndex]);
                spotIndex++;
            }
        }

        // Mały wyjazd na dole
        JLabel exit = new JLabel("WYJAZD", JLabel.CENTER);
        exit.setBounds(300, 260, 100, 20);
        exit.setOpaque(true);
        exit.setBackground(new Color(80, 80, 80));
        exit.setForeground(Color.WHITE);
        exit.setFont(new Font("Segoe UI", Font.BOLD, 10));
        exit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        parkingPanel.add(exit);

        // Panel legendy
        JPanel legendPanel = createLegendPanel();

        // Panel przycisków
        JPanel buttonPanel = createButtonPanel();

        mainPanel.add(parkingPanel, BorderLayout.CENTER);
        mainPanel.add(legendPanel, BorderLayout.EAST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createParkingSpot(int spotId) {
        JButton spotButton = new JButton(String.valueOf(spotId));
        spotButton.setFont(new Font("Segoe UI", Font.BOLD, 9));
        spotButton.setFocusPainted(false);
        spotButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        final int finalSpotId = spotId;
        spotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectSpot(finalSpotId);
            }
        });

        return spotButton;
    }

    private JPanel createLegendPanel() {
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.setBorder(BorderFactory.createTitledBorder("Legenda"));
        legendPanel.setBackground(Color.WHITE);
        legendPanel.setPreferredSize(new Dimension(120, 200));

        // Wolne miejsce
        JPanel freePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        freePanel.setBackground(Color.WHITE);
        JButton freeButton = new JButton();
        freeButton.setBackground(new Color(76, 175, 80));
        freeButton.setPreferredSize(new Dimension(15, 15));
        freeButton.setEnabled(false);
        freePanel.add(freeButton);
        JLabel freeLabel = new JLabel("Wolne miejsce");
        freeLabel.setForeground(Color.BLACK);
        freeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        freePanel.add(freeLabel);

        // Zajęte miejsce
        JPanel occupiedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        occupiedPanel.setBackground(Color.WHITE);
        JButton occupiedButton = new JButton();
        occupiedButton.setBackground(new Color(211, 47, 47));
        occupiedButton.setPreferredSize(new Dimension(15, 15));
        occupiedButton.setEnabled(false);
        occupiedPanel.add(occupiedButton);
        JLabel occupiedLabel = new JLabel("Zajęte miejsce");
        occupiedLabel.setForeground(Color.BLACK);
        occupiedLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        occupiedPanel.add(occupiedLabel);

        // Wybrane miejsce
        JPanel selectedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectedPanel.setBackground(Color.WHITE);
        JButton selectedButton = new JButton();
        selectedButton.setBackground(new Color(255, 193, 7));
        selectedButton.setPreferredSize(new Dimension(15, 15));
        selectedButton.setEnabled(false);
        selectedPanel.add(selectedButton);
        JLabel selectedLabel = new JLabel("Wybrane miejsce");
        selectedLabel.setForeground(Color.BLACK);
        selectedLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        selectedPanel.add(selectedLabel);

        // Droga
        JPanel roadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        roadPanel.setBackground(Color.WHITE);
        JButton roadButton = new JButton();
        roadButton.setBackground(new Color(120, 120, 120));
        roadButton.setPreferredSize(new Dimension(15, 15));
        roadButton.setEnabled(false);
        roadPanel.add(roadButton);
        JLabel roadLabel = new JLabel("Droga");
        roadLabel.setForeground(Color.BLACK);
        roadLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        roadPanel.add(roadLabel);

        legendPanel.add(freePanel);
        legendPanel.add(occupiedPanel);
        legendPanel.add(selectedPanel);
        legendPanel.add(roadPanel);

        return legendPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton confirmButton = new JButton("Potwierdź wybór");
        confirmButton.setBackground(new Color(25, 118, 210));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        confirmButton.addActionListener(e -> confirmSelection());

        JButton cancelButton = new JButton("Anuluj");
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(new Color(25, 118, 210));
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createLineBorder(new Color(25, 118, 210), 1));
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private void updateSpotStatus() {
        List<Rezerwacja> rezerwacje = system.getAllRezerwacje();
        boolean[] occupiedSpots = new boolean[TOTAL_SPOTS + 1];

        for (Rezerwacja rezerwacja : rezerwacje) {
            if (rezerwacja.status.equals("aktywna") && rezerwacja.getMiejsceParkingowe() <= TOTAL_SPOTS) {
                occupiedSpots[rezerwacja.getMiejsceParkingowe()] = true;
            }
        }

        for (int i = 0; i < TOTAL_SPOTS; i++) {
            if (spotButtons[i] != null) {
                int spotId = i + 1;
                JButton button = spotButtons[i];

                if (occupiedSpots[spotId]) {
                    button.setBackground(new Color(211, 47, 47));
                    button.setForeground(Color.WHITE);
                    button.setEnabled(false);
                } else {
                    button.setBackground(new Color(76, 175, 80));
                    button.setForeground(Color.WHITE);
                    button.setEnabled(true);
                }
            }
        }
    }

    private void selectSpot(int spotId) {
        for (int i = 0; i < TOTAL_SPOTS; i++) {
            if (spotButtons[i] != null) {
                JButton button = spotButtons[i];
                if (button.isEnabled() && button.getBackground().equals(new Color(255, 193, 7))) {
                    button.setBackground(new Color(76, 175, 80));
                    button.setForeground(Color.WHITE);
                }
            }
        }

        JButton selectedButton = spotButtons[spotId - 1];
        selectedButton.setBackground(new Color(255, 193, 7));
        selectedButton.setForeground(Color.BLACK);

        selectedSpot = spotId;
    }

    private void confirmSelection() {
        if (selectedSpot != -1) {
            confirmed = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Proszę wybrać miejsce parkingowe",
                    "Brak wyboru",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public int getSelectedSpot() {
        return selectedSpot;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
