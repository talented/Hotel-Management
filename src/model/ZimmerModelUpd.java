package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import dbUtil.DBHelper;
import util.HelperUtil;
import verwaltung.ZimmerUpd;

public class ZimmerModelUpd extends DBHelper {
	
	private PreparedStatement pr = null;
	private ResultSet rs = null;
	ZimmerUpd zimmer;
	
	public ZimmerModelUpd() {
		super();
		zimmer = new ZimmerUpd();
	}
	
	/**
	 * Diese Methode pr�ft dass die gleiche zimmerNr schon existiert.
	 *
	 * return boolean
	 * @param zimmerNr
	 */
	public boolean existZimmer(int zimmerNr) throws SQLException {

		String sql = "SELECT * FROM Zimmer WHERE zimmerNr = ?";

		try {
			this.pr = this.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pr.setInt(1, zimmerNr);
			rs = pr.executeQuery();

//			int autoGeneratedID = rs.getInt(1);
//			System.out.println(autoGeneratedID);

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
	
	public ZimmerUpd getZimmerByZimmerNr(int zimmerNr) throws SQLException {
		
		String getSql = "SELECT * FROM Zimmer WHERE zimmerNr = ?";

		try {
			this.pr = this.getConnection().prepareStatement(getSql, Statement.RETURN_GENERATED_KEYS);
			pr.setInt(1, zimmerNr);
			rs = pr.executeQuery();

			if (rs.next()) {
				zimmer = new ZimmerUpd(rs.getInt(1), rs.getInt(2));
				
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
	
	public ArrayList<ZimmerUpd> getVerfuegbareZimmer(LocalDate anreise, LocalDate abreise) throws SQLException {
		
		ArrayList<ZimmerUpd> alleZimmern = new ArrayList<>();
		ArrayList<ZimmerUpd> belegteZimmern = new ArrayList<>();
		
		String getZimmerSql = "SELECT * FROM Zimmer";
		
		try {
			pr = this.getConnection().prepareStatement(getZimmerSql);

			rs = pr.executeQuery();
			while (rs.next()) {				
				zimmer = new ZimmerUpd(rs.getInt(1), rs.getInt(2));
				alleZimmern.add(zimmer);
			}
				
			for (ZimmerUpd zimmer : alleZimmern) {
				
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
	
	public boolean istZimmerVerfuegbar(int zimmerNr, LocalDate anreise, LocalDate abreise) throws SQLException {
		
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

}
