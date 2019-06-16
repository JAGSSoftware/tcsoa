/*
 * (c) 2019 - José A. García Sánchez
 */
package org.jag.teamcenter.soa.types;

import com.teamcenter.soa.SoaConstants;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BindingTest {

    @Test
    public void value() {
        assertThat(Binding.REST.value()).isEqualTo(SoaConstants.REST);
    }
}
