/*
 * MIT License
 *
 * Copyright (c) 2018 José A. García Sánchez
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
package org.jag.teamcenter.jag4tc.soa.entity;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConnectionConfigurationFactoryBeanTest {

    private ConnectionConfigurationFactoryBean underTest;

    @Before
    public void setUp() {
        underTest = new ConnectionConfigurationFactoryBean();
    }

    @Test
    public void createConnectionConfigurationHttp() {
        final ConnectionConfiguration connectionConfiguration = underTest
                .createConnectionConfiguration("http://localhost:8080/tc", "SoaAppX");

        assertThat(connectionConfiguration).isNotNull();
        assertThat(connectionConfiguration).isInstanceOf(HttpConnectionConfiguration.class);
    }

    @Test
    public void createConnectionConfigurationHttps() {
        final ConnectionConfiguration connectionConfiguration = underTest
                .createConnectionConfiguration("https://localhost:8080/tc", "SoaAppX");

        assertThat(connectionConfiguration).isNotNull();
        assertThat(connectionConfiguration).isInstanceOf(HttpConnectionConfiguration.class);
    }

    @Test
    public void createConnectionConfigurationIiop() {
        final ConnectionConfiguration connectionConfiguration = underTest
                .createConnectionConfiguration("iiop://localhost:8080/tc", "SoaAppX");

        assertThat(connectionConfiguration).isNotNull();
        assertThat(connectionConfiguration).isInstanceOf(IiopConnectionConfiguration.class);
    }

    @Test
    public void createConnectionConfigurationTccs() {
        final ConnectionConfiguration connectionConfiguration = underTest
                .createConnectionConfiguration("tccs://localhost:8080/tc", "SoaAppX");

        assertThat(connectionConfiguration).isNotNull();
        assertThat(connectionConfiguration).isInstanceOf(TccsConnectionConfiguration.class);
    }

    @Test
    public void createConnectionConfigurationUnknown() {
        final ConnectionConfiguration connectionConfiguration = underTest.createConnectionConfiguration("", "SoaAppX");

        assertThat(connectionConfiguration).isNull();
    }
}
