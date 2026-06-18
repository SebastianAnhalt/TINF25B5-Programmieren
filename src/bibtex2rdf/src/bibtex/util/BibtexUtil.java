/*
 * Created on 21.01.2004
 * 
 * To change this generated comment go to Window>Preferences>Java>Code
 * Generation>Code Template
 */
package bibtex.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import bibtex.dom.BibtexEntry;
import bibtex.dom.BibtexPerson;
import bibtex.dom.BibtexPersonList;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;


/**
 * @author siberski
 */
public class BibtexUtil {
    private static Logger log = Logger.getLogger(BibtexUtil.class);
    private static Map<String,Character> accentsToUnicode;
    private static Map<String,String> controlToUnicode;
    private static Map<String,String> controlToString;
    private static Set<String> ignoreControl;

    static {
        accentsToUnicode = new HashMap<String,Character>();
        accentsToUnicode.put("´", new Character('\u0301'));
        accentsToUnicode.put("\'", new Character('\u0301'));
        accentsToUnicode.put("`", new Character('\u0300'));
        accentsToUnicode.put("^", new Character('\u0302'));
        accentsToUnicode.put("~", new Character('\u0303'));
        accentsToUnicode.put("\"", new Character('\u0308'));
        accentsToUnicode.put("r", new Character('\u030A'));
        accentsToUnicode.put("c", new Character('\u0327'));
        accentsToUnicode.put("acute", new Character('\u0301'));

        controlToUnicode = new HashMap<String,String>();
        controlToUnicode.put("ss", "ß");
        controlToUnicode.put("AA", "Å");
        controlToUnicode.put("aa", "å");
        controlToUnicode.put("o", "ø");
        controlToUnicode.put("mu", "\u03BC");
        controlToUnicode.put("Delta", "\u0394");
        controlToUnicode.put("kappa", "\u03BA");
        controlToUnicode.put("times", "x");
        controlToUnicode.put("ast", "*");
        controlToUnicode.put("slash", "/");
        
        controlToString = new HashMap<String,String>();
        controlToString.put("in", "in");
        controlToString.put("TeX", "TeX");
        controlToString.put("LaTeX", "LaTeX");

        ignoreControl = new HashSet<String>();
        ignoreControl.add("url");
        ignoreControl.add("tt");
        ignoreControl.add("it");
        ignoreControl.add("em");
        ignoreControl.add("tt");
        ignoreControl.add("textit");
        ignoreControl.add("cal");
        ignoreControl.add("linebreak");
    }

    public static String removeTexChars(String text) {
        text = text.trim();
        StringBuffer result = new StringBuffer(text.length());
        int i = 0;
        int level = 0;
        boolean inControl = false;
        boolean inWhitespace = false;
        boolean inMath = false;
        String controlWord = "";
        while (i < text.length()) {
            char c = text.charAt(i);
            if (!inControl) {
                if (Character.isWhitespace(c) || c == '~') {
                    if (!inWhitespace) {
                        result.append(' ');
                    }
                    inWhitespace = true;
                } else {
                    inWhitespace = false;
                    switch (c) {
                        case '{' :
                            level++;
                            break;
                        case '}' :
                            level--;
                            break;
                        case '\\' :
                            inControl = true;
                            break;
                        case '$' :
                            //                            result.append(c);
                            inMath = !inMath;
                            break;
                        default :
                            if(inMath && c=='^'){
                                // ignore
                            } else {
                                result.append(c);
                            }
                            break;
                    }
                }
            } else {
                if (Character.isLetterOrDigit(c)) {
                    controlWord += c;
                } else {
                    inControl = false;
                    // control word end reached
                    if (controlWord.length() == 0 || accentsToUnicode.containsKey(controlWord)) {
                        if (c == '-' || c=='/') {
                            // ignore 
                        } else if (c == ' ' || c == '&' || c == '#' || c == '_' || c == '\\' 
                                 || c == '~' || c == '{' || c == '}' || c == '[' || c == ']'
                                 || c == '$' ) {
                            result.append(c);
                        } else if (c == '\'' || c == '´' || c == '`'
                        || c == '^' || c == '"' || 
                         accentsToUnicode.containsKey(controlWord)) {
                            // handle accents
                            if (i > text.length() - 1) {
                                throw new IllegalArgumentException(
                                "incomplete accent (in " + text + ")");
                            }
                            String accentCommand;
                            char unaccented;
                            if (controlWord.length() == 0) {
                                accentCommand = "" + c;
                                i++;
                                unaccented = text.charAt(i);
                            } else {
                                accentCommand = controlWord;
                                unaccented = c;
                            }
                            Character unicodeAccent = ((Character)accentsToUnicode
                            .get(accentCommand));
                            if (unaccented == '}') {
                                level--;
                                i++;
                                unaccented = text.charAt(i);
                            }
                            if (unaccented == '{') {
                                if (i > text.length() - 2) {
                                    throw new IllegalArgumentException(
                                    "incomplete accent (in " + text + ")");
                                }
                                i++;
                                unaccented = text.charAt(i);
                                i++;
                                if (unaccented == '\\') {
                                    unaccented = text.charAt(i);
                                    i++;
                                }
                                if (text.charAt(i) == '}') {
                                    if(controlWord.length()>0) {
                                        i++;
                                    }
                                } else {
                                    throw new IllegalArgumentException(
                                    "accented char too long or '}' missing (in "
                                    + text + ")");
                                }
                            }
                            if(unicodeAccent !=null) {
                                String combined = new String(
                                new char[]{unaccented, 
                                unicodeAccent.charValue()});
                                String accented = AccentComposer
                                .composeAccents(combined);
                                result.append(accented);
                            } else {
                                //ignore accent
                                log.warn("ignoring accent <" + accentCommand + ">");
                                result.append(unaccented);
                            }
                        } else {
                            String output = "" + c;
                            if( c < ' ' || c > '~') {
                                output = "0x" + Integer.toHexString(c);
                            }
                            log.warn("character <" + output + "> occurred as control character");
                            result.append(c);
                        }
                    } else {
                        // handle control word
                        controlWord = controlWord.trim();
                        if (controlToUnicode.get(controlWord)!=null) {
                            result.append(controlToUnicode
                                .get(controlWord));
                        } else if (ignoreControl.contains(controlWord)) {
                                // ignore
                        } else if (controlToString.get(controlWord)!=null) {
                                if(result.length()>0 && !Character.isWhitespace(result.charAt(result.length()-1))){
                                    result.append(' ');
                                }
                                result.append(controlToString.get(controlWord));
                                if(!Character.isWhitespace(c)) {
                                    result.append(' ');
                                }
                        } else if (controlWord.length() == 1) {
                                result.append(controlWord);
                        } else if (controlWord.equals("verb") || controlWord.equals("path")) {
                            while(Character.isWhitespace(c)) {
                                i++;
                                c = text.charAt(i);
                            }
                            char stopChar = c;
                            i++;
                            c = text.charAt(i);
                            while(c != stopChar) {
                                result.append(c);
                                i++;
                                c = text.charAt(i);
                            }
                            i++;
                        } else {
                            if (inMath) {
                                result.append('\\');
                                result.append(controlWord);
                                log.info("control word <" + controlWord
                                + "> was not translated (in " + text + ")");
                            } else {
                                log.info("ignoring control word <"
                                + controlWord + "> (in " + text + ")");
                            }
                        }
                    }
                    if (controlWord.length() > 0) {
                        // handle stop character
                        if (Character.isWhitespace(c) || c == '~') {
                            inWhitespace = true;
                            if (result.length() > 0) {
                                result.append(' ');
                            }
                        } else if(c=='['){
                            // ignore arguments
                            while(c != ']') {
                                i++;
                                c = text.charAt(i);
                            }
                        } else {
                            i--;
                        }
                    }
                    controlWord = "";
                }
            }
            i++;
        }
        if (level != 0) {
            throw new IllegalArgumentException("unbalanced braces in " + text);
        }
        return result.toString();
    }

