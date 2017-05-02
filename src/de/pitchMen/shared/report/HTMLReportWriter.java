package de.pitchMen.shared.report;

	/**
	 * Subklasse von ReportWriter.
	 * 
	 * "Ein <code>ReportWriter</code>, der Reports mittels HTML formatiert. Das im
	 *  Zielformat vorliegende Ergebnis wird in der Variable <code>reportText</code>
	 *  abgelegt und kann nach Aufruf der entsprechenden Prozessierungsmethode mit
	 *  <code>getReportText()</code> ausgelesen werden. 
	 * @quelle Thies."
	 *  
	 * 
	 * @author
	 */
	public class HTMLReportWriter  extends ReportWriter {

	    /**
	     * 
	     */
	    private String reportText;

	    /**
	     * @return
	     */
	    public void restReportText() {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * @return
	     */
	    public String getReportText() {
	        // TODO implement here
	        return "";
	    }

	
}