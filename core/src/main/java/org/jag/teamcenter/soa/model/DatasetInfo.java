/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jag.teamcenter.soa.types.DatasetType;
import org.jag.teamcenter.soa.types.NamedReference;
import org.jag.teamcenter.soa.types.RelationType;

import com.teamcenter.services.strong.core._2008_06.DataManagement;
import com.teamcenter.services.strong.core._2008_06.DataManagement.NamedReferenceFilter;
import com.teamcenter.services.strong.core._2008_06.DataManagement.NamedReferenceList;

/**
 * The class contains the filter fields for the structure {@link DataManagement.DatasetInfo}. It's an immutable object
 * that creates the later needed filter structure calling the function {@link #datasetInfo()}. Prior to make use of the
 * {@link #datasetInfo()} function, an object of the class must be created with the help of the
 * {@link DatasetInfo.Builder} builder class. <br/>
 * Examples:
 * <ol>
 * <li>{@code final DatasetInfo datasetInfo = new DatasetInfo.Builder("client id").uid("AJGAAAycoJRX_C").build();}</li>
 * <li>
 * {@code final DatasetInfo datasetInfo = new DatasetInfo.Builder("client id").filter().all().name("excelfile.xlsx").useNameFirst(true).build();}
 * <li>
 * {@code final DatasetInfo datasetInfo = new DatasetInfo.Builder("client id").filter().all().namedReferenceFilter("PDF_Reference", "ApPAAAycoJRX_C", false).build();}
 * </li> </li>
 * </ol>
 *
 * @author Jose A. Garcia Sanchez
 * @see DataManagement.DatasetInfo
 */
public final class DatasetInfo {

    /** Client identifier. It should be the same in all filter structures. */
    private final String clientId;

    /** <i>UID</i> of the item to be filtered. */
    private final String uid;

    /** Dataset filter. */
    private final DatasetFilter filter;

    /** List of {@link NamedReferenceList}. */
    private final List<NamedReferenceList> namedRefs;

    /**
     * @param builder Builder with the filter information to be used as the final filter
     */
    private DatasetInfo(final Builder builder) {
        this.clientId = builder.clientId;
        this.uid = builder.uid;
        this.filter = builder.datasetFilterBuilder.build();
        this.namedRefs = Collections.unmodifiableList(builder.namedReferences);
    }

    /**
     * Creates the filter structure {@link DataManagement.DatasetInfo} to be used by the corresponding <i>SOA</i>
     * Teamcenter service. The function is synchronized.
     *
     * @return {@link DataManagement.DatasetInfo} structure with the configured filter
     */
    public synchronized DataManagement.DatasetInfo datasetInfo() {
        final DataManagement.DatasetInfo datasetInfo = new DataManagement.DatasetInfo();

        datasetInfo.clientId = clientId;
        datasetInfo.uid = uid;

        datasetInfo.filter.name = filter.datasetFilter().name;
        datasetInfo.filter.nrFilters = filter.datasetFilter().nrFilters.clone();
        datasetInfo.filter.processing = filter.datasetFilter().processing;
        datasetInfo.filter.relationFilters = filter.datasetFilter().relationFilters.clone();
        datasetInfo.filter.useNameFirst = filter.datasetFilter().useNameFirst;

        datasetInfo.namedRefs = new NamedReferenceList[namedRefs.size()];
        namedRefs.toArray(datasetInfo.namedRefs);

        return datasetInfo;
    }

    /**
     * Builder class to construct an instance of the class {@link DatasetInfo}.
     *
     * @author Jose A. Garcia Sanchez
     */
    private static class Builder {
        /** Client identifier. */
        private final String clientId;

        /** <i>UID</i> of the dataset to be sought. */
        protected String uid = "";

        /** Builder for the {@link DataManagement.DatasetFilter} field. */
        protected final DatasetFilter.Builder datasetFilterBuilder = new DatasetFilter.Builder();

