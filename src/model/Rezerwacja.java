package model;

import java.io.Serializable;
import java.util.Date;

public class Rezerwacja implements Serializable {
    private static final long serialVersionUID = 1L;

    public int idRezerwacji;
    public int idUzytkownika;
    public Date data;
    public String status;
    public int miejsceParkingowe;
    public double cena; // Dodaj pole dla ceny
    public Date dataPlatnosci; // Dodaj pole dla daty płatności

    public Rezerwacja(int idRezerwacji, int idUzytkownika, Date data) {
        this.idRezerwacji = idRezerwacji;
        this.idUzytkownika = idUzytkownika;
        this.data = data;
        this.status = "aktywna";
        this.miejsceParkingowe = 0;
        this.cena = 50.0; // Stała cena 50 zł
        this.dataPlatnosci = new Date(); // Data płatności to data utworzenia rezerwacji
    }

    public Rezerwacja(int idRezerwacji, int idUzytkownika, Date data, int miejsceParkingowe) {
        this.idRezerwacji = idRezerwacji;
        this.idUzytkownika = idUzytkownika;
        this.data = data;
        this.miejsceParkingowe = miejsceParkingowe;
        this.status = "aktywna";
        this.cena = 50.0; // Stała cena 50 zł
        this.dataPlatnosci = new Date(); // Data płatności to data utworzenia rezerwacji
    }

    // Gettery i settery
    public int getMiejsceParkingowe() {
        return miejsceParkingowe;
    }

    public void setMiejsceParkingowe(int miejsceParkingowe) {
        this.miejsceParkingowe = miejsceParkingowe;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Date getDataPlatnosci() {
        return dataPlatnosci;
    }

    public void setDataPlatnosci(Date dataPlatnosci) {
        this.dataPlatnosci = dataPlatnosci;
    }
}
