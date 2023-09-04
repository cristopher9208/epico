package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EpicoitemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Epicoitem.class);
        Epicoitem epicoitem1 = new Epicoitem();
        epicoitem1.setId(1L);
        Epicoitem epicoitem2 = new Epicoitem();
        epicoitem2.setId(epicoitem1.getId());
        assertThat(epicoitem1).isEqualTo(epicoitem2);
        epicoitem2.setId(2L);
        assertThat(epicoitem1).isNotEqualTo(epicoitem2);
        epicoitem1.setId(null);
        assertThat(epicoitem1).isNotEqualTo(epicoitem2);
    }
}
