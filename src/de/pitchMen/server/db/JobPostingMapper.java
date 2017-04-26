package de.pitchMen.server.db;

/**
 * Bildet JobPosting-Objekte auf eine relationale Datenbank ab. Ebenfalls ist es m�glich aus Datenbank-Tupel Java-Objekte zu erzeugen.
 * @author Heike
 *
 */

import java.sql.*;
import java.util.Vector;

public class JobPostingMapper {

	    /**
	     * F�gt ein JobPosting-Objekt der Datenbank hinzu.
	     * 
	     * @param jobPosting 
	     * @return
	     */
	    public JobPosting insert(JobPosting jobPosting) {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * Aktuallisiert ein JobPosting-Objekt in der Datenbank.
	     * 
	     * @param jobPostingt 
	     * @return
	     */
	    public JobPosting update(JobPosting jobPostingt) {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * L�scht ein JobPosting-Objekt aus der Datenbank.
	     * 
	     * @param jobPosting 
	     * @return
	     */
	    public void delete(JobPosting jobPosting) {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * Findet ein JobPosting-Objekt anhand der �bergebenen Id in der Datenbank.
	     * 
	     * @param id 
	     * @return
	     */
	    public JobPosting findById(int id) {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * Findet alle JobPosting-Objekte in der Datenbank.
	     * 
	     * @return
	     */
	    public ArrayList<JobPosting> findAll() {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * Findet ein JobPosting-Objekt anhand des �bergebenen Textes in der Datenbank.
	     * 
	     * @param text 
	     * @return
	     */
	    public ArrayList<JobPosting> findByText(String text) {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * Findet ein JobPosting-Objekt anhand des �bergebenen Titels in der Datenbank.
	     * 
	     * @param tatle 
	     * @return
	     */
	    public ArrayList<JobPosting> findByTitle(String tatle) {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * @return
	     */
	    public static JobPostingMapper jobPostingMapper() {
	        // TODO implement here
	        return null;
	    }

	
	
}
