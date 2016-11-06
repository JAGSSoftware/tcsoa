/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.service;

import java.util.ArrayList;
import java.util.List;

import com.teamcenter.ets.request.TranslationRequest;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.strong.DispatcherRequest;

/**
 * @author Jose A. Garcia Sanchez
 */
public class QueryDispatcherRequest {

    /**
     * @param filter
     * @return
     * @throws Exception
     */
    public static DispatcherRequest[] queryRequests(final QueryDispatcherRequestFilter filter) throws Exception {
        final ModelObject[] modelObjects = TranslationRequest.queryRequests(filter.providers(),
                filter.services(), filter.requestStates(), filter.priorities(), filter.modifiedDate(),
                filter.primaryObjects(),
                filter.tasksIds(), filter.loadData());

        final List<DispatcherRequest> requests = new ArrayList<DispatcherRequest>();
        for (final ModelObject modelObject : modelObjects) {
            requests.add((DispatcherRequest) modelObject);
        }

        return requests.toArray(new DispatcherRequest[requests.size()]);
    }
}
