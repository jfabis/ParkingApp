package model;
import java.util.*;

public class SystemRezerwacji {
    public List<Rezerwacja> listaRezerwacji = new ArrayList<>();
    private UserManager userManager = new UserManager();

    public SystemRezerwacji() {
        // Initialize with default users if needed
    }

    public void anulujRezerwacje(int idRezerwacji) {
        for (Rezerwacja r : listaRezerwacji) {
            if (r.idRezerwacji == idRezerwacji) {
                r.status = "anulowana";
                r.anulowana = true;
            }
        }
    }

    public UserManager getUserManager() {
        return userManager;
    }

    // Add methods to handle user-specific reservations
    public List<Rezerwacja> getRezerwacjeUzytkownika(int userId) {
        List<Rezerwacja> userRezerwacje = new ArrayList<>();
        for (Rezerwacja r : listaRezerwacji) {
            if (r.userId == userId) {
                userRezerwacje.add(r);
            }
        }
        return userRezerwacje;
    }

    public Rezerwacja createRezerwacja(int userId, Date date) {
        Rezerwacja r = new Rezerwacja();
        r.idRezerwacji = new Random().nextInt(10000);
        r.userId = userId;
        r.data = date;
        r.status = "aktywna";
        listaRezerwacji.add(r);
        return r;
    }
}