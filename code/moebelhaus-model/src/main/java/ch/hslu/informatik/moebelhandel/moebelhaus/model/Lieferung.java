package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

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
@NamedQueries({ @NamedQuery(name = "Lieferung.findByDatum", query = "SELECT e FROM Lieferung e WHERE e.datum=:datum"),
		@NamedQuery(name = "Lieferung.findByFiliale", query = "SELECT e FROM Lieferung e WHERE e.filiale=:filiale") })
public class Lieferung implements Serializable {

	private static final long serialVersionUID = 7164250618103209835L;

	@Id
	@GeneratedValue
	private long id;

	@Temporal(TemporalType.DATE)
	private GregorianCalendar datum;

	@ManyToOne
	private Moebelhaus filiale;

	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	List<LieferungPosition> lieferungPositionListe = new ArrayList<LieferungPosition>();

	public Lieferung() {

	}

	public Lieferung(GregorianCalendar datum, Moebelhaus filiale) {
		this.datum = datum;
		this.filiale = filiale;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public GregorianCalendar getDatum() {
		return datum;
	}

	public void setDatum(GregorianCalendar datum) {
		this.datum = datum;
	}

	public Moebelhaus getFiliale() {
		return filiale;
	}

	public void setFiliale(Moebelhaus filiale) {
		this.filiale = filiale;
	}

	public List<LieferungPosition> getLieferungPositionListe() {
		return lieferungPositionListe;
	}

	public void setLieferungPositionListe(List<LieferungPosition> lieferungPositionListe) {
		this.lieferungPositionListe = lieferungPositionListe;
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
		Lieferung other = (Lieferung) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
