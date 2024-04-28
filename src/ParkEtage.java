/**
 * PT Java 2. Semester
 * Projekt Garage
 * Klasse zum Erzeugen von Park-Etagen
 * @author Benjamin Schwarz
 * @version 28.04.24
 */

public class ParkEtage extends Main{

    private String etagenBezeichnung; // z.B. EG, 2.UG, 5.OG
    private int anzahlFreieParkplaetze;
    private int anzahlGesamtParkplaetze;

    public ParkEtage() {
    }

    public ParkEtage(String etagenBezeichnung, int anzahlFreieParkplaetze, int anzahlGesamtParkplaetze) {
        this.etagenBezeichnung = etagenBezeichnung;
        this.anzahlFreieParkplaetze = anzahlFreieParkplaetze;
        this.anzahlGesamtParkplaetze = anzahlGesamtParkplaetze;
    }

    public String getEtagenBezeichnung() {
        return etagenBezeichnung;
    }

    public void setEtagenBezeichnung(String etagenBezeichnung) {
        this.etagenBezeichnung = etagenBezeichnung;
    }

    public int getAnzahlFreieParkplaetze() {
        return anzahlFreieParkplaetze;
    }

    public void setAnzahlFreieParkplaetze(int anzahlParkplaetze) {
        this.anzahlFreieParkplaetze = anzahlParkplaetze;
    }

    public int getAnzahlGesamtParkplaetze() {
        return anzahlGesamtParkplaetze;
    }

    public void setAnzahlGesamtParkplaetze(int anzahlGesamtParkplaetze) {
        this.anzahlGesamtParkplaetze = anzahlGesamtParkplaetze;
    }

    // Ausgabe der Attribute eines ParkEtage-Objekts
    public String printMe() {
        return "Bezeichnung: " + getEtagenBezeichnung() +
                "\nAnzahl freie Parkplätze: " + getAnzahlFreieParkplaetze() + " / " + getAnzahlGesamtParkplaetze() + "\n";

    }

}
