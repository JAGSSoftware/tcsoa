/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.model;

import com.teamcenter.services.strong.core._2008_06.DataManagement;

/**
 * @author Jose A. Garcia Sanchez
 * @see DataManagement.RevInfo
 */
public class RevisionInfo {
    /** Client identifier. */
    private final String clientId;

    /** What data to be used for the search. */
    private String processing;

    /** Revision identifier. */
    private String id;

    /** Numbre of revisions to retrieve. */
    private int nRevs;

    /** <i>UID</i> of the revision to be sought. */
    private String uid;

    /** Flag to use first the <i>ID</i> or <i>UID</i> as filter. */
    private boolean useIdFirst;

    /** Revision rule filter. */
    private String revisionRule = "";

    /**
     * @param builder
     */
    private RevisionInfo(final Builder builder) {
        clientId = builder.clientId;
        id = builder.id;
        nRevs = builder.nRevs;
        processing = builder.processing;
        revisionRule = builder.revisionRule;
        uid = builder.uid;
        useIdFirst = builder.useIdFirst;
    }

    /**
     * @return
     */
    public DataManagement.RevInfo revisionInfo() {
        final DataManagement.RevInfo revisionInfo = new DataManagement.RevInfo();

        revisionInfo.clientId = clientId;
        revisionInfo.id = id;
        revisionInfo.nRevs = nRevs;
        revisionInfo.processing = processing;
        revisionInfo.revisionRule = revisionRule;
        revisionInfo.uid = uid;
        revisionInfo.useIdFirst = useIdFirst;

        return revisionInfo;
    }

    /**
     * @author Jose A. Garcia Sanchez
     */
    static class Builder {
        /** Client identifier. */
        private final String clientId;

        /** What data to be used in the search. */
        protected String processing = "None";

        /** <i>ID</i> if the revision. */
        protected String id = "";

        /** Number of revisions retrieve. */
        protected int nRevs = 0;

        /** <i>UID</i> of the revision to retrieve. */
        protected String uid = "";

        /** Flag to use first the <i>ID</i> or <i>UID</i> as filter. */
        protected boolean useIdFirst = false;

        /** Revision rule to be used. */
        protected String revisionRule = "";

        /**
         * @param clientId
         */
        private Builder(final String clientId) {
            if (clientId == null) {
                throw new NullPointerException("clientId is null");
            }

            this.clientId = clientId;
            // reset();
        }

        // /**
        // *
        // */
        // private void reset() {
        // this.id = "";
        // this.nRevs = 0;
        // this.processing = "None";
        // this.uid = "";
        // useIdFirst = false;
        // }

        // /**
        // * @param id
        // * @return
        // */
        // public RevisionInfo id(final String id) {
        // if (id == null) {
        // throw new NullPointerException("id is null");
        // }
        //
        // reset();
        // this.useIdFirst = true;
        // this.processing = "Ids";
        // this.id = id;
        //
        // return build();
        // }

        // /**
        // * @param nRevs
        // * @return
        // */
        // public RevisionInfo nRevs(final int nRevs) {
        // reset();
        // this.useIdFirst = false;
        // this.processing = "Nrev";
        // this.nRevs = nRevs;
        //
        // return build();
        // }

        // /**
        // * @param uid
        // * @return
        // */
        // public RevisionInfo uid(final String uid) {
        // if (uid == null) {
        // throw new NullPointerException("uid is null");
        // }
        // reset();
        // this.useIdFirst = false;
        // this.processing = "Ids";
        // this.uid = uid;
        //
        // return build();
        // }

        // /**
        // * @return
        // */
        // public RevisionInfo all() {
        // reset();
        // this.processing = "All";
        //
        // return build();
        // }

        // /**
        // * @return
        // */
        // public RevisionInfo none() {
        // reset();
        // this.processing = "None";
        //
        // return build();
        // }

        /**
         * @param revisionRule
         * @return
         */
        public Builder revisionRule(final String revisionRule) {
            if (revisionRule == null) {
                throw new NullPointerException("revisionRule is null");
            }

            this.revisionRule = revisionRule;

            return this;
        }

        /**
         * @return
         */
        public final RevisionInfo build() {
            return new RevisionInfo(this);
        }
    }

    /**
     * @author Jose A. Garcia Sanchez
     */
    public static final class BuilderId extends Builder {

        /**
         * @param clientId
         * @param id
         */
        public BuilderId(final String clientId, final String id) {
            super(clientId);

            this.useIdFirst = true;
            this.processing = "Ids";
            this.id = id;
        }
    }

    /**
     * @author Jose A. Garcia Sanchez
     */
    public static final class BuilderNumberRevisions extends Builder {

        /**
         * @param clientId
         * @param nRevs
         */
        public BuilderNumberRevisions(final String clientId, final int nRevs) {
            super(clientId);

            this.useIdFirst = false;
            this.processing = "Nrev";
            this.nRevs = nRevs;
        }
    }

    /**
     * @author Jose A. Garcia Sanchez
     */
    public static final class BuilderUid extends Builder {

        /**
         * @param clientId
         * @param uid
         */
        public BuilderUid(final String clientId, final String uid) {
            super(clientId);

            this.useIdFirst = false;
            this.processing = "Ids";
            this.uid = uid;
        }

    }

    /**
     * @author Jose A. Garcia Sanchez
     */
    public static final class BuilderAll extends Builder {

        /**
         * @param clientId
         */
        public BuilderAll(final String clientId) {
            super(clientId);
            this.processing = "All";
        }
    }

    /**
     * @author Jose A. Garcia Sanchez
     */
    public static final class BuilderNone extends Builder {

        /**
         * @param clientId
         */
        public BuilderNone(final String clientId) {
            super(clientId);
            this.processing = "None";
        }
    }
}
