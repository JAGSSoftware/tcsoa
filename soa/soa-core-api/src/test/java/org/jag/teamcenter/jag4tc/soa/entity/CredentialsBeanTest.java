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

public class CredentialsBeanTest {

    private CredentialsBean underTest;

    @Before
    public void setUp() {
        underTest = new CredentialsBean();
        underTest.setUsername("MyUsername");
        underTest.setPassword("MyPassword");
        underTest.setGroup("MyGroup");
        underTest.setRole("MyRole");
    }

    @Test
    public void getUsername() {
        assertThat(underTest.getUsername()).isEqualTo("MyUsername");
    }

    @Test
    public void getPassword() {
        assertThat(underTest.getPassword()).isEqualTo("MyPassword");
    }

    @Test
    public void getGroup() {
        assertThat(underTest.getGroup()).isEqualTo("MyGroup");
    }

    @Test
    public void getRole() {
        assertThat(underTest.getRole()).isEqualTo("MyRole");
    }

    @Test
    public void testToString() {
        assertThat(underTest.toString()).isEqualTo(
                "CredentialsBean{username: [MyUsername], password: [********], group: [MyGroup], role: [MyRole]}");
    }
}
