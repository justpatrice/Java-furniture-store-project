package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper.BestellungPositionWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.BestellungPosition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

public class BestellungenViewController implements Initializable {

    private static Logger logger = LogManager.getLogger(BestellungenViewController.class);

    @FXML
    private Label lblBestellungDetails;

    @FXML
    private ComboBox<Bestellung> cmbBestellung;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblBestellungDetails.setAlignment(Pos.BASELINE_RIGHT);
        lblBestellungDetails.setText("");

        try {
            List<Bestellung> bestellungListe = Context.getInstance().getMoebelhausLagerService().alleBestellungen();
            cmbBestellung.getItems().addAll(bestellungListe);

            if (cmbBestellung.getItems().size() > 0) {
                cmbBestellung.getSelectionModel().select(0);
                updateTable();
            }

            cmbBestellung.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Bestellung>() {

                @Override
                public void changed(ObservableValue<? extends Bestellung> observable, Bestellung oldValue,
                        Bestellung newValue) {
                    if (newValue != null) {
                        updateTable();
                    }

                }

            });

            cmbBestellung.setConverter(new StringConverter<Bestellung>() {

                @Override
                public String toString(Bestellung bestellung) {
                    if (bestellung == null) {
                        return "";
                    } else {
                        return "" + bestellung.getId();
                    }
                }

                @Override
                public Bestellung fromString(String string) {
                    if (string == null || string.trim().length() == 0) {
                        return null;
                    } else {
                        try {
                            String[] parts = string.split(" ");
                            long id = Long.parseLong(parts[0]);
                            return Context.getInstance().getMoebelhausLagerService().findeBestellungById(id);
                        } catch (Exception e) {
                            logger.error("Fehler bei der Konversion von String zu Objekt: ", e);
                            throw new RuntimeException(e);
                        }
                    }
                }

            });

            /* tblBestellung konfigurieren */
            colNummer.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, Integer>("nummer"));
            colLieferant.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, String>("lieferant"));
            colProduktname
                    .setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, String>("produktname"));
            colProduktCode
                    .setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, String>("produktCode"));
            colAnzahl.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, Integer>("anzahl"));

        } catch (Exception e) {
            logger.error("Fehler bei der Initialisierung der View: ", e);
            throw new RuntimeException(e);
        }

    }

    private void updateTable() {

        Bestellung bestellung = cmbBestellung.getSelectionModel().getSelectedItem();

        if (bestellung != null) {

            List<BestellungPosition> liste = bestellung.getBestellungPositionListe();
            List<BestellungPositionWrapper> wrapperListe = new ArrayList<>();

            int nummer = 1;

            for (BestellungPosition bPos : liste) {
                wrapperListe.add(new BestellungPositionWrapper(nummer++, bPos));
            }

            tblBestellung.getItems().clear();
            tblBestellung.getItems().addAll(wrapperListe);

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy 'um' HH:mm:ss");

            lblBestellungDetails.setText("Bestellungsdatum: " + sdf.format(bestellung.getDatum().getTime()));

        }

    }

}
