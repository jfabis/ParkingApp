package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SystemRezerwacji implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Rezerwacja> rezerwacje;
    private List<Rezerwacja> historiaRezerwacji; // Historia wszystkich rezerwacji (włącznie z anulowanymi)
    private UserManager userManager;

    public SystemRezerwacji() {
        this.rezerwacje = new ArrayList<>();
        this.historiaRezerwacji = new ArrayList<>();
        this.userManager = new UserManager();
    }

    public Rezerwacja createRezerwacja(int idUzytkownika, Date data) {
        int noweId = historiaRezerwacji.size() + 1; // Używamy historii do generowania ID
        Rezerwacja nowaRezerwacja = new Rezerwacja(noweId, idUzytkownika, data);
        rezerwacje.add(nowaRezerwacja);
        historiaRezerwacji.add(nowaRezerwacja); // Dodaj do historii
        return nowaRezerwacja;
    }

    public Rezerwacja createRezerwacja(int idUzytkownika, Date data, int miejsceParkingowe) {
        for (Rezerwacja r : rezerwacje) {
            if (r.getMiejsceParkingowe() == miejsceParkingowe && r.status.equals("aktywna")) {
                return null;
            }
        }

        int noweId = historiaRezerwacji.size() + 1; // Używamy historii do generowania ID
        Rezerwacja nowaRezerwacja = new Rezerwacja(noweId, idUzytkownika, data, miejsceParkingowe);
        rezerwacje.add(nowaRezerwacja);
        historiaRezerwacji.add(nowaRezerwacja); // Dodaj do historii
        return nowaRezerwacja;
    }

    public void anulujRezerwacje(int idRezerwacji) {
        for (Rezerwacja r : rezerwacje) {
            if (r.idRezerwacji == idRezerwacji) {
                r.status = "anulowana";
                rezerwacje.remove(r); // Usuń z aktywnych rezerwacji

                // Usuń z historii
                historiaRezerwacji.removeIf(hr -> hr.idRezerwacji == idRezerwacji);
                break;
            }
        }
    }

    public List<Rezerwacja> getRezerwacjeUzytkownika(int idUzytkownika) {
        List<Rezerwacja> mojeRezerwacje = new ArrayList<>();
        for (Rezerwacja r : rezerwacje) {
            if (r.idUzytkownika == idUzytkownika && r.status.equals("aktywna")) {
                mojeRezerwacje.add(r);
            }
        }
        return mojeRezerwacje;
    }

    // Nowa metoda do pobierania historii płatności użytkownika
    public List<Rezerwacja> getHistoriaRezerwacjiUzytkownika(int idUzytkownika) {
        List<Rezerwacja> historiaUzytkownika = new ArrayList<>();
        for (Rezerwacja r : historiaRezerwacji) {
            if (r.idUzytkownika == idUzytkownika) {
                historiaUzytkownika.add(r);
            }
        }
        return historiaUzytkownika;
    }

    public List<Rezerwacja> getAllRezerwacje() {
        return rezerwacje;
    }

    public List<Rezerwacja> getHistoriaRezerwacji() {
        return historiaRezerwacji;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
