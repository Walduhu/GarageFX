# GarageFX - Eine JavaFX-Anwendung zum Erstellen und Verwalten eines Parkhauses

<p>Die fiktive Stadt Vence hat den Programmierer mit der Implementierung eines Parkhaus-Simulationsprogramms beauftragt.
</p>

<p>
Es unterstützt unterschiedliche Arten von Fahrzeugen und jedes Fahrzeug erhält ein einzigartiges Kfz-Kennzeichen.</p>

<p>Das geplante Parkhaus soll mehrere Park-Etagen unterstützen, allerdings ist sich die Stadt Vence aus 
Gründen der Statik noch nicht sicher, wie hoch das Parkhaus gebaut werden kann. </p>

<p>Daraus resultierend ist es innerhalb der Anwendung möglich, die Anzahl der Etagen flexibel zu halten. <br><br>
Auch für die Anzahl der Parkplätze pro Etage, da das Grundstück für den Bau des Parkhauses noch 
nicht gefunden wurde, ist die Anwendung flexibel und konfigurierbar.</p>

<p>Fahrzeuge sind in der Lage, das Parkhaus zu befahren und zu verlassen. Die Anwendung weist dem einfahrenden 
Fahrzeug einen freien Platz zu, oder lehnt es ab, wenn keine freien Parkplätze vorhanden 
sind.</p>

<p>Der Anwender ist außerdem in der Lage, Fahrzeuge anhand ihres Kennzeichens zu suchen und deren Daten sowie Positionen im Parkhaus zu erfahren.
Zudem ist es möglich, die Anzahl der noch freien Parkplätze abzufragen. </p>

<p>Damit Fahrzeug- und Etagen-Daten einfach eingelesen werden können,
verfügt das Programm darüber hinaus über einen csv-Parser. Die Werte 
müssen in zwei csv-Dateien (fahrzeuge.csv und parketagen.csv im src-Ordner) jeweils durch Kommata 
getrennt vorliegen. Wenn Änderungen der Daten vorgenommen werden, speichert das Programm diese
beim Beenden in den csv-Dateien ab. Diese werden damit überschrieben.</p>

<hr>

# Anleitung zum Ausführen des Programms mit IntelliJ
<ol>
  <p>
<li>) OpenJFX-SDK auf <a href="https://gluonhq.com/products/javafx/">https://gluonhq.com/products/javafx/</a> runterladen und auf Laufwerk C: entpacken </li>
 </p>
  <p>
<li>) Normales Java Projekt erstellen </li>
 </p>
   <p>
<li>) Erstellen Sie eine Bibliothek <br>
Gehen Sie zu File -> Project Structure -> Libraries und fügen Sie das 
SDK als Java-Bibliothek zu Ihrem Projekt hinzu. Zeigen Sie 
auf den Ordner lib.</li> 
</p>
   <p>
<li>) VM-Optionen hinzufügen <br>
Die Programmausführung wirft Fehler. Um das Problem zu lösen, klicken Sie 
mit rechts auf die Main.java -> Modify Run Configurations -> Modify 
options. Fügen Sie folgendes den VM-Optionen hinzu:<br>

--module-path "\path\to\javafx-sdk-xx\lib" --add-modules 
javafx.controls,javafx.fxml <br>

Ersetzen Sie „path/to/…“ durch den korrekten Pfad zur fx-Library und 
zeigen sie auf den lib-Ordner. Beispiel: „C:\javafx-sdk-22\lib“ </li>
</p>
</ol>