    public static String getValue(BibtexEntry entry, String property) {
        String result = null;
        Object value = entry.getFieldValue(property);
        if (value != null) {
            result = value.toString();
            result = BibtexUtil.removeTexChars(result).trim();
        }
        return result;
    }

    public static String shortenTitle(String title) {
        title = title.replaceAll("[Tt]he ", "");
        title = title.replaceAll("[Oo]f ", "");
        title = title.replaceAll("[Ff]or ", "");
        title = title.replaceAll("[Pp]roceedings ", "");
        title = title.replaceAll("[,;.:]", " ");
        title = title.replaceAll("[ ]+", " ");
        title = title.trim();
        title = title.replace(' ', '_');
        return title;
    }

    public static String getTitleShorthand(String title) {
        String shortHand = null;
        int open = title.indexOf('(');
        int close = title.indexOf(')');
        if (open > -1 && close > -1 && open < close
        && title.indexOf('(', open + 1) == -1
        && title.indexOf(')', close + 1) == -1) {
            String sh = title.substring(open + 1, close).trim();
            int wordCount = 1;
            int i = 0;
            while (i != -1 && i < sh.length()) {
                i = sh.indexOf(' ', i);
                if (i != -1) {
                    wordCount++;
                    i++;
                }
            }
            if (wordCount < 3 && sh.length() >= 2
            && isShortHandChar(sh.charAt(0))
            && isShortHandChar(sh.charAt(1))) {
                shortHand = sh;
            }
        }
        return shortHand;
    }

    private static boolean isShortHandChar(char c) {
        return Character.isUpperCase(c) || c == '-' || c == '\'';
    }

    @SuppressWarnings("unchecked")
	public static String getNameList(BibtexEntry entry, String property, String delimiter) {
        String result = null;
        Object value = entry.getFieldValue(property);
        if (value instanceof BibtexPersonList) {
            BibtexPersonList pl = (BibtexPersonList)value;
            result = "";
            for (Iterator<BibtexPerson> i = pl.getList().iterator(); i.hasNext();) {
                BibtexPerson p = i.next();
                PersonData pd = new PersonData(p);
                result += pd.completeName;
                if(i.hasNext()){
                    result += delimiter;
                }
            }
        }
        return result;
    }

    public static void addProperty(Resource resource, String value, Property rdfProperty, RDFDatatype datatype) {
        if(datatype == null){
            resource.addProperty(rdfProperty, value);
        } else {
            Literal l = resource.getModel().createTypedLiteral(value, datatype);
            resource.addProperty(rdfProperty, l);
        }
    }
}
