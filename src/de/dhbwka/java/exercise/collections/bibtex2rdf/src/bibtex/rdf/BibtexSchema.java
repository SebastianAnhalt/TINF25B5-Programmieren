/*****************************************************************************
 *
 * Copyright (c) 2003 The Edutella Project
 * 
 * Redistributions in source code form must reproduce the above copyright 
 * and this condition. The contents of this file are subject to the 
 * Sun Project JXTA License Version 1.1 (the "License"); you may not use 
 * this file except in compliance with the License. 
 * A copy of the License is available at http://www.jxta.org/jxta_license.html.
 *  
 *****************************************************************************/

package bibtex.rdf;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.mem.ModelMem;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFException;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DCTerms;
import com.hp.hpl.jena.vocabulary.DC_11;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.VCARD;

/**
 * <p>
 * Defines a vocabulary of Bibtex types 
 * </p>
 * <p>
 * All constants are written like their RDF counterpart, not capitalized
 * as common for Java constants.
 * </p>
 */
public class BibtexSchema {
    private static Logger log = Logger.getLogger(BibtexSchema.class);
    
    private static final String DEFAULT_NS = "http://www.edutella.org/bibtex#";

    public static final String COLLECTION = "collection";
    public static final String PROCEEDINGS = "proceedings";
    public static final String JOURNAL = "journal";
    public static final String BOOK = "book";
    public static final String SERIES = "series";
    public static final String ARTICLE = "article";
    public static final String BOOKLET = "booklet";
    public static final String CONFERENCE = "conference";
    public static final String INBOOK = "inbook";
    public static final String INCOLLECTION = "incollection";
    public static final String INPROCEEDINGS = "inproceedings";
    public static final String MANUAL = "manual";
    public static final String MASTERSTHESIS = "mastersthesis";
    public static final String MISC = "misc";
    public static final String PHDTHESIS = "phdthesis";
    public static final String TECHREPORT = "techreport";
    public static final String UNPUBLISHED = "unpublished";
    public static final String SCHOOL = "school";
    public static final String ORGANIZATION = "organization";
    public static final String INSTITUTION = "institution";
    public static final String PUBLISHER = "publisher";
    public static final String PAGES = "pages";
    public static final String YEAR = "year";
    public static final String CROSSREF = "crossref";
    public static final String BOOKTITLE = "booktitle";
    public static final String EDITOR = "editor";
    public static final String AUTHOR = "author";
    public static final String KEY = "key";
    public static final String ANNOTE = "annote";
    public static final String NOTE = "note";

    public static final String LABEL = "label";
    public static final String SOURCE_FILE = "sourceFile";
    
    private String ns = DEFAULT_NS;
    //--BIBTEX TYPES--
// @article    
// An article from a journal or magazine. 
    private Resource Article;
    
//    @book
//    A book with an explicit publisher. 
    private Resource Book;
    
//    @booklet
//    A work that is printed and bound, but without a named publisher or sponsoring institution. 
    private Resource Booklet;
    
//    @conference
//    The same as inproceedings. 
    private Resource Conference;
    
//    @inbook
//    A part of a book, which may be a chapter (or section or whatever) and/or a range of pages. 
    private Resource InBook;
    
//    @incollection
//    A part of a book having its own title. 
    private Resource InCollection;
    
//    @inproceedings
//    An article in a conference proceedings. 
    private Resource InProceedings;
    
//    @manual
//    Technical documentation. 
    private Resource Manual;
    
//    @mastersthesis
//    A Master's thesis. 
    private Resource MastersThesis;
    
//    @misc
//    Use this type when nothing else fits. 
    private Resource Misc;

//    @phdthesis
//    A PhD thesis. 
    private Resource PhdThesis;
    
//    @proceedings
//    The proceedings of a conference. 
    private Resource Proceedings;
    
//    @techreport
//    A report published by a school or other institution, usually numbered within a series. 
    private Resource TechReport;
    
//    @unpublished
//    A document having an author and title, but not formally published.    
    private Resource Unpublished;
    
