/*
 * (c) 2019 - José A. García Sánchez
 */
package org.jag.teamcenter.soa.dispatcher;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class RequestStateFindStateNotByLevelToStringParameterizedTest {

    @Parameterized.Parameters(name = "{index}: findStateNotByLevelToString({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {RequestLevel.INITIAL, new String[]{"PREPARING", "SCHEDULED",
                        "TRANSLATING", "LOADING", "SUPERSEDING", "COMPLETE", "DUPLICATE", "DELETE",
                        "CANCELLED", "SUPERSEDED", "NO_TRANS",
                        "TERMINAL"}},
                {RequestLevel.IN_PROGRESS, new String[]{"INITIAL", "COMPLETE", "DUPLICATE", "DELETE",
                        "CANCELLED", "SUPERSEDED", "NO_TRANS",
                        "TERMINAL"}},
                {RequestLevel.FINAL,
                        new String[]{"INITIAL", "PREPARING", "SCHEDULED",
                                "TRANSLATING", "LOADING", "SUPERSEDING"}}
        });
    }

    private final RequestLevel input;
    private final String[] expected;

    public RequestStateFindStateNotByLevelToStringParameterizedTest(final RequestLevel input, final String[] expected) {
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void findStateByLevel() {
        final String[] stateByLevel = RequestState.findStateNotByLevelToString(input);

        assertThat(stateByLevel).containsExactly(expected);
    }
}
