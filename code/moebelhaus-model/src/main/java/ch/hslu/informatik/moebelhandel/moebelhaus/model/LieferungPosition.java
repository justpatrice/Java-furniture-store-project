package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "LieferungPosition.findByProduktTyp", query = "SELECT e FROM LieferungPosition e WHERE e.produktTyp=:produktTyp")
public class LieferungPosition implements Serializable {

	private static final long serialVersionUID = -4787291907814985515L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private ProduktTyp produktTyp;

	int anzahl;

	public LieferungPosition() {

	}

	public LieferungPosition(ProduktTyp produktTyp, int anzahl) {
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
		LieferungPosition other = (LieferungPosition) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
