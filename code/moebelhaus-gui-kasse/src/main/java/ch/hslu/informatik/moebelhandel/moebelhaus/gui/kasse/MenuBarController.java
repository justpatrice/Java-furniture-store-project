package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

/**
 * MenuBarController ist die obere Leiste der Applikation, welche zur Navigation dient
 * Hier sind hautsaechlich Navigationselemente zu anderen Views
 */
public class MenuBarController implements Initializable {
	
	@FXML
	private Label lblBenutzer;
	
	@FXML
	private Label lblZeit;
	
	@FXML
	
	private Label lblDatum;
	
	
	/**
	 * Oeffnet das VerkaufenView
	 */
	@FXML
	public void verkauf(){
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/VerkaufenView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }		
	}
	
	/**
	 * Oeffnet das RuecknahmeView
	 */
	@FXML
	public void produktruecknahme(){
		try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/RuecknahmeView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }
	}
	
	/**
	 * Oeffnet das TagesabschlussView
	 */
	@FXML
	public void tagesabschluss(){
		try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/TagesabschlussView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }
	}
	
	/**
	 * Den Benutzer abmelden. Zuers wird ein Dialog angezeigt fuer die Nachfrage
	 */
	@FXML
	public void abmelden(){
		try {
			// Erstelle Alert-Dialog fuer die Nachfrage
			Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle("Abmelden");
	        alert.setHeaderText(null);
	        alert.setContentText("Wollen Sie sich wirklich abmelden?\nNicht gespeicherte Änderungen gehen verloren.");
	        Optional<ButtonType> result = alert.showAndWait();
	        if (result.get() == ButtonType.OK){
	        	AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/LoginView.fxml"));
	            Context.getInstance().getMainRoot().setCenter(root);
	            Context.getInstance().getMainRoot().setTop(null);
	        } else {
	            alert.close();
	        }			

        } catch (IOException e) {
        	// IOException durch FXMLLoader moeglich
            throw new RuntimeException();
        }
	}
	

	/**
	 * Setzt den Benutzername und initialisiert einen Thread, um die Zeit zu aktualisieren
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		// Weitere initialisierungsarbeiten, falls nötig
		
		lblBenutzer.setText(Context.getInstance().getBenutzer().getVorname() + " " +Context.getInstance().getBenutzer().getNachname());
		
		// Thread, der die Zeit und Datum aktualisiert
		Thread t = new Thread(new Runnable(){
			@Override
			public void run(){
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
				while (true){
					// Zeit
					final String date = sdf.format(new Date());
					// Datum
					final String date2 = sdf2.format(new Date());
					
					// Um Thread uebergreifend zu sein, lassen wir ein Runnable auf dem anderen Thread laufen
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							lblZeit.setText(date);
							lblDatum.setText(date2);
							}
					});
					try {
						Thread.sleep(100);
						} catch (InterruptedException ex) {
							break;
						}
				}
			}
		});
		
		// Thread starten, als demon setzen, damit er zusammen mit dem Programm beendet
		t.setName("Runnable Time Updater");
		t.setDaemon(true);
		t.start();		
	}



}
