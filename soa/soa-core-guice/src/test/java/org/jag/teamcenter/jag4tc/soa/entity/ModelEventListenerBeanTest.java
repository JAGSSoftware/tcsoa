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

import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.Property;
import com.teamcenter.soa.client.model.Type;
import com.teamcenter.soa.exceptions.NotLoadedException;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ModelEventListenerBeanTest {

    private ModelEventListenerBean underTest;

    @Before
    public void setUp() {
        underTest = new ModelEventListenerBean();
    }

    @Test
    public void localObjectChange() throws NotLoadedException {
        final ModelObject modelObject = mock(ModelObject.class);
        final Type typeObject = mock(Type.class);
        final Property objectStringProperty = mock(Property.class);
        when(modelObject.getUid()).thenReturn("UID#0001");
        when(modelObject.getTypeObject()).thenReturn(typeObject);
        when(typeObject.getName()).thenReturn("Type#0001");
        when(typeObject.isInstanceOf(eq("WorkspaceObject"))).thenReturn(true);
        when(modelObject.getPropertyObject(eq("object_string"))).thenReturn(objectStringProperty);
        when(objectStringProperty.getStringValue()).thenReturn("ObjectString#0001");

        underTest.localObjectChange(new ModelObject[]{modelObject});
    }

    @Test
    public void localObjectChangeWithPropertyNotLoaded() throws NotLoadedException {
        final ModelObject modelObject = mock(ModelObject.class);
        final Type typeObject = mock(Type.class);
        final Property objectStringProperty = mock(Property.class);
        when(modelObject.getUid()).thenReturn("UID#0001");
        when(modelObject.getTypeObject()).thenReturn(typeObject);
        when(typeObject.getName()).thenReturn("Type#0001");
        when(typeObject.isInstanceOf(eq("WorkspaceObject"))).thenReturn(true);
        when(modelObject.getPropertyObject(eq("object_string"))).thenThrow(new NotLoadedException("Fake exception"));

        underTest.localObjectChange(new ModelObject[]{modelObject});

        verify(objectStringProperty, never()).getStringValue();
    }

    @Test
    public void localObjectChangeForNoWorkspaceObject() throws NotLoadedException {
        final ModelObject modelObject = mock(ModelObject.class);
        final Type typeObject = mock(Type.class);
        final Property objectStringProperty = mock(Property.class);
        when(modelObject.getUid()).thenReturn("UID#0001");
        when(modelObject.getTypeObject()).thenReturn(typeObject);
        when(typeObject.getName()).thenReturn("Type#0001");
        when(typeObject.isInstanceOf(anyString())).thenReturn(false);

        underTest.localObjectChange(new ModelObject[]{modelObject});

        verify(modelObject, never()).getPropertyObject(anyString());
        verify(objectStringProperty, never()).getStringValue();
    }

    @Test
    public void localObjectCreate() {
        final ModelObject modelObject = mock(ModelObject.class);

        underTest.localObjectCreate(new ModelObject[]{modelObject});
    }

    @Test
    public void localObjectDelete() {
        underTest.localObjectDelete(new String[]{"UID#0001"});
    }

    @Test
    public void localObjectDeleteEmptyArray() {
        underTest.localObjectDelete(new String[0]);
    }

    @Test
    public void sharedObjectChange() {
        final ModelObject modelObject = mock(ModelObject.class);

        underTest.sharedObjectChange(new ModelObject[]{modelObject});
    }

    @Test
    public void sharedObjectCreate() {
        final ModelObject modelObject = mock(ModelObject.class);

        underTest.sharedObjectCreate(new ModelObject[]{modelObject});
    }

    @Test
    public void sharedObjectDelete() {
        underTest.sharedObjectDelete(new String[]{"UID#0001"});
    }
}
