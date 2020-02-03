package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class RuecksendungPosition implements Serializable {

	private static final long serialVersionUID = 132623888148528413L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private ProduktTyp produktTyp;

	private String bemerkung;

	private int anzahl;

	public RuecksendungPosition() {

	}

	public RuecksendungPosition(ProduktTyp produktTyp, String bemerkung, int anzahl) {
		this.produktTyp = produktTyp;
		this.bemerkung = bemerkung;
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

	public String getBemerkung() {
		return bemerkung;
	}

	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
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
		RuecksendungPosition other = (RuecksendungPosition) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
