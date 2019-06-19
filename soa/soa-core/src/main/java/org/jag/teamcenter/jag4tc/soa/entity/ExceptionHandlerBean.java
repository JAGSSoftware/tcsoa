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

import com.teamcenter.schemas.soa._2006_03.exceptions.ConnectionException;
import com.teamcenter.schemas.soa._2006_03.exceptions.InternalServerException;
import com.teamcenter.schemas.soa._2006_03.exceptions.ProtocolException;
import com.teamcenter.soa.client.ExceptionHandler;
import com.teamcenter.soa.exceptions.CanceledOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO Convertir a objeto Guice
class ExceptionHandlerBean implements ExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerBean.class);

    @Override
    public void handleException(final InternalServerException exception) {
        LOGGER.info("Exception caught in handleException(InternalServerException)", exception);
        if (exception instanceof ConnectionException) {
            LOGGER.warn("The server returned a connection error: {}", exception.getMessage());
        } else if (exception instanceof ProtocolException) {
            LOGGER.warn("The server returned a protocol error: {}", exception.getMessage());
        } else {
            LOGGER.warn("The server returned an internal server error: {}", exception.getMessage());
        }
        throw new RuntimeException(exception);
    }

    @Override
    public void handleException(final CanceledOperationException exception) {
        LOGGER.info("Exception caught in handleException(CanceledOperationException)", exception);
        throw new RuntimeException(exception);
    }
}
