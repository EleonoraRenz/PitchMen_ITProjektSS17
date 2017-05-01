package de.pitchMen.server.db;

/**
 * Bildet Company-Objekte auf eine relationale Datenbank ab. Ebenfalls ist es m�glich aus Datenbank-Tupel Java-Objekte zu erzeugen.
 *
 * @author Heike
 *
 */

import java.sql.*;
import java.util.Vector;

import de.pitchMen.shared.bo.Company;

public class CompanyMapper {

	/**
	 * Die Klasse CompanyMapper wird nur einmal instantiiert. Die Variable ist
	 * mit static gekennzeichnet, da sie die einzige Instanz dieser Klasse
	 * speichert.
	 */

	private static CompanyMapper companyMapper = null;

	/**
	 * Ein gesch�tzter Konstrukter verhinder eine neue Instanz dieser Klasse zu
	 * erzeugen.
	 */

	protected CompanyMapper() {
	}

	/*
	 * Methode zum sicherstellen der Singleton-Eigenschaft. Es wird somit
	 * existiert nur eine einzige Instanz der CompanyMapper
	 */

	public static CompanyMapper companyMapper() {

		if (companyMapper == null)

		{
			companyMapper = new CompanyMapper();
		}
		return companyMapper;
	}

	/**
	 * F�gt ein Company-Objekt der Datenbank hinzu.
	 * 
	 * @param company
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public Company insert(Company company) throws ClassNotFoundException {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/* Abfrage des Prim�rschl�ssels, letzten hinzugef�gten Datensatzes 
			 * Der aktuelle Prim�rschl�ssel wird um eins erh�ht
			 */

			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM company");
			
			if (rs.next()) {
				company.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				stmt.executeUpdate("INSERT INTO company (id, name, description) VALUES ( " + company.getId() + ", 'Bosch' ,'Thermotechnik')");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return company;
	}

	/**
	 * Aktuallisiert ein Company-Objekt in der Datenbank.
	 * 
	 * @param company
	 * @return
	 */
	public Company update(Company company) {
		// TODO implement here
		return null;
	}

	/**
	 * L�scht ein Company-Objekt aus der Datenbank.
	 * 
	 * @param company
	 * @return
	 */
	public void delete(Company company) {
		// TODO implement here
		return null;
	}

	/**
	 * Findet ein Company-Objekt anhand der �bergebenen Id in der Datenbank.
	 * 
	 * @param id
	 * @return
	 */
	public Company findById(int id) {
		// TODO implement here
		return null;
	}

	/**
	 * Findet alle Company-Objekte in der Datenbank.
	 * 
	 * @return
	 */
	public ArrayList<Company> findAll() {
		// TODO implement here
		return null;
	}

	/**
	 * Findet ein Company-Objekt anhand des �bergebenen Namens in der Datenbank.
	 * 
	 * @param name
	 * @return
	 */
	public ArrayList<Company> findByName(String name) {
		// TODO implement here
		return null;
	}



}
