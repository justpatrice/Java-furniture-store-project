package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.wrapper.RechnungPositionWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RechnungPosition;
import javafx.beans.binding.Bindings;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller fuer das VerkaufenView. Wird vom User benutzt, um Items zu verkaufen und Rechnungen zu erstellen
 *
 */
public class VerkaufenViewController implements Initializable {
	
	private static Logger logger = LogManager.getLogger(VerkaufenViewController.class);
	
	@FXML
	private TextField txtProduktCode;
	
	@FXML
	private Label lblSumme;
	
	@FXML
	private Label lblError;
	
	@FXML
	private TableView<RechnungPositionWrapper> tblRechnung;
	
	@FXML
    private TableColumn<RechnungPositionWrapper, Integer> clmNummer;
	
	@FXML
    private TableColumn<RechnungPositionWrapper, String> clmProduktDetails;
	
	@FXML
    private TableColumn<RechnungPositionWrapper, Double> clmPreis;
	
	@FXML
	private Button btnDelete;
	
	@FXML
	private Button btnAbschliessen;
	
	@FXML
	private Button btnAbbrechen;
	
	/**
	 * Einen im txtProduktCode uebernehmen und in die Liste eintragen. Den User informieren, falls der Code zweimal benutzt wurde
	 * Aktualisiert ebenfalls die Summe (dh. Summe allter Produkte in der Liste)
	 */
	@FXML
	private void uebernehmen(){
		
		lblError.setText("");

        if (eingabeValid()) {

            /* Prüfen, ob der eingegebene ProduktTypCode korrekt ist */
            try {
                Produkt p = Context.getInstance().getKasseService().findByProduktCode(Long.parseLong(txtProduktCode.getText().trim()));

                if (p != null) {
                    
                	String itemId = txtProduktCode.getText();
            		boolean result = Context.getInstance().setTabellenItem(itemId);
            		lblError.setText("");
                    txtProduktCode.setText("");
            		summe();
            		
            		// Code darf man nur zweimal brauchen
            		if(result == false)
            		{
            			lblError.setText("Code darf nicht zweimal benutzt werden");
            		}
                	
                } else {
                    lblError.setText("Produkttyp ist nicht korrekt.");
                    return;
                }
            } catch (Exception e) {
            	lblError.setText("Produkttyp ist nicht korrekt.");
                return;
            }
        } else {
        	lblError.setText("Produkttyp ist nicht korrekt.");
            return;
        }
				
		
	}
	
	/**
	 * Oeffnet den ScannerView, um einen Produktcode zu holen
	 */
	@FXML
	private void produktCodeHolen(){
		Stage stage = new Stage();
		AnchorPane scannerRoot = new AnchorPane();
		try {
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Produkt-Code holen");
			Context.getInstance().getContentMap().put("txtProduktCode", txtProduktCode);
			scannerRoot = FXMLLoader.load(getClass().getResource("/fxml/ScannerView.fxml"));
			Scene scene = new Scene(scannerRoot, 660, 300);
			
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
			
		} catch (IOException e) {
			throw new RuntimeException();
		}	
	}
	
	/**
	 * Eine oder mehrere Position in der Tabelle loeschen, welche selektiert sind
	 * Aktualisert ebenfalls die Summe (dh. Summe aller Produkte in der Liste)
	 */
	@FXML
	private void positionLoeschen(){
		
		tblRechnung.getSelectionModel().getSelectedItems();
		for(RechnungPositionWrapper item : tblRechnung.getSelectionModel().getSelectedItems()){
			Context.getInstance().removeTabellenItem(item);
		}
		Context.getInstance().updateIndex();
		summe();
	}
	
