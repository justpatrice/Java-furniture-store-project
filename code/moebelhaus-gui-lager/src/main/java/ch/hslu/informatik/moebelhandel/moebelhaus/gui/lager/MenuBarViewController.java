package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MenuBarViewController implements Initializable {

    private static final String TITEL_BENUTZER_VERWALTEN = "Benutzer";
    private static final String TITEL_LIEFERANTEN_VERWALTEN = "Lieferanten";
    private static final String TITEL_REGALE_TABLARE_VERWALTEN = "Regale und Tablare";
    private static final String TITEL_LAGERBESTAND = "Lagerbestand";
    private static final String TITEL_BESTELLUNG_ERFASSEN = "Bestellung erfassen";
    private static final String TITEL_BESTELLUNGEN_ANSCHAUEN = "Bestellungen anschauen";
    private static final String TITEL_LIEFERUNG_ANNEHMEN = "Lieferung annehmen";
    private static final String TITEL_PRODUKT_ZURUECKSENDEN = "Produkt zur\u00fccksenden";
    private static final String TITEL_PRODUKT_TYP_VERWALTEN = "Produkt-Typ verwalten";

    @FXML
    private Button mBtnVerkauf;

    @FXML
    private Button mBtnProduktruecknahme;

    @FXML
    private Button mBtnTagesabschluss;

    @FXML
    private Button mBtnAbmeldung;

    @FXML
    private Label lblBenutzer;

    @FXML
    private Label lblTitel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String str = Context.getInstance().getBenutzer().getVorname() + " "
                + Context.getInstance().getBenutzer().getNachname();
        lblBenutzer.setText("Angemeldet: " + str);
        lblBenutzer.setAlignment(Pos.BASELINE_RIGHT);

        Context.getInstance().setMenuBarViewController(this);
    }

    @FXML
    public void home() {

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/LagerFilialeHomeView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @FXML
    public void lieferantenVerwalten() {

        lblTitel.setText(TITEL_LIEFERANTEN_VERWALTEN);

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/LieferantenVerwaltenView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @FXML
    public void regaleVerwalten() {

        lblTitel.setText(TITEL_REGALE_TABLARE_VERWALTEN);

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/RegaleVerwaltenView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    @FXML
    public void lagerbestandAbfragen() {

        lblTitel.setText(TITEL_LAGERBESTAND);

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/LagerbestandAbfragenView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    @FXML
    public void bestellungErfassen() {

        lblTitel.setText(TITEL_BESTELLUNG_ERFASSEN);

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/BestellungErfassenView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    @FXML
    public void bestellungAnschauen() {

        lblTitel.setText(TITEL_BESTELLUNGEN_ANSCHAUEN);

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/BestellungenView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    @FXML
    public void lieferungAnnehmen() {

        lblTitel.setText(TITEL_LIEFERUNG_ANNEHMEN);

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/LieferungAnnehmenView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    @FXML
    public void produktZuruecksenden() {

        lblTitel.setText(TITEL_PRODUKT_ZURUECKSENDEN);

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/ProduktRuecksendungView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    @FXML
    public void produktTypVerwalten() {

        lblTitel.setText(TITEL_PRODUKT_TYP_VERWALTEN);

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/ProduktTypVerwaltenView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    @FXML
    public void benutzerVerwalten() {

        lblTitel.setText(TITEL_BENUTZER_VERWALTEN);

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/BenutzerVerwaltenView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    @FXML
    public void abmelden() {

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/LoginView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);
            Context.getInstance().getMainRoot().setTop(null);

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

}
