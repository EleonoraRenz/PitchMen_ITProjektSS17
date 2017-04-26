package de.pitchMen.server.db;

import java.sql.*;
import java.util.Vector;

/**
 * Bildet Project-Objekte auf eine relationale Datenbank ab. Ebenfalls ist es m�glich aus Datenbank-Tupel Java-Objekte zu erzeugen.
 * 
 * @author 
 */
public class ProjectMapper {

     /**
     * F�gt ein Project-Objekt der Datenbank hinzu.
     * 
     * @param project 
     * @return
     */
    public Project insert(Project project) {
        // TODO implement here
        return null;
    }

    /**
     * Aktuallisiert ein Project-Objekt in der Datenbank.
     * 
     * @param project 
     * @return
     */
    public Project update(Project project) {
        // TODO implement here
        return null;
    }

    /**
     * L�scht ein Project-Objekt aus der Datenbank.
     * 
     * @param project 
     * @return
     */
    public void delete(Project project) {
        // TODO implement here
        return null;
    }

    /**
     * Findet ein Project-Objekt anhand der �bergebenen Id in der Datenbank.
     * 
     * @param id 
     * @return
     */
    public Project findById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * Findet alle Project-Objekte in der Datenbank.
     * 
     * @return
     */
    public ArrayList<Project> findAll() {
        // TODO implement here
        return null;
    }

    /**
     * Findet ein Project-Objekt anhand des �bergebenen Start-Datums in der Datenbank.
     * 
     * @param dateOpened 
     * @return
     */
    public ArrayList<Project> findByDateOpened(Date dateOpened) {
        // TODO implement here
        return null;
    }

    /**
     * Findet ein Project-Objekt anhand des �bergebenen End-Datums in der Datenbank.
     * 
     * @param dateClosed 
     * @return
     */
    public ArrayList<Project> findByDateClosed(Date dateClosed) {
        // TODO implement here
        return null;
    }

    /**
     * Findet ein Project-Objekt anhand des �bergebenen Titels in der Datenbank.
     * 
     * @param title 
     * @return
     */
    public ArrayList<Project> findByTitle(String title) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public static ProjectMapper projectMapper() {
        // TODO implement here
        return null;
    }

}