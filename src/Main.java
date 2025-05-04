
import GUI.LoginGUI;
import model.SystemRezerwacji;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            SystemRezerwacji system = new SystemRezerwacji();
            new LoginGUI(system);
        });
    }
}