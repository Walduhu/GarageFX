/**
 * PT Java 2. Semester
 * Projekt Garage
 * Hauptklasse des Programms
 * @author Benjamin Schwarz
 * @version 28.04.24
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Main extends Application {

    public static ArrayList<ParkEtage> alleParkEtagen = importParkEtagenFromCSV();
    public static ArrayList<Fahrzeug> alleFahrzeuge = importFahrzeugeFromCSV();
    public static ArrayList<Fahrzeug> geloeschteFahrzeuge = new ArrayList<>();
    private static final String FAHRZEUG_CSV_PATH = ".\\src\\fahrzeuge.csv";
    private static final String PARKETAGE_CSV_PATH = ".\\src\\parketagen.csv";


    private static ArrayList<Fahrzeug> importFahrzeugeFromCSV() {
        ArrayList<Fahrzeug> fahrzeuge = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FAHRZEUG_CSV_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Füge das geparste Fahrzeug der Liste hinzu
                fahrzeuge.add(Fahrzeug.parseCSVLine(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fahrzeuge;
    }

    private static ArrayList<ParkEtage> importParkEtagenFromCSV() {
        ArrayList<ParkEtage> parkEtagen = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(PARKETAGE_CSV_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Füge das geparste Parketage-Objekt der Liste hinzu
                parkEtagen.add(ParkEtage.parseCSVLine(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parkEtagen;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Parkhaus Management System");

        Label label = new Label("Willkommen im Parkhaus der Stadt Vence");
        label.getStyleClass().add("label1");

        Label watermarkLabel = new Label("\n© 2024 Benjamin Schwarz");
        watermarkLabel.getStyleClass().add("watermark");

        Button button1 = createButton("Fahrzeug einparken", "greenButtons");
        Button button2 = createButton("Park-Etage hinzufügen", "greenButtons");
        Button button3 = createButton("Fahrzeug nach Kennzeichen suchen", "cyanButtons");
        Button button4 = createButton("Fahrzeug ausparken", "redButtons");
        Button button5 = createButton("Park-Etage löschen", "redButtons");
        Button button6 = createButton("Alle Fahrzeuge im Parkhaus anzeigen", "blueButtons");
        Button button7 = createButton("Alle Park-Etagen anzeigen", "blueButtons");
        Button button8 = createButton("Park-Etage ändern", "blueButtons");

        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("scene");

        gridPane.add(label, 0, 0, 3, 1);
        gridPane.add(watermarkLabel, 0, 4, 1, 1);

        gridPane.add(button1, 0, 2);
        gridPane.add(button2, 0, 3);
        gridPane.add(button3, 2, 0);
        gridPane.add(button4, 1, 2);
        gridPane.add(button5, 1, 3);
        gridPane.add(button6, 2, 2);
        gridPane.add(button7, 2, 3);
        gridPane.add(button8, 2, 4);

        gridPane.setHgap(20);
        gridPane.setVgap(20);

        Scene scene = new Scene(gridPane, 975, 275);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("GarageStyles.css")).toExternalForm());
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car_icon.png"))));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        saveDataToCSV();
    }

    private Button createButton(String text, String styleClass) {
        Button button = new Button(text);
        button.getStyleClass().add(styleClass);

        button.setOnAction(event -> {
            switch (text) {
                case "Fahrzeug einparken":
                    addFahrzeug();
                    break;
                case "Park-Etage hinzufügen":
                    addEtage();
                    break;
                case "Fahrzeug nach Kennzeichen suchen":
                    sucheFahrzeug();
                    break;
                case "Fahrzeug ausparken":
                    deleteFahrzeug();
                    break;
                case "Park-Etage löschen":
                    deleteEtage();
                    break;
                case "Alle Fahrzeuge im Parkhaus anzeigen":
                    zeigeFahrzeuge();
                    break;
                case "Alle Park-Etagen anzeigen":
                    zeigeParkEtagen();
                    break;
                case "Park-Etage ändern":
                    configEtage();
                    break;
            }
        });
        return button;
    }

    private void sucheFahrzeug() {
        if (!alleFahrzeuge.isEmpty()) {
            TextInputDialog dialog = getTextInputDialog();

            dialog.setTitle("Fahrzeug suchen");
            dialog.setHeaderText(null);
            dialog.setContentText("Bitte geben Sie ein Kennzeichen\noder Teile eines Kennzeichens ein:");
            Optional<String> suchEingabe = dialog.showAndWait();

            Alert alert = getAlert();
            StringBuilder contentTextBuilder = new StringBuilder(); // StringBuilder für gefundene Fahrzeuge
            boolean matchesGefunden = false; // Hilfsvariable für Suchstatus

            if (suchEingabe.isPresent() && !suchEingabe.get().isEmpty()) {
                for (Fahrzeug kfz : alleFahrzeuge) {
                    // Kennzeichen in Kleinbuchstaben umwandeln und prüfen, ob es die Sucheingabe enthält
                    if (kfz.getFahrzeugID().toLowerCase().contains(suchEingabe.get().toLowerCase())) {
                        if (!matchesGefunden) {
                            alert.setHeaderText(null);
                            contentTextBuilder.append("Folgende Fahrzeuge, deren Kennzeichen der Eingabe \"").append(suchEingabe.get()).append("\" entsprechen, wurden gefunden:\n\n");
                            matchesGefunden = true; // Aktualisierung der Hilfsvariable
                        }
                        contentTextBuilder.append(kfz.printMe()).append("\n"); // Hinzufügen des gefundenen Fahrzeugs zum StringBuilder
                    }
                }
            } else {
                // Keine Eingabe oder leere Eingabe
                alert = new Alert(Alert.AlertType.WARNING);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car_icon.png"))));
                alert.setHeaderText(null);
                alert.setContentText("Die Sucheingabe darf nicht leer sein!");
                alert.showAndWait();
            }

            if (matchesGefunden) {
                alert.setContentText(contentTextBuilder.toString()); // Anzeigen der gefundenen Fahrzeuge
                alert.showAndWait();
            } else {
                // Keine Übereinstimmungen gefunden
                alert = new Alert(Alert.AlertType.WARNING);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car_icon.png"))));
                alert.setHeaderText(null);
                alert.setContentText("Es wurden keine Fahrzeug-Kennzeichen gefunden, die der Eingabe \"" +
                        suchEingabe.orElse("") + "\" entsprechen.");
                alert.showAndWait();
            }
        } else {
            keineFahrzeuge();
        }
    }


    private void zeigeFahrzeuge() {
        if (!alleFahrzeuge.isEmpty()) {
            StringBuilder contentTextBuilder = new StringBuilder();

            for (Fahrzeug fahrzeug : alleFahrzeuge) {
                contentTextBuilder.append(fahrzeug.printMe()).append("\n");
            }

            contentTextBuilder.append("---------------------------------------------------" +
                    "\nDas Parkhaus enthält insgesamt ").append(alleFahrzeuge.size()).append(" Fahrzeuge.");

            Alert alert = getAlert();
            alert.setTitle("Übersicht alle Fahrzeuge");
            alert.setHeaderText(null);
            alert.setContentText(contentTextBuilder.toString());
            alert.showAndWait();
        } else {
            keineFahrzeuge();
        }
    }

    private void zeigeParkEtagen() {
        if (!alleParkEtagen.isEmpty()) {
            StringBuilder contentTextBuilder = new StringBuilder();

            for (ParkEtage parkEtage : alleParkEtagen) {
                contentTextBuilder.append(parkEtage.printMe()).append("\n");
            }

            int gesamtFreieParkplaetze = 0;
            int gesamtAnzahlParkplaetze = 0;

            for (ParkEtage etage : alleParkEtagen) {
                gesamtFreieParkplaetze += etage.getAnzahlFreieParkplaetze();
                gesamtAnzahlParkplaetze += etage.getAnzahlGesamtParkplaetze();
            }

            contentTextBuilder.append("--------------------------------------------\n" +
                    "Anzahl Fahrzeuge: ").append(alleFahrzeuge.size()).append("\n");
            contentTextBuilder.append("Anzahl Etagen: ").append(alleParkEtagen.size()).append("\n");
            contentTextBuilder.append("Gesamtanzahl freie Parkplätze: ").append(gesamtFreieParkplaetze)
                    .append(" / ").append(gesamtAnzahlParkplaetze);

            Alert alert = getAlert();
            alert.setTitle("Übersicht alle Park-Etagen");
            alert.setHeaderText(null);
            alert.setContentText(contentTextBuilder.toString());
            alert.showAndWait();
        } else {
            keineEtagen();
        }
    }


    private void addFahrzeug() {
        boolean fahrzeugHinzugefuegt = false; // Hilfsvariable

        if (!alleParkEtagen.isEmpty()) {
            for (ParkEtage etage : alleParkEtagen) {
                if (etage.getAnzahlFreieParkplaetze() > 0) {
                    fahrzeugHinzugefuegt = true; // Aktualisierung der Hilfsvariable
                    TextInputDialog dialog = getTextInputDialog();
                    dialog.setTitle("Fahrzeug fährt ins Parkhaus...");
                    dialog.setHeaderText(null);

                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(10);
                    gridPane.setVgap(10);

                    gridPane.add(new Label("Fahrzeugart:"), 0, 0);
                    TextField fahrzeugArtFeld = new TextField();
                    gridPane.add(fahrzeugArtFeld, 1, 0);

                    gridPane.add(new Label("Kfz-Kennzeichen:"), 0, 1);
                    TextField kennzeichenFeld = new TextField();
                    gridPane.add(kennzeichenFeld, 1, 1);

                    gridPane.add(new Label("Markenname:"), 0, 2);
                    TextField markenFeld = new TextField();
                    gridPane.add(markenFeld, 1, 2);

                    gridPane.add(new Label("Anzahl Räder:"), 0, 3);
                    TextField radFeld = new TextField();
                    gridPane.add(radFeld, 1, 3);

                    gridPane.add(new Label("Leistung in kW:"), 0, 4);
                    TextField kwFeld = new TextField();
                    gridPane.add(kwFeld, 1, 4);

                    dialog.getDialogPane().setContent(gridPane);

                    dialog.showAndWait().ifPresent(result -> {
                        String fahrzeugArt = fahrzeugArtFeld.getText();
                        String kennzeichen = kennzeichenFeld.getText();
                        String marke = markenFeld.getText();
                        int rad = Integer.parseInt(radFeld.getText());
                        int leistung = Integer.parseInt(kwFeld.getText());

                        // Überprüfen, ob das Kennzeichen bereits existiert
                        boolean kennzeichenExistiert = alleFahrzeuge.stream()
                                .anyMatch(fahrzeug -> fahrzeug.getFahrzeugID().equalsIgnoreCase(kennzeichen));

                        if (kennzeichenExistiert) {
                            showAlert(Alert.AlertType.ERROR, "Fehler", "Kennzeichen existiert bereits.\nFahrzeug kann nicht ins Parkhaus einparken.");
                        } else {
                            int position = etage.getAnzahlGesamtParkplaetze() - etage.getAnzahlFreieParkplaetze() + 1;
                            // falls gelöschte Fahrzeuge vorhanden sind → Position entsprechend ändern
                            if (!geloeschteFahrzeuge.isEmpty()) {
                                for (Fahrzeug geloeschtesFahrzeug : geloeschteFahrzeuge) {
                                    position = geloeschtesFahrzeug.getPosition();
                                }
                            }
                            Alert alert = getAlert();
                            Fahrzeug neuesFahrzeug = new Fahrzeug(fahrzeugArt, kennzeichen, marke, rad, leistung, etage.getEtagenBezeichnung(), position);
                            alleFahrzeuge.add(neuesFahrzeug);
                            etage.setAnzahlFreieParkplaetze(etage.getAnzahlFreieParkplaetze() - 1);
                            alert.setContentText("Fahrzeug mit folgenden Daten erfolgreich eingeparkt:\n" +
                                    "\nFahrzeugart: " + fahrzeugArt + "\nKfz-Kennzeichen: " + kennzeichen +
                                    "\nMarkenname: " + marke + "\nAnzahl Räder: " + rad + "\nLeistung in kW: " + leistung +
                                    "\nPark-Etage: " + etage.getEtagenBezeichnung() + "\nPosition: " + position);
                            alert.showAndWait();
                        }
                    });
                }
            }

            if (!fahrzeugHinzugefuegt) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car_icon.png"))));
                alert.setHeaderText(null);
                alert.setContentText("Fahrzeug abgelehnt. Es sind keine freien Parkplätze verfügbar." +
                        "\nBitte fügen Sie Park-Etagen zum Parkhaus hinzu.");
                alert.showAndWait();
            }
        } else {
            keineEtagen();
        }
    }


    private void deleteFahrzeug() {

        if (!alleFahrzeuge.isEmpty()) {
            List<String> choices = new ArrayList<>();
            for (Fahrzeug fahrzeug : alleFahrzeuge) {
                choices.add(fahrzeug.getFahrzeugID());
            }
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.getFirst(), choices);

            dialog.setTitle("Fahrzeug aus dem Parkhaus entfernen");
            dialog.setHeaderText(null);
            dialog.setContentText("Welches Fahrzeug möchten Sie aus dem Parkhaus entfernen?");
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car_icon.png"))));

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(selectedKennzeichen -> {
                for (Fahrzeug fahrzeug : alleFahrzeuge) {
                    if (fahrzeug.getFahrzeugID().equals(selectedKennzeichen)) {
                        ParkEtage etage = ParkEtage.parseCSVLine(fahrzeug.getParkEtage());
                        etage.setAnzahlFreieParkplaetze(etage.getAnzahlFreieParkplaetze() + 1);
                        alleFahrzeuge.remove(fahrzeug);
                        geloeschteFahrzeuge.add(fahrzeug);
                        showAlert(Alert.AlertType.INFORMATION, "Information", "Fahrzeug erfolgreich ausgeparkt.");
                        return;
                    }
                }
            });
        } else {
            keineFahrzeuge();
        }
    }

    private void addEtage() {

        TextInputDialog dialog = getTextInputDialog();
        dialog.setTitle("Etage hinzufügen");
        dialog.setHeaderText(null);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(new Label("Bezeichnung"), 0, 0);
        TextField bezeichnungFeld = new TextField();
        gridPane.add(bezeichnungFeld, 1, 0);

        gridPane.add(new Label("Anzahl Parkplätze:"), 0, 1);
        TextField parkplatzFeld = new TextField();
        gridPane.add(parkplatzFeld, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        dialog.showAndWait().ifPresent(result -> {
            String bezeichnung = bezeichnungFeld.getText();
            int parkplaetze = Integer.parseInt(parkplatzFeld.getText());

            Alert alert = getAlert();
            alert.setContentText("Etage mit folgenden Daten erfolgreich hinzugefügt:\n" +
                    "\nBezeichnung: " + bezeichnung + "\nAnzahl Parkplätze: " + parkplaetze);
            ParkEtage etage = new ParkEtage(bezeichnung, parkplaetze, parkplaetze);
            alleParkEtagen.add(etage);
            alert.showAndWait();
        });
    }


    private void deleteEtage() {
        if (!alleParkEtagen.isEmpty()) {

            // Etagenauswahl
            List<String> choices = new ArrayList<>();
            for (ParkEtage etage : alleParkEtagen) {
                choices.add(etage.getEtagenBezeichnung());
            }
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.getFirst(), choices);

            dialog.setTitle("Park-Etage löschen");
            dialog.setHeaderText(null);
            dialog.setContentText("Welche Etage möchten Sie aus dem Parkhaus entfernen?");
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car_icon.png"))));

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(selectedEtage -> {
                ParkEtage loeschEtage = null;
                for (ParkEtage etage : alleParkEtagen) {
                    if (etage.getEtagenBezeichnung().equals(selectedEtage)) {
                        loeschEtage = etage;
                        break;
                    }
                }

                if (loeschEtage != null) {
                    Alert confirmDialog = getConfirmDialog(loeschEtage);
                    Optional<ButtonType> confirmResult = confirmDialog.showAndWait();
                    if (confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
                        alleParkEtagen.remove(loeschEtage);
                        for (int i = alleFahrzeuge.size() - 1; i >= 0; i--) {
                            Fahrzeug fahrzeug = alleFahrzeuge.get(i);
                            if (fahrzeug.getParkEtage().equals(loeschEtage.getEtagenBezeichnung())) {
                                alleFahrzeuge.remove(i);
                            }
                        }
                        showAlert(Alert.AlertType.INFORMATION, "Etage erfolgreich entfernt", "Etage erfolgreich entfernt.");
                    }
                }
            });
        } else {
            keineEtagen();
        }
    }

    private void configEtage() {

        if (!alleParkEtagen.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Park-Etage konfigurieren");
            alert.setHeaderText(null);
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car_icon.png"))));
            alert.setContentText("Welche Etage möchten Sie konfigurieren?");

            // Erstellen der Liste für die Auswahl der Etage
            List<String> choices = new ArrayList<>();
            for (ParkEtage etage : alleParkEtagen) {
                choices.add(etage.getEtagenBezeichnung());
            }
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.getFirst(), choices);
            dialog.setTitle("Park-Etage auswählen");
            dialog.setHeaderText(null);
            dialog.setContentText("Welche Etage möchten Sie konfigurieren?");
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car_icon.png"))));
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(selectedEtage -> {
                ParkEtage configEtage = alleParkEtagen.stream().filter(etage -> etage.getEtagenBezeichnung().equals(selectedEtage)).findFirst().orElse(null);

                if (configEtage != null) {
                    TextInputDialog textInputDialog = new TextInputDialog(configEtage.getEtagenBezeichnung());
                    textInputDialog.setTitle("Bezeichnung ändern");
                    textInputDialog.setHeaderText(null);
                    Stage textInputStage1 = (Stage) textInputDialog.getDialogPane().getScene().getWindow();
                    textInputStage1.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car_icon.png"))));
                    textInputDialog.setContentText("Geben Sie die neue Bezeichnung der Etage ein:");
                    Optional<String> newBezeichnungResult = textInputDialog.showAndWait();

                    newBezeichnungResult.ifPresent(configEtage::setEtagenBezeichnung);

                    if (configEtage.getAnzahlFreieParkplaetze() == configEtage.getAnzahlGesamtParkplaetze()) {
                        TextInputDialog anzahlParkplaetzeDialog = new TextInputDialog(String.valueOf(configEtage.getAnzahlGesamtParkplaetze()));
                        anzahlParkplaetzeDialog.setTitle("Anzahl Parkplätze ändern");
                        anzahlParkplaetzeDialog.setHeaderText(null);
                        Stage textInputStage2 = (Stage) anzahlParkplaetzeDialog.getDialogPane().getScene().getWindow();
                        textInputStage2.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car_icon.png"))));
                        anzahlParkplaetzeDialog.setContentText("Geben Sie die neue Anzahl der Parkplätze ein:");
                        Optional<String> newAnzahlParkplaetzeResult = anzahlParkplaetzeDialog.showAndWait();

                        newAnzahlParkplaetzeResult.ifPresent(newAnzahl -> {
                            try {
                                int anzahlParkplaetze = Integer.parseInt(newAnzahl);
                                configEtage.setAnzahlGesamtParkplaetze(anzahlParkplaetze);
                                configEtage.setAnzahlFreieParkplaetze(anzahlParkplaetze);
                            } catch (NumberFormatException e) {
                                showAlert(Alert.AlertType.ERROR, "Fehler", "Ungültige Eingabe. Bitte geben Sie eine Zahl ein.");
                            }
                        });
                    } else {
                        int anzahlFahrzeuge = configEtage.getAnzahlGesamtParkplaetze() - configEtage.getAnzahlFreieParkplaetze();
                        TextInputDialog anzahlParkplaetzeDialog = new TextInputDialog(String.valueOf(configEtage.getAnzahlGesamtParkplaetze()));
                        anzahlParkplaetzeDialog.setTitle("Anzahl Parkplätze ändern");
                        anzahlParkplaetzeDialog.setHeaderText(null);
                        anzahlParkplaetzeDialog.setContentText("Geben Sie die neue Anzahl Parkplätze ein (Es sind " + anzahlFahrzeuge + " Fahrzeuge geparkt):");
                        Optional<String> newAnzahlParkplaetzeResult = anzahlParkplaetzeDialog.showAndWait();

                        newAnzahlParkplaetzeResult.ifPresent(newAnzahl -> {
                            try {
                                int anzahlParkplaetze = Integer.parseInt(newAnzahl);
                                if (anzahlParkplaetze >= anzahlFahrzeuge) {
                                    configEtage.setAnzahlGesamtParkplaetze(anzahlParkplaetze);
                                    configEtage.setAnzahlFreieParkplaetze(anzahlParkplaetze - anzahlFahrzeuge);
                                } else {
                                    showAlert(Alert.AlertType.WARNING, "Ungültige Eingabe", "Anzahl Parkplätze ist zu niedrig. Geben Sie mindestens " + anzahlFahrzeuge + " ein.");
                                    configEtage();
                                }
                            } catch (NumberFormatException e) {
                                showAlert(Alert.AlertType.ERROR, "Fehler", "Ungültige Eingabe. Bitte geben Sie eine Zahl ein.");
                            }
                        });
                    }
                    showAlert(Alert.AlertType.INFORMATION, "Information", "Etage erfolgreich geändert.");
                }
            });
        } else {
            keineEtagen();
        }
    }


    private static Alert getConfirmDialog(ParkEtage loeschEtage) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Bestätigung");
        confirmDialog.setHeaderText(null);
        Stage alertStage = (Stage) confirmDialog.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("car_icon.png"))));
        confirmDialog.setContentText(loeschEtage.getAnzahlFreieParkplaetze() == loeschEtage.getAnzahlGesamtParkplaetze() ?
                "Möchten Sie wirklich die leere Etage entfernen?" :
                "Möchten Sie wirklich die Etage mitsamt der dort geparkten Fahrzeuge (Anzahl: " +
                        (loeschEtage.getAnzahlGesamtParkplaetze() - loeschEtage.getAnzahlFreieParkplaetze()) +
                        ") löschen?");
        return confirmDialog;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car_icon.png"))));
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void keineFahrzeuge() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car_icon.png"))));
        alert.setHeaderText(null);
        alert.setContentText("Es befinden sich noch keine Fahrzeuge im Parkhaus!");
        alert.showAndWait();
    }

    private void keineEtagen() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car_icon.png"))));
        alert.setHeaderText(null);
        alert.setContentText("Das Parkhaus hat noch keine Etagen!");
        alert.showAndWait();
    }

    private TextInputDialog getTextInputDialog() {
        TextInputDialog dialog = new TextInputDialog();
        Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
        dialogStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car_icon.png"))));
        return dialog;
    }

    private Alert getAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car_icon.png"))));
        alert.setHeaderText(null);
        alert.setTitle("Information");
        return alert;
    }


    private void saveDataToCSV() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FAHRZEUG_CSV_PATH))) {

            for (Fahrzeug fahrzeug : alleFahrzeuge) {
                writer.write(fahrzeug.toCSVFormat());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PARKETAGE_CSV_PATH))) {

            for (ParkEtage etage : alleParkEtagen) {
                writer.write(etage.toCSVFormat());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

