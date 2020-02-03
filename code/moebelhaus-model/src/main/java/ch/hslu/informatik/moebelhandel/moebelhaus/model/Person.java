package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
		@NamedQuery(name = "Person.findByNachname", query = "SELECT e FROM Person e WHERE e.nachname=:nachname"),
		@NamedQuery(name = "Person.findByVorname", query = "SELECT e FROM Person e WHERE e.vorname=:vorname"),
		@NamedQuery(name = "Person.findByNachnameUndVorname", query = "SELECT e FROM Person e WHERE e.nachname=:nachname AND e.vorname=:vorname"),
		@NamedQuery(name = "Person.findByEmail", query = "SELECT e FROM Person e WHERE e.kontakt.email=:email") })
public class Person implements Serializable {

	private static final long serialVersionUID = 711951573552511601L;

	@Id
	@GeneratedValue
	private long id;

	private String nachname;
	private String vorname;
	private Adresse adresse;
	private Kontakt kontakt;

	public Person() {

	}

	public Person(String nachname, String vorname, Adresse adresse, Kontakt kontakt) {
		this.nachname = nachname;
		this.vorname = vorname;
		this.adresse = adresse;
		this.kontakt = kontakt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
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
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", nachname=" + nachname + ", vorname=" + vorname + ", adresse=" + adresse
				+ ", kontakt=" + kontakt + "]";
	}
}
