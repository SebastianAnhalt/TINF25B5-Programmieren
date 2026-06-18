/*
 * Created on 21.01.2004
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package test.bibtex.rdf;

import java.io.File;

import junit.framework.TestCase;
import bibtex.rdf.BibtexParser;
import bibtex.rdf.BibtexSchema;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * @author siberski
 */
public class DuplicateTest extends TestCase {
    public static void main(String[] args) {
        junit.textui.TestRunner.run(DuplicateTest.class);
    }

    /**
     * this test reads three files with the same bibtex entry
     * (an article) and checks that only one RDF entry is created
     */
    
    public void testDuplicates() throws Exception {
        BibtexSchema schema = new BibtexSchema();
        String base = "http://test/";
        BibtexParser bm = new BibtexParser(schema, base);
        File bibfileDir = new File("test/test/bibtex/rdf/bibfiles/duplicates");

        Model result = bm.parse(bibfileDir);

        String articleType = schema.getEntryType("article").toString();
        ResIterator iter = result.listSubjectsWithProperty(RDF.type, result.createResource(articleType));
        int i=0;
        while(iter.hasNext()){
        	iter.next();
        	i++;
        }
        assertEquals(1,i);
    }
}
