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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.log4j.Logger;

import bibtex.dom.BibtexAbstractEntry;
import bibtex.dom.BibtexEntry;
import bibtex.dom.BibtexFile;
import bibtex.expansions.MacroReferenceExpander;
import bibtex.expansions.PersonListExpander;

import com.hp.hpl.jena.mem.ModelMem;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;


/**
 * This class creates an RDF model from a BibTeX file
 */
public class BibtexParser {
    private static Logger log = Logger.getLogger(BibtexParser.class);

    // all resource identifiers are prepended with the base URI
    private String baseURI;

    // pool of persons which is checked to avoid duplicates
    private PersonCollection persons;

    // pool of collections (journals, proceedings, etc.)
    private PublicationCollection publications;

    // handles model generation for one bibtex entry
    private EntryHandler entryHandler;

    private BibtexSchema schema;

    /**
     * Constructor
     * 
     * @param schema the specification used for conversion of bibtex entries .
     * @param baseURI all resource identifiers are prepended with this URI.
     */
    public BibtexParser(BibtexSchema schema, String baseURI) {
        this.schema = schema;
        this.baseURI = baseURI;

        // create a separate model for the person pool
        persons = new PersonCollection(schema, baseURI, new ModelMem());
        publications = new PublicationCollection(schema, baseURI, ModelFactory
            .createDefaultModel());
        entryHandler = new EntryHandler(schema, persons, publications, baseURI);

    }

    /**
     * creates an RDF model from a BibtexFile object
     * 
     * @param bibfile
     * @param model
     * @param sourceFile
     * @return
     */
    @SuppressWarnings("unchecked")
	private void format(BibtexFile bibfile, Resource sourceFile) {
        Model model = publications.getModel();
        List<BibtexAbstractEntry> entries = bibfile.getEntries();
        Resource pubList = null;
        int n = 1;

        if (schema.createEntryList()) {
            // create a Seq containing all bibtex entries
            pubList = model.createSeq(baseURI + "referenceList");
        }

        for (BibtexAbstractEntry e : entries) {
            if (e instanceof BibtexEntry) {
                BibtexEntry entry = (BibtexEntry)e;
                if (publications.findPublication(entry) == null) {
                    Resource pub = publications.createPublication(entry);
                    if (schema.createEntryList()) {
                        pubList.addProperty(RDF.li(n), pub);
                        n++;
                    }
                    entryHandler.addTriples(entry, sourceFile, pub);
                } else {
                    log.warn("duplicate entry <" + entry.getEntryKey()
                        + ">. ignoring duplicate(s)");
                }
            }
        }

        schema.addNsPrefixes(model);
        if (schema.createPersonResource()) {
            model.add(persons.getModel());
        }
        if (schema.createCollectionResource()) {
            model.add(publications.getModel());
        }
    }

    /**
     * creates an RDF model from bibtex content accessible by URI
     * 
     * @param uri location of the bibtex
     * @return created model. 
     */
    public Model parse(URI uri) throws IOException {
        InputStream is = uri.toURL().openConnection().getInputStream();
        Resource source = createSourceFile(uri);
        format(is, source);
        return getModel();
    }

    /**
     * creates an RDF model from an input stream
     * 
     * @param input stream
     * @return created model
     */
    public Model parse(InputStream is) throws IOException {
        format(is, null);
        return getModel();
    }

