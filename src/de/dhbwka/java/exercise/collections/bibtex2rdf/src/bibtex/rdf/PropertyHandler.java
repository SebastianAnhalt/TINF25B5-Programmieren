/*
 * Created on 20.01.2004
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package bibtex.rdf;

import bibtex.dom.BibtexEntry;

import com.hp.hpl.jena.rdf.model.Resource;


/**
 * @author siberski
 */
public interface PropertyHandler {
    public abstract void addTriples(BibtexEntry entry, Resource sourceFile, Resource r);
}