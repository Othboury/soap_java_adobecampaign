package com.soapadoberequest.eps;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.StringWriter;

/**
 * This class contains the methods in order to format a string response into a print print XML, it implements its methods
 * from the interface IFormatter
 */
public class Formatter implements IFormatter{

    /**
     * This methods allow to format a string output into a pretty print XML using Dom4j
     *
     * @param xmlString The String we want to format
     * @param indent The number of indents in the XML
     * @param skipDeclaration A boolean parameter in order to skip XML declarations
     * @return The pretty print XML output
     */
    public static String prettyPrintByDom4j(String xmlString, int indent, boolean skipDeclaration) {
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setIndentSize(indent);
            format.setSuppressDeclaration(skipDeclaration);
            format.setEncoding("UTF-8");

            org.dom4j.Document document = DocumentHelper.parseText(xmlString);
            StringWriter sw = new StringWriter();
            XMLWriter writer = new XMLWriter(sw, format);
            writer.write(document);
            return sw.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error occurs when pretty-printing xml:\n" + xmlString, e);
        }
    }
}
