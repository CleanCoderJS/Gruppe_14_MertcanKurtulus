import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


// Quelle: https://www.jetbrains.com/help/idea/junit.html

// Testklasse für die Klasse Lager
public class LagerTest {

    private Lager lager;

    // Wird vor jedem Test ausgeführt – Initialisiert ein neues Lager mit Testdaten
    @BeforeEach
    void setUp() {
        lager = new Lager();


        lager.addKaese(new Kaese("Gouda", "Deutschland", 25, LocalDate.now().plusDays(15)));
        lager.addKaese(new Kaese("Brie", "Frankreich", 5, LocalDate.now().plusDays(15)));
        lager.addKaese(new Kaese("Feta", "Griechenland", 30, LocalDate.now().plusDays(5)));
        lager.addKaese(new Kaese("Emmentaler", "Schweiz", 25, LocalDate.now().plusDays(25)));
    }

    // Testet die Methode KritischeVerkaufsbestand()
    @Test
    void testKritischeVerkaufsbestand() {
        List<Kaese> kritisch = lager.KritischeVerkaufsbestand();

        // Es sollte nur ein Käse (Gouda) die Kriterien erfüllen
        assertEquals(1, kritisch.size());
        assertEquals("Gouda", kritisch.get(0).getSorte());
    }
}