package verwaltung;



public class Zimmerkategorie {

	private final String NAME;
	
	private final int ANZAHL_BETTEN;
	
	private final double PREIS_PRO_NACHT;
	
	private final int KATEGORIE_NR;
	
	/**
	 * Parametrisierter Konstruktor f�r eine Zimmerkategorie-Objekt. Initialisiert dieses mit den �bergebenen Werten insofern g�ltig.
	 * 
	 * @param name - Name der Zimmerkategorie, darf nicht null oder leer sein
	 * @param anzahlBetten - Anzahl der zur Verf�gung stehenden Betten der Zimmerkategorie, darf nicht kleiner als 1 sein
	 * @param preisProNacht - Preis f�r eine �bernachtung, darf nicht kleiner als 0 sein
	 * @throws IllegalArgumentException wird geworfen, wenn keine g�ltigen Parameter
	 */

	public Zimmerkategorie(int kategorieNr, String name, int anzahlBetten, double preisProNacht) {
		
		if(kategorieNr < 1) {
			throw new IllegalArgumentException("Die Kategorienummer darf nicht kleiner oder gleich null sein");
		}
		this.KATEGORIE_NR = kategorieNr;
		
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Der angegebene Name darf nicht null oder leer sein");
		}
		this.NAME = name;
		
		if(anzahlBetten <= 0) {
			throw new IllegalArgumentException("Die angegebene Anzahl an Betten muss gr��er als 0 sein");
		}
		this.ANZAHL_BETTEN = anzahlBetten;
		
		if(preisProNacht < 0) {
			throw new IllegalArgumentException("Der angegebene Preis pro Nacht muss gr��er oder gleich 0 sein");
		}
		this.PREIS_PRO_NACHT = preisProNacht;
		
	}

	/**
	 * Gibt den Namen eines Zimmerkategorie-Objekts zur�ck.
	 * 
	 * @return Name des Zimmerkategorie-Objekts
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * Gibt die Anzahl der Betten eines Zimmerkategorie-Objekts zur�ck.
	 * 
	 * @return Anzahl der Betten des Zimmerkategorie-Objekts
	 */
	public int getAnzahlBetten() {
		return ANZAHL_BETTEN;
	}
	
	
	/**
	 * Gibt den Preis pro Nacht eines Zimmerkategorie-Objekts zur�ck.
	 * 
	 * @return Preis pro Nacht des Zimmerkategorie-Objekts
	 */
	public double getPreisProNacht() {
		return PREIS_PRO_NACHT;
	}
	
	/**
	 * Gibt die Kategorie-Nr. eines Zimmerkategorie-Objekts zur�ck.
	 * 
	 * @return  Kategorie-Nr. des Zimmerkategorie-Objekts
	 */
	public int getKategorieNr() {
		return KATEGORIE_NR;
	}
	

	@Override
	
	public String toString() {
		return this.getName() + " mit " + this.getAnzahlBetten() + " Bett(en) f�r " + this.getPreisProNacht() + " Euro pro Nacht";
	}
}
