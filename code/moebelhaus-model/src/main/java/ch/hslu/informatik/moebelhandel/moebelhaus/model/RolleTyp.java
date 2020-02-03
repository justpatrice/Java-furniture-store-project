package ch.hslu.informatik.moebelhandel.moebelhaus.model;

public enum RolleTyp {
	KASSE_MITARBEITER("Kasse-Mitarbeiter"), FILIALE_SACHBEARBEITER("Filiale-Sachbearbeiter"), FILIALE_LEITER(
			"Filiale-Leiter"), ADMINISTRATOR("Administrator");

	private String bezeichnung;

	private RolleTyp(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String bezeichnung() {
		return bezeichnung;
	}

	public String toString() {
		return bezeichnung;
	}
}