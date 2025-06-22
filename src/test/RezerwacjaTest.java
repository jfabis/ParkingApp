package test;

import model.Rezerwacja;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RezerwacjaTest {

    @Test
    void testConstructorBezMiejsca() {
        Date now = new Date();
        Rezerwacja r = new Rezerwacja(1, 100, now);

        assertEquals(1, r.idRezerwacji);
        assertEquals(100, r.idUzytkownika);
        assertEquals(now, r.data);
        assertEquals("aktywna", r.status);
        assertEquals(0, r.getMiejsceParkingowe());
        assertEquals(50.0, r.getCena());
        assertNotNull(r.getDataPlatnosci());
    }

    @Test
    void testConstructorZMiejscem() {
        Date now = new Date();
        Rezerwacja r = new Rezerwacja(2, 101, now, 25);

        assertEquals(2, r.idRezerwacji);
        assertEquals(101, r.idUzytkownika);
        assertEquals(now, r.data);
        assertEquals("aktywna", r.status);
        assertEquals(25, r.getMiejsceParkingowe());
        assertEquals(50.0, r.getCena());
        assertNotNull(r.getDataPlatnosci());
    }

    @Test
    void testSettersAndGetters() {
        Rezerwacja r = new Rezerwacja(1, 100, new Date());

        r.setMiejsceParkingowe(15);
        assertEquals(15, r.getMiejsceParkingowe());

        r.setCena(75.5);
        assertEquals(75.5, r.getCena());

        Date payDate = new Date(123456789);
        r.setDataPlatnosci(payDate);
        assertEquals(payDate, r.getDataPlatnosci());
    }
}
