package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
		@NamedQuery(name = "ProduktRuecknahme.findByDatum", query = "SELECT e FROM ProduktRuecknahme e WHERE e.datum=:datum"),
		@NamedQuery(name = "ProduktRuecknahme.findByProduktTyp", query = "SELECT e FROM ProduktRuecknahme e WHERE e.produktTyp=:produktTyp"),
		@NamedQuery(name = "ProduktRuecknahme.findByRechnung", query = "SELECT e FROM ProduktRuecknahme e WHERE e.rechnung=:rechnung") })
public class ProduktRuecknahme implements Serializable {

	private static final long serialVersionUID = 2804821347784900368L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private Rechnung rechnung;

	@OneToOne
	private ProduktTyp produktTyp;

	/* Anzahl Exemplare von dem ProduktTyp, die zurückgegeben werden */
	private int anzahl;

	@Temporal(TemporalType.TIMESTAMP)
	private GregorianCalendar datum;

	@Column(columnDefinition = "TEXT")
	private String bemerkung;

	public ProduktRuecknahme() {

	}

	public ProduktRuecknahme(Rechnung rechnung, ProduktTyp produktTyp, GregorianCalendar datum, int anzahl,
			String bemerkung) {
		this.rechnung = rechnung;
		this.produktTyp = produktTyp;
		this.datum = datum;
		this.anzahl = anzahl;
		this.bemerkung = bemerkung;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ProduktTyp getProduktTyp() {
		return produktTyp;
	}

	public void setProduktTyp(ProduktTyp produktTyp) {
		this.produktTyp = produktTyp;
	}

	public Rechnung getRechnung() {
		return rechnung;
	}

	public void setRechnung(Rechnung rechnung) {
		this.rechnung = rechnung;
	}

	public int getAnzahl() {
		return anzahl;
	}

	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}

	public GregorianCalendar getDatum() {
		return datum;
	}

	public void setDatum(GregorianCalendar datum) {
		this.datum = datum;
	}

	public String getBemerkung() {
		return bemerkung;
	}

	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
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
		ProduktRuecknahme other = (ProduktRuecknahme) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/* Helper */

	/**
	 * Liefert den Gesamtbetrag der Rücknahme (anzahl Produkte multipliziert mit
	 * dem Preis für den ProduktTyp)
	 *
	 * @return
	 */
	public double getBetrag() {

		return produktTyp.getPreis() * anzahl;
	}

}
