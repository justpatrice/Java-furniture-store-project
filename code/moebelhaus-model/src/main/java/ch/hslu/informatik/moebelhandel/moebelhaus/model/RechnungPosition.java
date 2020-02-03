package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class RechnungPosition implements Serializable {

	private static final long serialVersionUID = -8134144917154061790L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private ProduktTyp produktTyp;

	private int anzahl;

	public RechnungPosition() {

	}

	public RechnungPosition(ProduktTyp produktTyp, int anzahl) {
		this.produktTyp = produktTyp;
		this.anzahl = anzahl;
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

	public int getAnzahl() {
		return anzahl;
	}

	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anzahl;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((produktTyp == null) ? 0 : produktTyp.hashCode());
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
		RechnungPosition other = (RechnungPosition) obj;
		if (anzahl != other.anzahl)
			return false;
		if (id != other.id)
			return false;
		if (produktTyp == null) {
			if (other.produktTyp != null)
				return false;
		} else if (!produktTyp.equals(other.produktTyp))
			return false;
		return true;
	}

	/* Calculated Properties */
	public String getProduktCode() {
		return produktTyp.getTypCode();
	}

	public String getBeschreibung() {
		return produktTyp.getBeschreibung();
	}

	public String getProduktCodeUndBeschreibung() {
		String typ = produktTyp.getTypCode();
		String beschreibung = produktTyp.getBeschreibung();

		return typ + "\n" + beschreibung;
	}

	public double getPreis() {
		return produktTyp.getPreis();
	}

	public double getBetrag() {
		return anzahl * produktTyp.getPreis();
	}

}