    //--BIBTEX FIELDS--
    private Property address;
    private Property location;
    private Property addressLocality;
    private Property addressCountry;
    private boolean createAddressResource;
    
    private Property date;
    private Property year;
    private Property month;
    private boolean createDate;
    
    private Property title;
    private Property shortTitle;
    
    private Property booktitle; 
    private Property journal; 
    private Property series; 
    
    
    private Property pages; 
    private Property note;
    private Property volume;
    private Property number;
    private Property chapter;
    private Property annote;
    private Property edition;
    private Property howpublished;
    private Property url; 
    private Property key; 
    
    private Property crossref;
    
    private Property publisher;
    private Property institution;
    private Property organization;
    private Property school;
    private Property type;
    

    private Resource Journal;
    private Resource Collection;
    private Resource Series;
    private Resource EtAl;

    private Resource Author;
    private Resource Editor;
    
    private Resource Organization;
    private Resource Institution;
    private Resource School;
    private Resource Publisher;
    
    
    private Property author;
    private Property editor;
    
    private Property personFullname;
    private Property personStructuredName;
    private Property nameFamily;
    private Property namePrefix;
    private Property nameSuffix;
    private Property nameGiven;
    private Property nameOther;
    
    private Resource BibFile;
    private Property fileAbsolutePath;
    private Property sourceFile;
    
    private Property label;

    //--DATATYPES OF SOME BIBTEX FIELDS
    private RDFDatatype yearType;
    private RDFDatatype volumeType;
    private RDFDatatype numberType;
    private RDFDatatype dateType;
    private RDFDatatype chapterType;
    
    
    // if set, a Seq is created for an author/editor list
    private boolean createSeqForPersonList;
    
    // if set, a Resource with VCard properties is created for each
    // person/organization. Otherwise, the full name is added as literal
    private boolean createPersonResource;
    
    private boolean createCollectionResource;
    
    private boolean createEntryList;

    private boolean createDatatypes;
    
    private boolean createPersonNameStructure;

    Map<String,String> namespaces = new HashMap<String,String>();

    private String namespaceForUnknown = null;
    
    
    private Map<String,Property> entryProperties = new HashMap<String,Property>();

    private Map<String,Resource> entryTypes = new HashMap<String,Resource>();
    private Map<String,Map<String,String>> collectionTypes = new HashMap<String,Map<String,String>>();
    private Map<String,Resource> personTypes = new HashMap<String,Resource>();
    private Map<String,String> labelPatterns = new HashMap<String,String>();
    private Map<Property,RDFDatatype> datatypes = new HashMap<Property,RDFDatatype>();

    private Set<String> entryOutputProperties;
    private Map<String,Set<String>> collectionOutputPropertyMap = new HashMap<String,Set<String>>();
    private Set<String> personOutputProperties;
    
    private Set<String> specialProperties;
    private Set<String> verbatimProperties;
    
    
    private Model m = new ModelMem();




    public BibtexSchema(boolean seqForPerson, boolean personResource, boolean collectionResource, boolean entryList, boolean datatypes) {
        setDefaults();
        
        createSeqForPersonList = seqForPerson;
        createPersonResource = personResource;
        createCollectionResource = collectionResource;
        createEntryList = entryList;
        createDatatypes = datatypes;
        
        m.setNsPrefix("dc", DC_11.NS);
        m.setNsPrefix("dct", DCTerms.NS);
        m.setNsPrefix("bibtex", ns);
        m.setNsPrefix("vcard", VCARD.getURI());
        
        initMaps();
    }

    
    public BibtexSchema() {
        setDefaults();
        
        m.setNsPrefix("dc", DC_11.NS);
        m.setNsPrefix("dct", DCTerms.NS);
        m.setNsPrefix("bibtex", ns);
        m.setNsPrefix("vcard", VCARD.getURI());
        
        initMaps();
    }

