package de.pitchMen.server.db;

/**
 * Bildet Company-Objekte auf eine relationale Datenbank ab. Ebenfalls ist es m�glich aus Datenbank-Tupel Java-Objekte zu erzeugen.
 *
 * @author Heike
 *
 */

import java.sql.*;
import java.util.Vector;


public class CompanyMapper {

	    /**
	     * F�gt ein Company-Objekt der Datenbank hinzu.
	     * 
	     * @param company 
	     * @return
	     */
	    public Company insert(Company company) {
	        // TODO implement here
	        return null;
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

	    /**
	     * @return
	     */
	    public static CompanyMapper companyMapper() {
	        // TODO implement here
	        return null;
	    }

	
}
