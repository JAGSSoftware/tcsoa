/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.types;

/**
 * @author Jose A. Garcia Sanchez
 */
public enum DatasetType {
    PDF("PDF"),
    EXCELX("MSExcelX");

    private final String datasetName;
    
    private DatasetType(final String name) {
        this.datasetName = name;
    }
    
    public String datasetName() {
        return datasetName;
    }
}
