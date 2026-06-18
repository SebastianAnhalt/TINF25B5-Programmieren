/*
 * Created on 20.01.2004
 * 
 * To change this generated comment go to Window>Preferences>Java>Code
 * Generation>Code Template
 */
package bibtex.rdf;

import java.util.*;

import bibtex.dom.BibtexEntry;
import bibtex.util.BibtexUtil;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;


/**
 * @author siberski
 */
public class PublicationCollection {

    private Model model;
    private Map<String,Resource> publications = new HashMap<String,Resource>();
    private String baseUri;
    private BibtexSchema schema;

    /**
     * @param bibProperty
     * @param rdfProperty
     */
    public PublicationCollection(BibtexSchema schema, String baseUri, Model m) {
        this.schema = schema;
        this.baseUri = baseUri;
        this.model = m;
        schema.addNsPrefixes(model);
    }


    /**
     * @param title
     * @param entryKey
     * @return
     */
    public Resource findPublication(BibtexEntry entry) {
        return findPublication(entry, null);
    }
        
    /**
     * @param title
     * @param entryKey
     * @param bibProperty
     * @return
     */
    public Resource findPublication(BibtexEntry entry, String bibProperty) {
        Resource publication = null;
            Object po = publications.get(getUri(entry, bibProperty));
            if (po instanceof Resource) {
                publication = (Resource)po;
            }
        return publication;
    }
    
    /**
     * @param title
     * @param entryKey
     * @return
     */
    public Resource createPublication(BibtexEntry entry) {
        return createPublication(entry, null);
    }
    
    
    /**
     * @param title
     * @param entryKey
     * @param bibProperty
     * @return
     */
    public Resource createPublication(BibtexEntry entry, String bibProperty) {
        String uri = getUri(entry, bibProperty);
        Resource publication = model.createResource(uri);
        publications.put(uri, publication);
        return publication;
    }

    private String getUri(BibtexEntry entry, String bibProperty) {
        String resName = entry.getEntryKey();
        if (bibProperty != null) {
            String entryName = BibtexUtil.getValue(entry, bibProperty);
            resName = resName + "_" + entryName;
        }
        char[] resNameArray = resName.toCharArray();
        for(int i=0; i<resNameArray.length; i++){
            char ch = resNameArray[i];
            if(! ((ch>='0'&&ch<='9')||(ch>='a'&&ch<='z')||(ch>='A'&&ch<='Z')) ){
                resNameArray[i]='_';
            }
        }
        resName = new String(resNameArray);
        
        return baseUri + resName;
    }

    public String toString() {
        Collection<String> titles = new ArrayList<String>();
        for (Resource pub : publications.values() ) {
                titles.add(pub.getProperty(schema.getTitle()).getObject().toString());
        }
        
		String[] sortedTitles = titles.toArray(new String[titles.size()]);
		Arrays.sort(sortedTitles);
		StringBuffer buf = new StringBuffer();
		for (String title : sortedTitles) {
			buf.append(title);
			buf.append("\n");
		}
		return buf.toString();
    }

    /**
     * @return
     */
    public Model getModel() {
        return model;
    }
    
    public void setModel(Model model) {
        this.model = model;
        this.publications.clear();
    }
}
