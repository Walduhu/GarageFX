/**
 * PT Java 2. Semester
 * Projekt Garage
 * Klasse zum Erzeugen von Fahrzeugen
 *
 * @author Benjamin Schwarz
 * @version 28.04.24
 */

public class Fahrzeug extends Main {

    private String fahrzeugArt;
    private String fahrzeugID; // Kfz-Kennzeichen
    private String markenName;
    private int anzahlRaeder;
    private int leistung;// in kW
    private String parkEtage;
    private int position;

    public Fahrzeug() {
    }

    public Fahrzeug(String fahrzeugArt, String fahrzeugID, String markenName,
                    int anzahlRaeder, int leistung, String parkEtage, int position) {
        this.fahrzeugArt = fahrzeugArt;
        this.fahrzeugID = fahrzeugID;
        this.markenName = markenName;
        this.anzahlRaeder = anzahlRaeder;
        this.leistung = leistung;
        this.parkEtage = parkEtage;
        this.position = position;
    }

    public String getFahrzeugArt() {
        return fahrzeugArt;
    }

    public String getFahrzeugID() {
        return fahrzeugID;
    }

    public String getMarkenName() {
        return markenName;
    }

    public int getAnzahlRaeder() {
        return anzahlRaeder;
    }

    public int getLeistung() {
        return leistung;
    }

    public String getParkEtage() {
        return parkEtage;
    }

    public int getPosition() {
        return position;
    }

    // Ausgabe der Attribute eines Fahrzeug-Objekts
    public String printMe() {
        return "Kennzeichen: " + getFahrzeugID() + "\nFahrzeugart: " + getFahrzeugArt() +
                "\nMarke: " + getMarkenName() + "\nAnzahl Räder: " + getAnzahlRaeder() + "\nLeistung: " + getLeistung() + " kW" +
                "\nPark-Etage: " + getParkEtage() + "\nPosition: " + getPosition() + "\n";
    }

    // Methode zur Konvertierung des Fahrzeugs in CSV-Format
    public String toCSVFormat() {
        return String.format("%s,%s,%s,%d,%d,%s,%d", fahrzeugArt, fahrzeugID, markenName, anzahlRaeder, leistung, parkEtage, position);
    }

    // Methode zum Parsen einer Zeile aus der CSV-Datei in ein Fahrzeug-Objekt
    public static Fahrzeug parseCSVLine(String line) {
        String[] parts = line.split(",");
        return new Fahrzeug(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), parts[5], Integer.parseInt(parts[6]));
    }
}