    public BibtexSchema(File propertiesFile) {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(propertiesFile);
            props.load(fis);
            fis.close();
            
            // parse namespaces
            for(Enumeration<Object> i=props.keys();i.hasMoreElements();) {
                String propKey = ((String)i.nextElement()).trim();
                if(propKey.indexOf("ns_")==0) {
                    String nsShorthand = propKey.substring(3);
                    String nsUri = props.getProperty(propKey).trim();
                    if(namespaces.containsKey(nsShorthand)) {
                        log.warn("namespace <" + nsShorthand + "> declared twice; ignoring uri <"+nsUri+">");
                    } 
                    if(nsShorthand.equals("unknown")) {
                        namespaceForUnknown = nsUri;
                    } else {
                        if (nsShorthand.equals("bibtex")) {
                            ns = nsUri;
                        }
                        namespaces.put(nsShorthand, nsUri);
                        m.setNsPrefix(nsShorthand, nsUri);
                    }  
                }
            }
            
            if(namespaceForUnknown != null 
                    && !namespaces.containsValue(namespaceForUnknown)) {
                m.setNsPrefix("unknown", namespaceForUnknown);
            }
            
            setDefaults();
            
            // parse property output lists
            String entryOutput = props.getProperty("entryProperties");
            if( entryOutput != null) {
                entryOutputProperties = parseSet(entryOutput);
            }
            String collectionOutput = props.getProperty("collectionProperties");
            String bookOutput = props.getProperty("bookProperties");
            String journalOutput = props.getProperty("journalProperties");
            String proceedingsOutput = props.getProperty("proceedingsProperties");
            String seriesOutput = props.getProperty("seriesProperties");
            if(collectionOutput != null 
                    || bookOutput != null 
                    || proceedingsOutput != null 
                    || journalOutput != null 
                    || seriesOutput != null){
                // if any collection property is specified, we assume that
                // the user does not wish to use the default specializations
                collectionOutputPropertyMap.remove(BOOK);
                collectionOutputPropertyMap.remove(JOURNAL);
                collectionOutputPropertyMap.remove(PROCEEDINGS);
                collectionOutputPropertyMap.remove(SERIES);
            }
            if( collectionOutput != null) {
                //default collection properties. used if nothing else is specified
                collectionOutputPropertyMap.put(COLLECTION, parseSet(collectionOutput));
            }
            // if collection properties for the specific collections are specified,
            // these are used
            if( bookOutput != null) {
                collectionOutputPropertyMap.put(BOOK, parseSet(bookOutput));
            }
            if( journalOutput != null) {
                collectionOutputPropertyMap.put(JOURNAL, parseSet(journalOutput));
            }
            if( proceedingsOutput != null) {
                collectionOutputPropertyMap.put(PROCEEDINGS, parseSet(proceedingsOutput));
            }
            if( seriesOutput != null) {
                collectionOutputPropertyMap.put(SERIES, parseSet(seriesOutput));
            }

            String personOutput = props.getProperty("personProperties");
            if( personOutput != null) {
                personOutputProperties = parseSet(personOutput);
            }
            
            String verbatimOutput = props.getProperty("verbatimProperties");
            if ( verbatimOutput != null) {
                verbatimProperties = parseSet(verbatimOutput);
            }
            
            // parse label pattern properties
            for(Enumeration<Object> i=props.keys();i.hasMoreElements();) {
                String propKey = (String)i.nextElement();
                String lk = propKey.trim().toLowerCase();
                int index = lk.indexOf("labelpattern");
                if(index >=0){
                    String entryType = lk.substring(0,index);
                    String pattern = props.getProperty(propKey);
                    labelPatterns.put(entryType, pattern);
                }
            }
            
            // parse all other properties
            for(Enumeration<Object> i=props.keys();i.hasMoreElements();) {
                String propKey = ((String)i.nextElement()).trim();
                if(propKey.indexOf("ns_")<0 
                        && propKey.indexOf("Properties") < 0) {
                    try {
                        Object valObj = getValueObject(props, propKey);
                        try {
                            Field propField = BibtexSchema.class.getDeclaredField(propKey);
                            try {
                                propField.set(this, valObj);
                            } catch (IllegalArgumentException e) {
                                log.warn("value <" + valObj + "> is not allowed for property <" + propKey + ">");
                            }
                        } catch (NoSuchFieldException e) {
                            if(valObj instanceof Resource) {
                                if(Character.isUpperCase(propKey.charAt(0))) {
                                    entryTypes.put(propKey.toLowerCase(), (Resource)valObj);
                                } else {
                                    entryProperties.put(propKey.toLowerCase(), (Property)valObj);
                                }
                            } else {
                                log.error("property <" + propKey + "> has invalid value <" + valObj + ">");
                            }
                        }
                    } catch (Exception e) {
                        log.error("exception occurred during parsing of <"+propKey+">:", e);
                    }
                }
            }
            
            
            if(!createCollectionResource) {
                // if we do not create collection resource, 
                // we do not attach any fields to them
                collectionOutputPropertyMap.clear();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } 
        
        initMaps();
    }
    

