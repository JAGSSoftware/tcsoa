/*
 * (c) 2019 - José A. García Sánchez
 */
package org.jag.teamcenter.soa.service;

import com.teamcenter.soa.client.model.strong.DispatcherRequest;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class QueryDispatcherRequestTest {

    @Test
    @Ignore("Not yet implemented")
    public void queryRequests() throws Exception {
        final QueryDispatcherRequestFilter filter = new QueryDispatcherRequestFilter.Builder().build();

        final DispatcherRequest[] dispatcherRequests = QueryDispatcherRequest.queryRequests(filter);

        assertThat(dispatcherRequests).isNotNull();
        fail("Not yet implemented");
    }
}
