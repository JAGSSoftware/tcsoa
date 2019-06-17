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

import com.teamcenter.soa.client.model.ModelEventListener;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.exceptions.NotLoadedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO Convertir a objeto Guice
public class ModelEventListenerBean extends ModelEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelEventListenerBean.class);

    @Override
    public void localObjectChange(final ModelObject[] objects) {
        for (final ModelObject modelObject : objects) {
            final String uid = modelObject.getUid();
            final String type = modelObject.getTypeObject().getName();
            String name = "";
            if (modelObject.getTypeObject().isInstanceOf("WorkspaceObject")) {
                try {
                    name = modelObject.getPropertyObject("object_string").getStringValue();
                } catch (NotLoadedException e) {
                    LOGGER.warn("NotLoadedException happened {}", e.getMessage());
                }
            }

            LOGGER.info("    uid: {}, type: {}, name: {}", uid, type, name);
        }
    }

    @Override
    public void localObjectCreate(final ModelObject[] objects) {
        LOGGER.trace("localObjectCreate({})", (Object) objects);
        super.localObjectCreate(objects);
    }

    @Override
    public void localObjectDelete(final String[] uids) {
        if (uids.length == 0) {
            return;
        }

        LOGGER.info("The following objects have been deleted from the server and removed from the client data model:");
        for (final String uid : uids) {
            LOGGER.info("    {}", uid);
        }
    }

    @Override
    public void sharedObjectChange(final ModelObject[] objects) {
        LOGGER.trace("sharedObjectChange({})", (Object) objects);
        super.sharedObjectChange(objects);
    }

    @Override
    public void sharedObjectCreate(final ModelObject[] objects) {
        LOGGER.trace("sharedObjectCreate({})", (Object) objects);
        super.sharedObjectCreate(objects);
    }

    @Override
    public void sharedObjectDelete(final String[] uids) {
        LOGGER.trace("sharedObjectDelete({})", (Object) uids);
        super.sharedObjectDelete(uids);
    }
}
