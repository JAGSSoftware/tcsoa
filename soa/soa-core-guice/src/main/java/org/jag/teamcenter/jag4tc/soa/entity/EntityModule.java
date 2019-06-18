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
package org.jag.teamcenter.jag4tc.soa.entity;

import com.google.inject.AbstractModule;
import com.teamcenter.soa.client.ExceptionHandler;
import com.teamcenter.soa.client.RequestListener;
import com.teamcenter.soa.client.model.ModelEventListener;
import com.teamcenter.soa.client.model.PartialErrorListener;

public class EntityModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ExceptionHandler.class).to(ExceptionHandlerBean.class);
        bind(PartialErrorListener.class).to(PartialErrorListenerBean.class);
        bind(ModelEventListener.class).to(ModelEventListenerBean.class);
        bind(RequestListener.class).to(RequestListenerBean.class);

        bind(ConnectionConfigurationFactory.class).to(ConnectionConfigurationFactoryBean.class);
        bind(ConnectionConnector.class).to(ConnectionConnectorBean.class);
    }
}
