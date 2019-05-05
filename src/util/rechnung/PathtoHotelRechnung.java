package util.rechnung;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import util.Konstanten;



/**
 * Klasse f�r Arbeit mit das Verzeichnis , wo die Datei wird geschpeichert
 * @author Olga Vasilieva
 * @param  Anreisedatum , wird f�r Generirung  Verzeichnisname benutzt.
 */
public class PathtoHotelRechnung {
	private LocalDate anreisePath = null;

	public PathtoHotelRechnung(LocalDate anreise) {
		this.anreisePath = anreise; //Anreise Tag , kann nicht Null sein

	}

	/**
	 * Metod PathtoHotelRechnungExists  wird  das Verzeichnis ,wo werden generirte Datein geschpeichert , nach die Existenz uberpr�ft und Wenn es notwendig ist, es war geschaffen.
	 * Besteht aus drei Teilen : das Stammverzeichnis plus Jahr des Buchungs und  Monat des Buchugs
	 * 
	 * @return true falls das Verzeichnis , wo die Datei geschpeichert wird , schon
	 *         existirt oder war erfolgreich gemacht.
	 */

	public String PathtoHotelRechnungExists() {
		
		String txtPath = Konstanten.PATH + anreisePath.getYear(); //nemmen Stammverzeichnis und Jahr von Anreise Tag

		File folder = new File(txtPath);
		
		// if the directory does not exist, create it
		try {
			if (!folder.exists()) {
				folder.mkdir();
				//nemmen Stammverzeichnis dann  Jahr von Anreise Tag dann Monat von Anreise Tag
				
				txtPath = txtPath + "\\" + anreisePath.getMonthValue();
				folder = new File(txtPath);

				if (!folder.exists()) {
					folder.mkdir();
				
				}
				//falls Stammverzeichnis und Verzeichnis   Jahr von Anreise Tag schon existiert
			} else {
				//nemmen Stammverzeichnis dann  Jahr von Anreise Tag dann Monat von Anreise Tag
				txtPath = txtPath + "\\" + anreisePath.getMonthValue();
				folder = new File(txtPath);
				if (!folder.exists()) {
					folder.mkdir();
					
				}
			}
			
		} catch (SecurityException se) {
			new IllegalArgumentException(se.toString()  );
		}
		/*
		if (!result) {
			System.out.println("DIR nicht created" + txtPath);

		}
		*/
		return txtPath;
	}

}
