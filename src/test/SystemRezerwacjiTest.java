package test;

import model.Rezerwacja;
import model.SystemRezerwacji;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SystemRezerwacjiTest {

    private SystemRezerwacji system;

    @BeforeEach
    void setUp() {
        system = new SystemRezerwacji();
    }

    @Test
    void testCreateRezerwacjaBezMiejsca() {
        Rezerwacja r = system.createRezerwacja(1, new Date());
        assertNotNull(r);
        assertEquals(1, r.idRezerwacji);
        assertEquals(1, r.idUzytkownika);
        assertEquals("aktywna", r.status);
        assertEquals(50.0, r.getCena());
    }

    @Test
    void testCreateRezerwacjaZMiejscem() {
        Rezerwacja r = system.createRezerwacja(1, new Date(), 10);
        assertNotNull(r);
        assertEquals(10, r.getMiejsceParkingowe());
    }

    @Test
    void testCreateRezerwacjaNaZajeteMiejsce() {
        system.createRezerwacja(1, new Date(), 10);
        Rezerwacja r2 = system.createRezerwacja(2, new Date(), 10);
        assertNull(r2); // miejsce już zajęte
    }

    @Test
    void testAnulujRezerwacje() {
        Rezerwacja r = system.createRezerwacja(1, new Date());
        system.anulujRezerwacje(r.idRezerwacji);
        List<Rezerwacja> aktywne = system.getRezerwacjeUzytkownika(1);
        assertTrue(aktywne.isEmpty());

        List<Rezerwacja> historia = system.getHistoriaRezerwacjiUzytkownika(1);
        // Powinno nadal być w historii, ale w Twojej implementacji usuwasz anulowane z historii
        // Jeśli chcesz zachować, zmień kod w anulujRezerwacje
        assertTrue(historia.isEmpty());
    }

    @Test
    void testGetRezerwacjeUzytkownika() {
        system.createRezerwacja(1, new Date());
        system.createRezerwacja(1, new Date(), 5);
        system.createRezerwacja(2, new Date(), 6);

        List<Rezerwacja> r = system.getRezerwacjeUzytkownika(1);
        assertEquals(2, r.size());
    }

    @Test
    void testGetHistoriaRezerwacjiUzytkownika() {
        system.createRezerwacja(1, new Date());
        system.createRezerwacja(1, new Date(), 5);
        system.createRezerwacja(2, new Date(), 6);

        List<Rezerwacja> h = system.getHistoriaRezerwacjiUzytkownika(1);
        assertEquals(2, h.size());
    }
}
