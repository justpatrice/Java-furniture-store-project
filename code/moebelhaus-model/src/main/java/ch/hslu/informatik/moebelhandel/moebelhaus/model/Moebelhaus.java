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
@NamedQueries({ @NamedQuery(name = "Moebelhaus.findByName", query = "SELECT e FROM Moebelhaus e WHERE e.name=:name"),
		@NamedQuery(name = "Moebelhaus.findByMoebelhausCode", query = "SELECT e FROM Moebelhaus e WHERE e.moebelhausCode=:moebelhausCode") })
public class Moebelhaus implements Serializable {

	private static final long serialVersionUID = 213969726806133096L;

	@Id
	@GeneratedValue
	private long id;

	@Column(unique = true)
	private String moebelhausCode;
	@Column(unique = true)
	private String name;
	@OneToOne(cascade = CascadeType.ALL)
	private Adresse adresse;
	@OneToOne(cascade = CascadeType.ALL)
	private Kontakt kontakt;
	@OneToOne
	private Lager lager;

	public Moebelhaus() {

	}

	public Moebelhaus(String filialeCode, String name, Adresse adresse, Kontakt kontakt, Lager lager) {
		this.moebelhausCode = filialeCode;
		this.name = name;
		this.adresse = adresse;
		this.kontakt = kontakt;
		this.lager = lager;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getMoebelhausCode() {
		return moebelhausCode;
	}

	public void setMoebelhausCode(String moebelhausCode) {
		this.moebelhausCode = moebelhausCode;
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

	public Lager getLager() {
		return lager;
	}

	public void setLager(Lager lager) {
		this.lager = lager;
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
		Moebelhaus other = (Moebelhaus) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Moebelhaus [id=" + id + ", moebelhausCode=" + moebelhausCode + ", name=" + name + "]";
	}

}
