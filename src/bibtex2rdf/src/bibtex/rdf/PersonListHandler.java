/*
 * Created on 20.01.2004
 * 
 * To change this generated comment go to Window>Preferences>Java>Code
 * Generation>Code Template
 */
package bibtex.rdf;

import java.util.Iterator;

import bibtex.dom.BibtexEntry;
import bibtex.dom.BibtexPerson;
import bibtex.dom.BibtexPersonList;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;


/**
 * @author siberski
 */
public class PersonListHandler implements PropertyHandler {
    private BibtexSchema schema;
    private PersonCollection persons;
    protected String bibProperty;
    protected Property rdfProperty;
    
    /**
     * @param bibProperty
     * @param rdfProperty
     */
    public PersonListHandler(BibtexSchema schema, PersonCollection persons, String bibProperty, Property rdfProperty) {
        this.schema = schema;
        this.persons = persons;
        this.bibProperty = bibProperty;
        this.rdfProperty = rdfProperty;
    }

    @SuppressWarnings("unchecked")
	public void addTriples(BibtexEntry entry, Resource sourceFile, Resource r) {
        Object value = entry.getFieldValue(bibProperty);
        if (value instanceof BibtexPersonList) {
            BibtexPersonList pl = (BibtexPersonList)value;
            Seq list = null;
            if (schema.createSeqForPersonList()) {
                for(StmtIterator i = r.listProperties(rdfProperty); list == null && i.hasNext();) {
                    RDFNode obj = i.nextStatement().getObject();
                    if(obj instanceof Resource) {
                        Resource objRes = (Resource)obj;
                        if(objRes.hasProperty(RDF.type, RDF.Seq)) {
                            list = r.getModel().getSeq(objRes);
                        }
                    }
                }
                if(list ==null) {
                    list = r.getModel().createSeq();
                    r.addProperty(rdfProperty, list);
                }
            }
            for (Iterator<BibtexPerson> i = pl.getList().iterator(); i.hasNext(); ) {
                Resource p = persons.getPerson(i.next(), entry.getEntryKey(), bibProperty, sourceFile);
                RDFNode o = p;
                if (!schema.createPersonResource()) {
                    if (p.equals(schema.getEtAl())) {
                        RDFDatatype dt = schema.getDatatype(rdfProperty);
                        String etal = "et al.";
                        if(dt == null){
                            o = r.getModel().createLiteral(etal);
                        }
                        o = r.getModel().createTypedLiteral(etal, dt);
                    } else {
                        o = p.getProperty(schema.getPersonFullname()).getObject();
                    }
                }
                if(schema.createSeqForPersonList()) {
                    if(!list.contains(o)) {
                        list.add(o);
                    }
                } else {
                    r.addProperty(rdfProperty, o);
                }
            }
        }
    }
}