    public Resource getEntryType(String bibtexType) {
        return (Resource)entryTypes.get(bibtexType.toLowerCase());
    }
    
    public String getCollectionType(String bibtexType, String bibProperty) {
        String result = null;
        Map<String,String> bibtexTypeCollectionMap= collectionTypes.get(bibProperty);
        if(bibtexTypeCollectionMap != null){
            result = (String)bibtexTypeCollectionMap.get(bibtexType);
        }
        if (result == null) {
            result = COLLECTION;
        }
        return result;
    }
    
    public Resource getPersonType(String bibtexProperty) {
        return (Resource)personTypes.get(bibtexProperty.toLowerCase());
    }
    
    public Property getProperty(String bibtexProperty) {
        return (Property)entryProperties.get(bibtexProperty.toLowerCase());
    }
    
    
    public void addNsPrefixes(Model model) {
        model.setNsPrefixes(m);
    }

    
    /**
     * @return Returns the address.
     */
    public Property getAddress() {
        return address;
    }
    /**
     * @return Returns the address_country.
     */
    public Property getAddressCountry() {
        return addressCountry;
    }
    /**
     * @return Returns the address_locality.
     */
    public Property getAddressLocality() {
        return addressLocality;
    }
    /**
     * @return Returns the annote.
     */
    public Property getAnnote() {
        return annote;
    }
    /**
     * @return Returns the author.
     */
    public Property getAuthor() {
        return author;
    }
    /**
     * @return Returns the booktitle.
     */
    public Property getBooktitle() {
        return booktitle;
    }
    /**
     * @return Returns the chapter.
     */
    public Property getChapter() {
        return chapter;
    }
 
    /**
     * @return Returns the createAddressResource.
     */
    public boolean createAddressResource() {
        return createAddressResource;
    }
    /**
     * @return Returns the createCollectionResource.
     */
    public boolean createCollectionResource() {
        return createCollectionResource;
    }
    /**
     * @return Returns the createDate.
     */
    public boolean createDate() {
        return createDate;
    }
    /**
     * @return Returns the createPersonResource.
     */
    public boolean createPersonResource() {
        return createPersonResource;
    }
    /**
     * @return Returns the createSeqForPersonList.
     */
    public boolean createSeqForPersonList() {
        return createSeqForPersonList;
    }
    /**
     * @return Returns the crossref.
     */
    public Property getCrossref() {
        return crossref;
    }
    /**
     * @return Returns the date.
     */
    public Property getDate() {
        return date;
    }
    /**
     * @return Returns the edition.
     */
    public Property getEdition() {
        return edition;
    }
    /**
     * @return Returns the editor.
     */
    public Property getEditor() {
        return editor;
    }

