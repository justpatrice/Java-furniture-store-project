package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "Credentials.findByBenutzername", query = "SELECT e FROM Credentials e WHERE e.benutzername=:benutzername") })
public class Credentials implements Serializable {

	private static final long serialVersionUID = -585087541600298206L;

	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String benutzername;
	private String passwort;

	public Credentials() {

	}

	public Credentials(String benutzername, String passwort) {
		this.benutzername = benutzername;
		this.passwort = passwort;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
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
		Credentials other = (Credentials) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Credentials [id=" + id + ", benutzername=" + benutzername + ", passwort=" + passwort + "]";
	}

}
