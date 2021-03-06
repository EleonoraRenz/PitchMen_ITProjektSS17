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
    
       
    private int jobPostingId;


	public int getJobPostingId() {
		return jobPostingId;
	}


	public void setJobPostingId(int jobPostingId) {
		this.jobPostingId = jobPostingId;
	}
}
