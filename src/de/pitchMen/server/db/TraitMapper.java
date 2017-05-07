package de.pitchMen.server.db;
/**
 * Die Klasse TraitMapper bildet Trait-Objekte auf einer relationalen Datenbank ab. 
 * Ebenfalls ist es m�glich aus den Datenbank-Tupel Java-Objekte zur erzeugen. 
 * 
 * Zur Verwaltung der Objekte implementiert die Mapper-Klasse entsprechende Methoden (Insert, Search, delete, update).
 * 
 * @author Lars
 */
import java.sql.*;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import de.pitchMen.shared.bo.Trait;

public class TraitMapper {

	/**
	 * Die Klasse TraitMapper wird nur einmal instantiiert
	 * (Singleton-Eigenschaft). Damit diese Eigenschaft gegeben ist, wird eine
	 * Variable mit dem Schl�sselwort static und dem Standardwert null erzeugt.
	 * Sie speichert die Instanz dieser Klasse.
	 */

	private static TraitMapper traitMapper = null;

	/**
	 * Ein gesch�tzter Konstruktor verhindert das erneute erzeugen von weiteren
	 * Instanzen dieser Klasse.
	 */

	protected TraitMapper() {

	}

	/**
	 * Methode zum sicherstellen der Singleton-Eigenschaft. Diese sorgt daf�r,
	 * dass nur eine einzige Instanz der TraitMapper-Klasse existiert.
	 * Aufgerufen wird die Klasse somit �ber TraitMapper.traitMapper() und nicht
	 * �ber den New-Operator.
	 * 
	 * @return traitMapper
	 */

	public static TraitMapper traitMapper() {
		if (traitMapper == null) {
			traitMapper = new TraitMapper();
		}
		return traitMapper;
	}

	/**
	 * F�gt ein Trait-Objekt der Datenbank hinzu.
	 * 
	 * @param trait
	 * @return trait
	 */
	public Trait insert(Trait trait) throws ClassNotFoundException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Abfrage des zuletzt hinzugef�gten Prim�rschl�ssels (id). Die
			 * aktuelle id wird um eins erh�ht.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM trait");

			trait.setId(rs.getInt("maxid") + 1);
			stmt = con.createStatement();

