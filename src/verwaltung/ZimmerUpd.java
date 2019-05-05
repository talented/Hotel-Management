package verwaltung;

public class ZimmerUpd {
	
	private int zimmerNr;
	private int kategorieNr;
	
	
	public ZimmerUpd() {
		this.zimmerNr = 0;
		this.kategorieNr = 0;
	}
	
	
	/**
	 * �berladener Konstruktor f�r ein Zimmer-Objekt. Initialisiert dieses mit einer laufenden Nummer, mit der �bergebenen
	 * Kategorie (darf nicht null sein) und setzt den �bergebenen Wahrheitswert, ob ein Zimmer belegt ist oder nicht. 
	 * Das Zimmer-Objekt wird direkt der ArrayList<Zimmer> in der Zimmerkategorie hinzugef�gt.
	 * 
	 * @param kategorie Zu dem Zimmer-Objekt geh�rige Zimmerkategorie
	 * @param istBelegt true, wenn das Zimmer belegt ist. False andernfalls.
	 */
	public ZimmerUpd(int zimmerNr, int kategorieNr) {
		setZimmerNr(zimmerNr);
		setKategorieNr(kategorieNr);
		
	}

	private void setKategorieNr(int kategorieNr) {
		
		this.kategorieNr = kategorieNr;
	}

	private void setZimmerNr(int zimmerNr) {
		
		this.zimmerNr = zimmerNr;
	}

	/**
	 * Gibt die Zimmer-Nummer eines Zimmer-Objekts zur�ck.
	 * 
	 * @return Zimmer-Nummer des Zimmer-Objekts
	 */
	public int getZimmerNr() {
		return zimmerNr;
	}


	/**
	 * Gibt die Zimmerkategorie eines Zimmer-Objekts zur�ck.
	 * 
	 * @return Zimmerkategorie des Zimmer-Objekts
	 */
	public int getKategorieNr() {
		return kategorieNr;
	}
	
	public String getAll() {
		 
		 if (zimmerNr != 0) {
	 
			 String result =
			 String.format(
			 "Zimmer Nr: " + getZimmerNr() + "\n"
			 + "Zimmer Kategorie: " + getKategorieNr() + "\n");
			 
			 return result; 
			 
		 }
		 
		 return "";
	 
	 }

}
