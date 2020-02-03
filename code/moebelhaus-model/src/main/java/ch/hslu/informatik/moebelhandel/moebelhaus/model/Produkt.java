package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "Produkt.findByProduktTyp", query = "SELECT e FROM Produkt e WHERE e.typ=:produktTyp AND e.verkauft=FALSE"),
		@NamedQuery(name = "Produkt.findByProduktTypCode", query = "SELECT e FROM Produkt e WHERE e.typ.typCode=:typCode AND e.verkauft=FALSE"),
		@NamedQuery(name = "Produkt.findByCode", query = "SELECT e FROM Produkt e WHERE e.code=:code AND e.verkauft=FALSE") })
public class Produkt implements Serializable {

	private static final long serialVersionUID = -976270022927169240L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private ProduktTyp typ;
	@Column(unique = true)
	private long code;

	private boolean verkauft;

	public Produkt() {
	}

	public Produkt(ProduktTyp typ, long uid) {
		this.typ = typ;
		this.code = uid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ProduktTyp getTyp() {
		return typ;
	}

	public void setTyp(ProduktTyp typ) {
		this.typ = typ;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public boolean isVerkauft() {
		return verkauft;
	}

	public void setVerkauft(boolean verkauft) {
		this.verkauft = verkauft;
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
		Produkt other = (Produkt) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/* Helper */

	/**
	 * Liefert den Preis zurück.
	 *
	 * @return
	 */
	public double getPreis() {
		return typ.getPreis();
	}

	public String getProduktCodeUndBeschreibung() {
		return typ.getProduktCodeUndBeschreibung();
	}

}