			/**
			 * SQL-Anweisung zum Einf�gen des neuen Traits in die Datenbank
			 */
			stmt.executeUpdate("INSERT INTO trait (id, name, value)" + "VALUES (" + trait.getId() + ", '"
					+ trait.getName() + "', '" + trait.getValue() + "')");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return trait;
	}

	/**
	 * Aktualisiert ein Trait-Objekt in der Datenbank.
	 * 
	 * @param trait
	 * @throws ClassNotFoundException
	 * @return trait
	 */
	public Trait update(Trait trait) throws ClassNotFoundException {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE trait SET name='" + trait.getName() + "', value= '" + trait.getValue()
			+ "' WHERE id= " + trait.getId());
		}

		/**
		 * Das aufrufen des printStackTrace bietet die M�glichkeit, die
		 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
		 * ausgegeben, was passiert ist und wo im Code es passiert ist.
		 * 
		 */
		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return trait;
	}

	/**
	 * L�scht ein Trait-Objekt aus der Datenbank.
	 * 
	 * @param trait
	 * @throws ClassNotFoundException
	 */
	public void delete(Trait trait) throws ClassNotFoundException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM trait WHERE id=" + trait.getId());
		}
		/**
		 * Das aufrufen des printStackTrace bietet die M�glichkeit, die
		 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
		 * ausgegeben, was passiert ist und wo im Code es passiert ist.
		 * 
		 */
		catch (SQLException e2) {
			e2.printStackTrace();
			;
		}

	}

	/**
	 * Findet ein Trait-Objekt anhand der �bergebenen Id in der Datenbank.
	 * 
	 * @param id
	 * @return
	 */
	public Trait findById(int id) throws ClassNotFoundException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, name, value FROM trait WHERE id=" + id);

			/**
			 * Zu einem Prim�rschl�ssel exisitiert nur max ein Datenbank-Tupel,
			 * somit kann auch nur einer zur�ckgegeben werden. Es wird mit einer
			 * IF-Abfragen gepr�ft, ob es f�r den angefragten Prim�rschl�ssel
			 * ein DB-Tupel gibt.
			 */

			if (rs.next()) {
				Trait trait = new Trait();
				trait.setId(rs.getInt("id"));
				trait.setName(rs.getString("name"));
				trait.setValue(rs.getString("value"));
				return trait;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	/**
	 * Findet alle Trait-Objekte in der Datenbank.
	 * 
	 * @return result
	 */
	public ArrayList<Trait> findAll() throws ClassNotFoundException {
		Connection con = DBConnection.connection();

		ArrayList<Trait> result = new ArrayList<Trait>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id AS ID, name as NAME, value AS VALUE FROM trait ORDER BY id");

			/**
			 * Da es sein kann, dass mehr als nur ein Datenbank-Tupel in der
			 * Tabelle trait vorhanden ist, muss das Abfragen des ResultSet so
			 * oft erfolgen (while-Schleife), bis alle Tupel durchlaufen wurden.
			 * Die DB-Tupel werden in Java-Objekte transformiert und
			 * anschlie�end der ArrayList hinzugef�gt.
			 */

			while (rs.next()) {
				Trait trait = new Trait();
				trait.setId(rs.getInt("ID"));
				trait.setName(rs.getString("NAME"));
				trait.setValue(rs.getString("VALUE"));

				result.add(trait);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * Findet Trait-Objekte anhand des �bergebenen Namens in der Datenbank.
	 * 
	 * @param name
	 * @return result
	 */
	public ArrayList<Trait> findByName(String name) throws ClassNotFoundException {
		Connection con = DBConnection.connection();

		ArrayList<Trait> result = new ArrayList<Trait>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT id, name, value FROM trait WHERE name LIKE '" + name + "' ORDER BY id");

			/**
			 * Da es sein kann, dass mehr als nur ein Datenbank-Tupel in der
			 * Tabelle trait mit dem �bergebenen Namen vorhanden ist, muss das
			 * Abfragen des ResultSet so oft erfolgen (while-Schleife), bis alle
			 * Tupel durchlaufen wurden. Die DB-Tupel werden in Java-Objekte
			 * transformiert und anschlie�end der ArrayList hinzugef�gt.
			 */
			while (rs.next()) {
				Trait trait = new Trait();
				trait.setId(rs.getInt("id"));
				trait.setName(rs.getString("name"));
				trait.setValue(rs.getString("value"));

				result.add(trait);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	/**
	 * Findet ein Trait-Objekt anhand des �bergebenen Wertes(value) in der
	 * Datenbank.
	 * 
	 * @param value
	 * @return
	 */
	public ArrayList<Trait> findByValue(String value) throws ClassNotFoundException {
		Connection con = DBConnection.connection();

		ArrayList<Trait> result = new ArrayList<Trait>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT id, name, value FROM trait WHERE value LIKE '" + value + "' ORDER BY id");

			/**
			 * Da es sein kann, dass mehr als nur ein Datenbank-Tupel in der
			 * Tabelle trait mit dem �bergebenen Wert (value) vorhanden ist,
			 * muss das Abfragen des ResultSet so oft erfolgen (while-Schleife),
			 * bis alle Tupel durchlaufen wurden. Die DB-Tupel werden in
			 * Java-Objekte transformiert und anschlie�end der ArrayList
			 * hinzugef�gt.
			 */
			while (rs.next()) {
				Trait trait = new Trait();
				trait.setId(rs.getInt("ID"));
				trait.setName(rs.getString("NAME"));
				trait.setValue(rs.getString("VALUE"));

				result.add(trait);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

}