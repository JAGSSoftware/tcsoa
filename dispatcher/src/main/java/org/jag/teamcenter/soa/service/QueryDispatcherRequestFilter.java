/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jag.teamcenter.soa.dispatcher.RequestState;

import com.teamcenter.soa.client.model.ModelObject;

/**
 * @author Jose A. Garcia Sanchez
 */
public final class QueryDispatcherRequestFilter {
    private final String[] providers;
    private final String[] services;
    private final String[] requestStates;
    private final int[] priorities;
    private final Calendar modifiedDate;
    private final ModelObject[] primaryObjects;
    private final String[] taskIds;
    private final boolean loadData;

    private static final int[] valueOf(final List<Integer> values) {
        final int[] array = new int[values.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = values.get(i);
        }
        return array;
    }

    private QueryDispatcherRequestFilter(final Builder builder) {
        this.providers = builder.providers.toArray(new String[builder.providers.size()]);
        this.services = builder.services.toArray(new String[builder.services.size()]);
        this.requestStates = builder.requestStates.toArray(new String[builder.requestStates.size()]);
        this.priorities = valueOf(builder.priorities);
        this.modifiedDate = builder.modifiedDate;
        this.primaryObjects = builder.primaryObjects.toArray(new ModelObject[builder.primaryObjects.size()]);
        this.taskIds = builder.taskIds.toArray(new String[builder.taskIds.size()]);
        this.loadData = builder.noLoadData;
    }

    public String[] providers() {
        return providers;
    }

    public String[] services() {
        return services;
    }

    public String[] requestStates() {
        return requestStates;
    }

    public int[] priorities() {
        return priorities;
    }

    public String modifiedDate() {
        final String formattedDate;
        if (modifiedDate == null) {
            formattedDate = null;
        } else {
            formattedDate = DateFormat.getDateTimeInstance().format(modifiedDate.getTime());
        }
        return formattedDate;
    }

    public ModelObject[] primaryObjects() {
        return primaryObjects;
    }

    public String[] tasksIds() {
        return taskIds;
    }

    public boolean loadData() {
        return loadData;
    }

    public static class Builder {
        private List<String> providers = new ArrayList<String>();
        private List<String> services = new ArrayList<String>();
        private List<String> requestStates = new ArrayList<String>();
        private List<Integer> priorities = new ArrayList<Integer>();
        private Calendar modifiedDate = null;
        private List<ModelObject> primaryObjects = new ArrayList<ModelObject>();
        private List<String> taskIds = new ArrayList<String>();
        private boolean noLoadData;

        public Builder addProvider(final String provider) {
            providers.add(provider);

            return this;
        }

        public Builder addService(final String service) {
            services.add(service);

            return this;
        }

        public Builder add(final RequestState requestState) {
            requestStates.add(requestState.toString());

            return this;
        }

        public Builder add(final RequestState[] requestStates) {
            for (final RequestState requestState : requestStates) {
                add(requestState);
            }

            return this;
        }

        public Builder add(final List<RequestState> requestStates) {
            for (final RequestState requestState : requestStates) {
                add(requestState);
            }

            return this;
        }

        public Builder add(final int priority) {
            priorities.add(priority);

            return this;
        }

        public Builder modifiedDate(final Calendar modifiedDate) {
            this.modifiedDate = modifiedDate;

            return this;
        }

        public Builder add(final ModelObject primaryObject) {
            primaryObjects.add(primaryObject);

            return this;
        }

        public Builder addTaskId(final String taskId) {
            taskIds.add(taskId);

            return this;
        }

        public Builder loadData(final boolean loadData) {
            noLoadData = !loadData;

            return this;
        }

        public QueryDispatcherRequestFilter build() {
            return new QueryDispatcherRequestFilter(this);
        }
    }
}