        /** List of {@link NamedReferenceList}. */
        protected final List<NamedReferenceList> namedReferences = new ArrayList<NamedReferenceList>();

        /**
         * @param clientId Client identifier
         * @throws NullPointerException if the argument is null
         */
        public Builder(final String clientId) {
            if (clientId == null) {
                throw new NullPointerException("clientId is null");
            }

            this.clientId = clientId;
        }

        /**
         * @return Instance of {@link DatasetInfo} with the values provided to the builder
         */
        public DatasetInfo build() {
            return new DatasetInfo(this);
        }
    }

    /**
     * Builder specialized to build the filter using <i>UID</i> of one dataset.
     *
     * @author Jose A. Garcia Sanchez
     */
    public static class BuilderUid extends Builder {

        /**
         * @param clientId Client identified
         * @param uid <i>UID</i> of the sought dataset
         * @throws NullPointerException if any of the parameters is null
         */
        public BuilderUid(final String clientId, final String uid) {
            super(clientId);
            if (uid == null) {
                throw new NullPointerException("uid is null");
            }
            this.uid = uid;
        }
    }

    /**
     * Builder specialized to build the generic filter for datasets.
     *
     * @author Jose A. Garcia Sanchez
     */
    public static class BuilderFilter extends Builder {
        /**
         * @param clientId Client identifier
         * @throws NullPointerException if the parameter is null
         */
        public BuilderFilter(final String clientId) {
            super(clientId);
            this.uid = "";
        }

        /**
         * Set the filter to look for all datasets.
         *
         * @return The itself instance of the builder
         */
        public BuilderFilter all() {
            datasetFilterBuilder.all();

            return this;
        }

        /**
         * Set the filter to look for the minimum amount of matching datasets.
         *
         * @return The itself instance of the builder
         */
        public BuilderFilter min() {
            datasetFilterBuilder.min();
            return this;
        }

        /**
         * Set the filter to not look for any datasets.
         *
         * @return The itself instance of the builder
         */
        public BuilderFilter none() {
            datasetFilterBuilder.none();
            return this;
        }

        /**
         * Set the filter to look for datasets with the provided name as argument.
         *
         * @param name Name of the dataset to be looked for
         * @return The itself instance of the builder
         * @throws NullPointerException if the parameter is null
         */
        public BuilderFilter name(final String name) {
            if (name == null) {
                throw new NullPointerException("name is null");
            }

            datasetFilterBuilder.name(name);

            return this;
        }

        /**
         * Set the order of searching
         *
         * @param useNameFirst {@code true} to use first the name as filter, {@code false} to use any other field.
         * @return The itself instance of the builder
         */
        public BuilderFilter useNameFirst(final boolean useNameFirst) {
            datasetFilterBuilder.useNameFirst(useNameFirst);

            return this;
        }

        /**
         * Add a {@link DataManagement.DatasetFilter} element to the filter.
         *
         * @param datasetTypeName Name of the dataset type
         * @param relationTypeName Name of the relation type
         * @return The itself instance of the builder
         * @throws NullPointerException if any of the arguments is null
         */
        public BuilderFilter datasetRelationFilter(final String datasetTypeName, final String relationTypeName) {
            if (datasetTypeName == null) {
                throw new NullPointerException("datasetTypeName is null");
            }

            if (relationTypeName == null) {
                throw new NullPointerException("relationTypeName is null");
            }

            datasetFilterBuilder.datasetRelationFilter(datasetTypeName, relationTypeName);

            return this;
        }

        /**
         * Add a {@link DataManagement.DatasetFilter} element to the filter using enumerations as allowed values.
         *
         * @param dataset Value type for the dataset
         * @param relation Value type for the relation
         * @return The itself instance of the builder
         */
        public BuilderFilter datasetRelationFilter(final DatasetType dataset, final RelationType relation) {
            return datasetRelationFilter(dataset.datasetName(), relation.relationName());
        }

