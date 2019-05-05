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
	 * Methode pr�ft, ob ein Zimmer mit gleicher Zimmer-Nr. bereits exisitiert. 
	 *
	 * return boolean true, wenn Zimmer bereits exisitiert. False andernfalls.
	 * @param zimmerNr zu pr�fende Zimmer-Nr.
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
	 * Liefert ein Zimmer-Objekt mit der angegebenen Zimmer-Nummer aus der Datenbank zur�ck.
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
	 * Liefert eine Liste von Zimmer-Objekten, welche f�r den Zeitraum zwischen Anreise und Abreise 
	 * zur Buchung zur Verf�gung stehen. Sind Zimmer innerhalb dieses Zeitraums bereits gebucht, so stehen diese
	 * nicht mehr zur Verf�gung (Ausnahme ist der Abreisetag).
	 * 
	 * @param anreise zu buchendes Anreisedatum
	 * @param abreise zu buchendes Abreisedatum
	 * @return ArrayList<Zimmer> alle verf�gbaren Zimmer
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
	 * Pr�ft, ob ein Zimmer f�r den Zeitraum zwischen Anreise und Abreise zur Buchung zur Verf�gung steht.
	 * Exisitiert bereits eine Buchung innerhalb dieses Zeitraums, wird false zur�ck geliefert
	 * (Ausnahme ist der Abreisetag, welcher als buchbarer Tag f�r neue Buchungen gez�hlt wird).
	 *  
	 * @param zimmerNr Zimmer-Nummer des zu pr�fenden Zimmers
	 * @param anreise zu pr�fendes Anreisedatum
	 * @param abreise zu pr�fendes Abreisedatum
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
	 * Pr�ft ein Anreise- und Abreisedatum auf Plausibikit�t. Die Anreise darf nicht hinter der Abreise liegen.
	 * Die Anreise darf nicht vor dem heutigen Datum liegen. An- und Abreisedatum d�rfen nicht auf den gleichen Tag fallen.
	 * 
	 * @param anreise Datum der Anreise
	 * @param abreise Datum der Abreise
	 */
	public void checkDates(LocalDate anreise, LocalDate abreise) {
		if(!abreise.isAfter(anreise) || anreise.isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("F�r An- und Abreise wurden keine plausiblen Werte eingegeben. Bitte pr�fen Sie Ihre Eingaben");
		}
	}

}
