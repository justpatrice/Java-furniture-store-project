package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.wrapper;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.RechnungPosition;

/**
 * Wrapper fuer eine RechnugnsPosition, wird im VerkaufenViewController verwendet, um die einzelnen Produkte anzuzeigen
 * Ein RechnungPositionWrapper entspricht dabei einer Zeile
 */
public class RechnungPositionWrapper {
	
	private int nummer;
	
	private RechnungPosition rechnungPosition;
	
	private long produktCode;

	public RechnungPositionWrapper(int nummer, RechnungPosition rechnungPosition, long produktCode) {
		this.nummer = nummer;
		this.rechnungPosition = rechnungPosition;
		this.produktCode = produktCode;
	}
	
	public long getProduktCode() {
		return produktCode;
	}

	public void setProduktCode(long produktCode) {
		this.produktCode = produktCode;
	}

	public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }
    
    public String getProduktTypName() {
        return rechnungPosition.getProduktTyp().getName();
    }
    
    public String getProduktBezeichnung(){
    	return rechnungPosition.getProduktTyp().getBeschreibung();
    }
    
    public String getProduktCodeUndBeschreibung(){
    	return rechnungPosition.getProduktTyp().getProduktCodeUndBeschreibung();
    }
    
    public double getProduktPreis(){
    	return rechnungPosition.getProduktTyp().getPreis();
    }
    
    public RechnungPosition getRechnungPosition() {
        return rechnungPosition;
    }

    public void setRechnungPosition(RechnungPosition rechnungPosition) {
        this.rechnungPosition = rechnungPosition;
    }

}
