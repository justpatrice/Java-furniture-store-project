package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "ProduktTyp.findByName", query = "SELECT e FROM ProduktTyp e WHERE e.name=:name"),
		@NamedQuery(name = "ProduktTyp.findByTypCode", query = "SELECT e FROM ProduktTyp e WHERE e.typCode=:typCode"),
		@NamedQuery(name = "ProduktTyp.findByLieferant", query = "SELECT e FROM ProduktTyp e WHERE e.lieferant=:lieferant") })
public class ProduktTyp implements Serializable {

	private static final long serialVersionUID = -5246976481194968043L;

	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String beschreibung;
	private double preis;
	private int minimalerBestand;
	private int maximalerBestand;
	@Column(unique = true)
	private String typCode;
	@ManyToOne
	private Lieferant lieferant;
	@ManyToOne
	private Tablar ablageTablar;

	public ProduktTyp() {

	}

	public ProduktTyp(String name, String beschreibung, double preis, String typCode, Lieferant lieferant) {
		this.name = name;
		this.beschreibung = beschreibung;
		this.preis = preis;
		this.typCode = typCode;
		this.lieferant = lieferant;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public int getMinimalerBestand() {
		return minimalerBestand;
	}

	public void setMinimalerBestand(int minimalerBestand) {
		this.minimalerBestand = minimalerBestand;
	}

	public int getMaximalerBestand() {
		return maximalerBestand;
	}

	public void setMaximalerBestand(int maximalerBestand) {
		this.maximalerBestand = maximalerBestand;
	}

	public String getTypCode() {
		return typCode;
	}

	public void setTypCode(String typCode) {
		this.typCode = typCode;
	}

	public Lieferant getLieferant() {
		return lieferant;
	}

	public void setLieferant(Lieferant lieferant) {
		this.lieferant = lieferant;
	}

	public Tablar getAblageTablar() {
		return ablageTablar;
	}

	public void setAblageTablar(Tablar ablageTablar) {
		this.ablageTablar = ablageTablar;
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
		ProduktTyp other = (ProduktTyp) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/* Helper */
	public String getProduktCodeUndBeschreibung() {
		return typCode + "\n" + beschreibung;
	}

	@Override
	public String toString() {
		return name;
	}

	/* Helper-Methode - Calculated Property */
	public String getAblage() {
		if (ablageTablar != null) {
			return ablageTablar.getBezeichnung();
		}
		return "unbekannt";
	}
}