        /**
         * Add a {@link DataManagement.NamedReferenceFilter} element to the filter.
         *
         * @param namedReference Name of the reference
         * @param uidReferenced <i>UID</i> of the reference
         * @return The itself instance of the builder
         * @throws NullPointerException if any of the arguments is null
         */
        public BuilderFilter namedReferenceFilter(final String namedReference, final String uidReferenced) {
            if (namedReference == null) {
                throw new NullPointerException("namedReference is null");
            }

            if (uidReferenced == null) {
                throw new NullPointerException("uidReferenced is null");
            }

            datasetFilterBuilder.namedReferenceFilter(namedReference, uidReferenced);

            return this;
        }

        /**
         * Add a {@link DataManagement.NamedReferenceFilter} element to the filter.
         *
         * @param namedReference Enumeration value for the named reference
         * @param uidReferenced <i>UID</i> of the reference
         * @return The itself instance of the builder
         * @throws NullPointerException if any of the arguments is null
         */
        public BuilderFilter namedReferenceFilter(final NamedReference namedReference, final String uidReferenced) {
            if (namedReference == null) {
                throw new NullPointerException("namedReference is null");
            }

            return namedReferenceFilter(namedReference.namedReference(), uidReferenced);
        }

        /**
         * Add a {@link NamedReferenceList} element to the filter.
         *
         * @param namedReference Name of the reference
         * @param ticket {@code true} to get the ticket of the attached dataset, {@code false} otherwise
         * @return The itself instance of the builder
         * @throws NullPointerException if {@code namedReference} is null
         */
        public BuilderFilter namedReference(final String namedReference, final boolean ticket) {
            if (namedReference == null) {
                throw new NullPointerException("namedReference is null");
            }

            final NamedReferenceList namedReferenceElement = new NamedReferenceList();
            namedReferenceElement.namedReference = namedReference;
            namedReferenceElement.ticket = ticket;
            namedReferences.add(namedReferenceElement);

            return this;
        }

        /**
         * Add a {@link NamedReferenceList} element to the filter, specifying the namedReference from
         * {@link NamedReference}.
         *
         * @param namedReference Enumeration value for the reference
         * @param ticket {@code true} to get the ticket of the attached dataset, {@code false} otherwise
         * @return The itself instance of the builder
         * @throws NullPointerException if namedReference is null
         */
        public BuilderFilter namedReference(final NamedReference namedReference, final boolean ticket) {
            if (namedReference == null) {
                throw new NullPointerException("namedReference is null");
            }

            return namedReference(namedReference.namedReference(), ticket);
        }

        /**
         * Add a {@link NamedReferenceList} and a {@link NamedReferenceFilter} elements to the filter using the same
         * {@code namedReference}.
         *
         * @param namedReference Name of the reference
         * @param uidReferenced <i>UID</i> of the reference
         * @param ticket {@code true} to the the ticket of the attached dataset, {@code false} otherwise
         * @return The itself instance of the builder
         */
        public BuilderFilter namedReferenceFilter(final String namedReference, final String uidReferenced,
                final boolean ticket) {

            return namedReference(namedReference, ticket).namedReferenceFilter(namedReference, uidReferenced);
        }

        /**
         * Add a {@link NamedReferenceList} and a {@link NamedReferenceFilter} elements to the filter using the same
         * {@code namedReference}, specifying the namedReference from {@link NamedReference}.
         *
         * @param namedReference Enumeration value for the reference
         * @param uidReferenced <i>UID</i> of the reference
         * @param ticket {@code true} to the the ticket of the attached dataset, {@code false} otherwise
         * @return The itself instance of the builder
         */
        public BuilderFilter namedReferenceFilter(final NamedReference namedReference, final String uidReferenced,
                final boolean ticket) {
            return namedReference(namedReference, ticket).namedReferenceFilter(namedReference, uidReferenced);
        }
    }
}
