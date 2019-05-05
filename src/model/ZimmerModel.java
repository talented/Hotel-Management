package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import dbUtil.DBHelper;
import util.HelperUtil;
import verwaltung.Zimmer;
import verwaltung.Zimmerkategorie;

public class ZimmerModel extends DBHelper {
	
	private PreparedStatement pr = null;
	private ResultSet rs = null;
	Zimmer zimmer;
	Zimmerkategorie kategorie;
	ZimmerkategorieModel ziModel;
	
	public ZimmerModel() throws SQLException {
		ziModel = new ZimmerkategorieModel();
	}
	
	/**
	 * Methode prüft, ob ein Zimmer mit gleicher Zimmer-Nr. bereits exisitiert. 
	 *
	 * return boolean true, wenn Zimmer bereits exisitiert. False andernfalls.
	 * @param zimmerNr zu prüfende Zimmer-Nr.
	 */
	public boolean existZimmer(int zimmerNr) throws SQLException {

		String sql = "SELECT * FROM Zimmer WHERE zimmerNr = ?";

		try {
			this.pr = this.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pr.setInt(1, zimmerNr);
			rs = pr.executeQuery();

			if (rs.next()) {
				System.out.println("this Zimmer is already in Database..");
				pr.close();
				rs.close();
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Liefert ein Zimmer-Objekt mit der angegebenen Zimmer-Nummer aus der Datenbank zurück.
	 * 
	 * @param zimmerNr zu suchende Zimmer-Nummer
	 * @return Zimmer gesuchtes Zimmer-Objekt
	 * @throws SQLException wenn SQL-Fehler auftreten
	 */
	public Zimmer getZimmerByZimmerNr(int zimmerNr) throws SQLException {
		
		String sql = "SELECT * FROM Zimmer WHERE zimmerNr = ?";

		try {
			this.pr = this.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pr.setInt(1, zimmerNr);
			rs = pr.executeQuery();

			if (rs.next()) {
				kategorie = ziModel.getZimmerKategorie(rs.getInt("kategorieNr"));
				
				zimmer = new Zimmer(rs.getInt("zimmerNr"), kategorie);
				
			} else {
				System.out.println("Zimmer not found in database!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pr.close();
			rs.close();
		}
		
		return zimmer;
	}
	
	/**
	 * Liefert eine Liste von Zimmer-Objekten, welche für den Zeitraum zwischen Anreise und Abreise 
	 * zur Buchung zur Verfügung stehen. Sind Zimmer innerhalb dieses Zeitraums bereits gebucht, so stehen diese
	 * nicht mehr zur Verfügung (Ausnahme ist der Abreisetag).
	 * 
	 * @param anreise zu buchendes Anreisedatum
	 * @param abreise zu buchendes Abreisedatum
	 * @return ArrayList<Zimmer> alle verfügbaren Zimmer
	 * @throws SQLException wenn SQL-Fehler auftreten
	 */
	public ArrayList<Zimmer> getVerfuegbareZimmer(LocalDate anreise, LocalDate abreise) throws SQLException {
		
		checkDates(anreise, abreise);
		
		ArrayList<Zimmer> alleZimmern = new ArrayList<>();
		ArrayList<Zimmer> belegteZimmern = new ArrayList<>();
		
		String getZimmerSql = "SELECT * FROM Zimmer";
		
		try {
			pr = this.getConnection().prepareStatement(getZimmerSql);

			rs = pr.executeQuery();
			while (rs.next()) {	
				kategorie = ziModel.getZimmerKategorie(rs.getInt("kategorieNr"));
				zimmer = new Zimmer(rs.getInt(1), kategorie);
				alleZimmern.add(zimmer);
			}
				
			for (Zimmer zimmer : alleZimmern) {
				
				String getJoinSql = "SELECT b.anreise, b.abreise FROM Buchung b INNER JOIN Zimmer z ON "
						+ "z.zimmerNr = b.zimmerNr WHERE b.storniert = false AND b.zimmerNr = ?";
				
				pr = this.getConnection().prepareStatement(getJoinSql);

				pr.setInt(1, zimmer.getZimmerNr());
				rs = pr.executeQuery();

				while (rs.next() && HelperUtil.isDatesOverLapped(anreise, abreise, LocalDate.parse(rs.getString("anreise")), LocalDate.parse(rs.getString("abreise")))) {
					belegteZimmern.add(zimmer);
				}
				
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			rs.close();
			pr.close();
		}
		
		alleZimmern.removeAll(belegteZimmern);		
		
		return alleZimmern;
		
	}
	
	/**
	 * Prüft, ob ein Zimmer für den Zeitraum zwischen Anreise und Abreise zur Buchung zur Verfügung steht.
	 * Exisitiert bereits eine Buchung innerhalb dieses Zeitraums, wird false zurück geliefert
	 * (Ausnahme ist der Abreisetag, welcher als buchbarer Tag für neue Buchungen gezählt wird).
	 *  
	 * @param zimmerNr Zimmer-Nummer des zu prüfenden Zimmers
	 * @param anreise zu prüfendes Anreisedatum
	 * @param abreise zu prüfendes Abreisedatum
	 * @return true, wenn in diesem Zeitraum buchbar. False andernfalls
	 * @throws SQLException wenn SQL-Fehler auftreten
	 */
	public boolean istZimmerVerfuegbar(int zimmerNr, LocalDate anreise, LocalDate abreise) throws SQLException {
		
		checkDates(anreise, abreise);
		
		String getJoinSql = "SELECT b.anreise, b.abreise FROM Buchung b INNER JOIN Zimmer z ON "
				+ "z.zimmerNr = b.zimmerNr WHERE b.storniert = false AND b.zimmerNr = ?";
		
		try {
			pr = this.getConnection().prepareStatement(getJoinSql);
			pr.setInt(1, zimmerNr);
			rs = pr.executeQuery();
			
			while (rs.next() && HelperUtil.isDatesOverLapped(anreise, abreise, LocalDate.parse(rs.getString("anreise")), LocalDate.parse(rs.getString("abreise")))) {
				return false;
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			rs.close();
			pr.close();
		}
			
		return true;
	}
	
	/**
	 * Prüft ein Anreise- und Abreisedatum auf Plausibikität. Die Anreise darf nicht hinter der Abreise liegen.
	 * Die Anreise darf nicht vor dem heutigen Datum liegen. An- und Abreisedatum dürfen nicht auf den gleichen Tag fallen.
	 * 
	 * @param anreise Datum der Anreise
	 * @param abreise Datum der Abreise
	 */
	public void checkDates(LocalDate anreise, LocalDate abreise) {
		if(!abreise.isAfter(anreise) || anreise.isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("Für An- und Abreise wurden keine plausiblen Werte eingegeben. Bitte prüfen Sie Ihre Eingaben");
		}
	}

}
