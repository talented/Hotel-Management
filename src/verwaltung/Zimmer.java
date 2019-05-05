package verwaltung;



public class Zimmer{
	
	private final int ZIMMER_NR;
	
	private final Zimmerkategorie KATEGORIE;
	
	private final int KATEGORIE_NR;
	
	/**
	 * Parametrisierter Konstruktor, welcher ein Zimmer-Objekt mit den �bergebenen Werten initialisiert. 
	 * IllegalArgumentException bei ung�ltigen Werten.
	 * 
	 * @param zimmerNr Zimmer-Nummer des Zimmer-Objekts
	 * @param kategorie Zimmerkategorie des Zimmer-Objekts
	 * @throws IllegalArgumentException:
	 * -wenn zimmerNr kleiner oder gleich 0
	 * -wenn Kategorie gleich null
	 */
	public Zimmer(int zimmerNr, Zimmerkategorie kategorie) {
		if(zimmerNr <1) {
			throw new IllegalArgumentException("Die Zimmer-Nr. darf nicht kleiner oder gleich null sein");
		}
		this.ZIMMER_NR = zimmerNr;
		
		if(kategorie == null) {
			throw new IllegalArgumentException("Die Kategorie darf nicht null sein");
		}
		this.KATEGORIE = kategorie;
		
		this.KATEGORIE_NR = kategorie.getKategorieNr();
	}
	
	
	/**
	 * Gibt die Zimmer-Nummer eines Zimmer-Objekts zur�ck.
	 * 
	 * @return Zimmer-Nummer des Zimmer-Objekts
	 */
	public int getZimmerNr() {
		return ZIMMER_NR;
	}


	/**
	 * Gibt die Zimmerkategorie eines Zimmer-Objekts zur�ck.
	 * 
	 * @return Zimmerkategorie des Zimmer-Objekts
	 */
	public Zimmerkategorie getKategorie() {
		return KATEGORIE;
	}
	
	/**
	 * Gibt die KategorieNummer der jeweiligen Kategorie des Zimmerobjekts zur�ck.
	 * 
	 * @return KategorieNummer des Zimmer-Objekts.
	 */
	public int getKategorieNr() {
		return KATEGORIE_NR;
	}
	
	@Override
	public String toString() {
		return "Zimmer-Nr.: " + this.getZimmerNr() + " ist ein " + this.getKategorie().getName();
	}

}
