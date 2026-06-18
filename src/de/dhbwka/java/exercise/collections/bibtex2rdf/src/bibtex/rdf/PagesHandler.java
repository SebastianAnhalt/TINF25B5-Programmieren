/*
 * Created on 20.01.2004
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package bibtex.rdf;

import bibtex.dom.BibtexEntry;
import bibtex.util.BibtexUtil;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import org.apache.log4j.Logger;

/**
 * @author siberski
 */
public class PagesHandler implements PropertyHandler {
    Logger log = Logger.getLogger(PagesHandler.class);

    private BibtexSchema schema;
    
    /**
     * @param bibProperty
     * @param rdfProperty
     */
    public PagesHandler(BibtexSchema schema) {
        this.schema = schema;
    }
    
    /* (non-Javadoc)
     * @see bibtex.rdf.DefaultPropertyHandler#addTriples(bibtex.dom.BibtexEntry, com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)
     */
    public void addTriples(BibtexEntry entry, Resource sourceFile, Resource r) {
        String pages = BibtexUtil.getValue(entry, "pages");
        if (pages != null && !pages.equals("")) {
            int i = pages.indexOf('-');
            while(i > 0) {
                String before = pages.substring(0,i).trim();
                int j = i+1;
                while(pages.indexOf('-',j) > 0) {
                    j++;
                }
                String after = pages.substring(j).trim();
                pages = before + "-" + after;
                i =pages.indexOf('-', i+1);
            }
            Property pagesProp = schema.getPages();
            BibtexUtil.addProperty(r, pages, pagesProp, schema.getDatatype(pagesProp));
        }
    }
}
