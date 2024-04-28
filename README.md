# GarageFX - Eine einfache JavaFX-Anwendung zum Erstellen und Verwalten eines Parkhauses

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
