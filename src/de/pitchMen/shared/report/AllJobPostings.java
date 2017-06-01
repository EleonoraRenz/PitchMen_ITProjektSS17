package de.pitchMen.shared.report;

/**
 * Report zur Abfrage von allen Ausschreibungen.  
 * Subklasse von SimpleReport. Enthält keine eigenen Attribute und Methoden.
 * Dient der Objektorientierten-Programmierung. 
 * 
 * @author
 */
public class AllJobPostings extends SimpleReport {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
       
    private int profilId;


	public int getProfilId() {
		return profilId;
	}


	public void setProfilId(int profilId) {
		this.profilId = profilId;
	}
}