	/**
	 * Abbrechen, dh. alle Einträge/Produkte in der Liste/Tabelle loeschen
	 */
	@FXML
	private void abbrechen(){
		
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle("Abbrechen");
	        alert.setHeaderText(null);
	        alert.setContentText("Wollen Sie wirklich abbrechen?\nAlle Änderungen werden gelöscht.");
	        Optional<ButtonType> result = alert.showAndWait();
	        if (result.get() == ButtonType.OK){
	        	txtProduktCode.setText("");
	    		lblError.setText("");
	    		lblSumme.setText("0.0");
	    		Context.getInstance().clearTeabellenItems();
	        } else {
	            alert.close();
	        }			

        } catch (Exception e) {
            throw new RuntimeException();
        }
	}
	
	/**
	 * Abschliessen, eine Rechnung erstellen mit den Produkten in der Tabelle
	 * Die Rechnung wird zudem als PDF gespeichert 
	 * Der Benutzer wird via Dialog darueber informiert
	 */
	@FXML
	private void abschliessen(){
		
		Benutzer b = Context.getInstance().getBenutzer();
		Rechnung rechnung = new Rechnung();
		
		//Braucht es glaub nicht mehr
		/*Rechnung rechnung = new Rechnung();
		rechnung.setBenutzer(b);
		rechnung.setZeit(new GregorianCalendar());
		
		Integer rechnungsId = (Integer)Context.getInstance().getContentMap().get("rechnung_abschliessen_id");
		if(rechnungsId == null)
		{
		rechnungsId = new Integer (0);
		}
		int val = rechnungsId.intValue()+ 1;
		rechnung.setId(val);
		Context.getInstance().getContentMap().put("rechnung_abschliessen_id", new Integer(val));*/
		
		
		List<RechnungPosition> rechnungsPositionen = new ArrayList<>();
		List<Produkt> produktliste = new ArrayList<>();
		
		try {
			for(RechnungPositionWrapper item : Context.getInstance().getTabelleListe())
			{
				rechnungsPositionen.add(item.getRechnungPosition());
				produktliste.add(Context.getInstance().getKasseService().findByProduktCode(item.getProduktCode()));
			}
				
		//Rechung speichern
		rechnung = Context.getInstance().getKasseService().verkaufen(produktliste, b);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Rechnung drucken
		rechnung.setRechnungPositionListe(rechnungsPositionen);
		try {
			Context.getInstance().getPdfPrinter().printRechnungAlsPdf(rechnung);
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Rechnung abgeschlossen");
        alert.setHeaderText("Rechnung abgeschlossen");
        alert.setContentText("Die Rechnung wurde gespeichert unter C:/Temp.");
        alert.showAndWait();
		
        reset();
	}

	/**
	 * Initialisiert die Tabelle
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
            clmNummer.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, Integer>("nummer")); // ruft getNummer auf
            clmProduktDetails.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, String>("ProduktCodeUndBeschreibung")); //getProduktTypName
            clmPreis.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, Double>("produktPreis")); //getProduktPreis
            tblRechnung.setItems(Context.getInstance().getTabelleListe());
            btnDelete.disableProperty().bind(Bindings.size(tblRechnung.getSelectionModel().getSelectedItems()).isEqualTo(0));
            btnAbschliessen.disableProperty().bind(Bindings.size(tblRechnung.getItems()).isEqualTo(0));
            
        } catch (Exception e) {
            logger.error("Fehler bei der Initialisierung der View: ", e);
            throw new RuntimeException(e);
        }
	}
	
	/**
	 * Schaut, ob die Eingabe vom ProduktCode korrekt ist
	 * @return True, wenn alles ok, false sonst
	 */
	private boolean eingabeValid() {
        boolean eingabeKorrekt = isValid(txtProduktCode.getText());
        return eingabeKorrekt;
    }
	
	/**
	 * Schaut die Länge vom String an, wenn man die Whitespaces ignoriert
	 * @param str Der String, den man anschaut
	 * @return True, wenn laenger als 1 (ohne Whitespaces), False sonst
	 */
    private boolean isValid(String str) {
        return str != null && str.trim().length() > 0;
    }
    
    private void reset() {
    	lblError.setText("");
        txtProduktCode.setText("");
        lblSumme.setText("0.0");
        Context.getInstance().clearTeabellenItems();
        
    }
    
    /**
     * Summe aller Produkte. Der Betrag wird beim Label lblSumme gesetzt
     */
    private void summe(){
    	double summand = 0;
    	double summe = 0;
    	Iterator<RechnungPositionWrapper> it = Context.getInstance().getTabelleListe().iterator();
    	while (it.hasNext()){
    		
    		summand = it.next().getProduktPreis();
    		summe += summand;
    		lblSumme.setText(Double.toString(summe));
    	}
    	
    }
    	
}
