import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

// Diese Klasse verwaltet eine Liste von Käseobjekten (Lagerverwaltung)
public class Lager {

    // Interne Liste zur Speicherung aller Käseobjekte
    private final List<Kaese> kaeseList;

    // Konstruktor: Initialisiert die Käseliste
    public Lager(){
        kaeseList = new ArrayList<>();
    }

    public void addKaese(Kaese kaese) {
        kaeseList.add(kaese);
    }

    public void removeKaese(int index) {
        if (index >= 0 && index < kaeseList.size()) {
            kaeseList.remove(index);
        }
    }

    public List<Kaese> getKaese() {
        return kaeseList;
    }

    /**
     * Gibt eine Liste aller Käse mit kritischem Verkaufsbestand zurück.
     * Kritisch = Bestand >= 20 kg UND Haltbarkeit zwischen 11 und 20 Tagen ab heute
     */
    public List<Kaese> KritischeVerkaufsbestand() {
        List<Kaese> gefilterteListe = new ArrayList<>();
        LocalDate heute = LocalDate.now();

        for (Kaese k : kaeseList) {
            int bestandInt = k.getBestand();
            int tageBisAblauf = (int) ChronoUnit.DAYS.between(heute, k.getHaltbarkeit());

            if (bestandInt >= 20 && tageBisAblauf > 10 && tageBisAblauf <= 20) {
                gefilterteListe.add(k);
            }
        }

        return gefilterteListe;
    }
}
