package de.pitchMen.server.db;

import java.sql.*;
import java.util.ArrayList;
import de.pitchMen.shared.bo.Rating;

/**
 * Bildet Rating-Objekte auf eine relationale Datenbank ab. Ebenfalls ist es
 * m�glich aus Datenbank-Tupel Java-Objekte zu erzeugen.
 * 
 * @author Lars
 */

public class RatingMapper {

	/**
	 * Die Klasse RatingMapper wird nur einmal instantiiert
	 * (Singleton-Eigenschaft). Damit diese Eigenschaft erf�llt werden kann,
	 * wird zun�chst eine Variable mit dem Schl�sselwort static und dem
	 * Standardwert null erzeugt. Sie speichert die Instanz dieser Klasse.
	 */

	private static RatingMapper ratingMapper = null;

	/**
	 * Ein gesch�tzter Konstruktor verhindert das erneute erzeugen von weiteren
	 * Instanzen dieser Klasse.
	 */

	protected RatingMapper() {

	}

	/**
	 * Methode zum sicherstellen der Singleton-Eigenschaft. Diese sorgt daf�r,
	 * dass nur eine einzige Instanz der RatingMapper-Klasse existiert.
	 * Aufgerufen wird die Klasse somit �ber RatingMapper.ratingMapper() und
	 * nicht �ber den New-Operator.
	 * 
	 * @return ratingMapper
	 */

	public static RatingMapper ratingMapper() {
		if (ratingMapper == null) {
			ratingMapper = new RatingMapper();
		}
		return ratingMapper;
	}

	/**
	 * F�gt ein Rating-Objekt der Datenbank hinzu.
	 * 
	 * @param rating
	 * @return trait
	 */
	public Rating insert(Rating rating) throws ClassNotFoundException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Abfrage des zuletzt hinzugef�gten Prim�rschl�ssels (id). Die
			 * aktuelle id wird um eins erh�ht.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM rating");

			rating.setId(rs.getInt("maxid") + 1);
			stmt = con.createStatement();

			/**
			 * SQL-Anweisung zum Einf�gen des neuen Rating-Tupels in die
			 * Datenbank
			 */
			stmt.executeUpdate("INSERT INTO rating (id, statement, score)" + "VALUES (" + rating.getId() + ", '"
					+ rating.getStatement() + "', '" + rating.getScore() + "')");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return rating;
	}

	/**
	 * Aktualisiert ein Rating-Objekt in der Datenbank.
	 * 
	 * @param rating
	 * @throws ClassNotFoundException
	 * @return rating
	 */
	public Rating update(Rating rating) throws ClassNotFoundException {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE rating SET statement='" + rating.getStatement() + "', score= '"
					+ rating.getScore() + "' WHERE id= " + rating.getId());
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return rating;
	}

	/**
	 * L�scht ein Rating-Objekt aus der Datenbank.
	 * 
	 * @param rating
	 * @throws ClassNotFoundException
	 */
	public void delete(Rating rating) throws ClassNotFoundException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM rating WHERE id=" + rating.getId());
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Findet ein Rating-Objekt anhand der �bergebenen Id in der Datenbank.
	 * 
	 * @param id
	 * 
	 */
	public Rating findById(int id) throws ClassNotFoundException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, statement, score FROM rating WHERE id=" + id);

			/**
			 * Zu einem Prim�rschl�ssel exisitiert nur max ein Datenbank-Tupel,
			 * somit kann auch nur einer zur�ckgegeben werden. Es wird mit einer
			 * IF-Abfragen gepr�ft, ob es f�r den angefragten Prim�rschl�ssel
			 * ein DB-Tupel gibt.
			 */

			if (rs.next()) {
				Rating rating = new Rating();
				rating.setId(rs.getInt("id"));
				rating.setStatement(rs.getString("statement"));
				rating.setScore(rs.getFloat("score"));
				return rating;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	/**
	 * Findet alle Rating-Objekte in der Datenbank.
	 * 
	 * @return result
	 */
	public ArrayList<Rating> findAll() throws ClassNotFoundException {
		Connection con = DBConnection.connection();

		ArrayList<Rating> result = new ArrayList<Rating>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, statement, score FROM rating ORDER BY id");

			while (rs.next()) {
				Rating rating = new Rating();
				rating.setId(rs.getInt("id"));
				rating.setStatement(rs.getString("statement"));
				rating.setScore(rs.getFloat("score"));

				result.add(rating);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * Findet ein Rating-Objekt anhand des �bergebenen Bewertungswerts in der
	 * Datenbank.
	 * 
	 * @param score
	 * @return
	 */
	public ArrayList<Rating> findByScore(float score) throws ClassNotFoundException {
		Connection con = DBConnection.connection();

		ArrayList<Rating> result = new ArrayList<Rating>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT id, statement, score FROM rating WHERE score=" + score + " ORDER BY id");

			while (rs.next()) {
				Rating rating = new Rating();
				rating.setId(rs.getInt("id"));
				rating.setStatement(rs.getString("statement"));
				rating.setScore(rs.getFloat("score"));

				result.add(rating);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

}
