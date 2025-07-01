import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class LagerGUI extends JFrame {
    private JPanel Lager;
    private JTable table1;
    private JTextField textField1,textField2,textField3;
    private JComboBox<String> comboBox1,comboBox2;
    private JButton speichernButton,löschenButton,kritischeVerkaufsbestandButton;


    private final Lager lager;
    private final DefaultTableModel tableModel;
    private final TableRowSorter<DefaultTableModel> sorter;
    private final LocalDate heute = LocalDate.now();

    //Quelle: https://docs.oracle.com/javase/8/docs/api/javax/swing/table/DefaultTableModel.html
    //Quelle: https://www.jetbrains.com/help/idea/design-gui-using-swing.html#adjust_design

    // Konstruktor: Initialisiert GUI, Datenmodell und Event-Handler
    public  LagerGUI() {
        lager = new Lager();


        //Quelle: https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#data
        tableModel = new DefaultTableModel(new String[]{"Sorte", "Herkunftsland", "Bestand", "Haltbarkeit"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            // Quelle: https://stackoverflow.com/questions/12405605/using-custom-tablemodel-make-iscelleditable-true-for-a-particular-row-on-button
            public Class getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 2: // bestand
                        return Integer.class;
                    case 3: // haltbarkeit
                        return LocalDate.class;
                    default: // Standard für andere Spalten
                        return String.class;
                }
            }
        };

        table1.setModel(tableModel);

        EinheitenRenderer(); // Setzt benutzerdefinierte Renderer für Einheiten & Farben

        // Sortierer für die Tabelle initialisieren
        sorter = new TableRowSorter<>(tableModel);
        table1.setRowSorter(sorter);

        // Einfügen der Beispiel-Daten
        initObjekte();


        // GUI-Fenster Layout
        setTitle("Käsespezialitäten Atasoy GbR");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setContentPane(Lager);
        setVisible(true);
        setLocationRelativeTo(null);


        // Event-Listener für Buttons
        speichernButton.addActionListener(_ -> speichernKaese());
        löschenButton.addActionListener(_ -> loeschenKaese());
        comboBox2.addActionListener(_ -> filterTabelle());
        kritischeVerkaufsbestandButton.addActionListener(_ -> berechneKritischeKaese());
        berechneKritischeKaese();

    }

    // Fügt Beispiel-Daten ins Lager ein
    private void initObjekte() {
        lager.addKaese(new Kaese("Gouda", "Deutschland", 12, LocalDate.of(2025, 8, 5)));
        lager.addKaese(new Kaese("Edamer", "Deutschland", 8, LocalDate.of(2025, 9, 1)));
        lager.addKaese(new Kaese("Emmentaler", "Schweiz", 3, LocalDate.of(2025, 7, 12)));
        lager.addKaese(new Kaese("Butterkäse", "Deutschland", 5, LocalDate.of(2025, 7, 4)));
        lager.addKaese(new Kaese("Tilsiter", "Schweiz", 4, LocalDate.of(2025, 8, 18)));
        lager.addKaese(new Kaese("Camembert", "Frankreich", 10, LocalDate.of(2025, 10, 15)));
        lager.addKaese(new Kaese("Brie", "Frankreich", 6, LocalDate.of(2025, 7, 22)));
        lager.addKaese(new Kaese("Mozzarella", "Italien", 20, LocalDate.of(2025, 8, 5)));
        lager.addKaese(new Kaese("Frischkäse", "Deutschland", 15, LocalDate.of(2025, 11, 10)));
        lager.addKaese(new Kaese("Feta", "Griechenland", 7, LocalDate.of(2025, 10, 5)));
        lager.addKaese(new Kaese("Bergkäse", "Österreich", 11, LocalDate.of(2025, 8, 30)));
        lager.addKaese(new Kaese("Harzer Käse", "Deutschland", 13, LocalDate.of(2025, 7, 28)));
        lager.addKaese(new Kaese("Camembert", "Deutschland", 14, LocalDate.of(2025, 8, 18)));
        lager.addKaese(new Kaese("Frischkäse", "Deutschland", 6, LocalDate.of(2025, 7, 17)));
        lager.addKaese(new Kaese("Tilsiter", "Deutschland", 7, LocalDate.of(2025, 8, 2)));
        lager.addKaese(new Kaese("Gouda", "Deutschland", 10, LocalDate.of(2025, 7, 9)));
        lager.addKaese(new Kaese("Edamer", "Deutschland", 21, LocalDate.of(2025, 7, 15)));
        lager.addKaese(new Kaese("Mozzarella", "Deutschland", 22, LocalDate.of(2025, 7, 13)));
        lager.addKaese(new Kaese("Ziegenkäse", "Frankreich", 18, LocalDate.of(2025, 7, 20)));
        lager.addKaese(new Kaese("Raclette", "Schweiz", 6, LocalDate.of(2025, 8, 12)));
        lager.addKaese(new Kaese("Manchego", "Spanien", 3, LocalDate.of(2025, 9, 5)));
        lager.addKaese(new Kaese("Pecorino", "Italien", 11, LocalDate.of(2025, 11, 1)));
        lager.addKaese(new Kaese("Halloumi", "Griechenland", 14, LocalDate.of(2025, 7, 30)));
        lager.addKaese(new Kaese("Roquefort", "Frankreich", 2, LocalDate.of(2025, 7, 3)));
        lager.addKaese(new Kaese("Gorgonzola", "Italien", 9, LocalDate.of(2025, 8, 18)));
        lager.addKaese(new Kaese("Cheddar", "England", 30, LocalDate.of(2025, 7, 12)));
        lager.addKaese(new Kaese("Limburger", "Belgien", 7, LocalDate.of(2025, 8, 8)));

        updateTable();
    }

    // Speichert neuen Käse nach Validierung
    private void speichernKaese() {
        try {
            String sorte = Objects.requireNonNull(comboBox1.getSelectedItem()).toString();
            String herkunftsland = textField1.getText();
            String bestandText = textField2.getText();
            String haltbarkeitText = textField3.getText();

            boolean bestandGueltig = pruefeBestand(bestandText);
            boolean herkunftGueltig = pruefeHerkunftsland(herkunftsland);

            LocalDate haltbarkeit;
            boolean haltbarkeitGueltig = true;
            try {
                haltbarkeit = parseHaltbarkeit(haltbarkeitText);
                if (haltbarkeit.isBefore(heute)) {
                    haltbarkeitGueltig = false;
                    JOptionPane.showMessageDialog(this,
                            "Achtung: Das Haltbarkeitsdatum liegt in der Vergangenheit!",
                            "Ungültiges Datum",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (Exception e) {
                haltbarkeitGueltig = false;
                haltbarkeit = null;
            }

            if (!bestandGueltig || !herkunftGueltig || !haltbarkeitGueltig) {
                // Fehlermeldung bei ungültigen Eingaben
                String fehlermeldung = "";
                if (!bestandGueltig) fehlermeldung += "Bestand muss eine positive ganze Zahl sein.\n";
                if (!herkunftGueltig) fehlermeldung += "Herkunftsland darf nur Buchstaben und Leerzeichen enthalten.\n";
                if (!haltbarkeitGueltig && haltbarkeit == null)
                    fehlermeldung += "Haltbarkeit muss im Format TT.MM.JJJJ sein.\n";
                JOptionPane.showMessageDialog(this, fehlermeldung);
                return;
            }

            int bestand = Integer.parseInt(bestandText);

            Kaese neuerKaese = new Kaese(sorte, herkunftsland, bestand, haltbarkeit);
            lager.addKaese(neuerKaese);
            updateTable();
            JOptionPane.showMessageDialog(this, "Käse erfolgreich gespeichert!");

            // Eingabefelder leeren
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unbekannter Fehler beim Speichern des Käses.");
        }
    }


    // Renderer für formatierung in der Tabelle
    // Quelle: https://openbook.rheinwerk-verlag.de/java8/10_019.html#u10.19.4
    // Quelle: https://www.youtube.com/watch?v=yf0U3c2Oxy8
    // Quelle: https://www.codejava.net/java-se/swing/jtable-simple-renderer-example
    // Quelle: https://docs.oracle.com/javase/8/docs/api/javax/swing/table/DefaultTableCellRenderer.html

    // Setzt Zellenrenderer für Einheiten und farbliche Haltbarkeit
    private void EinheitenRenderer() {

        table1.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            public void setValue(Object value) {
                if (value instanceof Integer) {
                    setText(value + " kg");
                } else {
                    setText(value == null ? "" : value.toString());
                }
            }
        });




        // Rendert Haltbarkeit farblich
        table1.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            public void setValue(Object value) {
                setOpaque(true);

                if (value instanceof LocalDate) {
                    LocalDate haltbarkeit = (LocalDate) value;
                    setText(haltbarkeit.format(formatter));

                    long daysLeft = ChronoUnit.DAYS.between(heute, haltbarkeit);

                    if (daysLeft <= 5) {
                        setBackground(Color.RED);
                        setForeground(Color.WHITE);
                    } else if (daysLeft <= 10) {
                        setBackground(Color.YELLOW);
                        setForeground(Color.BLACK);
                    } else {
                        setBackground(Color.WHITE);
                        setForeground(Color.BLACK);
                    }
                } else {
                    setText(value == null ? "" : value.toString());
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    comp.setBackground(table.getSelectionBackground());
                    comp.setForeground(table.getSelectionForeground());
                }
                return comp;
            }
        });


    }


    // Prüft ob Bestand gültig (positive ganze Zahl) ist
    private boolean pruefeBestand(String bestand) {
        try {
            int wert = Integer.parseInt(bestand);
            return wert > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean pruefeHerkunftsland(String herkunft) {
        return herkunft != null &&
                !herkunft.trim().isEmpty() &&
                herkunft.matches("[a-zA-ZäöüÄÖÜß\\s]+");
    }


    // Wandelt Text in LocalDate um
    private LocalDate parseHaltbarkeit(String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(text, formatter);
    }

    private void loeschenKaese() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = table1.convertRowIndexToModel(selectedRow);
            lager.removeKaese(modelRow);
            updateTable();
            JOptionPane.showMessageDialog(this, "Käse erfolgreich gelöscht!");
        } else {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Käse aus der Tabelle aus, der gelöscht werden soll.");
        }
    }


    // Filter Funktion in ComboBox
        /*Quelle: https://www.youtube.com/watch?v=U5Sh0KDLXSc
              https://www.youtube.com/watch?v=Gg2CZHF37sQ
              https://www.youtube.com/watch?v=Tg62AxNRir4
        */

    private void filterTabelle() {
        String filterOption = Objects.requireNonNull(comboBox2.getSelectedItem()).toString();
        switch (filterOption) {
            case "Nicht Filtern":
                sorter.setRowFilter(null);
                break;
            case "0-5 kg":
                sorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, 6, 2));
                break;
            case "6-10 kg":
                sorter.setRowFilter(RowFilter.andFilter(List.of(
                        RowFilter.numberFilter(RowFilter.ComparisonType.AFTER, 5, 2),
                        RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, 11, 2)
                )));
                break;
            case ">10 kg":
                sorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.AFTER, 10, 2));
                break;
        }
    }

    // Zeigt alle Käse mit kritischem Verkaufsbestand (z.B. bald ablaufend) in Dialog an
    private void berechneKritischeKaese() {
        List<Kaese> kritischeListe = lager.KritischeVerkaufsbestand();

        if (kritischeListe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Es gibt derzeit keine Käse mit kritischem Verkaufsbestand.");
        } else {
            StringBuilder meldung = new StringBuilder("Kritischer Käsebestand:\n\n");

            for (Kaese k : kritischeListe) {
                meldung.append("• ")
                        .append(k.getSorte()).append(" aus ")
                        .append(k.getHerkunftsland()).append(", Bestand: ")
                        .append(k.getBestand()).append(" kg, Haltbar bis: ")
                        .append(k.getHaltbarkeit().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                        .append("\n");
            }

            JTextArea textArea = new JTextArea(meldung.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 250));

            JOptionPane.showMessageDialog(this, scrollPane, "Kritischer Verkaufsbestand", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Aktualisiert die Tabelle mit allen Käsen aus dem Lager
    private void updateTable() {
        tableModel.setRowCount(0);

        for (Kaese kaese : lager.getKaese()) {
            tableModel.addRow(new Object[]{
                    kaese.getSorte(),
                    kaese.getHerkunftsland(),
                    kaese.getBestand(),
                    kaese.getHaltbarkeit()
            });
        }
    }

    public static void main(String[] args) {
        new LagerGUI(); 
    }
}

/*Weitere Quellen
https://www.youtube.com/watch?v=P-D5tDNZdnY
https://openbook.rheinwerk-verlag.de/javainsel
https://openbook.rheinwerk-verlag.de/java8
https://www.youtube.com/watch?v=crm0yaneCb0
https://www.youtube.com/watch?v=O1yJ9wvlviA
https://www.youtube.com/watch?v=xXDDVSjogs0
https://www.youtube.com/@mrcresseysclassvideos8183
https://www.youtube.com/@KnowledgetoShare
https://docs.oracle.com/javase/8/docs/api/javax/swing/table/DefaultTableModel.html

 */