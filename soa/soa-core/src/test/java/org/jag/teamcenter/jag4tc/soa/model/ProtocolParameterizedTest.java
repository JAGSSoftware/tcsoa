package org.jag.teamcenter.jag4tc.soa.model;/*
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

import java.util.Arrays;
import java.util.Collection;

import com.teamcenter.soa.SoaConstants;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ProtocolParameterizedTest {

    @Parameterized.Parameters(name = "{0}.getProtocolValue() = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Protocol.HTTP, SoaConstants.HTTP},
                {Protocol.IIOP, SoaConstants.IIOP},
                {Protocol.TCCS, SoaConstants.TCCS}
        });
    }

    private final Protocol input;
    private final String expectedProtocolValue;

    public ProtocolParameterizedTest(final Protocol input, final String expectedProtocolValue) {
        this.input = input;
        this.expectedProtocolValue = expectedProtocolValue;
    }

    @Test
    public void getProtocolValue() {
        assertThat(input.getProtocolValue()).isEqualTo(expectedProtocolValue);
    }
}
