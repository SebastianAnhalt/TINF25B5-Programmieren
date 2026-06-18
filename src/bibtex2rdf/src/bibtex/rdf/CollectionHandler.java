/*
 * Created on 20.01.2004
 * 
 * To change this generated comment go to Window>Preferences>Java>Code
 * Generation>Code Template
 */
package bibtex.rdf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import bibtex.dom.BibtexEntry;
import bibtex.rdf.labeling.Labeler;
import bibtex.util.BibtexUtil;

import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import org.apache.log4j.Logger;


/**
 * @author siberski
 */
public class CollectionHandler implements PropertyHandler {
    private static Logger log = Logger.getLogger(CollectionHandler.class);
    
    private BibtexSchema schema;
    private PublicationCollection collections;
    private Map<String,PropertyHandler> propertyMap;
    protected String bibProperty;
    protected Property rdfProperty;
    
 
    /**
     * @param bibProperty
     * @param rdfProperty
     */
    public CollectionHandler(BibtexSchema schema, PersonCollection persons, 
    PublicationCollection collections, 
    String bibProperty, Property rdfProperty) {
        this.schema = schema;
        this.collections = collections;
        this.bibProperty = bibProperty;
        this.rdfProperty = rdfProperty;
        
        propertyMap = new HashMap<String,PropertyHandler>();
        
        propertyMap.put("year", new DateHandler(schema, true));
        propertyMap.put("editor", new PersonListHandler(schema, persons, "editor", schema.getEditor()));
        propertyMap.put("location", new AddressHandler(schema, "location"));
        propertyMap.put("address", new AddressHandler(schema, "address"));
        propertyMap.put("publisher", new OrganizationHandler(schema, persons,
            "publisher", 
            schema.getPublisher()));
    }

    @SuppressWarnings("unchecked")
	public void addTriples(BibtexEntry entry, Resource sourceFile, Resource r) {
        if (collections.findPublication(entry, bibProperty) == null) {
            String title = BibtexUtil.getValue(entry, bibProperty);
            if (title != null && !title.equals("")) {
                if (schema.createCollectionResource()) {
                    String collectionType = schema.getCollectionType(entry.getEntryType(), bibProperty);
                    Resource collection = collections.createPublication(entry,
                        bibProperty);
                    r.addProperty(rdfProperty, collection);
                    collection.addProperty(RDF.type, schema.getEntryType(collectionType));

                    if(schema.outputCollectionProperty(collectionType, "shorttitle")) {
                        String sh = BibtexUtil.getTitleShorthand(title);
                        if (sh != null) {
                            Property shortTitleProp = schema.getShortTitle();
                            BibtexUtil.addProperty(collection, sh, shortTitleProp, schema.getDatatype(shortTitleProp));
                        }
                    }
                    
                    for(Iterator<String> i = entry.getFields().keySet().iterator(); i.hasNext();) {
                        String entryProperty = i.next();
                        if (schema.outputCollectionProperty(collectionType, entryProperty)) {
                            PropertyHandler h =(PropertyHandler)propertyMap.get(entryProperty);
                            if(entryProperty.equals(bibProperty)) {
                                Property titleProp = schema.getTitle();
                                BibtexUtil.addProperty(collection, title, titleProp, schema.getDatatype(titleProp));
                            } else if (entryProperty.equals("address")) {
                                if (h != null && BibtexUtil.getValue(entry, "publisher") == null
                                        && BibtexUtil.getValue(entry, "location") == null) {
                                    // we interpret the address as address of an event
                                    h.addTriples(entry, sourceFile, collection);
                                }                                
                            } else {
                                if (h != null) {
                                    h.addTriples(entry, sourceFile, collection);
                                } else {
                                    Property entryRdfProperty = schema.getProperty(entryProperty);
                                    String o = BibtexUtil.getValue(entry, entryProperty);
                                    if( entryRdfProperty != null) {
                                        if (o != null && !o.equals("")) {
                                            BibtexUtil.addProperty(collection, o, entryRdfProperty, schema.getDatatype(entryRdfProperty));
                                        }
                                    } else {
                                        // handle unknown properties
                                        if(schema.getNamespaceForUnknown() != null) {
                                            String ns = schema.getNamespaceForUnknown();
                                            Property p = collection.getModel().createProperty(ns, entryProperty);
                                            BibtexUtil.addProperty(collection, o, p, schema.getDatatype(p));
                                        } else {
                                            log.warn("ignoring property " + entryProperty + "; no handler or RDF property registered");
                                        }
                                    }
                                }
                            }
                        }
                    }                    
                    
                    if (schema.outputCollectionProperty(collectionType, BibtexSchema.SOURCE_FILE)) {
                        collection.addProperty(schema.getSourceFile(), sourceFile);
                    }
                    if (schema.outputCollectionProperty(collectionType, BibtexSchema.LABEL) && !collection.hasProperty(schema.getLabel())) {
                        String labelPattern = schema.getLabelPattern(entry.getEntryType());
                        collection.addProperty(schema.getLabel(), Labeler.getLabel(entry, labelPattern));
                    }
                } else {
                    if(schema.outputEntryProperty(bibProperty)) {
                        r.addProperty(rdfProperty, title);
                    }
                }
            }
        }
    }
}
