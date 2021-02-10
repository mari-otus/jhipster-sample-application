package ru.otus.spring.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.otus.spring.web.rest.TestUtil;

public class ProfilesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profiles.class);
        Profiles profiles1 = new Profiles();
        profiles1.setId(1L);
        Profiles profiles2 = new Profiles();
        profiles2.setId(profiles1.getId());
        assertThat(profiles1).isEqualTo(profiles2);
        profiles2.setId(2L);
        assertThat(profiles1).isNotEqualTo(profiles2);
        profiles1.setId(null);
        assertThat(profiles1).isNotEqualTo(profiles2);
    }
}
