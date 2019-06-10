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
public class RequestStateFindStateByLevelToStringParameterizedTest {

    @Parameterized.Parameters(name = "{index}: findStateByLevelToString({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {RequestLevel.INITIAL, new String[]{"INITIAL"}},
                {RequestLevel.IN_PROGRESS, new String[]{"PREPARING", "SCHEDULED",
                        "TRANSLATING", "LOADING", "SUPERSEDING"}},
                {RequestLevel.FINAL,
                        new String[]{"COMPLETE", "DUPLICATE", "DELETE",
                                "CANCELLED", "SUPERSEDED", "NO_TRANS",
                                "TERMINAL"}}
        });
    }

    private final RequestLevel input;
    private final String[] expected;

    public RequestStateFindStateByLevelToStringParameterizedTest(final RequestLevel input, final String[] expected) {
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void findStateByLevel() {
        final String[] stateByLevel = RequestState.findStateByLevelToString(input);

        assertThat(stateByLevel).containsExactly(expected);
    }
}
