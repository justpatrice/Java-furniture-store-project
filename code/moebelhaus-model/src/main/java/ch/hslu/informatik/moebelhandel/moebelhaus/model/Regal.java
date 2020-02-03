package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
		@NamedQuery(name = "Regal.findByBezeichnung", query = "SELECT e FROM Regal e WHERE e.bezeichnung=:bezeichnung"),
		@NamedQuery(name = "Regal.findByNummer", query = "SELECT e FROM Regal e WHERE e.nummer=:nummer") })

public class Regal implements Serializable {

	private static final long serialVersionUID = -4801719934184260938L;

	@Id
	@GeneratedValue
	private long id;

	@Column(unique = true)
	private int nummer;

	@Column(unique = true)
	private String bezeichnung;

	@OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	private List<Tablar> tablarListe = new ArrayList<Tablar>();

	public Regal() {

	}

	public Regal(int nummer) {
		this.nummer = nummer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNummer() {
		return nummer;
	}

	public void setNummer(int nummer) {
		this.nummer = nummer;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public List<Tablar> getTablarListe() {
		return tablarListe;
	}

	public void setTablarListe(List<Tablar> tablarListe) {
		this.tablarListe = tablarListe;
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
		Regal other = (Regal) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return bezeichnung;
	}

	/* Helper-Methoden */
	public boolean addTablar(Tablar tablar) {
		return tablarListe.add(tablar);
	}

	public boolean removeTablar(Tablar tablar) {
		return tablarListe.remove(tablar);
	}

	public int getAnzahlTablare() {
		return tablarListe.size();
	}
}
