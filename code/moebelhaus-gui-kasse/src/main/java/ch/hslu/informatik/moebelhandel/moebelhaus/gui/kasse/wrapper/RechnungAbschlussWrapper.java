package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.wrapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;

/**
 * Wrapper fuer den Rechnungsabschluss, die Rechnungen.
 * Wird im TagesabschlussViewController verwendet, um die Rechnungen anzuzeigen
 * Ein RechnungAbschlussWrapper entspricht dabei einer Zeile
 */
public class RechnungAbschlussWrapper {
	
	long rechnungsId;
	Benutzer benutzer;
	GregorianCalendar datum;
	double betrag;
	
	
	public RechnungAbschlussWrapper(long rechnungsId, Benutzer benutzer, GregorianCalendar datum, double betrag)
	{
		this.rechnungsId = rechnungsId;
		this.benutzer = benutzer;
		this.datum = datum;
		this.betrag = betrag;
	}


	public long getRechnungsId() {
		return rechnungsId;
	}


	public void setRechnungsId(long rechnungsId) {
		this.rechnungsId = rechnungsId;
	}


	public Benutzer getBenutzer() {
		return benutzer;
	}
	
	public String getBenutzername(){
		return benutzer.getVorname() + " " + benutzer.getNachname();
	}


	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}


	public GregorianCalendar getDatum() {
		return datum;
	}


	public void setDatum(GregorianCalendar datum) {
		this.datum = datum;
	}
	
	public String getDatumstring(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		Date d = this.datum.getTime();
		return sdf.format(d);
	}


	public double getBetrag() {
		return betrag;
	}


	public void setBetrag(double betrag) {
		this.betrag = betrag;
	}
	

}
