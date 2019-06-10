/*
 * (c) 2019 - José A. García Sánchez
 */
package org.jag.teamcenter.soa.dispatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class RequestStateFindStateNotByLevelParameterizedTest {

    @Parameterized.Parameters(name = "{index}: findStateNotByLevel({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {RequestLevel.INITIAL, new RequestState[]{RequestState.PREPARING, RequestState.SCHEDULED,
                        RequestState.TRANSLATING, RequestState.LOADING, RequestState.SUPERSEDING, RequestState.COMPLETE,
                        RequestState.DUPLICATE, RequestState.DELETE,
                        RequestState.CANCELLED, RequestState.SUPERSEDED, RequestState.NO_TRANS,
                        RequestState.TERMINAL}},
                {RequestLevel.IN_PROGRESS,
                        new RequestState[]{RequestState.INITIAL, RequestState.COMPLETE, RequestState.DUPLICATE,
                                RequestState.DELETE,
                                RequestState.CANCELLED, RequestState.SUPERSEDED, RequestState.NO_TRANS,
                                RequestState.TERMINAL}},
                {RequestLevel.FINAL,
                        new RequestState[]{RequestState.INITIAL, RequestState.PREPARING, RequestState.SCHEDULED,
                                RequestState.TRANSLATING, RequestState.LOADING, RequestState.SUPERSEDING}}
        });
    }

    private final RequestLevel input;
    private final RequestState[] expected;

    public RequestStateFindStateNotByLevelParameterizedTest(final RequestLevel input, final RequestState[] expected) {
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void findStateByLevel() {
        final List<RequestState> stateByLevel = RequestState.findStateNotByLevel(input);

        assertThat(stateByLevel).containsExactly(expected);
    }
}
