/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.types;

/**
 * @author Jose A. Garcia Sanchez
 */
@Deprecated
public enum NamedReference {
    PDF("PDF_Reference"),
    EXCEL("excel");

    private final String reference;

    NamedReference(final String reference) {
        this.reference = reference;
    }

    public String namedReference() {
        return reference;
    }
}
