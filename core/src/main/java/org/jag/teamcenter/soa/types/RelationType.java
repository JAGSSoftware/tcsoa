/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.types;

/**
 * @author Jose A. Garcia Sanchez
 */
public enum RelationType {
    IMAN_MANIFESTATION("IMAN_manifestation"),
    IMAN_SPECIFICATION("IMAN_specification");
    
    private final String relationName;
    
    private RelationType(final String name) {
        this.relationName = name;
    }
    
    public String relationName() {
        return relationName;
    }
}
