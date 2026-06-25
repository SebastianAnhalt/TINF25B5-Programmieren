/*
 * Created on 20.01.2004
 * 
 * To change this generated comment go to Window>Preferences>Java>Code
 * Generation>Code Template
 */
package bibtex.rdf;

import java.util.*;

import bibtex.dom.BibtexPerson;
import bibtex.util.BibtexUtil;
import bibtex.util.PersonData;

import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author siberski
 */
public class PersonCollection {
	private Model model;
	private Map<String, List<Resource>> persons = new HashMap<String, List<Resource>>();
	private String baseUri;
	private BibtexSchema schema;

	/**
	 * @param bibProperty
	 * @param rdfProperty
	 */
	public PersonCollection(BibtexSchema schema, String baseUri, Model m) {
		this.schema = schema;
		this.baseUri = baseUri;
		this.model = m;
		schema.addNsPrefixes(model);
	}

	public Resource getPerson(BibtexPerson p, String entryKey, String property,
			Resource sourceFile) {
		PersonData pd = new PersonData(p);
		Resource result = findPerson(pd);
		if (result == null) {
			result = createPerson(pd, entryKey, property, sourceFile);
		}
		return result;
	}

	public Resource getPerson(String fullName, String entryKey,
			String property, Resource sourceFile) {
		PersonData pd = new PersonData();
		pd.family = fullName;
		pd.completeName = fullName;
		Resource result = findPerson(pd);
		if (result == null) {
			result = createPerson(pd, entryKey, property, sourceFile);
		}
		return result;
	}

	private Resource findPerson(PersonData pd) {
		Resource person = null;
		if (pd.isOthers) {
			person = schema.getEtAl();
		} else {
			List<Resource> prList = persons.get(pd.family);
			if (prList != null) {
				for (Resource pr : prList) {
					if (matches(pd, pr)) {
						person = pr;
						break;
					}
				}
			}
		}
		return person;
	}

	private boolean matches(PersonData pd, Resource pr) {
		boolean matches = false;
		if (pr != null) {
			String g = null;
			if (pr.hasProperty(schema.getPersonStructuredName())) {
				Resource n = (Resource) pr.getProperty(
						schema.getPersonStructuredName()).getObject();
				if (n.hasProperty(schema.getNameGiven())) {
					g = n.getProperty(schema.getNameGiven()).getObject()
							.toString();
					if (matchesFirst(g, pd.given)) {
						matches = true;
						if (g.length() == 2
								&& g.charAt(1) == '.'
								&& (pd.given.length() > 2 || !pd.given
										.endsWith("."))) {
							n.getProperty(schema.getNameGiven()).changeObject(
									pd.given);
							if (n.hasProperty(schema.getNameOther())) {
								String ot = n
										.getProperty(schema.getNameOther())
										.getObject().toString();
								if (ot.length() == 2
										&& ot.charAt(1) == '.'
										&& (pd.other != null && (pd.other
												.length() > 2 || !pd.other
												.endsWith(".")))) {
									n.getProperty(schema.getNameOther())
											.changeObject(pd.other);
								}
							}
							pr.getProperty(schema.getPersonFullname())
									.changeObject(pd.completeName);
						}
					}
				}
			} else {
				if (pr.hasProperty(schema.getPersonFullname())) {
					String fn = pr.getProperty(schema.getPersonFullname())
							.getObject().toString();
					matches = fn.equals(pd.completeName);
				}
			}
		}
		return matches;
	}

	/**
	 * @param g
	 * @param given
	 * @return
	 */
	private boolean matchesFirst(String f1, String f2) {
		boolean result = false;
		if (f1 == null) {
			if (f2 == null) {
				result = true;
			}
		} else {
			if (f1.equals(f2)) {
				result = true;
			} else {
				if (f2 != null) {
					if (f1.charAt(0) == f2.charAt(0)
							&& ((f1.length() == 2 && f1.charAt(1) == '.') || f2
									.length() == 2
									&& f2.charAt(1) == '.')) {
						result = true;
					}
				}
			}
		}
		return result;
	}

