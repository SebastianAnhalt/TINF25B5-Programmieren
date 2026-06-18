package bibtex.util;

import bibtex.dom.BibtexPerson;

/**
 * 
 */
public class PersonData {
    public boolean isOthers = false;
    public String prefix = null;
    public String family = null;
    public String suffix = null;
    public String given = null;
    public String other = null;
    public String completeName = null;
    
    public PersonData(){
    	// do nothing
    }
    
	public PersonData(BibtexPerson p) {
	    if (p.isOthers()) {
	        this.isOthers = true;
	    } else {
	        StringBuffer name = new StringBuffer();
	        if (p.getFirst() != null && !p.getFirst().equals("")) {
	            String first = BibtexUtil.removeTexChars(p.getFirst()).trim();
	            name.append(first + " ");
	            int split = first.indexOf(" ");
	            if (split > 0) {
	            	this.given = first.substring(0, split).trim();
	            	this.other = first.substring(split + 1).trim();
	            } else {
	            	this.given = first;
	            }
	        }
	        if (p.getPreLast() != null && !p.getPreLast().equals("")) {
	        	this.prefix = BibtexUtil.removeTexChars(p.getPreLast()).trim();
	            name.append(this.prefix + " ");
	        }
	        this.family = BibtexUtil.removeTexChars(p.getLast()).trim();
	        name.append(this.family);
	        if (p.getLineage() != null && !p.getLineage().equals("")) {
	        	this.suffix = BibtexUtil.removeTexChars(p.getLineage()).trim();
	            name.append(", " + this.suffix);
	        }
	        this.completeName = name.toString();
	    }
	}
}