    /**
     * @return Returns the et_al.
     */
    public Resource getEtAl() {
        return EtAl;
    }
    /**
     * @return Returns the howpublished.
     */
    public Property getHowpublished() {
        return howpublished;
    }
    /**
     * @return Returns the institution.
     */
    public Property getInstitution() {
        return institution;
    }
    /**
     * @return Returns the journal.
     */
    public Property getJournal() {
        return journal;
    }
    /**
     * @return Returns the key.
     */
    public Property getKey() {
        return key;
    }
    /**
     * @return Returns the month.
     */
    public Property getMonth() {
        return month;
    }
    /**
     * @return Returns the note.
     */
    public Property getNote() {
        return note;
    }
    /**
     * @return Returns the number.
     */
    public Property getNumber() {
        return number;
    }
    /**
     * @return Returns the organization.
     */
    public Property getOrganization() {
        return organization;
    }
    /**
     * @return Returns the pages.
     */
    public Property getPages() {
        return pages;
    }
    /**
     * @return Returns the person_family.
     */
    public Property getNameFamily() {
        return nameFamily;
    }
    /**
     * @return Returns the person_fullname.
     */
    public Property getPersonFullname() {
        return personFullname;
    }
    /**
     * @return Returns the person_given.
     */
    public Property getNameGiven() {
        return nameGiven;
    }
    /**
     * @return Returns the person_name.
     */
    public Property getPersonStructuredName() {
        return personStructuredName;
    }
    /**
     * @return Returns the person_other.
     */
    public Property getNameOther() {
        return nameOther;
    }
    /**
     * @return Returns the person_prefix.
     */
    public Property getNamePrefix() {
        return namePrefix;
    }
    /**
     * @return Returns the person_suffix.
     */
    public Property getNameSuffix() {
        return nameSuffix;
    }

    /**
     * @return Returns the publisher.
     */
    public Property getPublisher() {
        return publisher;
    }
    /**
     * @return Returns the school.
     */
    public Property getSchool() {
        return school;
    }
    /**
     * @return Returns the series.
     */
    public Property getSeries() {
        return series;
    }
    /**
     * @return Returns the shortTitle.
     */
    public Property getShortTitle() {
        return shortTitle;
    }
    /**
     * @return Returns the title.
     */
    public Property getTitle() {
        return title;
    }
    /**
     * @return Returns the type.
     */
    public Property getType() {
        return type;
    }
    /**
     * @return Returns the url.
     */
    public Property getUrl() {
        return url;
    }
    /**
     * @return Returns the volume.
     */
    public Property getVolume() {
        return volume;
    }
    /**
     * @return Returns the year.
     */
    public Property getYear() {
        return year;
    }

    /**
     * @param bibProperty
     * @return
     */
    public boolean outputEntryProperty(String bibProperty) {
        return entryOutputProperties.contains(bibProperty)
            || ( !specialProperties.contains(bibProperty) && entryOutputProperties.contains("all")
                && ! outputCollectionProperty(bibProperty));
    }

    /**
     * @param collectionType 
     * @param bibProperty
     * @return
     */
    public boolean outputCollectionProperty(String bibProperty) {
        boolean isOutputCollectionProperty = false;
        for(Iterator<String> i = collectionOutputPropertyMap.keySet().iterator();!isOutputCollectionProperty && i.hasNext();){
            isOutputCollectionProperty = outputCollectionProperty(i.next(), bibProperty);
        }
        return isOutputCollectionProperty;
    }

    
    /**
     * @param collectionType 
     * @param bibProperty
     * @return
     */
    public boolean outputCollectionProperty(String entryType, String bibProperty) {
        Set<String> collectionOutputProperties = collectionOutputPropertyMap.get(entryType);
        if ( collectionOutputProperties == null ){
            collectionOutputProperties = collectionOutputPropertyMap.get(COLLECTION);
        }
        return collectionOutputProperties.contains(bibProperty);
    }
    
