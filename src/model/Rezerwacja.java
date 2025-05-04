package model;
import java.util.*;

public class Rezerwacja {
    public int idRezerwacji;
    public int userId;
    public Date data;
    public String status;
    public boolean anulowana;

    // Constructor
    public Rezerwacja() {
        this.anulowana = false;
    }
}