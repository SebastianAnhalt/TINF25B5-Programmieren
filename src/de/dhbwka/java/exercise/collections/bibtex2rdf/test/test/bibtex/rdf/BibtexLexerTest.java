/*
 * Created on 21.01.2004
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package test.bibtex.rdf;

import bibtex.util.BibtexUtil;
import junit.framework.TestCase;

/**
 * @author siberski
 */
public class BibtexLexerTest extends TestCase {
    public static void main(String[] args) {
        junit.textui.TestRunner.run(BibtexLexerTest.class);
    }

    public void testRemoveTexChars1() {
        String result =BibtexUtil.removeTexChars("{{EDUTELLA}: a {P2P} {N}etworking {I}nfrastructure based on {RDF}}");
        assertEquals("EDUTELLA: a P2P Networking Infrastructure based on RDF", result);
    }

    public void testRemoveTexChars2() {
        String result =BibtexUtil.removeTexChars("{Popa, Lucian and Hernández, Mauricio and Velegrakis, Yannis and Naumann, Felix and Miller, Ren{\\'e}e and Ho, Ching-Tien}");
        assertEquals("Popa, Lucian and Hernández, Mauricio and Velegrakis, Yannis and Naumann, Felix and Miller, Renée and Ho, Ching-Tien", result);
    }
    
    public void testRemoveTexChars3() {
        String result =BibtexUtil.removeTexChars("{Mauricio A.\\ Hern\\'andez and Salvatore J.\\ Stolfo}");
        assertEquals("Mauricio A. Hernández and Salvatore J. Stolfo", result);
    }

    
    public void testRemoveTexChars4() {
        String result =BibtexUtil.removeTexChars("{M\\\"{a}dchen}");
        assertEquals("Mädchen", result);
    }
    
    public void testRemoveTexChars5() {
        String result =BibtexUtil.removeTexChars("{F{\\\"}oderation}");
        assertEquals("Föderation", result);
    }

    public void testRemoveTexChars6() {
        String result =BibtexUtil.removeTexChars("{80$\\times$86}");
        assertEquals("80x86", result);
    }
    public void testRemoveTexChars7() {
        String result =BibtexUtil.removeTexChars("{In\\-for\\-ma\\-tik-Spek\\-trum}");
        assertEquals("Informatik-Spektrum", result);
    }
    
    public void testRemoveTexChars8() {
        String result =BibtexUtil.removeTexChars("{N{\\o}rv{\\aa}g}");
        assertEquals("Nørvåg", result);
    }
    
    public void testRemoveTexChars9() {
        String result =BibtexUtil.removeTexChars("{W.~Yan and P.-{\\AA}. Larson}");
        assertEquals("W. Yan and P.-Å. Larson", result);
    }

    public void testRemoveTexChars10() {
        String result =BibtexUtil.removeTexChars("{NF\\/$^2$}");
        assertEquals("NF2", result);
    }
    public void testRemoveTexChars11() {
        String result =BibtexUtil.removeTexChars("{{\\tt http://www.db.fmi.uni-passau.de/\\verb|~|objglobe/ObjectGlobe-Metaschema.rdf}}");
        assertEquals("http://www.db.fmi.uni-passau.de/~objglobe/ObjectGlobe-Metaschema.rdf", result);
    }
    public void testRemoveTexChars12() {
        String result =BibtexUtil.removeTexChars("{The {$k$}-critical {$2k$}-connected graphs for {$k\\in\\{3,4\\}$}}");
        assertEquals("The k-critical 2k-connected graphs for k in {3,4}", result);
    }
    public void testRemoveTexChars13() {
        String result =BibtexUtil.removeTexChars("{\\LaTeX : {A} Document Preparation System}");
        assertEquals("LaTeX : A Document Preparation System", result);
    }
    public void testRemoveTexChars14() {
        String result =BibtexUtil.removeTexChars("{http://www.daml.org/\\linebreak[0]{}services/\\linebreak[0]{}daml-s/\\linebreak[0]{}2001/05/}");
        System.out.println(result);
        assertEquals("http://www.daml.org/services/daml-s/2001/05/", result);
    }
    
    
}
