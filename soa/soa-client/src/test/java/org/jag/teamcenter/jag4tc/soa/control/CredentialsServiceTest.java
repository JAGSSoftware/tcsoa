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
package org.jag.teamcenter.jag4tc.soa.control;

import org.jag.teamcenter.jag4tc.soa.entity.Credentials;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CredentialsServiceTest {

    private CredentialsService underTest;

    @Before
    public void setUp() {
        underTest = new CredentialsService();
    }

    @Test
    public void getCredentialsFrom() {
        final Arguments arguments = new Arguments();
        arguments.setUsername("username");
        arguments.setPassword("password");
        arguments.setGroup("group");
        arguments.setRole("role");
        arguments.setHost("host");

        final Credentials credentials = underTest.getCredentialsFrom(arguments);

        assertThat(credentials).isNotNull();
        assertThat(credentials.getUsername()).isEqualTo("username");
        assertThat(credentials.getPassword()).isEqualTo("password");
        assertThat(credentials.getGroup()).isEqualTo("group");
        assertThat(credentials.getRole()).isEqualTo("role");
    }
}
