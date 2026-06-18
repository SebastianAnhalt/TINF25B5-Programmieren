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
public class AddressHandler implements PropertyHandler {
    private String bibProperty;
    private BibtexSchema schema;

    /**
     * @param bibProperty
     * @param rdfProperty
     */
    public AddressHandler(BibtexSchema schema, String bibProperty) {
        this.schema = schema;
        this.bibProperty = bibProperty;
    }

    /*
     * (non-Javadoc)
     * 
     * @see bibtex.rdf.DefaultPropertyHandler#addTriples(bibtex.dom.BibtexEntry,
     *      com.hp.hpl.jena.rdf.model.Resource,
     *      com.hp.hpl.jena.rdf.model.Model)
     */
    public void addTriples(BibtexEntry entry, Resource sourceFile, Resource r) {
        String adr = BibtexUtil.getValue(entry, bibProperty);
        if (adr != null && !adr.equals("")) {
            RDFNode address;
            if (schema.createAddressResource()) {
                Resource adrRes = r.getModel().createResource();
                address = adrRes;
                int split = adr.lastIndexOf(',');
                Property localityProp=schema.getAddressLocality();
                if (split > 0) {
                    String locality = adr.substring(0, split).trim();
                    BibtexUtil.addProperty(adrRes, locality, localityProp, schema.getDatatype(localityProp));
                    String country = adr.substring(split + 1).trim();
                    Property countryProp=schema.getAddressCountry();
                    BibtexUtil.addProperty(adrRes, country, countryProp, schema.getDatatype(countryProp));
                } else {
                    BibtexUtil.addProperty(adrRes, adr, localityProp, schema.getDatatype(localityProp));
                }
            } else {
                address = r.getModel().createLiteral(adr);
            }
            r.addProperty(schema.getProperty(bibProperty), address);
        }
    }
}
