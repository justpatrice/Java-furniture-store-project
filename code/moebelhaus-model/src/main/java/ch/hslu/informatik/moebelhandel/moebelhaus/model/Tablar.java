package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Tablar implements Serializable {

	private static final long serialVersionUID = -3566445167737055008L;

	@Id
	@GeneratedValue
	private long id;
	private String bezeichnung;
	@OneToMany(orphanRemoval = true)
	private List<Produkt> produktListe = new ArrayList<Produkt>();

	public Tablar() {

	}

	public Tablar(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
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
		Tablar other = (Tablar) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return bezeichnung;
	}

}
