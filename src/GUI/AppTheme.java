package GUI;

import java.awt.*;
import javax.swing.*;


public class AppTheme {
    // Główne kolory aplikacji
    public static final Color PRIMARY = new Color(25, 118, 210);    // Niebieski
    public static final Color SECONDARY = new Color(66, 165, 245);  // Jaśniejszy niebieski
    public static final Color BACKGROUND = new Color(245, 245, 245); // Jasny szary
    public static final Color TEXT = new Color(33, 33, 33);         // Ciemny szary
    public static final Color SUCCESS = new Color(76, 175, 80);     // Zielony
    public static final Color ERROR = new Color(211, 47, 47);       // Czerwony

    // Czcionki
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    // Marginesy
    public static final int MARGIN = 15;

    // Metoda do stylizacji przycisków
    public static void styleButton(JButton button, boolean isPrimary) {
        button.setFont(NORMAL_FONT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (isPrimary) {
            button.setBackground(PRIMARY);
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(PRIMARY);
        }
    }

    // Metoda do stylizacji pól tekstowych
    public static void styleTextField(JTextField textField) {
        textField.setFont(NORMAL_FONT);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    // Metoda do ustawienia wyglądu systemowego
    public static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
