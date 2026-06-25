/*
 * Created on 20.01.2004
 * 
 * To change this generated comment go to Window>Preferences>Java>Code
 * Generation>Code Template
 */
package bibtex.rdf;

import bibtex.dom.BibtexEntry;
import bibtex.util.BibtexUtil;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;


/**
 * @author siberski
 */
public class OrganizationHandler implements PropertyHandler {
    private BibtexSchema schema;
    private PersonCollection persons;
    protected String bibProperty;
    protected Property rdfProperty;
    
    
    /**
     * @param bibProperty
     * @param rdfProperty
     */
    public OrganizationHandler(BibtexSchema schema, PersonCollection persons, String bibProperty,
            Property rdfProperty) {
        this.schema = schema;
        this.persons = persons;
        this.bibProperty = bibProperty;
        this.rdfProperty = rdfProperty;
    }

    public void addTriples(BibtexEntry entry, Resource sourceFile, Resource r) {
        String fullName = BibtexUtil.getValue(entry, bibProperty);
        
        if (fullName != null) {
            Resource p = persons.getPerson(fullName, entry.getEntryKey(), bibProperty, sourceFile);
            
            RDFNode o = p;
            if (!schema.createPersonResource()) {
                o = p.getProperty(schema.getPersonFullname()).getObject();
            }
            r.addProperty(rdfProperty, o);
        }
    }
}
