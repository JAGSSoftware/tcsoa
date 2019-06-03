/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.dispatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jose A. Garcia Sanchez
 */
public enum RequestState {
    INITIAL(RequestLevel.INITIAL),
    PREPARING(RequestLevel.IN_PROGRESS),
    SCHEDULED(RequestLevel.IN_PROGRESS),
    TRANSLATING(RequestLevel.IN_PROGRESS),
    LOADING(RequestLevel.IN_PROGRESS),
    SUPERSEDING(RequestLevel.IN_PROGRESS),
    COMPLETE(RequestLevel.FINAL),
    DUPLICATE(RequestLevel.FINAL),
    DELETE(RequestLevel.FINAL),
    CANCELLED(RequestLevel.FINAL),
    SUPERSEDED(RequestLevel.FINAL),
    NO_TRANS(RequestLevel.FINAL),
    TERMINAL(RequestLevel.FINAL);

    private final RequestLevel level;

    /**
     * @param state
     */
    private RequestState(final RequestLevel state) {
        this.level = state;
    }

    /**
     * @param level
     * @return
     */
    public static synchronized List<RequestState> findStateByLevel(final RequestLevel level) {
        final List<RequestState> states = new ArrayList<RequestState>();

        for (final RequestState state : values()) {
            if (state.level == level) {
                states.add(state);
            }
        }

        return states;
    }

    /**
     * @param level
     * @return
     */
    public static synchronized String[] findStateByLevelToString(final RequestLevel level) {
        final List<String> states = new ArrayList<String>();

        for (final RequestState state : findStateByLevel(level)) {
            states.add(state.toString());
        }

        return states.toArray(new String[states.size()]);
    }

    /**
     * @param level
     * @return
     */
    public static synchronized List<RequestState> findStateNotByLevel(final RequestLevel level) {
        final List<RequestState> states = new ArrayList<RequestState>();

        for (final RequestState state : values()) {
            if (state.level != level) {
                states.add(state);
            }
        }

        return states;
    }

    /**
     * @param level
     * @return
     */
    public static synchronized String[] findStateNotByLevelToString(final RequestLevel level) {
        final List<String> states = new ArrayList<String>();

        for (final RequestState state : findStateNotByLevel(level)) {
            states.add(state.toString());
        }

        return states.toArray(new String[states.size()]);
    }

}
