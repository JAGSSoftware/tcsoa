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
import com.teamcenter.soa.client.model.PartialErrorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PartialErrorListenerBean implements PartialErrorListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PartialErrorListenerBean.class);

    @Override
    public void handlePartialError(final ErrorStack[] errorStacks) {
        if (errorStacks.length == 0) {
            return;
        }

        LOGGER.warn("Partial errors caught in {}", getClass().getSimpleName());
        for (final ErrorStack errorStack : errorStacks) {
            LOGGER.warn("Partial error for ");
            if (errorStack.hasAssociatedObject()) {
                LOGGER.warn("object {}", errorStack.getAssociatedObject().getUid());
            } else if (errorStack.hasClientId()) {
                LOGGER.warn("client id {}", errorStack.hasClientId());
            } else if (errorStack.hasClientIndex()) {
                LOGGER.warn("client index {}", errorStack.getClientIndex());
            }

            final ErrorValue[] errorValues = errorStack.getErrorValues();
            for (final ErrorValue errorValue : errorValues) {
                LOGGER.warn("    Code: {}\tSeverity: {}\t{}", errorValue.getCode(), errorValue.getLevel(),
                        errorValue.getMessage());
            }
        }
    }
}
