package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Kontakt implements Serializable {

	private static final long serialVersionUID = -3834643839871314740L;

	@Column(unique = true)
	private String email;
	private String telefon;

	public Kontakt() {

	}

	public Kontakt(String email, String telefon) {
		this.email = email;
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

}
