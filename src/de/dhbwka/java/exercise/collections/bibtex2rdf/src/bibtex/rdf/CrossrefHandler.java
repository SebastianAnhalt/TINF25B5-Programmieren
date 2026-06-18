/*
 * Created on 20.01.2004
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package bibtex.rdf;

import bibtex.dom.BibtexEntry;
import bibtex.util.BibtexUtil;

import com.hp.hpl.jena.rdf.model.Resource;

import org.apache.log4j.Logger;

/**
 * @author siberski
 */
public class CrossrefHandler implements PropertyHandler {
    Logger log = Logger.getLogger(CrossrefHandler.class);

    
    private BibtexSchema schema;
    private String baseUri;
    
    /**
     * @param bibProperty
     * @param rdfProperty
     */
    public CrossrefHandler(BibtexSchema schema, String baseUri) {
        this.schema = schema;
        this.baseUri = baseUri;
    }
    
    /* (non-Javadoc)
     * @see bibtex.rdf.DefaultPropertyHandler#addTriples(bibtex.dom.BibtexEntry, com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)
     */
    public void addTriples(BibtexEntry entry, Resource sourceFile, Resource r) {
        String crossref = BibtexUtil.getValue(entry, "crossref");
        Resource referencedPub = r.getModel().createResource(baseUri+crossref);
        r.addProperty(schema.getCrossref(), referencedPub);
    }
}