    /**
     * @param bibProperty
     * @return
     */
    public boolean outputPersonProperty(String bibProperty) {
        return personOutputProperties.contains(bibProperty)
            || (!specialProperties.contains(bibProperty) && personOutputProperties.contains("all"));
    }
    
    /**
     * @return
     */
    public Resource[] getUsedRDFTypes() {
        Resource[] result = new Resource[] {
                        Article, Book, Booklet, InBook,InCollection, InProceedings,
                        Manual, MastersThesis, Misc, PhdThesis, TechReport, Unpublished,
                        Collection, Conference, Journal, Proceedings, Series,
                        Author, Editor, Organization, Institution, School, Publisher,
                        BibFile};
        
        return result;
    }
    /**
     * @return Returns the createEntryList.
     */
    public boolean createEntryList() {
        return createEntryList;
    }

    /**
     * @return Returns the fileAbsolutePath.
     */
    public Property getFileAbsolutePath() {
        return fileAbsolutePath;
    }
    /**
     * @return Returns the sourceFile.
     */
    public Resource getBibFile() {
        return BibFile;
    }

    /**
     * @return Returns the source.
     */
    public Property getSourceFile() {
        return sourceFile;
    }

    /**
     * @return Returns the label.
     */
    public Property getLabel() {
        return label;
    }

    /**
     * @return Returns the namespaceForUnknown.
     */
    public String getNamespaceForUnknown() {
        return namespaceForUnknown;
    }    
    
    
    private Object getValueObject(Properties props, String propKey) {
        String value = props.getProperty(propKey).trim();
        Object valObj;
        if (value.equals("true")) {
            valObj = Boolean.TRUE;
        } else if (value.equals("false")) {
            valObj = Boolean.FALSE;
        } else {
            int firstColonPos = value.indexOf(':');
            if(firstColonPos != -1) {
                String nsShorthand = value.substring(0,firstColonPos);
                String nsUri = (String)namespaces.get(nsShorthand);
                if ( nsUri != null) {
                    value = nsUri+value.substring(firstColonPos+1);
                }
            }
            if(propKey.indexOf("Type") != -1){
                valObj = TypeMapper.getInstance().getTypeByName(value);
            } else if(Character.isUpperCase(propKey.charAt(0))) {
                valObj = m.createResource(value);
            } else {
                valObj = m.createProperty(value);
            }
        }
        return valObj;
    }
    
    public String getLabelPattern(String entryType){
        String pattern = (String)labelPatterns.get(entryType);
        if(pattern == null){
            pattern = (String)labelPatterns.get("default");
        }
        return pattern;
    }
    
    private Set<String> parseSet(String set) {
        Set<String> result =new HashSet<String>();
        StringTokenizer st = new StringTokenizer(set.trim(), " ,;");
        while(st.hasMoreTokens()) {
            String token = st.nextToken();
            result.add(token.trim());
        }
        return result;
    }

