package verwaltung;



import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import util.PropertiesTable;


public class Gast implements Serializable {

	private static final long serialVersionUID = 8495494391525570321L;

	// Es werden die ben�tigten Variablen declariert

	private int gastNr; // ((nicht ID DB -> Trigger)) INTEGER oder int????????????

	private String vorname;
	private String nachname;
	private LocalDate geburtsdatum;
	private String strasse;
	private String hausNr;
	private String ort;
	private String plz;
	private String land;
	private String email;
	private String telefon;
	
	public Gast() {
		
	}

	/**
	 * overloaded constructor giving a vorname, nachname, geburtsdatum, strasse,
	 * hausNr, ort, plz, land, email, telefon as Takes parameters and creates an
	 * object from them.
	 * 
	 * @param vorname      Vorname des Gastes
	 * @param nachname     Nachname des Gastes
	 * @param geburtsdatum Geburtsdatum des Gastes
	 * @param strasse      Strasse des Gastes
	 * @param hausNr       HausNr des Gastes
	 * @param ort          Ort des Gastes
	 * @param plz          Postleitzahl des Gastes
	 * @param land         Land des Gastes
	 * @param email        EMail des Gastes
	 * @param telefon      TelefonNr des Gastes
	 * 
	 */
	public Gast(String vorname, String nachename, LocalDate geburtsdatum, String strasse, String hausNr, 
			String plz, String ort, String land, String email, String telefon) {
		setVorname(vorname);
		setNachname(nachename);
		setGeburtsdatum(geburtsdatum);
		setStrasse(strasse);
		setHausNr(hausNr);
		setOrt(ort);
		setPlz(plz);
		setLand(land);
		setEmail(email);
		setTelefon(telefon);
	}


	/**
	 * overloaded constructor giving a gastNr, vorname, nachname, geburtsdatum, strasse,
	 * hausNr,  plz,ort, land, email, telefon as Takes parameters and creates an
	 * object from them.
	 * 
	 * @param gastNr	   gastNr des Gastes 
	 * @param vorname      Vorname des Gastes
	 * @param nachname     Nachname des Gastes
	 * @param geburtsdatum Geburtsdatum des Gastes
	 * @param strasse      Strasse des Gastes
	 * @param hausNr       HausNr des Gastes
	 * @param plz          Postleitzahl des Gastes
	 * @param ort          Ort des Gastes
	 * @param land         Land des Gastes
	 * @param email        EMail des Gastes
	 * @param telefon      TelefonNr des Gastes
	 * 
	 */
	public Gast(int gastNr, String vorname, String nachename, LocalDate geburtsdatum, String strasse, String hausNr, 
			String plz, String ort, String land, String email, String telefon) {
		setVorname(vorname);
		setNachname(nachename);
		setGeburtsdatum(geburtsdatum);
		setStrasse(strasse);
		setHausNr(hausNr);
		setPlz(plz);
		setOrt(ort);
		setLand(land);
		setEmail(email);
		setTelefon(telefon);
		this.gastNr = gastNr;
	}
	
	/**
	 * 
	 * @throws Eine IllegalArgumentException wird generiert, wenn der Vorname null, leer oder
	 *             gr��er als die maximal zul�ssige L�nge ist. Die L�nge ist in der
	 *             Klasse PropertiesTable definiert.
	 * 
	 * @param vorname Weist dem Vornamen den �bergebenen Wert zu.
	 */
	public void setVorname(String vorname) {

		if (vorname == null || vorname.isEmpty()) {
			throw new IllegalArgumentException("Der Vorname muss mindestens ein Zeichen haben.");
		}
		if (vorname.length() > PropertiesTable.MAXVORNAME) {
			throw new IllegalArgumentException("Der Vorname darf eine L�nge von " + PropertiesTable.MAXVORNAME + " Zeichen nicht �berschreiten.");
		}
		this.vorname = vorname;
	}

