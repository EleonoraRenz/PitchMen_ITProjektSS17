package de.pitchMen.server.db;

/**
 * Bildet Trait-Objekte auf eine relationale Datenbank ab. Ebenfalls ist es m�glich aus Datenbank-Tupel Java-Objekte zu erzeugen.
 * 
 * @author
 */

import java.sql.*;
import java.util.Vector;

public class TraitMapper {
	
	    /**
	     * F�gt ein Trait-Objekt der Datenbank hinzu.
	     * 
	     * @param trait 
	     * @return
	     */
	    public Trait insert(Trait trait) {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * Aktuallisiert ein Trait-Objekt in der Datenbank.
	     * 
	     * @param trait 
	     * @return
	     */
	    public Trait update(Trait trait) {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * L�scht ein Trait-Objekt aus der Datenbank.
	     * 
	     * @param trait 
	     * @return
	     */
	    public void delete(Trait trait) {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * Findet ein Trait-Objekt anhand der �bergebenen Id in der Datenbank.
	     * 
	     * @param id 
	     * @return
	     */
	    public Trait findById(int id) {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * Findet alle Trait-Objekte in der Datenbank.
	     * 
	     * @return
	     */
	    public ArrayList<Trait> findAll() {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * Findet ein Trait-Objekt anhand des �bergebenen Namens in der Datenbank.
	     * 
	     * @param name 
	     * @return
	     */
	    public ArrayList<Trait> findByName(String name) {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * Findet ein Trait-Objekt anhand des �bergebenen Wertes in der Datenbank.
	     * 
	     * @param value 
	     * @return
	     */
	    public ArrayList<Trait> findByValue(String value) {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * @return
	     */
	    public static TraitMapper tratMapper() {
	        // TODO implement here
	        return null;
	    }

	}