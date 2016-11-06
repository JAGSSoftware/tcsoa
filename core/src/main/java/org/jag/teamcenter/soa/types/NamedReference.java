/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.types;

/**
 * @author Jose A. Garcia Sanchez
 *
 */
public enum NamedReference {
    PDF("PDF_Reference"),
    EXCEL("excel");
    
    private final String namedReference;
    
    private NamedReference(final String namedReference) {
        this.namedReference = namedReference;
    }
    
    public String namedReference() {
        return namedReference;
    }
}