	private Resource createPerson(PersonData pd, String entryKey,
			String property, Resource sourceFile) {
		String uri = createUri(pd, entryKey);
		int i = 1;
		Resource person = model.createResource(uri);
		while (model.contains(person, RDF.type)) {
			uri = uri + Integer.toString(i++);
			person = model.createResource(uri);
		}
		Resource type = schema.getPersonType(property);
		person.addProperty(RDF.type, type);

		if (schema.outputPersonProperty("personFullname")) {
			Property fullNameProp = schema.getPersonFullname();
			BibtexUtil.addProperty(person, pd.completeName, fullNameProp,
					schema.getDatatype(fullNameProp));
		}

		if (schema.outputPersonProperty("personStructuredName")
				&& pd.given != null) {
			Resource n = person;
			if (schema.createPersonNameStructure()) {
				n = model.createResource();
				person.addProperty(schema.getPersonStructuredName(), n);
			}
			if (schema.outputPersonProperty("nameGiven")) {
				Property nameGivenProp = schema.getNameGiven();
				BibtexUtil.addProperty(n, pd.given, nameGivenProp, schema
						.getDatatype(nameGivenProp));
			}
			if (pd.other != null && schema.outputPersonProperty("nameOther")) {
				Property nameOtherProp = schema.getNameOther();
				BibtexUtil.addProperty(n, pd.other, nameOtherProp, schema
						.getDatatype(nameOtherProp));
			}
			if (pd.prefix != null && schema.outputPersonProperty("namePrefix")) {
				Property namePrefixProp = schema.getNamePrefix();
				BibtexUtil.addProperty(n, pd.prefix, namePrefixProp, schema
						.getDatatype(namePrefixProp));
			}
			if (schema.outputPersonProperty("nameFamily")) {
				Property nameFamilyProp = schema.getNameFamily();
				BibtexUtil.addProperty(n, pd.family, nameFamilyProp, schema
						.getDatatype(nameFamilyProp));
			}
			if (pd.suffix != null && schema.outputPersonProperty("nameSuffix")) {
				Property nameSuffixProp = schema.getNameSuffix();
				BibtexUtil.addProperty(n, pd.suffix, nameSuffixProp, schema
						.getDatatype(nameSuffixProp));
			}
		}

		if (schema.outputPersonProperty(BibtexSchema.SOURCE_FILE)) {
			Property sourceFileProp = schema.getSourceFile();
			person.addProperty(sourceFileProp, sourceFile);
		}
		if (schema.outputPersonProperty(BibtexSchema.LABEL)
				&& !person.hasProperty(schema.getLabel())) {
			Property rdfLabelProp = schema.getLabel();
			BibtexUtil.addProperty(person, pd.completeName, rdfLabelProp,
					schema.getDatatype(rdfLabelProp));
		}

		// add resource to hash table
		List<Resource> familyMatches = persons.get(pd.family);
		if (familyMatches == null) {
			familyMatches = new ArrayList<Resource>(1);
			persons.put(pd.family, familyMatches);
		}
		familyMatches.add(person);

		return person;
	}

	private String createUri(PersonData pd, String entryKey) {
		String resName = pd.family;
		if (pd.prefix != null) {
			resName = pd.prefix + resName;
		}
		if (pd.suffix != null) {
			resName = resName + pd.suffix;
		}
		if (pd.given != null) {
			resName = resName + "_" + pd.given;
		}
		if (pd.other != null) {
			resName = resName + "_" + pd.other;
		}
		resName = entryKey + "_" + resName;
		char[] resNameArray = resName.toCharArray();
		for (int i = 0; i < resNameArray.length; i++) {
			char ch = resNameArray[i];
			if (!((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))) {
				resNameArray[i] = '_';
			}
		}
		resName = new String(resNameArray);

		String uri = baseUri + resName;
		return uri;
	}

	public String toString() {
		List<String> names = new ArrayList<String>();
		for (List<Resource> familyMatches : persons.values()) {
			for (Resource pr : familyMatches) {
				names.add(pr.getProperty(schema.getPersonFullname())
						.getObject().toString()
						+ "*");
			}
		}

		String[] sortedNames = names.toArray(new String[names.size()]);
		Arrays.sort(sortedNames);
		StringBuffer buf = new StringBuffer();
		for (String name : sortedNames) {
			buf.append(name);
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
}
