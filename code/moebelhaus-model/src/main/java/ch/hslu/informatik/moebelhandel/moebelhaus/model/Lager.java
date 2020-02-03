package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({ @NamedQuery(name = "Lager.findByName", query = "SELECT e FROM Lager e WHERE e.name=:name") })
public class Lager implements Serializable {

	private static final long serialVersionUID = -6192335151049257572L;

	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String name;

	@OneToMany(orphanRemoval = true)
	private List<Regal> regalListe = new ArrayList<Regal>();

	public Lager() {

	}

	public Lager(String name) {
		this.name = name;
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

	public List<Regal> getRegalListe() {
		return regalListe;
	}

	public void setRegalListe(List<Regal> regalListe) {
		this.regalListe = regalListe;
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
		Lager other = (Lager) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lager [id=" + id + ", name=" + name + "]";
	}

	/* Helper-Methoden */
	/**
	 * Fügt das übergebene Regal ein.
	 * 
	 * @param regal
	 * @return
	 */
	public boolean addRegal(Regal regal) {
		return regalListe.add(regal);
	}

	/**
	 * Entferent das übergebene Regal.
	 * 
	 * @param regal
	 * @return
	 */
	public boolean removeRegal(Regal regal) {
		return regalListe.remove(regal);
	}

}
