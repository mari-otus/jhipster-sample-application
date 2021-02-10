package ru.otus.spring.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.otus.spring.web.rest.TestUtil;

public class SubscribingsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subscribings.class);
        Subscribings subscribings1 = new Subscribings();
        subscribings1.setId(1L);
        Subscribings subscribings2 = new Subscribings();
        subscribings2.setId(subscribings1.getId());
        assertThat(subscribings1).isEqualTo(subscribings2);
        subscribings2.setId(2L);
        assertThat(subscribings1).isNotEqualTo(subscribings2);
        subscribings1.setId(null);
        assertThat(subscribings1).isNotEqualTo(subscribings2);
    }
}
