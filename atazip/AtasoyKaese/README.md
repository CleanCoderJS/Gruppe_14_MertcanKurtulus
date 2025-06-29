# Lagerverwaltung – Käsespezialitäten Atasoy GbR

Dieses Java-Projekt ist eine Desktop-Anwendung, die mit Java Swing entwickelt wurde und der Verwaltung eines Käselagers dient.  
Benutzer können Käseprodukte hinzufügen, löschen, filtern und kritische Lagerbestände einsehen.

---

## Hauptfunktionen

- Neues Käseprodukt mit Sorte, Herkunftsland, Bestand (in kg) und Haltbarkeitsdatum erfassen
- Käse aus der Tabelle löschen
- Filterung des Bestands nach Kategorien:  
  0–5 kg, 6–10 kg, über 10 kg
- Anzeige kritischer Lagerbestände (hoher Bestand + baldige Haltbarkeit)
- Farbige Hervorhebung der Haltbarkeit (z. B. rot für <5 Tage, gelb für <10 Tage)
- Enthält einen JUnit-Test in LagerTest.java

---

## Verwendete Technologien

- Java 17 oder höher
- Java Swing (für die grafische Oberfläche)
- JUnit 5 (für automatisierte Tests)
- Maven
- Entwicklungsumgebung: IntelliJ IDEA, Eclipse oder ähnlich

---

## Klassenstruktur


Kaese.java --> Datenmodell für Käse  
Lager.java --> Logik zur Lagerverwaltung und Filterung  
LagerGUI.java --> Benutzeroberfläche (Swing)  
LagerTest.java --> JUnit-Testklasse


---

## Anwendung starten

### In der IDE:

Starte die Anwendung über die main -Methode in der Datei LagerGUI.java.

### Im Terminal:


javac LagerGUI.java  
java LagerGUI


---

## Definition eines kritischen Lagerbestands

Ein Käse gilt als kritisch, wenn:

- der Bestand mindestens 20 kg beträgt und
- das Haltbarkeitsdatum zwischen 11 und 20 Tagen ab heute liegt.

Diese Produkte werden dem Benutzer in einem Hinweisfenster angezeigt.

---

## Test

Die Klasse LagerTest testet die Methode KritischeVerkaufsbestand().

Geprüfte Szenarien:

- Ausreichender Bestand und geeignetes Haltbarkeitsdatum → wird angezeigt
- Geringer Bestand oder nicht zutreffendes Datum → wird nicht angezeigt

---

## Lizenz

Dieses Projekt wurde ausschließlich zu Bildungszwecken erstellt.
