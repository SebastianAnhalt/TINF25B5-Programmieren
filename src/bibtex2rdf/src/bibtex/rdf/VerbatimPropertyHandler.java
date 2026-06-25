/*
 * Created on 19.01.2004
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package bibtex.rdf;

import bibtex.dom.BibtexEntry;
import bibtex.dom.BibtexPersonList;
import bibtex.dom.BibtexString;
import bibtex.util.BibtexUtil;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;


/**
 * @author siberski
 */
public class VerbatimPropertyHandler implements PropertyHandler {
    protected String bibProperty;
    protected Property rdfProperty;
    private BibtexSchema schema;


    /**
     * 
     */
    public VerbatimPropertyHandler(String bibProperty, Property rdfProperty, BibtexSchema schema) {
        this.bibProperty = bibProperty;
        this.rdfProperty = rdfProperty;
        this.schema = schema;
    }

    public void addTriples(BibtexEntry entry, Resource sourceFile, Resource r) {
        String o = getVerbatimValue(entry, bibProperty);
        if (o != null && !o.equals("")) {
            BibtexUtil.addProperty(r, o, rdfProperty, schema
                .getDatatype(rdfProperty));
        }
    }

    private String getVerbatimValue(BibtexEntry entry, String property) {
        String result = null;
        Object value = entry.getFieldValue(property);
        if (value instanceof BibtexString) {
            result = BibtexUtil.getValue(entry, property);
        } else if (value instanceof BibtexPersonList) {
            result = BibtexUtil.getNameList(entry, property, " and ");
        }else {
            if (value != null) {
                result = value.toString();
                if (result.charAt(0) == '{'
                    && result.charAt(result.length() - 1) == '}') {
                    result = result.substring(1, result.length() - 1);
                }
            }
        }
        return result;
    }
}
