/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.types;

import com.teamcenter.soa.SoaConstants;

/**
 * @author Jose A. Garcia Sanchez
 *
 */
public enum Binding {
    REST(SoaConstants.REST);
    
    /**  */
    private final String value;
    
    /**
     * @param value
     */
    private Binding(final String value) {
        this.value = value;
    }
    
    /**
     * @return
     */
    public String value() {
        return this.value;
    }
}
