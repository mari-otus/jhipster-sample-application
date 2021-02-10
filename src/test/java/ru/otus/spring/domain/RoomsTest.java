package ru.otus.spring.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.otus.spring.web.rest.TestUtil;

public class RoomsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rooms.class);
        Rooms rooms1 = new Rooms();
        rooms1.setId(1L);
        Rooms rooms2 = new Rooms();
        rooms2.setId(rooms1.getId());
        assertThat(rooms1).isEqualTo(rooms2);
        rooms2.setId(2L);
        assertThat(rooms1).isNotEqualTo(rooms2);
        rooms1.setId(null);
        assertThat(rooms1).isNotEqualTo(rooms2);
    }
}
