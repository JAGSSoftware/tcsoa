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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ArgumentsServiceTest {

    @InjectMocks
    private ArgumentsService underTest;

    @Spy
    private OptionsHelpFormatter optionsHelpFormatter = new OptionsHelpFormatter();

    @Test
    public void parseShortArguments() {
        final Arguments arguments = underTest.parse(new String[]{"-host", "http://my.host.com", "-u", "myUsername",
                "-p", "myPassword", "-g", "Engineering", "-r", "Project Leader"});

        assertThat(arguments).isNotNull();
        assertThat(arguments.getHost()).isEqualTo("http://my.host.com");
        assertThat(arguments.getUsername()).isEqualTo("myUsername");
        assertThat(arguments.getPassword()).isEqualTo("myPassword");
        assertThat(arguments.getGroup()).isEqualTo("Engineering");
        assertThat(arguments.getRole()).isEqualTo("Project Leader");
    }

    @Test
    public void parseLongArguments() {
        final Arguments arguments = underTest.parse(new String[]{"-host", "http://my.long.host.com",
                "--username", "username", "--password", "password", "--group", "Design", "--role", "Administrator"});

        assertThat(arguments).isNotNull();
        assertThat(arguments.getHost()).isEqualTo("http://my.long.host.com");
        assertThat(arguments.getUsername()).isEqualTo("username");
        assertThat(arguments.getPassword()).isEqualTo("password");
        assertThat(arguments.getGroup()).isEqualTo("Design");
        assertThat(arguments.getRole()).isEqualTo("Administrator");
    }

    @Test
    public void parseMandatoryArguments() {
        final Arguments arguments = underTest.parse(new String[]{"-host", "http://mandatory.host.com",
                "-u", "max", "-p", "mustermann"});

        assertThat(arguments).isNotNull();
        assertThat(arguments.getHost()).isEqualTo("http://mandatory.host.com");
        assertThat(arguments.getUsername()).isEqualTo("max");
        assertThat(arguments.getPassword()).isEqualTo("mustermann");
        assertThat(arguments.getGroup()).isEmpty();
        assertThat(arguments.getRole()).isEmpty();
    }

    @Test
    public void parseHelpArgument() {
        final Arguments arguments = underTest.parse(new String[]{"-host", "http://mandatory.host.com",
                "-u", "max", "-p", "mustermann", "-h"});

        assertThat(arguments).isNull();
    }

    @Test
    public void parseEmptyArguments() {
        final Arguments arguments = underTest.parse(new String[0]);

        assertThat(arguments).isNull();
    }

    @Test
    public void parseArgumentsWithoutValue() {
        final Arguments arguments = underTest.parse(new String[]{"-host", "-u", "max", "-p", "mustermann"});

        assertThat(arguments).isNull();
    }
}
