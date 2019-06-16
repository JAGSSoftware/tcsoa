/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.teamcenter.services.strong.core._2008_06.DataManagement;

/**
 * @author Jose A. Garcia Sanchez
 */
@Deprecated
class DatasetFilter {

    private final String name;
    private final List<DataManagement.NamedReferenceFilter> namedReferenceFilter;
    private final String processing;
    private final List<DataManagement.DatasetRelationFilter> datasetRelationFilters;
    private final boolean useNameFirst;

    /**
     * @param builder
     */
    private DatasetFilter(final Builder builder) {
        this.name = builder.name;
        this.namedReferenceFilter = Collections.unmodifiableList(builder.namedReferenceFilters);
        this.processing = builder.processing;
        this.datasetRelationFilters = Collections.unmodifiableList(builder.datasetRelationFilters);
        this.useNameFirst = builder.useNameFirst;
    }

    /**
     * @return
     */
    public DataManagement.DatasetFilter datasetFilter() {
        final DataManagement.DatasetFilter datasetFilter = new DataManagement.DatasetFilter();

        datasetFilter.name = this.name;
        datasetFilter.processing = this.processing;
        datasetFilter.useNameFirst = this.useNameFirst;

        datasetFilter.nrFilters = new DataManagement.NamedReferenceFilter[this.namedReferenceFilter.size()];
        this.namedReferenceFilter.toArray(datasetFilter.nrFilters);

        datasetFilter.relationFilters = new DataManagement.DatasetRelationFilter[this.datasetRelationFilters.size()];
        this.datasetRelationFilters.toArray(datasetFilter.relationFilters);

        return datasetFilter;
    }

    /**
     * @author Jose A. Garcia Sanchez
     */
    static class Builder {

        private String name = "";
        private List<DataManagement.NamedReferenceFilter> namedReferenceFilters =
                new ArrayList<>();
        private String processing = "None";
        private List<DataManagement.DatasetRelationFilter> datasetRelationFilters =
                new ArrayList<>();
        private boolean useNameFirst = false;

        /**
         * @param name
         * @return
         */
        public Builder name(final String name) {
            this.name = name;

            return this;
        }

        /**
         * @param namedReference
         * @param uidReferenced
         * @return
         */
        public Builder namedReferenceFilter(final String namedReference, final String uidReferenced) {
            final DataManagement.NamedReferenceFilter namedReferenceFilter = new DataManagement.NamedReferenceFilter();

            namedReferenceFilter.namedReference = namedReference;
            namedReferenceFilter.uidReferenced = uidReferenced;

            namedReferenceFilters.add(namedReferenceFilter);

            return this;
        }

        /**
         * @return
         */
        public Builder none() {
            this.processing = "None";

            return this;
        }

        /**
         * @return
         */
        public Builder all() {
            this.processing = "All";

            return this;
        }

        /**
         * @return
         */
        public Builder min() {
            this.processing = "Min";

            return this;
        }

        /**
         * @param datasetTypeName
         * @param relationTypeName
         * @return
         */
        public Builder datasetRelationFilter(final String datasetTypeName, final String relationTypeName) {
            final DataManagement.DatasetRelationFilter datasetRelationFilter =
                    new DataManagement.DatasetRelationFilter();

            datasetRelationFilter.datasetTypeName = datasetTypeName;
            datasetRelationFilter.relationTypeName = relationTypeName;

            datasetRelationFilters.add(datasetRelationFilter);
            return this;
        }

        /**
         * @param useNameFirst
         * @return
         */
        public Builder useNameFirst(final boolean useNameFirst) {
            this.useNameFirst = useNameFirst;

            return this;
        }

        /**
         * @return
         */
        public DatasetFilter build() {
            return new DatasetFilter(this);
        }
    }
}
