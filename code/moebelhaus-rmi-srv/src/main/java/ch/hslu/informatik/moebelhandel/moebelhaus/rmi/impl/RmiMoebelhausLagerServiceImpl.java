package ch.hslu.informatik.moebelhandel.moebelhaus.rmi.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.MoebelhausLagerService;
import ch.hslu.informatik.moebelhandel.moebelhaus.business.lager.MoebelhausLagerManager;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Moebelhaus;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Regal;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Ruecksendung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Tablar;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api.RmiMoebelhausLagerService;

public class RmiMoebelhausLagerServiceImpl extends UnicastRemoteObject implements RmiMoebelhausLagerService {

	private static final long serialVersionUID = -5349664764422743058L;
	
	private MoebelhausLagerService moebelhausLagerService;
	
	public RmiMoebelhausLagerServiceImpl() throws RemoteException {
		
	}
	
	public MoebelhausLagerService getMoebelhausService(){
		if (moebelhausLagerService == null){
			moebelhausLagerService = new MoebelhausLagerManager();
		}
		return moebelhausLagerService;
	}

	@Override
	public Bestellung bestellen(Bestellung bestellung) throws Exception {
		return getMoebelhausService().bestellen(bestellung);
	}

	@Override
	public List<Produkt> produktBestand(ProduktTyp produktTyp) throws Exception {
		return getMoebelhausService().produktBestand(produktTyp);
	}

	@Override
	public List<Produkt> produktBestand(String produktTypCode) throws Exception {
		return getMoebelhausService().produktBestand(produktTypCode);
	}

	@Override
	public void produktEinlagern(ProduktTyp produktTyp, int anzahl) throws Exception {
		getMoebelhausService().produktEinlagern(produktTyp, anzahl);		
	}

	@Override
	public void produktRetournieren(Produkt produkt) throws Exception {
		getMoebelhausService().produktRetournieren(produkt);
	}

	@Override
	public void produktAuslagern(List<Produkt> produktListe) throws Exception {
		getMoebelhausService().produktAuslagern(produktListe);		
	}

	@Override
	public Regal regalHinzufuegen(int nummer, int anzahlTablare) throws Exception {
		return getMoebelhausService().regalHinzufuegen(nummer, anzahlTablare);
	}

	@Override
	public Regal regalAktualisieren(Regal regal) throws Exception {
		return getMoebelhausService().regalAktualisieren(regal);
	}

	@Override
	public void regalLoeschen(Regal regal) throws Exception {
		getMoebelhausService().regalLoeschen(regal);
	}

	@Override
	public Regal regalByBezeichnung(String bezeichnung) throws Exception {
		return getMoebelhausService().regalByBezeichnung(bezeichnung);
	}

	@Override
	public Regal regalByNummer(int nummer) throws Exception {
		return getMoebelhausService().regalByNummer(nummer);
	}

	@Override
	public List<Regal> alleRegale() throws Exception {
		return getMoebelhausService().alleRegale();
	}

	@Override
	public List<Lieferant> alleLieferanten() throws Exception {
		return getMoebelhausService().alleLieferanten();
	}

	@Override
	public Regal regalByTablar(Tablar tablar) throws Exception {
		return getMoebelhausService().regalByTablar(tablar);
	}

	@Override
	public List<ProduktTyp> produktTypAlle() throws Exception {
		return getMoebelhausService().produktTypAlle();
	}

	@Override
	public void produktTypLoeschen(ProduktTyp produktTyp) throws Exception {
		getMoebelhausService().produktTypLoeschen(produktTyp);
	}

	@Override
	public ProduktTyp produktTypHinzufuegen(ProduktTyp produktTyp) throws Exception {
		return getMoebelhausService().produktTypHinzufuegen(produktTyp);
	}

	@Override
	public ProduktTyp produktTypUpdaten(ProduktTyp produktTyp) throws Exception {
		return getMoebelhausService().produktTypUpdaten(produktTyp);
	}

	@Override
	public Lieferant lieferantenHinzufuegen(Lieferant lieferant) throws Exception {
		return getMoebelhausService().lieferantenHinzufuegen(lieferant);
	}

	@Override
	public Lieferant lieferantenAktualisieren(Lieferant lieferant) throws Exception {
		return getMoebelhausService().lieferantenAktualisieren(lieferant);
	}

	@Override
	public void lieferantenLoeschen(Lieferant lieferant) throws Exception {
		getMoebelhausService().lieferantenLoeschen(lieferant);
	}

	@Override
	public List<ProduktTyp> findeByLieferant(Lieferant lieferant) throws Exception {
		return getMoebelhausService().findeByLieferant(lieferant);
	}

	@Override
	public List<ProduktTyp> findeByProduktname(String name) throws Exception {
		return getMoebelhausService().findeByProduktname(name);
	}

	@Override
	public ProduktTyp findeByProduktTypCode(String produktTypCode) throws Exception {
		return getMoebelhausService().findeByProduktTypCode(produktTypCode);
	}

	@Override
	public Moebelhaus getMoebelhaus() throws Exception {
		return getMoebelhausService().getMoebelhaus();
	}

	@Override
	public long getProduktCode() throws Exception {
		return getMoebelhausService().getProduktCode();
	}

	@Override
	public List<Bestellung> alleBestellungen() throws Exception {
		return getMoebelhausService().alleBestellungen();
	}

	@Override
	public Bestellung findeBestellungById(long id) throws Exception {
		return getMoebelhausService().findeBestellungById(id);
	}

	@Override
	public void ruecksendungHinzufuegen(Ruecksendung ruecksendung) throws Exception {
		getMoebelhausService().ruecksendungHinzufuegen(ruecksendung);
	}	
	
}
