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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.hp.hpl.jena.rdf.model.Model;


/**
 * main program for the bibtex2rdf converter
 */
public final class bibtex2rdf {
    public static final String DEFAULT_BASE="http://128347.12342.34534.3424/12342/98357/";
    private static final String DEFAULT_ENCODING="ISO-8859-1";
    public static void usage() {
        System.err
            .println(
            "\nbibtex2rdf version 1.0 beta 4. Usage:"
            + "\nbibtex2rdf [-schema <file>] [-baseuri <uri>] [-enc <enc>] <bibtex> [<output>]."
            + "\n\nArguments:"
            + "\n-schema <file>  use the specified configuration file."
            + "\n-baseuri <uri>  prepend all generated uris with the specified base uri."
            + "\n-enc <enc>      use specified encoding. default is ISO-8859-1."
            + "\n<bibtex>        this file is translated to RDF. If it is a directory, "
            + "\n                bibtex2rdf scans it (and its sub-directories) and "
            + "\n                translates all files found which have a .bib suffix"
            + "\n<output>        the result is written to this file. Default is stdout");
    }

    public static void main(String[] args) {
        if (args.length < 1 || args.length > 8) {
            usage();
            return;
        }
        File bibFile = null;
        try {
            String schemaFile = null;
            String bibFileName = null;
            String outputFile = null;
            String baseUri = null;
            String encoding = DEFAULT_ENCODING;
            for(int i=0; i<args.length;i++) {
                if (args[i].toLowerCase().equals("-schema")) {
                    schemaFile = args[i+1];
                    i++;
                } else if (args[i].toLowerCase().equals("-baseuri")) {
                    baseUri = args[i+1];
                    i++;
                } else if (args[i].toLowerCase().equals("-enc")) {
                    encoding = args[i+1];
                    i++;
                } else if(args[i].startsWith("-")) {
                    usage();
                    return;
                } else if (bibFileName == null) {
                    bibFileName = args[i];
                } else {
                    outputFile = args[i];
                }
            }

            String base;
            if(baseUri != null) {
                base = baseUri;
            } else {
                base = DEFAULT_BASE;
            }
            BibtexSchema schema;
            if (schemaFile == null) {
                schema = new BibtexSchema();
            } else {
                schema = new BibtexSchema(new File(schemaFile));
            }
            BibtexParser bm = new BibtexParser(schema, base);
            bibFile = new File(bibFileName);
            
            Model result = bm.parse(bibFile);

            OutputStream out;
            if (outputFile == null) {
                out = System.out;
            } else {
                out = new FileOutputStream(outputFile);
            }

            if(baseUri != null) {
                bm.writeModel(result, out, null, encoding);
            }
            else {
                bm.writeModel(result, out, DEFAULT_BASE, encoding);
            }
        } catch (FileNotFoundException e) {
            if(bibFile != null) {
                System.out.println(bibFile.getAbsolutePath());
            }
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
