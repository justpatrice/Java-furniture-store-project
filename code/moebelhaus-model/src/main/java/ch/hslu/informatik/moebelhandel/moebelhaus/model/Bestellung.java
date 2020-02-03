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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
	@NamedQuery(name = "Bestellung.findByDatum", query = "SELECT e FROM Bestellung e WHERE e.datum=:datum") })
public class Bestellung implements Serializable {

	private static final long serialVersionUID = -4816294801238012027L;

	@Id
	@GeneratedValue
	private long id;

	@Temporal(TemporalType.DATE)
	private GregorianCalendar datum;
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	List<BestellungPosition> bestellungPositionListe = new ArrayList<BestellungPosition>();
	
	private Moebelhaus besteller;

	public Bestellung() {

	}

	public Bestellung(GregorianCalendar datum, Moebelhaus besteller) {
		this.datum = datum;
		this.besteller = besteller;
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

	public List<BestellungPosition> getBestellungPositionListe() {
		return bestellungPositionListe;
	}

	public void setBestellungPositionListe(List<BestellungPosition> bestellungPositionListe) {
		this.bestellungPositionListe = bestellungPositionListe;
	}

	public Moebelhaus getBesteller() {
		return besteller;
	}

	public void setBesteller(Moebelhaus besteller) {
		this.besteller = besteller;
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
		Bestellung other = (Bestellung) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
