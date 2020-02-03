package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager;

import java.net.URL;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper.RuecksendungPositionWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Ruecksendung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RuecksendungPosition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProduktRuecksendungViewController implements Initializable {

    private static Logger logger = LogManager.getLogger(ProduktRuecksendungViewController.class);

    private static final String ERROR_MESSAGE_EINGABE_NICHT_KORREKT = "Eingabe nicht korrekt.";
    private static final String ERROR_MESSAGE_PRODUKT_TYP_CODE_NICHT_KORREKT = "Der eingegebene ProduktTyp-Code ist nicht korrekt bzw. kann nicht gefunden werden.";

    @FXML
    private Label lblError;

    @FXML
    private TextField txtProduktCode;

    @FXML
    private TextField txtAnzahl;

    @FXML
    private TextField txtBemerkung;

    @FXML
    private Button btnLoeschen;

    @FXML
    private TableView<RuecksendungPositionWrapper> tblRuecksendung;

    @FXML
    private TableColumn<RuecksendungPositionWrapper, Integer> colNummer;

    @FXML
    private TableColumn<RuecksendungPositionWrapper, String> colProduktCode;

    @FXML
    private TableColumn<RuecksendungPositionWrapper, Integer> colAnzahl;

    @FXML
    private TableColumn<RuecksendungPositionWrapper, String> colBemerkung;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblError.setText("");

        /* Tabelle configurieren */
        colNummer.setCellValueFactory(new PropertyValueFactory<RuecksendungPositionWrapper, Integer>("nummer"));
        colProduktCode
                .setCellValueFactory(new PropertyValueFactory<RuecksendungPositionWrapper, String>("produktTypCode"));
        colBemerkung.setCellValueFactory(new PropertyValueFactory<RuecksendungPositionWrapper, String>("bemerkung"));
        colAnzahl.setCellValueFactory(new PropertyValueFactory<RuecksendungPositionWrapper, Integer>("anzahl"));

        btnLoeschen.disableProperty()
                .bind(Bindings.size(tblRuecksendung.getSelectionModel().getSelectedItems()).isEqualTo(0));
    }

    @FXML
    private void reset() {
        lblError.setText("");
        txtProduktCode.setText("");
        txtAnzahl.setText("");
        txtBemerkung.setText("");

        txtProduktCode.requestFocus();
    }

    @FXML
    private void ruecksendungPositionHinzufuegen() {

        lblError.setText("");

        if (eingabeValid()) {

            /* Prüfen, ob der eingegebene ProduktTypCode korrekt ist */
            try {
                ProduktTyp pTyp = Context.getInstance().getMoebelhausLagerService()
                        .findeByProduktTypCode(txtProduktCode.getText().trim());

                if (pTyp != null) {
                    String bemerkung = txtBemerkung.getText();
                    int anzahl = Integer.parseInt(txtAnzahl.getText().trim());

                    int nummer = tblRuecksendung.getItems().size() + 1;

                    RuecksendungPositionWrapper wrapper = new RuecksendungPositionWrapper(nummer,
                            new RuecksendungPosition(pTyp, bemerkung, anzahl));

                    tblRuecksendung.getItems().add(wrapper);

                    reset();
                } else {
                    lblError.setText(ERROR_MESSAGE_PRODUKT_TYP_CODE_NICHT_KORREKT);
                    return;
                }
            } catch (Exception e) {
                lblError.setText(ERROR_MESSAGE_PRODUKT_TYP_CODE_NICHT_KORREKT);
                return;
            }
        } else {
            lblError.setText(ERROR_MESSAGE_EINGABE_NICHT_KORREKT);
            return;
        }
    }

    @FXML
    private void ruecksendungPositionLoeschen() {

        lblError.setText("");

        RuecksendungPositionWrapper item = tblRuecksendung.getSelectionModel().getSelectedItem();

        if (item != null) {
            tblRuecksendung.getItems().remove(item);
            reset();
        }
    }

    @FXML
    private void abbrechen() {
        tblRuecksendung.getItems().clear();
        reset();
    }

    @FXML
    private void ruecksendungGenerieren() {

        List<RuecksendungPositionWrapper> liste = tblRuecksendung.getItems();

        Ruecksendung ruecksendung = new Ruecksendung(new GregorianCalendar(), Context.getInstance().getMoebelhaus());

        for (RuecksendungPositionWrapper wrapper : liste) {
            ruecksendung.addRuecksendungPosition(wrapper.getRuecksendungPosition());
        }

        /* Ruecksendung speichern */
        try {
            Context.getInstance().getMoebelhausLagerService().ruecksendungHinzufuegen(ruecksendung);
        } catch (Exception e) {
            logger.error("Fehler beim Speichern der Ruecksendung: ", e);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Ruecksendung speichern");
            alert.setHeaderText("Information");
            alert.setContentText("Das Generierung der Rücksendung ist misslungen.");
            alert.showAndWait();

            return;
        }

        try {
            Context.getInstance().getPdfPrinter().printRuecksendungAlsPdf(ruecksendung);
        } catch (Exception e) {
            logger.error("Fehler bei der Generierung der Ruecksendung als PDF: ", e);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Ruecksendung als PDF");
            alert.setHeaderText("Information");
            alert.setContentText("Das Generierung der Rücksendung als PDF ist misslungen.");
            alert.showAndWait();

            return;
        }

        /* Info */
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Rücksendung speichern");
        alert.setHeaderText("Information");
        alert.setContentText("Die Rücksendung wurde erfolgreich generiert.");
        alert.showAndWait();

        reset();
    }

    private boolean eingabeValid() {

        boolean eingabeKorrekt = isValid(txtProduktCode.getText()) && isValid(txtAnzahl.getText())
                && isValid(txtBemerkung.getText());

        /* Eingabe für Anzahl prüfen */
        try {
            Integer.parseInt(txtAnzahl.getText());
        } catch (NumberFormatException e) {
            eingabeKorrekt = false;
        }

        return eingabeKorrekt;
    }

    private boolean isValid(String str) {
        return str != null && str.trim().length() > 0;
    }

}
