package bibtex.rdf.labeling;

import java.util.HashMap;
import java.util.Map;

import bibtex.dom.BibtexEntry;

/**
 * 
 */
public class Labeler {
    private static Map<String,LabelPatternParser> labelers = new HashMap<String,LabelPatternParser>();
    
    public static String getLabel(BibtexEntry entry, String pattern){
        LabelPatternParser l = labelers.get(pattern);
        if(l== null){
            l = new LabelPatternParser(pattern);
            labelers.put(pattern, l);
        }
        return l.getLabel(entry);
    }
    
    

}
