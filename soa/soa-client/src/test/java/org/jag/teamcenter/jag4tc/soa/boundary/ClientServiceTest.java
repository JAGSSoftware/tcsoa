/*
 * MIT License
 *
 * Copyright (c) 2019 José A. García Sánchez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.jag.teamcenter.jag4tc.soa.boundary;

import org.jag.teamcenter.jag4tc.soa.control.Arguments;
import org.jag.teamcenter.jag4tc.soa.control.ArgumentsServiceBA;
import org.jag.teamcenter.jag4tc.soa.control.CredentialsServiceBA;
import org.jag.teamcenter.jag4tc.soa.entity.Credentials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService underTest;

    @Mock
    private ArgumentsServiceBA argumentsService;

    @Mock
    private CredentialsServiceBA credentialsService;

    @Test
    public void parse() {
        final Arguments arguments = new Arguments();

        when(argumentsService.parse(any(String[].class))).thenReturn(arguments);

        final Arguments actualArguments = underTest.parse(new String[]{"a", "b", "c"});

        assertThat(actualArguments).isEqualTo(arguments);

        final ArgumentCaptor<String[]> argumentCaptor = ArgumentCaptor.forClass(String[].class);
        verify(argumentsService).parse(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).containsExactly("a", "b", "c");
    }

    @Test
    public void getCredentialsFrom() {
        final Credentials credentials = mock(Credentials.class);

        when(credentialsService.getCredentialsFrom(any(Arguments.class))).thenReturn(credentials);

        final Credentials actualCredentials = underTest.getCredentialsFrom(new Arguments());

        assertThat(actualCredentials).isNotNull();
        assertThat(actualCredentials).isEqualTo(credentials);
        verify(credentialsService).getCredentialsFrom(any(Arguments.class));
    }
}