	/**
	 * 
	 * @throws Eine IllegalArgumentException wird generiert, wenn der Nachname null, leer oder
	 *             gr��er als die maximal zul�ssige L�nge ist. Die L�nge ist in der
	 *             Klasse PropertiesTable definiert.
	 *
	 * @param nachname Weist dem Nachnamen den �bergebenen Wert zu.
	 */
	public void setNachname(String nachname) {
		if (nachname == null || nachname.isEmpty()) {
			throw new IllegalArgumentException("Der Nachname muss mindestens ein Zeichen haben.");
		}
		if (nachname.length() > PropertiesTable.MAXNACHNAME) {
			throw new IllegalArgumentException("Der Nachname darf eine L�nge von " + PropertiesTable.MAXNACHNAME + " Zeichen nicht �berschreiten.");
		}
		this.nachname = nachname;
	}

	/**
	 * 
	 * @throws Eine IllegalArgumentException wird generiert, wenn der Geburtstag null oder der Gast
	 *             nicht mindestens 18 Jahre alt ist.
	 * 
	 *
	 * @param geburtsdatum Weist dem Geburtstag den �bergebenen Wert zu.
	 */
	public void setGeburtsdatum(LocalDate geburtsdatum) {
		if (geburtsdatum == null) {
			throw new IllegalArgumentException("Das Geburtsdatum darf nicht null sein.");
		}
		
		Period p = Period.between(geburtsdatum, LocalDate.now());
		if (p.getYears() < 18) {
			throw new IllegalArgumentException("Der Gast muss mindestens 18 Jahre alt sein.");
		}
		this.geburtsdatum = geburtsdatum;
	}

	/**
	 * 
	 * @throws Eine IllegalArgumentException wird generiert, wenn die Strasse gr��er als die
	 *             maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
	 *             PropertiesTable definiert.
	 *
	 * @param strasse Weist der Strasse den �bergebenen Wert zu.
	 */
	public void setStrasse(String strasse) {
		if (strasse.length() > PropertiesTable.MAXSTRASSE) {
			throw new IllegalArgumentException("Die Strasse darf eine L�nge von " + PropertiesTable.MAXSTRASSE + " Zeichen nicht �berschreiten ");
		}
		this.strasse = strasse;
	}

	/**
	 * 
	 * @throws Eine IllegalArgumentException wird generiert, wenn die Strasse gr��er als die
	 *             maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
	 *             PropertiesTable definiert.
	 *
	 * @param hausNr Weist der HausNr den �bergebenen Wert zu.
	 * 
	 */
	public void setHausNr(String hausNr) {
		if (strasse.length() > PropertiesTable.MAXHAUSNR) {
			throw new IllegalArgumentException("Die HausNr darf eine L�nge von " + PropertiesTable.MAXHAUSNR + " Zeichen nicht �berschreiten ");
		}
		this.hausNr = hausNr;
	}

	/**
	 * 
	 * @throws Eine IllegalArgumentException wird generiert, wenn die Postleitzahl gr��er als die
	 *             maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
	 *             PropertiesTable definiert.
	 *
	 * @param plz Weist der Postleitzahl den �bergebenen Wert zu.
	 */
	public void setPlz(String plz) {
		if (plz.length() > PropertiesTable.MAXPLZ) {
			throw new IllegalArgumentException("Die Postleitzahl darf eine L�nge von " + PropertiesTable.MAXPLZ + " Zeichen nicht �berschreiten ");
		}
		this.plz = plz;
	}

	/**
	 * 
	 * @throws Eine IllegalArgumentException wird generiert, wenn der Ort null, leer oder gr��er
	 *             als die maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
	 *             PropertiesTable definiert.
	 *
	 * @param ort Weist dem Ort den �bergebenen Wert zu.
	 */
	public void setOrt(String ort) {
		if (ort == null || ort.isEmpty()) {
			throw new IllegalArgumentException("Der Ort darf nicht null oder leer sein.");
		}
		if (ort.length() > PropertiesTable.MAXORT) {
			throw new IllegalArgumentException("Der Ort darf eine L�nge von " + PropertiesTable.MAXORT + " Zeichen nicht �berschreiten.");
		}
		this.ort = ort;
	}

	/**
	 * 
	 * @throws Eine IllegalArgumentException wird generiert, wenn das Land null, leer oder gr��er
	 *             als die maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
	 *             PropertiesTable definiert.
	 *
	 * @param land Weist dem Land den �bergebenen Wert zu.
	 */
	public void setLand(String land) {
//		if (land == null || land.isEmpty()) {
//			throw new IllegalArgumentException("Das Land darf nicht null oder leer sein.");
//		}
//		if (land.length() > PropertiesTable.MAXLAND) {
//			throw new IllegalArgumentException("Das Land darf eine L�nge von " + PropertiesTable.MAXLAND + " Zeichen nicht �berschreiten.");
//		}
		this.land = land;
	}

