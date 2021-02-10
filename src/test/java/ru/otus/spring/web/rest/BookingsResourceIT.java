package ru.otus.spring.web.rest;

import ru.otus.spring.JbookingApp;
import ru.otus.spring.domain.Bookings;
import ru.otus.spring.repository.BookingsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static ru.otus.spring.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BookingsResource} REST controller.
 */
@SpringBootTest(classes = JbookingApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BookingsResourceIT {

    private static final Integer DEFAULT_ROOM_ID = 1;
    private static final Integer UPDATED_ROOM_ID = 2;

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_BEGIN_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BEGIN_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookingsMockMvc;

    private Bookings bookings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bookings createEntity(EntityManager em) {
        Bookings bookings = new Bookings()
            .roomId(DEFAULT_ROOM_ID)
            .login(DEFAULT_LOGIN)
            .beginDate(DEFAULT_BEGIN_DATE)
            .endDate(DEFAULT_END_DATE)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .deleteDate(DEFAULT_DELETE_DATE);
        return bookings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bookings createUpdatedEntity(EntityManager em) {
        Bookings bookings = new Bookings()
            .roomId(UPDATED_ROOM_ID)
            .login(UPDATED_LOGIN)
            .beginDate(UPDATED_BEGIN_DATE)
            .endDate(UPDATED_END_DATE)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .deleteDate(UPDATED_DELETE_DATE);
        return bookings;
    }

    @BeforeEach
    public void initTest() {
        bookings = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookings() throws Exception {
        int databaseSizeBeforeCreate = bookingsRepository.findAll().size();
        // Create the Bookings
        restBookingsMockMvc.perform(post("/api/bookings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isCreated());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeCreate + 1);
        Bookings testBookings = bookingsList.get(bookingsList.size() - 1);
        assertThat(testBookings.getRoomId()).isEqualTo(DEFAULT_ROOM_ID);
        assertThat(testBookings.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testBookings.getBeginDate()).isEqualTo(DEFAULT_BEGIN_DATE);
        assertThat(testBookings.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testBookings.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testBookings.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testBookings.getDeleteDate()).isEqualTo(DEFAULT_DELETE_DATE);
    }

    @Test
    @Transactional
    public void createBookingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookingsRepository.findAll().size();

        // Create the Bookings with an existing ID
        bookings.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingsMockMvc.perform(post("/api/bookings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRoomIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingsRepository.findAll().size();
        // set the field null
        bookings.setRoomId(null);

        // Create the Bookings, which fails.


        restBookingsMockMvc.perform(post("/api/bookings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingsRepository.findAll().size();
        // set the field null
        bookings.setLogin(null);

        // Create the Bookings, which fails.


        restBookingsMockMvc.perform(post("/api/bookings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBeginDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingsRepository.findAll().size();
        // set the field null
        bookings.setBeginDate(null);

        // Create the Bookings, which fails.


        restBookingsMockMvc.perform(post("/api/bookings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingsRepository.findAll().size();
        // set the field null
        bookings.setEndDate(null);

        // Create the Bookings, which fails.


        restBookingsMockMvc.perform(post("/api/bookings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingsRepository.findAll().size();
        // set the field null
        bookings.setCreateDate(null);

        // Create the Bookings, which fails.


        restBookingsMockMvc.perform(post("/api/bookings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList
        restBookingsMockMvc.perform(get("/api/bookings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookings.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomId").value(hasItem(DEFAULT_ROOM_ID)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].beginDate").value(hasItem(sameInstant(DEFAULT_BEGIN_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].deleteDate").value(hasItem(sameInstant(DEFAULT_DELETE_DATE))));
    }
    
    @Test
    @Transactional
    public void getBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get the bookings
        restBookingsMockMvc.perform(get("/api/bookings/{id}", bookings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookings.getId().intValue()))
            .andExpect(jsonPath("$.roomId").value(DEFAULT_ROOM_ID))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.beginDate").value(sameInstant(DEFAULT_BEGIN_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)))
            .andExpect(jsonPath("$.deleteDate").value(sameInstant(DEFAULT_DELETE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingBookings() throws Exception {
        // Get the bookings
        restBookingsMockMvc.perform(get("/api/bookings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();

        // Update the bookings
        Bookings updatedBookings = bookingsRepository.findById(bookings.getId()).get();
        // Disconnect from session so that the updates on updatedBookings are not directly saved in db
        em.detach(updatedBookings);
        updatedBookings
            .roomId(UPDATED_ROOM_ID)
            .login(UPDATED_LOGIN)
            .beginDate(UPDATED_BEGIN_DATE)
            .endDate(UPDATED_END_DATE)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .deleteDate(UPDATED_DELETE_DATE);

        restBookingsMockMvc.perform(put("/api/bookings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBookings)))
            .andExpect(status().isOk());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate);
        Bookings testBookings = bookingsList.get(bookingsList.size() - 1);
        assertThat(testBookings.getRoomId()).isEqualTo(UPDATED_ROOM_ID);
        assertThat(testBookings.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testBookings.getBeginDate()).isEqualTo(UPDATED_BEGIN_DATE);
        assertThat(testBookings.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testBookings.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testBookings.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testBookings.getDeleteDate()).isEqualTo(UPDATED_DELETE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBookings() throws Exception {
        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingsMockMvc.perform(put("/api/bookings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        int databaseSizeBeforeDelete = bookingsRepository.findAll().size();

        // Delete the bookings
        restBookingsMockMvc.perform(delete("/api/bookings/{id}", bookings.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
