/*
 * Created on 20.01.2004
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package bibtex.rdf;

import java.util.HashMap;
import java.util.Map;

import bibtex.dom.BibtexEntry;
import bibtex.util.BibtexUtil;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import org.apache.log4j.Logger;

/**
 * @author siberski
 */
public class DateHandler implements PropertyHandler {
    Logger log = Logger.getLogger(DateHandler.class);

    private static Map<String,String> monthMap;
    
    private BibtexSchema schema;
    private boolean inCollection;
    
    static {
        monthMap = new HashMap<String,String>();
        monthMap.put("jan", "01");
        monthMap.put("feb", "02");
        monthMap.put("mar", "03");
        monthMap.put("apr", "04");
        monthMap.put("may", "05");
        monthMap.put("jun", "06");
        monthMap.put("jul", "07");
        monthMap.put("aug", "08");
        monthMap.put("sep", "09");
        monthMap.put("oct", "10");
        monthMap.put("nov", "11");
        monthMap.put("dec", "12");
    }

    /**
     * @param bibProperty
     * @param rdfProperty
     */
    public DateHandler(BibtexSchema schema, boolean inCollection) {
        this.schema = schema;
        this.inCollection = inCollection;
    }
    
    /* (non-Javadoc)
     * @see bibtex.rdf.DefaultPropertyHandler#addTriples(bibtex.dom.BibtexEntry, com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)
     */
    public void addTriples(BibtexEntry entry, Resource sourceFile, Resource r) {
        String year = BibtexUtil.getValue(entry, "year");
        if (year != null && !year.equals("")) {
            String month = BibtexUtil.getValue(entry, "month");
            if(schema.createDate()) {
                boolean isInteger = true;
                try {
                    Integer.parseInt(year);
                } catch (NumberFormatException e) {
                    isInteger = false;
                }
                String date = year;
        
                boolean handleMonth;
                if(inCollection) {
                    handleMonth = schema.outputCollectionProperty("month");
                } else {
                    handleMonth = schema.outputEntryProperty("month");
                }
                if (month != null && month.length()>=3 && handleMonth) {
                    String monthShorthand = month.toLowerCase().substring(0,3);
                    String monthNo = (String)monthMap.get(monthShorthand);
                    if ( monthNo != null ) {
                        date += "-" + monthNo;
                    } else {
                        log.warn("didn't recognize month <" + month + "> in entry " + entry.getEntryKey());
                    }
                }
                if(isInteger) {
                    Property dateProp = schema.getDate();
                    BibtexUtil.addProperty(r, date, dateProp, schema.getDatatype(dateProp));                    
                } else {
                    log.warn("can't create date from year <"+year+"> in entry " + entry.getEntryKey());
                }
            } else {
                Property yearProp = schema.getYear();
                BibtexUtil.addProperty(r, year, yearProp, schema.getDatatype(yearProp));                    
                if (month != null && !month.equals("")) {
                    Property monthProp = schema.getMonth();
                    BibtexUtil.addProperty(r, month, monthProp, schema.getDatatype(monthProp));                    
                }
            }
        }
    }
}