    private void setDefaults() {
        try {
            Article = m.createResource(ns + "Article");
            Book = m.createResource(ns + "Book");
            Booklet = m.createResource(ns + "Booklet");
            Conference = m.createResource(ns + "Conference");
            InBook = m.createResource(ns + "InBook");
            InCollection = m.createResource(ns + "InCollection");
            InProceedings = m.createResource(ns + "InProceedings");
            Manual = m.createResource(ns + "Manual");
            MastersThesis = m.createResource(ns + "MastersThesis");
            Misc = m.createResource(ns + "Misc");
            PhdThesis = m.createResource(ns + "PhdThesis");
            Proceedings = m.createResource(ns + "Proceedings");
            TechReport = m.createResource(ns + "TechReport");
            Unpublished = m.createResource(ns + "Unpublished");
            
            address = VCARD.ADR;
            location = VCARD.ADR;
            addressCountry = VCARD.Country;
            addressLocality = VCARD.Locality;
            createAddressResource = true;
            
            date = DC_11.date;
            year = m.createProperty(ns + "year");
            month = m.createProperty(ns + "month");
            createDate = true;
            
            title = DC_11.title;
            shortTitle = m.createProperty(ns + "shortTitle");
            
            booktitle = DCTerms.isPartOf;
            journal = DCTerms.isPartOf;
            series = DCTerms.isPartOf;
            crossref = DCTerms.isPartOf;
            
            annote = m.createProperty(ns + "annote");
            chapter = m.createProperty(ns + "chapter");
            edition = m.createProperty(ns + "edition");
            howpublished = m.createProperty(ns + "howpublished");
            note = m.createProperty(ns + "note");
            number = m.createProperty(ns + "number");
            pages = m.createProperty(ns + "pages");
            type = m.createProperty(ns + "type");
            volume = m.createProperty(ns + "volume");

            url = DC_11.identifier;
            key = DC_11.identifier;
            
            
            publisher = DC_11.publisher;
            institution = m.createProperty(ns + "institution");
            organization = m.createProperty(ns + "organisation");
            school = m.createProperty(ns + "school");

            Journal = m.createResource(ns + "Journal");
            Collection = m.createResource(ns + "Collection");
            Series = m.createResource(ns + "Series");
            EtAl = m.createResource(ns + "Et_al");
            EtAl.addProperty(RDFS.label, "et al.");

            Author = m.createResource(ns + "Person");
            Editor = m.createResource(ns + "Person");
            
            Organization = m.createResource(ns + "Organization");
            Institution = Organization;
            School = Organization;
            Publisher = Organization;
            
            
            author = DC_11.creator;
            editor = m.createProperty(ns + "editor");
            
            personFullname = VCARD.FN;
            personStructuredName = VCARD.N;
            nameFamily = VCARD.Family;
            namePrefix = VCARD.Prefix;
            nameSuffix = VCARD.Suffix;
            nameGiven = VCARD.Given;
            nameOther = VCARD.Other;
            createPersonNameStructure = true;
            
            BibFile = m.createResource(ns + BibtexSchema.SOURCE_FILE);
            fileAbsolutePath = m.createProperty(ns + "absolutePath");
            sourceFile = m.createProperty(ns + "source");

            label = RDFS.label;
            
            createSeqForPersonList = true;
            createPersonResource = true;
            createCollectionResource = true;
            createEntryList = true;
            createDatatypes = false;

            entryOutputProperties = parseSet("all");
            collectionOutputPropertyMap.put(COLLECTION, parseSet(
                "address, booktitle, crossref, editor, journal, location, "
               +"month, number, publisher, series, volume, year, shortTitle"));
            collectionOutputPropertyMap.put(PROCEEDINGS, parseSet(
                "address, booktitle, location, publisher, month, volume, year"));
            collectionOutputPropertyMap.put(JOURNAL, parseSet(
                "address, journal, month, number, publisher, volume, year"));
            collectionOutputPropertyMap.put(SERIES, parseSet(
                "publisher, series"));
            collectionOutputPropertyMap.put(BOOK, parseSet(
                "booktitle, editor, series, year"));
            
            
            personOutputProperties = parseSet("all, label");
            
            specialProperties = parseSet("label, shortTitle, sourceFile");
            verbatimProperties = parseSet("note, annote, key");
            labelPatterns.put("default", "<title>");

            dateType = TypeMapper.getInstance().getTypeByName("http://www.w3.org/2001/XMLSchema#gYearMonth");
            RDFDatatype nni = TypeMapper.getInstance().getTypeByName("http://www.w3.org/2001/XMLSchema#nonNegativeInteger"); 
            yearType = nni;
            volumeType = nni;
            numberType = nni;
            chapterType = nni;
            
        } catch (RDFException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void initMaps() {
        entryTypes.put(ARTICLE, Article);
        entryTypes.put(BOOK, Book);
        entryTypes.put(BOOKLET, Booklet);
        entryTypes.put(CONFERENCE, Conference);
        entryTypes.put(INBOOK, InBook);
        entryTypes.put(INCOLLECTION, InCollection);
        entryTypes.put(INPROCEEDINGS, InProceedings);
        entryTypes.put(MANUAL, Manual);
        entryTypes.put(MASTERSTHESIS, MastersThesis);
        entryTypes.put(MISC, Misc);
        entryTypes.put(PHDTHESIS, PhdThesis);
        entryTypes.put(PROCEEDINGS, Proceedings);
        entryTypes.put(TECHREPORT, TechReport);
        entryTypes.put(UNPUBLISHED, Unpublished);
        entryTypes.put(COLLECTION, Collection);
        entryTypes.put(JOURNAL, Journal);
        entryTypes.put(SERIES, Series);
        
        Map<String,String> booktitleTypes = new HashMap<String,String>();
        booktitleTypes.put(ARTICLE, JOURNAL);
        booktitleTypes.put(INPROCEEDINGS, PROCEEDINGS);
        booktitleTypes.put(CONFERENCE, PROCEEDINGS);
        booktitleTypes.put(BOOK, SERIES);
        booktitleTypes.put(INBOOK, BOOK);
        collectionTypes.put("booktitle", booktitleTypes);

        Map<String,String> seriesTypes = new HashMap<String,String>();
        seriesTypes.put(ARTICLE, SERIES);
        seriesTypes.put(INPROCEEDINGS, SERIES);
        seriesTypes.put(CONFERENCE, SERIES);
        seriesTypes.put(BOOK, SERIES);
        seriesTypes.put(INBOOK, SERIES);
        collectionTypes.put("series", seriesTypes);

        Map<String,String> journalTypes = new HashMap<String,String>();
        journalTypes.put(ARTICLE, JOURNAL);
        collectionTypes.put("journal", journalTypes);
        
        personTypes.put("author", Author);
        personTypes.put("editor", Editor);
        personTypes.put("publisher", Publisher);
        personTypes.put("school", School);
        personTypes.put("organization", Organization);
        personTypes.put("institution", Institution);
        
        entryProperties.put("address", address);
        entryProperties.put("annote", annote);
        entryProperties.put("author", author);
        entryProperties.put("booktitle", booktitle);
        entryProperties.put("chapter", chapter);
        entryProperties.put("crossref", crossref);
        entryProperties.put("editor", editor);
        entryProperties.put("edition", edition);
        entryProperties.put("howpublished", howpublished);
        entryProperties.put("institution", institution);
        entryProperties.put("journal", journal);
        entryProperties.put("key", key);
        entryProperties.put("location", location);
        entryProperties.put("month", month);
        entryProperties.put("note", note);
        entryProperties.put("number", number);
        entryProperties.put("organization", organization);
        entryProperties.put("pages", pages);
        entryProperties.put("publisher", publisher);
        entryProperties.put("school", school);
        entryProperties.put("series", series);
        entryProperties.put("title", title);
        entryProperties.put("type", type);
        entryProperties.put("url", url);
        entryProperties.put("volume", volume);
        entryProperties.put("year", year);
        
        datatypes.put(date, dateType);
        datatypes.put(year, yearType);
        datatypes.put(volume, volumeType);
        datatypes.put(number, numberType);
        datatypes.put(chapter, chapterType);
    }

    public RDFDatatype getDatatype(Property rdfProperty) {
        RDFDatatype result = null;
        if(createDatatypes){
            result = (RDFDatatype)datatypes.get(rdfProperty);
            if(result == null){
                result = XSDDatatype.XSDstring;
            }
        }
        return result;
    }

    public Set<String> getVerbatimProperties() {
        return verbatimProperties;
    }


    public boolean createPersonNameStructure() {
        return createPersonNameStructure;
    }
}
