/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jose A. Garcia Sanchez
 */
public class GetItemAndRelatedObjectsInfo {
    /** Client identifier. */
    private final String clientId;
//    private final ItemInfo itemInfo;
//    private final RevisionInfo revisionInfo;

    /**
     * @param builder
     */
    private GetItemAndRelatedObjectsInfo(final Builder builder) {
        this.clientId = builder.clientId;
//        this.itemInfo = builder.itemInfoBuilder.build();
//        this.revisionInfo = builder.revisionInfoBuilder.build();
    }

    /**
     * @return
     */
    public synchronized com.teamcenter.services.strong.core._2008_06.DataManagement.GetItemAndRelatedObjectsInfo getItemAndRelatedObjectsInfo() {
        final com.teamcenter.services.strong.core._2008_06.DataManagement.GetItemAndRelatedObjectsInfo structInfo = new com.teamcenter.services.strong.core._2008_06.DataManagement.GetItemAndRelatedObjectsInfo();

        structInfo.clientId = clientId;
//        structInfo.itemInfo = itemInfo.itemInfo();

        return structInfo;
    }

    /**
     * @author Jose A. Garcia Sanchez
     */
    public static class Builder {
        /** Client identifier. */
        private final String clientId;

        private final List<String> bomViewRevisionTypeNames = new ArrayList<String>();
//        private final ItemInfo.Builder itemInfoBuilder;
//        private final RevisionInfo.Builder revisionInfoBuilder;


        /**
         * @param clientId
         */
        public Builder(final String clientId, final ItemInfo.Builder itemInfoBuilder) {
            if (clientId == null) {
                throw new IllegalArgumentException("clientId is null");
            }

            if (itemInfoBuilder == null) {
                throw new IllegalArgumentException("itemInfoBuilder is null");
            }

            this.clientId = clientId;
//            this.itemInfoBuilder = itemInfoBuilder;
//            this.revisionInfoBuilder = new RevisionInfo.Builder(clientId);
        }

        /**
         * @return
         */
        public GetItemAndRelatedObjectsInfo build() {
            return new GetItemAndRelatedObjectsInfo(this);
        }

        /**
         * @param bvrTypeName
         * @return
         */
        public Builder bvrTypeName(final String bvrTypeName) {
            if (bvrTypeName == null) {
                throw new IllegalArgumentException("bvrTypeName is null");
            }

            bomViewRevisionTypeNames.add(bvrTypeName);

            return this;
        }

//        public Builder itemInfo(final ItemInfo.Builder itemInfoBuilder) {
//            if (itemInfoBuilder == null) {
//                throw new IllegalArgumentException("itemInfoBuilder is null");
//            }
//
//            return this;
//        }
    }
}
