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

import com.teamcenter.soa.client.model.ErrorStack;
import com.teamcenter.soa.client.model.ErrorValue;
import com.teamcenter.soa.client.model.ModelObject;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PartialErrorListenerBeanTest {

    private PartialErrorListenerBean underTest;

    @Before
    public void setUp() {
        underTest = new PartialErrorListenerBean();
    }

    @Test
    public void handlePartialErrorWithEmptyArray() {
        underTest.handlePartialError(new ErrorStack[0]);
    }

    @Test
    public void handlePartialError() {
        final ErrorStack errorStackWithAssociatedObject = mock(ErrorStack.class);
        final ErrorStack errorStackWithClientId = mock(ErrorStack.class);
        final ErrorStack errorStackWithClientIndex = mock(ErrorStack.class);
        final ModelObject associatedObject = mock(ModelObject.class);
        final ErrorValue errorValue = mock(ErrorValue.class);

        when(errorStackWithAssociatedObject.hasAssociatedObject()).thenReturn(true);
        when(errorStackWithAssociatedObject.getAssociatedObject()).thenReturn(associatedObject);
        when(errorStackWithAssociatedObject.getErrorValues()).thenReturn(new ErrorValue[0]);
        when(associatedObject.getUid()).thenReturn("UID#001");
        when(errorStackWithClientId.hasClientId()).thenReturn(true);
        when(errorStackWithClientId.getClientId()).thenReturn("UID#002");
        when(errorStackWithClientId.getErrorValues()).thenReturn(new ErrorValue[0]);
        when(errorStackWithClientIndex.hasClientIndex()).thenReturn(true);
        when(errorStackWithClientIndex.getClientIndex()).thenReturn(1234);
        when(errorStackWithClientIndex.getErrorValues()).thenReturn(new ErrorValue[]{errorValue});
        when(errorValue.getCode()).thenReturn(1);
        when(errorValue.getLevel()).thenReturn(2);
        when(errorValue.getMessage()).thenReturn("Error message for the error value");

        underTest.handlePartialError(new ErrorStack[]{errorStackWithAssociatedObject, errorStackWithClientId,
                errorStackWithClientIndex});
    }
}