    /**
     * creates an RDF model from an input stream
     * 
     * @param reader source file
     * @param model destination
     * @return created model
     */
    private void format(InputStream is, Resource sourceFile) throws IOException {
        Reader reader = new InputStreamReader(is);

        BibtexFile bibtexFile = new BibtexFile();
        bibtex.parser.BibtexParser parser = new bibtex.parser.BibtexParser(
            false);

        MacroReferenceExpander expander1 = new MacroReferenceExpander(true,
            true, true, false);

        PersonListExpander expander3 = new PersonListExpander(true, true, false);

        try {
            // parse the file using the javabib parser
            parser.parse(bibtexFile, reader);

            // expand macros and person lists
            expander1.expand(bibtexFile);
            expander3.expand(bibtexFile);

            format(bibtexFile, sourceFile);
        } catch (IOException e) {
            log.error("could not write to output", e);
            throw e;
        } catch (Exception e2) {
            log.error("exception occurred", e2);
            throw new IllegalArgumentException(e2.getMessage());
        } finally {
            printNonFatalExceptions(parser.getExceptions());
            printNonFatalExceptions(expander1.getExceptions());
            printNonFatalExceptions(expander3.getExceptions());
        }
    }

    /**
     * sets the model where all bibtex entry RDF representations are added.
     * 
     * @param m destination model
     */
    public void setModel(Model m) {
        publications.setModel(m);
    }

    
    
    public Model parse(File bibFile) throws IOException {
        if (!bibFile.isDirectory()) {
            // simple file case
            InputStream is = new FileInputStream(bibFile);
            Resource source = createSourceFile(bibFile.toURI());
            format(is, source);
            getModel();
        } else {
            // directory case
            File bibFiles[] = bibFile.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    try {
                        return pathname.isDirectory()
                            || pathname.getCanonicalPath().endsWith(".bib");
                    } catch (IOException e) {
                        return false;
                    }
                }
            });
            for (int i = 0; i < bibFiles.length; i++) {
                if (bibFiles[i].isDirectory()) {
                    log.debug("Scanning directory "
                        + bibFiles[i].getCanonicalPath());
                } else {
                    log
                        .info("\nParsing file "
                            + bibFiles[i].getCanonicalPath());
                }
                parse(bibFiles[i]);
            }
        }
        return publications.getModel();
    }

    /**
     * writes a BibTeX RDF model to an OutputStream. Tries to pretty-print as
     * good as possible.
     * 
     * @param m model
     * @param out destination
     * @param base URI used as base for resource URIs
     * @param encoding character encoding used
     * @throws IOException
     */
    public void writeModel(Model m, OutputStream out, String base,
            String encoding) throws IOException {
        log.info("Writing Output");
        RDFWriter jenaWriter = m.getWriter("RDF/XML-ABBREV");

        Resource[] prettyTypes = schema.getUsedRDFTypes();
        jenaWriter.setProperty("prettyTypes", prettyTypes);

        Writer w = new BufferedWriter(new OutputStreamWriter(out, Charset
            .forName(encoding)));
        w.write("<?xml version='1.0' encoding='" + encoding + "'?>\n");
        jenaWriter.write(m, w, base);
        log.info("Output written");
    }

    /**
     * outputs exceptions which occurred during BibTeX parsing
     * 
     * @param exceptions
     */
    private void printNonFatalExceptions(Exception[] exceptions) {
        if (exceptions != null && exceptions.length > 0) {
            for (int i = 0; i < exceptions.length; i++) {
                log.warn("javabib: " + exceptions[i].getMessage());
            }
        }
    }

    private Resource createSourceFile(URI uri) {
        if (uri == null) {
            return null;
        }
        int i = 2;
        String basicLocalName = "bibfile";
        String localName = basicLocalName;
        boolean resExists;
        Resource result;
        do {
            result = getModel().createResource(baseURI + localName);
            resExists = getModel().contains(result, RDF.type);
            if (resExists) {
                localName = basicLocalName + i;
                i++;
            }
        } while (resExists);

        result.addProperty(RDF.type, schema.getBibFile());
        result.addProperty(schema.getFileAbsolutePath(), uri);

        if (schema.outputEntryProperty(BibtexSchema.LABEL)
            && !result.hasProperty(schema.getLabel())) {
            result.addProperty(schema.getLabel(), result.getProperty(
                schema.getFileAbsolutePath()).getObject());
        }
        return result;
    }

    private Model getModel() {
        return publications.getModel();
    }
}
