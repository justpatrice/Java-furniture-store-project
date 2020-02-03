package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper.BestellungPositionWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.BestellungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class LieferungAnnehmenViewController implements Initializable {

    private static Logger logger = LogManager.getLogger(LieferungAnnehmenViewController.class);

    public static final String ERROR_MESSAGE_EINGABE_FUER_NUMMER_FALSCH = "Eingabe für die Bestellung-Nummer ist nicht korrekt.";
    public static final String ERROR_MESSAGE_FEHLER_BEI_BESTELLUNG_ANZEIGE = "Bestellung konnte nicht angezeigt werden";
    public static final String MESSAGE_BESTELLUNG_NICHT_GEFUNDEN = "Keine Bestellung für die eingegebene  Bestellung-Nummer gefunden.";

    @FXML
    private Label lblError;

    @FXML
    private Label lblBestellungDetails;

    @FXML
    private TextField txtBestellungNummer;

    @FXML
    private TextField txtProduktTypCode;

    @FXML
    private TextField txtAnzahl;

    @FXML
    private TableView<BestellungPositionWrapper> tblBestellung;

    @FXML
    private TableColumn<BestellungPositionWrapper, Integer> colNummer;

    @FXML
    private TableColumn<BestellungPositionWrapper, String> colLieferant;

    @FXML
    private TableColumn<BestellungPositionWrapper, String> colProduktname;

    @FXML
    private TableColumn<BestellungPositionWrapper, String> colProduktCode;

    @FXML
    private TableColumn<BestellungPositionWrapper, Integer> colAnzahl;

    @FXML
    private Button btnEinlagern;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblError.setText("");
        lblBestellungDetails.setAlignment(Pos.BASELINE_RIGHT);

        /* tblBestellung konfigurieren */
        colNummer.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, Integer>("nummer"));
        colLieferant.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, String>("lieferant"));
        colProduktname.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, String>("produktname"));
        colProduktCode.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, String>("produktCode"));
        colAnzahl.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, Integer>("anzahl"));

        tblBestellung.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<BestellungPositionWrapper>() {

                    @Override
                    public void changed(ObservableValue<? extends BestellungPositionWrapper> observable,
                            BestellungPositionWrapper oldValue, BestellungPositionWrapper newValue) {

                        if (newValue != null) {
                            updateView();
                        }
                    }

                });

        btnEinlagern.disableProperty()
                .bind(Bindings.size(tblBestellung.getSelectionModel().getSelectedItems()).isEqualTo(0));

    }

    @FXML
    private void einlagern() {

        try {
            lblError.setText("");

            int anzahl = 0;

            try {

                anzahl = Integer.parseInt(txtAnzahl.getText());
                ProduktTyp pTyp = tblBestellung.getSelectionModel().getSelectedItem().getProduktTyp();
                Context.getInstance().getMoebelhausLagerService().produktEinlagern(pTyp, anzahl);

                /* BestellungPosition aus der Tabelle entferenen */
                tblBestellung.getItems().remove(tblBestellung.getSelectionModel().getSelectedItem());

                reset();

            } catch (NumberFormatException e) {
                lblError.setText(ERROR_MESSAGE_EINGABE_FUER_NUMMER_FALSCH);
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @FXML
    private void bestellungAnzeigen() {

        reset();

        /* Angezeigte BestellungPositionen aus der Tabelle entferenen */
        tblBestellung.getItems().clear();

        long id = 0;

        String strNr = txtBestellungNummer.getText();

        if (isValid(strNr)) {

            try {
                id = Long.parseLong(strNr);

                /* Bestellung holen */
                Bestellung bestellung = Context.getInstance().getMoebelhausLagerService().findeBestellungById(id);

                if (bestellung == null) {
                    lblError.setText(MESSAGE_BESTELLUNG_NICHT_GEFUNDEN);
                    return;
                }

                /* Bestellung-Nr. und Datum anzeigen */
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy 'um' HH:mm:ss");
                lblBestellungDetails.setText("Bestellung Nr. " + bestellung.getId() + " vom "
                        + sdf.format(new Date(bestellung.getDatum().getTimeInMillis())));

                List<BestellungPositionWrapper> wrapperListe = new ArrayList<>();

                int nummer = 1;

                for (BestellungPosition bPosition : bestellung.getBestellungPositionListe()) {
                    wrapperListe.add(new BestellungPositionWrapper(nummer++, bPosition));
                }

                tblBestellung.getItems().clear();
                tblBestellung.getItems().addAll(wrapperListe);

            } catch (NumberFormatException e) {
                lblError.setText(ERROR_MESSAGE_EINGABE_FUER_NUMMER_FALSCH);
                return;
            } catch (Exception e) {
                logger.error("Fehler bei der Suche nach der Bestellung: ", e);
                lblError.setText(ERROR_MESSAGE_FEHLER_BEI_BESTELLUNG_ANZEIGE);
                return;
            }

        }
    }

    private boolean isValid(String str) {
        return str != null && str.trim().length() > 0;
    }

    private void updateView() {

        BestellungPositionWrapper wrapper = tblBestellung.getSelectionModel().getSelectedItem();

        if (wrapper != null) {
            BestellungPosition p = wrapper.getBestellungPosition();
            txtProduktTypCode.setText(p.getProduktTyp().getTypCode());
            txtAnzahl.setText("" + p.getAnzahl());
        }
    }

    @FXML
    private void reset() {
        lblError.setText("");
        lblBestellungDetails.setText("");
        txtProduktTypCode.setText("");
        txtAnzahl.setText("0");
        tblBestellung.getSelectionModel().clearSelection();
    }
}