	/**
	 * 
	 * @throws Eine IllegalArgumentException wird generiert, wenn EMail gr��er als die maximal
	 *             zul�ssige L�nge ist. Die L�nge ist in der Klasse PropertiesTable
	 *             definiert.
	 *
	 * @param email Weist der EMail den �bergebenen Wert zu.
	 */
	public void setEmail(String email) {

		if (email.length() > PropertiesTable.MAXEMAIL) {
			throw new IllegalArgumentException("EMail darf eine L�nge von " + PropertiesTable.MAXEMAIL + " Zeichen nicht �berschreiten.");		}
		this.email = email;
	}

	/**
	 * 
	 * @throws Eine IllegalArgumentException wird generiert, wenn die TelefonNr gr��er als die
	 *             maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
	 *             PropertiesTable definiert.
	 *
	 * @param telefon Weist der TelefonNr den �bergebenen Wert zu.
	 */
	public void setTelefon(String telefon) {

		if (telefon.length() > PropertiesTable.MAXTELEFONNUMBER) {
			throw new IllegalArgumentException("Die TelefonNr darf eine L�nge von " + PropertiesTable.MAXTELEFONNUMBER
					+ " Zeichen nicht �berschreiten.");
		}
		this.telefon = telefon;
	}

	/**
	 * 
	 * @return gastNr Liefert eine eindeutige Nummer (unique) vom Gast zur�ck.
	 */
	public Integer getGastNr() {
		return gastNr;
	}

	/**
	 * 
	 * @return vorname Liefert den Vornamen vom Gast zur�ck.
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * 
	 * @return nachname Liefert den Nachname vom Gast zur�ck.
	 */
	public String getNachname() {
		return nachname;
	}

	/**
	 * 
	 * @return geburtsdatum Liefert den Geburtstag vom Gast zur�ck.
	 */
	public LocalDate getGeburtsdatum() {
		return geburtsdatum;
	}

	/**
	 * 
	 * @return strasse Liefert die Strasse vom Gast zur�ck.
	 */
	public String getStrasse() {
		return strasse;
	}

	/**
	 * 
	 * @return hausNr Liefert die HausNr vom Gast zur�ck.
	 */
	public String getHausNr() {
		return hausNr;
	}

	/**
	 * 
	 * @return Plz Liefert die Postleitzahl vom Gast zur�ck.
	 */
	public String getPlz() {
		return plz;
	}

	/**
	 * 
	 * @return ort Liefert den Ort vom Gast zur�ck.
	 */
	public String getOrt() {
		return ort;
	}

	/**
	 * 
	 * @return land Liefert das Land vom Gast zur�ck.
	 */
	public String getLand() {
		return land;
	}

	/**
	 * 
	 * @return email Liefert EMail vom Gast zur�ck.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 *
	 * @return telefon Liefert die TelefonNr vom Gast zur�ck.
	 */
	public String getTelefon() {
		return telefon;
	}

	public String getAll() {
		 
		 if (gastNr != 0) {
	 
			 String result =
			 String.format(
			 "Gast Nr: " + getGastNr() + "\n"
			 + "Vorname: " + getVorname() + "\n"
			 + "Nachname: " + getNachname() + "\n"
			 + "Geburtsdatum: " + getGeburtsdatum() +"\n"
			 + "Strasse: " + getStrasse() +"\n"
			 + "HausNr: " + getHausNr() +"\n"
			 + "Postleitzahl: " + getPlz() +"\n"
			 + "Ort: " + getOrt() +"\n"
		     + "Land: " + getLand() +"\n"
			 + "Email: " + getEmail() +"\n"
			 + "Telefon: " + getTelefon() +"\n");
	
			 return result; 
			 
		 }
		 
		 return "";
	 
	 }

	/**
	 * 
	 * @param ort Weist dem Gast den �bergebenen Wert zu.
	 */
	public void setGastNr(int gastNr) {
		this.gastNr = gastNr;	
	}

}
