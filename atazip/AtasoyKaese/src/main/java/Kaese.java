import java.time.LocalDate;

// Diese Klasse repräsentiert ein Käseobjekt mit Sorte, Herkunft, Bestand und Haltbarkeit
public class Kaese {
    private final String sorte;
    private final String herkunftsland;
    private final int bestand;
    private final LocalDate haltbarkeit;


    public Kaese(String sorte, String herkunftsland, int bestand, LocalDate haltbarkeit) {
        this.sorte = sorte;
        this.herkunftsland = herkunftsland;
        this.bestand = bestand;
        this.haltbarkeit = haltbarkeit;
    }

    public String getSorte() {
        return sorte;
    }

    public String getHerkunftsland() {
        return herkunftsland;
    }

    public int getBestand() {
        return bestand;
    }

    public LocalDate getHaltbarkeit() {
        return haltbarkeit;
    }
}
