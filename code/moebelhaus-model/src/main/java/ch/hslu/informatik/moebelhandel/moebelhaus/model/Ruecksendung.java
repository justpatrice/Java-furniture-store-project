package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQuery(name = "Ruecksendung.findByDatum", query = "SELECT e FROM Ruecksendung e WHERE e.zeit BETWEEN :startDatum AND :endDatum")
public class Ruecksendung implements Serializable {

	private static final long serialVersionUID = 8114504930498788145L;

	@Id
	@GeneratedValue
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private GregorianCalendar zeit;

	@ManyToOne
	private Moebelhaus absender;

	@OneToMany(cascade = CascadeType.ALL)
	private List<RuecksendungPosition> ruecksendungPositionListe = new ArrayList<RuecksendungPosition>();

	public Ruecksendung() {

	}

	public Ruecksendung(GregorianCalendar zeit, Moebelhaus absender) {
		this.zeit = zeit;
		this.absender = absender;
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

	public Moebelhaus getAbsender() {
		return absender;
	}

	public void setAbsender(Moebelhaus absender) {
		this.absender = absender;
	}

	public List<RuecksendungPosition> getRuecksendungPositionListe() {
		return ruecksendungPositionListe;
	}

	public void setRuecksendungPositionListe(List<RuecksendungPosition> ruecksendungPositionListe) {
		this.ruecksendungPositionListe = ruecksendungPositionListe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((zeit == null) ? 0 : zeit.hashCode());
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
		Ruecksendung other = (Ruecksendung) obj;
		if (id != other.id)
			return false;
		if (zeit == null) {
			if (other.zeit != null)
				return false;
		} else if (!zeit.equals(other.zeit))
			return false;
		return true;
	}

	/* Helper */
	public boolean addRuecksendungPosition(RuecksendungPosition ruecksendungPosition) {
		return ruecksendungPositionListe.add(ruecksendungPosition);
	}

	public boolean removeRuecksendungPosition(RuecksendungPosition ruecksendungPosition) {
		return ruecksendungPositionListe.remove(ruecksendungPosition);
	}
}
