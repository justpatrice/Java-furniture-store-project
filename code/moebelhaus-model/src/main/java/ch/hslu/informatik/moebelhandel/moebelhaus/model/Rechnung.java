package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
		@NamedQuery(name = "Rechnung.findByDatum", query = "SELECT e FROM Rechnung e WHERE e.zeit BETWEEN :startDatum AND :endDatum"),
		@NamedQuery(name = "Rechnung.findByBenutzer", query = "SELECT e FROM Rechnung e WHERE e.benutzer=:benutzer"),
		@NamedQuery(name = "Rechnung.findByBenutzerUndDatum", query = "SELECT e FROM Rechnung e WHERE e.zeit BETWEEN :startDatum AND :endDatum AND e.benutzer=:benutzer") })
public class Rechnung implements Serializable {

	private static final long serialVersionUID = 2024524432739676459L;

	@Id
	@GeneratedValue
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private GregorianCalendar zeit;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<RechnungPosition> rechnungPositionListe = new ArrayList<RechnungPosition>();

	/**
	 * Der an der Kasse angemeldetet Benutzer, der die Rechnung erstellt hat
	 * (Ersteller)
	 */
	@ManyToOne
	private Benutzer benutzer;

	public Rechnung() {

	}

	public Rechnung(GregorianCalendar zeit, Benutzer benutzer) {
		this.zeit = zeit;
		this.benutzer = benutzer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public GregorianCalendar getZeit() {
		return zeit;
	}

	public void setZeit(GregorianCalendar zeit) {
		this.zeit = zeit;
	}

	public List<RechnungPosition> getRechnungPositionListe() {
		return rechnungPositionListe;
	}

	public void setRechnungPositionListe(List<RechnungPosition> rechnungPositionListe) {
		this.rechnungPositionListe = rechnungPositionListe;
	}

	public Benutzer getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rechnung other = (Rechnung) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/* Helper Methoden */

	/**
	 * Fügt die übergebene RechnungPosition in die Liste ein.
	 *
	 * @param rechnungPosition
	 * @return
	 */
	public boolean addRechnungPosition(RechnungPosition rechnungPosition) {
		return rechnungPositionListe.add(rechnungPosition);
	}

	/**
	 * Entferent die übergebene Rechnungsposition aus der Liste.
	 *
	 * @param rechnungPosition
	 * @return
	 */
	public boolean removeRechnungPosition(RechnungPosition rechnungPosition) {
		return rechnungPositionListe.remove(rechnungPosition);
	}

	/**
	 * Liefert den Rechnungsbetrag zurück.
	 *
	 * @return
	 */
	public double getBetrag() {

		double betrag = 0;

		for (RechnungPosition rPosition : rechnungPositionListe) {
			betrag += rPosition.getBetrag();
		}

		return betrag;
	}

	/**
	 * Liefert die Rechnungsposition für den übergebenen Produtktyp zurück.
	 *
	 * @param produktTyp
	 * @return
	 */
	public RechnungPosition findByProduktTyp(ProduktTyp produktTyp) {

		for (RechnungPosition pos : rechnungPositionListe) {
			if (pos.getProduktTyp().equals(produktTyp)) {
				return pos;
			}
		}

		return null;
	}

	/**
	 * Liefert die Rechnungsposition für den übergebenen Produtktyp Code zurück.
	 *
	 * @param produktTypCode
	 * @return
	 */
	public RechnungPosition findByProduktTypCode(String produktTypCode) {

		for (RechnungPosition pos : rechnungPositionListe) {
			if (pos.getProduktTyp().getTypCode().equals(produktTypCode)) {
				return pos;
			}
		}

		return null;
	}
}
