package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse;

import java.net.URL;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktRuecknahme;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RechnungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.pdfprinter.PdfPrinterImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RuecknahmeViewController implements Initializable 
{	
	@FXML
	private Label Datum;
	private GregorianCalendar datum;
	
	@FXML
    private DatePicker txtDatum;

    @FXML
    private TextField txtRechnung;
    private long lRechnungNummer;
    private String sRechnungNummer;
    private Rechnung rechnung;
        
    @FXML
    private ComboBox<String> cbProduktTypCode = new ComboBox<String>();
    private ObservableList<String> produktTypCodeList = FXCollections.observableArrayList();

    private String sProduktTypCode;
    private ProduktTyp produktTyp;
    
    @FXML
    private Label lblPreisProStueck;
    private double dPreisProStueck;
    
    @FXML
    private Label lblAnzahl;
    private int iAnzahl;
    
    @FXML
    private Label lblBetrag;
    private double dBetrag;
    
    @FXML
    private TextField txtBemerkung;
    private String sBemerkung;
    
    @FXML
    private Button btnBestaetigen = new Button();
    
    private Alert meldung;
    private String sMeldung;
    
    private ProduktRuecknahme prodRuecknahme;
    
    
    private static Logger logger = LogManager.getLogger(RuecknahmeViewController.class);
    
    //Rücknahme Bestätigen alle
    
    @FXML
    public void ruecknahmeBestaetigen()
    {
    	datum = new GregorianCalendar();

    	if(txtBemerkung.getText() != null)
    	{
            sBemerkung = txtBemerkung.getText();
    	}
    	else
    	{
    		sBemerkung = "";
    	}
    	
    	prodRuecknahme = new ProduktRuecknahme(rechnung, produktTyp, datum, iAnzahl, sBemerkung);
    	
    	if(validateEntries() == true)
    	{
    		zuruecknehmen();
    	}
        else
    	{
    		printWarnings();
    	}    	
    }


    //Werte in allen Feldern zurücksetzten
    
	public void setToDefaults()
	{
    	txtDatum.setValue(LocalDate.now());
    	txtRechnung.setText("");
    	lblPreisProStueck.setText("0.00");
    	lblAnzahl.setText("0");
    	lblBetrag.setText("0.00");
    	txtBemerkung.setText("");
    	btnBestaetigen.setDisable(true);    	
    	dPreisProStueck = 0.0;
    	iAnzahl = 0;
    	dBetrag = 0;
    	sBemerkung = null;
    	sMeldung = "";
    	cbProduktTypCode.getItems().clear();
    	produktTypCodeList.clear();
	}
	
	//Eingetragene Werte in den Feldern Überprüfen und Warnmeldung ausgeben
	
	public boolean validateEntries()
	{
		if(datum == null || txtDatum == null)
    	{
    		sMeldung += "- Datum\n";
    	}
		if(rechnung == null)
    	{
    		sMeldung += "- Rechnungsnummer\n";
    	}
		if(sProduktTypCode == null)
    	{
    		sMeldung += "- ProduktTyp\n";
    	}
		if(dPreisProStueck == 0.00)
    	{
    		sMeldung += "- Preis pro Stück\n";
      	}
		if(iAnzahl == 0)
    	{
    		sMeldung += "- Preis pro Stück\n";
    	}
		if(dBetrag == 0)
    	{
    		sMeldung += "- Betrag\n";
    	}
		if(sBemerkung.length() <= 0)
		{
			sMeldung += "- Bemerkung\n";
		}
		
		if(sMeldung == null)
		{
			return false;
		}
		
		return true;
	}
	
	//Überprüfung ob Bemerkung eingegeben
	
	public void zuruecknehmen()
	{
		if(sBemerkung.length() <= 0)
		{
			meldung = new Alert(AlertType.CONFIRMATION);
			meldung.setTitle("Bestätigung");
			meldung.setHeaderText(null);
			meldung.setContentText("Sie haben keine Bemerkung eingegeben. Wollen Sie fortfahren?");
			Optional<ButtonType> result = meldung.showAndWait();
			
			if (result.get() == ButtonType.OK)
			{
				try
	        	{
	        		Context.getInstance().getKasseService().produktZuruecknehmen(prodRuecknahme);
	        		PdfPrinterImpl pdf = new PdfPrinterImpl();
	        		pdf.printRuecknahmeAlsPdf(prodRuecknahme);
	        		
	        		Alert alert = new Alert(AlertType.INFORMATION);
	                alert.setTitle("Rücknahme drucken");
	                alert.setHeaderText(null);
	                alert.setContentText("Die Rücknahme wurde gespeichert unter C:/Temp.");
	                alert.showAndWait();
	        	}
	        	catch (Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        
	        	setToDefaults();
			} 
			else 
			{
			    return;
			}
		}
		else
		{
			try
        	{
        		Context.getInstance().getKasseService().produktZuruecknehmen(prodRuecknahme);
        		PdfPrinterImpl pdf = new PdfPrinterImpl();
        		pdf.printRuecknahmeAlsPdf(prodRuecknahme);
        		
        		Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Rücknahme drucken");
                alert.setHeaderText(null);
                alert.setContentText("Die Rücknahme wurde gespeichert unter C:/Temp.");
                alert.showAndWait();
        	}
        	catch (Exception e)
        	{
        		e.printStackTrace();
        	}
			
			setToDefaults();
		}
	}
	
	// Warnungsmeldung
	
	public void printWarnings()
	{
		meldung = new Alert(AlertType.ERROR);
		meldung.setHeaderText("Folgende Felder sind Pflichtfelder und wurde nicht ausgefüllt: ");
		meldung.setContentText(sMeldung);
		meldung.show();
	}

	//LINO??
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		setToDefaults();
		
		//Listener for txtRechnung input
		txtRechnung.textProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String newValue, String oldValue) 
			{
				if (!txtRechnung.getText().isEmpty())
				{
					sRechnungNummer = txtRechnung.getText().trim();
				}				
				
				if(sRechnungNummer != null && sRechnungNummer != "" && sRechnungNummer.length() >= 4)
		    	{
	        		lRechnungNummer = Long.parseLong(sRechnungNummer);
	        	
	        		try 
		    		{
		    			rechnung = Context.getInstance().getKasseService().findByRechnungNummer(lRechnungNummer);
		    			
	    				for(RechnungPosition rp : rechnung.getRechnungPositionListe())
		        		{
		        			produktTypCodeList.add(rp.getProduktCode());
			        		cbProduktTypCode.getItems().add(rp.getProduktCode());
		        		}
		    		}
		    		catch (Exception e)
		    		{
		    			if (rechnung == null)
		    			{
		    				btnBestaetigen.setDisable(true);
		    				meldung = new Alert(AlertType.ERROR);
		    				meldung.setHeaderText("Field value not acceptable");
		    				meldung.setContentText("Keine Rechnung unter der Nummer " + sRechnungNummer + " gefunden.");
		    				meldung.show();
		    			}
		    			logger.error("Keine Rechnung gefunden: ", e);
		    			e.printStackTrace();
		    		}
	        		
	        		
		    	}		        	
		        else
		        {
		        	btnBestaetigen.setDisable(true);
		        	cbProduktTypCode.getItems().clear();
		        	produktTypCodeList.clear();

		        }
			}
		});
		
		//Listener for Dropdown-Selection of ProduktTypCode
		cbProduktTypCode.valueProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String newValue, String oldValue)
			{
				if (rechnung != null && rechnung.getRechnungPositionListe().size() != 0)
				{
					dPreisProStueck = rechnung.getRechnungPositionListe().get(cbProduktTypCode.getSelectionModel().getSelectedIndex()).getPreis();
					lblPreisProStueck.setText(String.valueOf(dPreisProStueck));
					
					iAnzahl = rechnung.getRechnungPositionListe().get(cbProduktTypCode.getSelectionModel().getSelectedIndex()).getAnzahl();
					lblAnzahl.setText(String.valueOf(iAnzahl));
					
					dBetrag = dPreisProStueck * iAnzahl;
					lblBetrag.setText(String.valueOf(dBetrag));
					
					produktTyp = rechnung.getRechnungPositionListe().get(cbProduktTypCode.getSelectionModel().getSelectedIndex()).getProduktTyp();
					
					btnBestaetigen.setDisable(false);
				}
			}
		});
	}
	
	
}
