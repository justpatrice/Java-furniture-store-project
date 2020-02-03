package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({ @NamedQuery(name = "Lieferant.findByName", query = "SELECT e FROM Lieferant e WHERE e.name=:name") })
public class Lieferant implements Serializable {

	private static final long serialVersionUID = -7656682076622669760L;

	@Id
	@GeneratedValue
	private long id;

	@Column(unique = true)
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Adresse adresse;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Kontakt kontakt;

	public Lieferant() {

	}

	public Lieferant(String name, Adresse adresse, Kontakt kontakt) {
		this.name = name;
		this.adresse = adresse;
		this.kontakt = kontakt;
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

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public Kontakt getKontakt() {
		return kontakt;
	}

	public void setKontakt(Kontakt kontakt) {
		this.kontakt = kontakt;
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
		Lieferant other = (Lieferant) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name + ", " + adresse.getOrt();
	}

}
