package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.Context;
import ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.wrapper.RechnungAbschlussWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


/**
 * Controller fuer den Tagesabschluss. Der User kann ein Datum wählen, und sieht eine Liste aller Rechnungen, die an diesem Tag
 * erstellt wurden
 * Des weiteren kann der User den Tagesabschluss ausdrucken
 */
public class TagesabschlussViewController implements Initializable{

	private static Logger logger = LogManager.getLogger(TagesabschlussViewController.class);

	@FXML
	private Label lblDatum;

	@FXML
	private DatePicker txtDatum;

	@FXML
	private Label lblUmsatz;

	@FXML
	private Label lblBetrag;

	@FXML
	private Button btnDrucken;

	@FXML
	private Button btnZurueck;

	@FXML
	private TableView<RechnungAbschlussWrapper> tblAbschluss;


	@FXML
	private TableColumn<RechnungAbschlussWrapper, Long> clmRechnungId;

	@FXML
	private TableColumn<RechnungAbschlussWrapper, String> clmUser;

	@FXML
	private TableColumn<RechnungAbschlussWrapper, String> clmDatum;

	@FXML
	private TableColumn<RechnungAbschlussWrapper, Double> clmBetrag;


	private GregorianCalendar date;

	private double umsatz;

	private ObservableList<RechnungAbschlussWrapper> rechnungen =  FXCollections.observableArrayList();


	/**
	 * Tagesabschluss Drucken
	 * @throws Exception Exception, wenn man die Liste der Rechnungen nicht laden kann
	 */
	@FXML 
	private void drucken() throws Exception{

		Benutzer b = Context.getInstance().getBenutzer();
		List<Rechnung> rechnungen = Context.getInstance().getKasseService().findByBenutzerUndDatum(b, date);

		try {
			Context.getInstance().getPdfPrinter().printAbschlussAlsPdf(rechnungen, b);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Tagesabschluss drucken");
			alert.setHeaderText(null);
			alert.setContentText("Der Tagesabschluss wurde gespeichert unter C:/Temp.");
			alert.showAndWait();
		}
		catch (Exception e) {
			logger.error("Fehler beim Drucken des Tagesabschlusses: ", e);
			throw new RuntimeException(e);
		}

	}

	/**
	 * Gegeben das Datum (und der User via Context), holt die Liste der Rechnungen vom Server
	 * @param date Das Datum
	 */
	private void ladeListeRechnungen(GregorianCalendar date)
	{
		Benutzer b = Context.getInstance().getBenutzer();

		this.rechnungen.clear();

		try {
			List<Rechnung> rechnungen = Context.getInstance().getKasseService().findByBenutzerUndDatum(b, date);
			for(Rechnung r : rechnungen)
			{
				RechnungAbschlussWrapper raw = new RechnungAbschlussWrapper(r.getId(), r.getBenutzer(), r.getZeit(), r.getBetrag());
				this.rechnungen.add(raw);
			}

		} catch (Exception e) {
			logger.error("Fehler beim Laden der Liste: ", e);
			e.printStackTrace();
		}
	}

	/**
	 * Gegeben das Datum, hole den Umsatz vom Server und setze den Betrag
	 * @param date Das Datum, zu welchem man den Umsatz will
	 */
	private void ladeUmsatz(GregorianCalendar date)
	{
		try {
			// Hole den Umsatz von Server
			umsatz = Context.getInstance().getKasseService().getTagesAbrechnung(date);
		} catch (Exception e) {
			logger.error("Fehler beim Laden des Umsatzes: ", e);
			e.printStackTrace();
		}

		lblBetrag.setText(Double.toString(umsatz));
	}


	/**
	 * Initialisieren der Tabelle und des aktuellen Datum
	 * Lade zudem die Liste der Items vom Abschluss und der Umsatz
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		clmRechnungId.setCellValueFactory(new PropertyValueFactory<RechnungAbschlussWrapper, Long>("rechnungsId")); // ruft getNummer auf
		clmUser.setCellValueFactory(new PropertyValueFactory<RechnungAbschlussWrapper, String>("Benutzername")); //getProduktTypName
		clmDatum.setCellValueFactory(new PropertyValueFactory<RechnungAbschlussWrapper, String>("Datumstring")); //getProduktPreis
		clmBetrag.setCellValueFactory(new PropertyValueFactory<RechnungAbschlussWrapper, Double>("Betrag")); //getBetrag
		tblAbschluss.setItems(this.rechnungen);

		date = new GregorianCalendar();
		ladeListeRechnungen(date);
		ladeUmsatz(date);

		// Setze das aktuelle Datum beim Datum-Textfeld und abonieren einen Event-Listener, um zu merken, wann
		// das Datum aendert
		txtDatum.setValue(LocalDate.now());
		txtDatum.valueProperty().addListener((ov, oldValue, newValue) -> {
			LocalDate datum = txtDatum.valueProperty().get();
			date = GregorianCalendar.from(datum.atStartOfDay(ZoneId.systemDefault()));
			ladeListeRechnungen(date);
			ladeUmsatz(date);
		});


	}
	
	
	/**
	 * Zurueck zum Verkaufenview. Es wird zuerst ein Alert-Dialog angezeigt, um den User zu warnen (verhindern von Daten-Verlust)
	 */
	@FXML
	public void zurueck(){
		try {
			Alert alert2 = new Alert(AlertType.CONFIRMATION);
			alert2.setTitle("Zurück");
			alert2.setHeaderText(null);
			alert2.setContentText("Wollen Sie wirklich zum vorherigen Fenster zurückkehren?");
			Optional<ButtonType> result2 = alert2.showAndWait();
			if (result2.get() == ButtonType.OK){
				//AnchorPane menuBar = FXMLLoader.load(getClass().getResource("/fxml/MenuBarView.fxml"));
				AnchorPane root  = FXMLLoader.load(getClass().getResource("/fxml/VerkaufenView.fxml"));
				//Context.getInstance().getMainRoot().setTop(menuBar);
				Context.getInstance().getMainRoot().setCenter(root);
			} else {
				alert2.close();
			}			

		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

}
