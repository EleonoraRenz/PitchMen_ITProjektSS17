package de.pitchMen.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.pitchMen.shared.bo.Company;

/**
 * Die Klasse CompanyMapper bildet Company-Objekte auf einer relationale
 * Datenbank ab. Ebenfalls ist es m�glich aus Datenbank-Tupel Java-Objekte zu
 * erzeugen.
 * 
 * Zur Verwaltung der Objekte implementiert die Mapper-Klasse entsprechende
 * Methoden (insert, search, delete, update).
 * 
 * @author Heike
 */

public class CompanyMapper {

	/**
	 * Die Klasse CompanyMapper wird nur einmal instantiiert
	 * (Singelton-Eigenschaft). amit diese Eigenschaft erf�llt werden kann,
	 * wird zun�chst eine Variable mit dem Schl�sselwort static und dem
	 * Standardwert null erzeugt. Sie speichert die Instanz dieser Klasse.
	 */
	private static CompanyMapper companyMapper = null;

	/**
	 * Ein gesch�tzter Konstrukter verhindert eine neue Instanz dieser Klasse zu
	 * erzeugen.
	 */
	protected CompanyMapper() {
	}

	/**
	 * Methode zum Sicherstellen der Singleton-Eigenschaft. Diese sorgt daf�r,
	 * dass nur eine einzige Instanz der companyMapper-Klasse existiert.
	 * Aufgerufen wird die Klasse somit �ber CompanyMapper.companyMapper() 
	 * und nicht �ber den New-Operator.
	 * 
	 * @return companyMapper
	 */
	public static CompanyMapper companyMapper() {
		if (companyMapper == null) {
			companyMapper = new CompanyMapper();
		}
		return companyMapper;
	}

	/**
	 * F�gt ein Company-Objekt der Datenbank hinzu. Und gibt das korrigierte
	 * Company-Objekt zur�ck.
	 * 
	 * @param company
	 * @return company
	 */
	public Company insert(Company company) {
		/**
		 *  DB-Verbindung holen.
		 */
		Connection con = DBConnection.connection();

		try {
			/**
			 * leeres SQL-Statement (JDBC) anlegen.
			 */
			Statement stmt = con.createStatement();

			/**
			 * Abfrage des als letztes hinzugef�gten Prim�rschl�ssels des Datensatzes. Die aktuelle id wird um eins erh�ht.
			 * Statement ausf�llen und als Query an die Datenbank senden.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM company");

			company.setId(rs.getInt("maxid") + 1);
			stmt = con.createStatement();
			/**
			 * SQL-Anweisung zum Einf�gen des neuen Datensatzes in die Datenbank.
			 */
				stmt.executeUpdate("INSERT INTO company (id, name, description)" + "VALUES ( " + company.getId() + ", '"
						+ company.getName() + "' ,'" + company.getDescription() + "')");
			/**
			 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
			 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
			 * ausgegeben, was passiert ist und wo im Code es passiert ist.
			 */	
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return company;
	}

	/**
	 * Aktuallisiert ein Company-Objekt in der Datenbank.
	 * 
	 * @param company
	 * @return company
	 */
	public Company update(Company company) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * SQL-Anweisung zur Aktualisierung des �bergebenen Datensatzes in der Datenbank.
			 */
			stmt.executeUpdate("UPDATE company SET Name='" + company.getName() + "', " + "description='"
					+ company.getDescription() + "' " + "WHERE id=" + company.getId());
		}
		/**
		 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
		 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
		 * ausgegeben, was passiert ist und wo im Code es passiert ist.
		 * 
		 */
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return company;
	}

	/**
	 * L�scht ein Company-Objekt aus der Datenbank.
	 * 
	 * @param company
	 */
	public void delete(Company company) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * SQL-Anweisung zum L�schen des �bergebenen Datensatzes in der Datenbank.
			 */
			stmt.executeUpdate("DELETE FROM company " + "WHERE id=" + company.getId());
		}
		/**
		 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
		 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
		 * ausgegeben, was passiert ist und wo im Code es passiert ist.
		 */
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Findet ein Company-Objekt anhand der �bergebenen Id in der Datenbank.
	 * 
	 * @param id
	 * @return company
	 */
	public Company findById(int id) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * SQL-Anweisung zum Finden des Datensatzes, anhand der �bergebenen Id, in der Datenbank.
			 */
			ResultSet rs = stmt.executeQuery("SELECT id, name, description FROM company " + "WHERE id=" + id);
			/**
			 * Zu einem Prim�rschl�ssel exisitiert nur max ein Datenbank-Tupel,
			 * somit kann auch nur einer zur�ckgegeben werden. Es wird mit einer
			 * If-Abfragen gepr�ft, ob es f�r den angefragten Prim�rschl�ssel
			 * ein DB-Tupel gibt.
			 */
			if (rs.next()) {
				Company company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setDescription(rs.getString("description"));
				return company;
			}
			/**
			 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
			 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
			 * ausgegeben, was passiert ist und wo im Code es passiert ist.
			 */
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	/**
	 * Findet alle Company-Objekte in der Datenbank.
	 * 
	 * @return ArrayList<Company>
	 */
	public ArrayList<Company> findAll() {
		Connection con = DBConnection.connection();
		/**
		 * Erzeugen einer ArrayList.
		 */
		ArrayList<Company> result = new ArrayList<Company>();

		try {
			Statement stmt = con.createStatement();
			/**
			 * SQL-Anweisung zum Finden aller Datensatzes in der Datenbank, sortiert nach der Id.
			 */
			ResultSet rs = stmt.executeQuery("SELECT id, name, description FROM company " + "ORDER BY id");
			/**
			 * Da es sein kann, dass mehr als nur ein Datenbank-Tupel in der
			 * Tabelle company vorhanden ist, muss das Abfragen des ResultSet so
			 * oft erfolgen (while-Schleife), bis alle Tupel durchlaufen wurden.
			 * Die DB-Tupel werden in Java-Objekte transformiert und
			 * anschlie�end der ArrayList hinzugef�gt.
			 */
			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setDescription(rs.getString("description"));
				result.add(company);
			}
			/**
			 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
			 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
			 * ausgegeben, was passiert ist und wo im Code es passiert ist.
			 */
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * Findet ein Company-Objekt anhand des �bergebenen Namens in der Datenbank.
	 * 
	 * @param name
	 * @return ArryList<company>
	 */
	public ArrayList<Company> findByName(String name) {
		Connection con = DBConnection.connection();

		ArrayList<Company> result = new ArrayList<Company>();

		try {
			Statement stmt = con.createStatement();
			/**
			 * SQL-Anweisung zum Finden des Datensatzes, nach dem gesuchten Namen, in der Datenbank, sortiert nach der Id.
			 */
			ResultSet rs = stmt.executeQuery(
					"SELECT id, name, description FROM company " + "WHERE name LIKE '" + name + "' ORDER BY id");
			/**
			 * Da es sein kann, dass mehr als nur ein Datenbank-Tupel in der
			 * Tabelle company mit dem �bergebenen Namen vorhanden ist, muss das
			 * Abfragen des ResultSet so oft erfolgen (while-Schleife), bis alle
			 * Tupel durchlaufen wurden. Die DB-Tupel werden in Java-Objekte
			 * transformiert und anschlie�end der ArrayList hinzugef�gt.
			 */
			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setDescription(rs.getString("description"));
				result.add(company);
			}
			/**
			 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
			 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
			 * ausgegeben, was passiert ist und wo im Code es passiert ist.
			 * 
			 */
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

}
