package com.soapadoberequest.eps;

/**
 * This class is an interface containing the methods used in the class Formatter
 */
public interface IFormatter {

    /**
     * This method allow to format a string output into a pretty print XML using Dom4j
     *
     * @param xmlString The string to format
     * @param indent The number of indents in the XML
     * @param skipDeclaration A boolean parameter in order to skip XML declarations
     * @return The pretty print XML output
     */
    public static String prettyPrintByDom4j(String xmlString, int indent, boolean skipDeclaration) {
        return null;
    }
}