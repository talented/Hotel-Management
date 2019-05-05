package model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import verwaltung.Buchung;
import verwaltung.Gast;
import verwaltung.ZimmerUpd;

public class MainModel {

	private static Gast gast;
	private static Buchung buchung;
	private static ZimmerUpd zimmer;
	private static GastModel gastModel;
	private static BuchungModel buchungModel;
	private static ZimmerModelUpd zimmerModel;

	public static void main(String[] args) throws SQLException, InterruptedException {

		gastModel = new GastModel();
		buchungModel = new BuchungModel();
		zimmerModel = new ZimmerModelUpd();
		buchung = new Buchung();
		zimmer = new ZimmerUpd();

//		System.out.println(gastModel.isDatabaseConnected());
		
		/*
		 * Gast testing methode
		 */
		
//		System.out.println(gastModel.existGast(1008));

		getGaesteListe();

//		addGast();
		
//		getGastByGastNr(1007);
		
//		LocalDate ldObj = LocalDate.of(1952, 01, 04);
		
//		getGast("456fgh", "jackson", ldObj);
		
//		updateGast();
		
		/*
		 * Buchung testing methode
		 */
		
//		System.out.println(buchungModel.existBuchung("2019-7"));
		
		getBuchungsListe();
		
//		addBuchung();
		
//		getBuchungByBuchungsNr("2019-9");
		
//		switchStorniert("2019-8");
		
//		updateBuchung();
		
		/*
		 * Zimmer testing methode
		 */
		
//		System.out.println(zimmerModel.existZimmer(4));
				
//		getZimmerByZimmerNr(2);
		
//		getVerfuegbareZimmern();
		
//		istZimmerVerfuegbar();
		
	}


	private static void istZimmerVerfuegbar() throws SQLException {
		LocalDate anreise = LocalDate.of(2019, 5, 7);
		LocalDate abreise = LocalDate.of(2019, 5, 28);
		System.out.println(zimmerModel.istZimmerVerfuegbar(4, anreise, abreise));
		
	}


	private static void getVerfuegbareZimmern() throws SQLException {
		LocalDate anreise = LocalDate.of(2019, 11, 27);
		LocalDate abreise = LocalDate.of(2019, 11, 28);
		ArrayList<ZimmerUpd> zimmern = zimmerModel.getVerfuegbareZimmer(anreise, abreise);
		
		System.out.println(zimmern);
		
		for (ZimmerUpd zimmer : zimmern) {
			System.out.println(zimmer.getAll());
		}	
		
	}


	private static void getZimmerByZimmerNr(int zimmerNr) throws SQLException {
		zimmer = zimmerModel.getZimmerByZimmerNr(zimmerNr);
		System.out.println(zimmer);
		
	}



	/*
	 * Buchung testing methode
	 */
	
	private static void getBuchungByBuchungsNr(String buchungsNr) throws SQLException {
		Buchung buchung = buchungModel.getBuchungByBuchungsNr(buchungsNr);
		System.out.println(buchung.getAll());
		
	}

	private static void getBuchungsListe() throws SQLException {
		System.out.println(buchungModel.getBuchungsListe());
//		System.out.println(buchungModel.getBuchungsListe().get(index));
		
	}
	
	private static void addBuchung() throws InterruptedException, SQLException {
		
		LocalDate anreise = LocalDate.of(2019, 9, 1);
		LocalDate abreise = LocalDate.of(2019, 9, 11);
		
		buchung.setAnreise(anreise);
		buchung.setAbreise(abreise);
		buchung.setFruehstueck(false);
		buchung.setZahlungsart("RECHNUNG");
		buchung.setAnzahlPersonen(4);
		buchung.setKinderbett(true);
		buchung.setGastNr(1010);
		buchung.setZimmerNr(3);
		buchung.setStorniert(false);
		
		try {
			buchungModel.addBuchung(buchung);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void switchStorniert(String buchungsNr) throws SQLException {

		buchungModel.switchStorniert(buchungsNr);
		
	}
	
	private static void updateBuchung() throws SQLException {
		
//		buchung = buchungModel.getBuchungByBuchungsNr("2019-7");
		
		buchung.setGastNr(1002);
		buchung.setZimmerNr(2);
		
		try {
			buchungModel.updateBuchung(buchung);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	/*
	 * Gast testing methode
	 */

	private static void getGaesteListe() throws SQLException {
				
//		System.out.println(gastModel.getGaesteListe().get(1009).getGastNr());
		for (Gast gast : gastModel.getGaesteListe()) {
			System.out.println(gast.getAll());
		}
//		System.out.println(gastModel.getGaesteListe());

	}

	private static void addGast() {
		
		gast = new Gast("tom2", "cruise", LocalDate.of(1979, 8, 1), "wonderlandstr.", "16", "123BC", "Miami", "USA", "bradpitt@gmail.com", "1111555111");
		
		gast.setGastNr(0);
//		gast.setVorname("foo5");
//		gast.setNachname("bar5");
//		gast.setGeburtsdatum(LocalDate.of(1979, 8, 1));
//		gast.setStrasse("londoner str.");
//		gast.setHausNr("53");
//		gast.setPlz("12345");
//		gast.setOrt("London");
//		gast.setLand("England");
//		gast.setEmail("abc@gmail.com");
//		gast.setTelefon("00441234567");

		try {
			gastModel.addGast(gast);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void getGastByGastNr(int gastNr) throws SQLException {
		Gast gast = gastModel.getGastByGastNr(gastNr);
		System.out.println(gast.getAll());
	}
	
	private static void getGast(String vorname, String nachname, LocalDate geburtsDatum) throws SQLException {
		
		Gast gast = gastModel.getGast(vorname, nachname, geburtsDatum);
		System.out.println(gast.getAll());
		
	}
	
	private static void updateGast() throws SQLException {
		
		gast = gastModel.getGastByGastNr(1008);
		
		gast.setVorname("ozzy");
		gast.setNachname("ozbourne");
//		gast.setGeburtsdatum(LocalDate.of(1979, 8, 1));
//		gast.setStrasse("londoner str.");
//		gast.setHausNr("53");
//		gast.setPlz("12345");
//		gast.setOrt("London");
//		gast.setLand("England");
//		gast.setEmail("abc@gmail.com");
//		gast.setTelefon("00441234567");

		try {
			gastModel.updateGast(gast);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void testing() {
		String year = String.valueOf(LocalDate.now().getYear());
		System.out.println(year);		
	}
	

}
