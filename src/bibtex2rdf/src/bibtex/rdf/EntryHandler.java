/*
 * Created on 20.01.2004
 * 
 * To change this generated comment go to Window>Preferences>Java>Code
 * Generation>Code Template
 */
package bibtex.rdf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import bibtex.dom.BibtexEntry;
import bibtex.rdf.labeling.Labeler;
import bibtex.util.BibtexUtil;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;


/**
 * @author siberski
 */
public class EntryHandler {
    private static final String JOURNAL = "journal";

    private static Logger log = Logger.getLogger(EntryHandler.class);
    
    private BibtexSchema schema;
    private Map<String,PropertyHandler> propertyMap;
    private Set<String> ignoredProperties = new HashSet<String>();
    
    
    /**
     * @param bibProperty
     * @param rdfProperty
     */
    public EntryHandler(BibtexSchema schema, PersonCollection persons, PublicationCollection collections, String baseUri) {
        this.schema = schema;
        
        propertyMap = new HashMap<String,PropertyHandler>();
        
        propertyMap.put(BibtexSchema.AUTHOR, new PersonListHandler(schema, persons, BibtexSchema.AUTHOR, schema.getAuthor()));
        propertyMap.put(BibtexSchema.EDITOR, new PersonListHandler(schema, persons, BibtexSchema.EDITOR, schema.getEditor()));
        
        propertyMap.put(BibtexSchema.BOOKTITLE, new CollectionHandler(schema, persons, collections, BibtexSchema.BOOKTITLE, schema.getBooktitle()));
        propertyMap.put(JOURNAL, new CollectionHandler(schema, persons, collections, JOURNAL, schema.getJournal()));
        propertyMap.put(BibtexSchema.SERIES, new CollectionHandler(schema, persons, collections, BibtexSchema.SERIES, schema.getSeries()));
        propertyMap.put(BibtexSchema.CROSSREF, new CrossrefHandler(schema, baseUri));
        
        propertyMap.put(BibtexSchema.YEAR, new DateHandler(schema, false)); 
        propertyMap.put(BibtexSchema.PAGES, new PagesHandler(schema));

        propertyMap.put(BibtexSchema.PUBLISHER, new OrganizationHandler(schema, persons, BibtexSchema.PUBLISHER, schema.getPublisher()));
        propertyMap.put(BibtexSchema.INSTITUTION, new OrganizationHandler(schema, persons, BibtexSchema.INSTITUTION, schema.getInstitution()));
        propertyMap.put(BibtexSchema.ORGANIZATION, new OrganizationHandler(schema, persons, BibtexSchema.ORGANIZATION, schema.getOrganization()));
        propertyMap.put(BibtexSchema.SCHOOL, new OrganizationHandler(schema, persons, BibtexSchema.SCHOOL, schema.getSchool()));
        
        for(String bibtexVerbatimField : schema.getVerbatimProperties() ){
            propertyMap.put(bibtexVerbatimField, new VerbatimPropertyHandler(bibtexVerbatimField, schema.getProperty(bibtexVerbatimField), schema));
        }
    }

    @SuppressWarnings("unchecked")
	public void addTriples(BibtexEntry entry, Resource sourceFile, Resource e) {
        addType(entry.getEntryType(), e);
        
        boolean inCollection = false;
        for(Iterator<String> i = entry.getFields().keySet().iterator(); i.hasNext();) {
            String bibProperty = i.next();
            boolean ch = (propertyMap.get(bibProperty) instanceof CollectionHandler);
            inCollection = inCollection || ch;
            if (ch || schema.outputEntryProperty(bibProperty) ) {
                addTriples(entry, sourceFile, e, bibProperty);
            } else {
                if (!schema.outputCollectionProperty(bibProperty) && !ignoredProperties.contains(bibProperty)) {
                    log.info("property <" + bibProperty + "> is not declared as output property; ignoring");
                    ignoredProperties.add(bibProperty);
                }
            }
        }
        
        if(!inCollection) {
            for(Iterator<String> i = entry.getFields().keySet().iterator(); i.hasNext();) {
                String bibProperty = i.next();
                if (!schema.outputEntryProperty(bibProperty) && schema.outputCollectionProperty(bibProperty)) {
                    addTriples(entry, sourceFile, e, bibProperty);
                }
            }
        }
        if (sourceFile != null && schema.outputEntryProperty(BibtexSchema.SOURCE_FILE)) {
            e.addProperty(schema.getSourceFile(), sourceFile);
        }
        if (schema.outputEntryProperty(BibtexSchema.LABEL) && !e.hasProperty(schema.getLabel())) {
            String labelPattern = schema.getLabelPattern(entry.getEntryType());
            e.addProperty(schema.getLabel(), Labeler.getLabel(entry, labelPattern));
        }
    }

    private void addTriples(BibtexEntry entry, Resource sourceFile, Resource e, String bibProperty) {
        PropertyHandler h =(PropertyHandler)propertyMap.get(bibProperty);
        if (h != null) {
            h.addTriples(entry, sourceFile, e);
        } else {
            String o = BibtexUtil.getValue(entry, bibProperty);

            Property rdfProperty = schema.getProperty(bibProperty);
            if( rdfProperty != null) {
                if (o != null && !o.equals("")) {
                    BibtexUtil.addProperty(e, o, rdfProperty, schema.getDatatype(rdfProperty));
                }
            } else {
                // handle unknown properties
                if(!schema.outputCollectionProperty(bibProperty) && schema.getNamespaceForUnknown() != null) {
                    String ns = schema.getNamespaceForUnknown();
                    rdfProperty = e.getModel().createProperty(ns, bibProperty);
                    BibtexUtil.addProperty(e, o, rdfProperty, schema.getDatatype(rdfProperty));
                } else {
                    if (!ignoredProperties.contains(bibProperty)) {
                        log.warn("ignoring property " + bibProperty + " in entry " + entry.getEntryKey() +"; no handler or RDF property registered");
                        ignoredProperties.add(bibProperty);
                    }
                }
            }
        }
    }

    /**
     * @param string
     * @param r
     * @param model
     */
    private void addType(String type, Resource r) {
        Resource rdfType = schema.getEntryType(type);
        if (rdfType != null) {
            r.addProperty(RDF.type, rdfType);
        }
    }
    
    
}
