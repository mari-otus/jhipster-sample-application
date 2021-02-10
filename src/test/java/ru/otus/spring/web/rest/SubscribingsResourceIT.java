package ru.otus.spring.web.rest;

import ru.otus.spring.JbookingApp;
import ru.otus.spring.domain.Subscribings;
import ru.otus.spring.repository.SubscribingsRepository;

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
 * Integration tests for the {@link SubscribingsResource} REST controller.
 */
@SpringBootTest(classes = JbookingApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SubscribingsResourceIT {

    private static final Integer DEFAULT_ROOM_ID = 1;
    private static final Integer UPDATED_ROOM_ID = 2;

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SubscribingsRepository subscribingsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubscribingsMockMvc;

    private Subscribings subscribings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subscribings createEntity(EntityManager em) {
        Subscribings subscribings = new Subscribings()
            .roomId(DEFAULT_ROOM_ID)
            .login(DEFAULT_LOGIN)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .deleteDate(DEFAULT_DELETE_DATE);
        return subscribings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subscribings createUpdatedEntity(EntityManager em) {
        Subscribings subscribings = new Subscribings()
            .roomId(UPDATED_ROOM_ID)
            .login(UPDATED_LOGIN)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .deleteDate(UPDATED_DELETE_DATE);
        return subscribings;
    }

    @BeforeEach
    public void initTest() {
        subscribings = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubscribings() throws Exception {
        int databaseSizeBeforeCreate = subscribingsRepository.findAll().size();
        // Create the Subscribings
        restSubscribingsMockMvc.perform(post("/api/subscribings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscribings)))
            .andExpect(status().isCreated());

        // Validate the Subscribings in the database
        List<Subscribings> subscribingsList = subscribingsRepository.findAll();
        assertThat(subscribingsList).hasSize(databaseSizeBeforeCreate + 1);
        Subscribings testSubscribings = subscribingsList.get(subscribingsList.size() - 1);
        assertThat(testSubscribings.getRoomId()).isEqualTo(DEFAULT_ROOM_ID);
        assertThat(testSubscribings.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testSubscribings.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testSubscribings.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testSubscribings.getDeleteDate()).isEqualTo(DEFAULT_DELETE_DATE);
    }

    @Test
    @Transactional
    public void createSubscribingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subscribingsRepository.findAll().size();

        // Create the Subscribings with an existing ID
        subscribings.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscribingsMockMvc.perform(post("/api/subscribings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscribings)))
            .andExpect(status().isBadRequest());

        // Validate the Subscribings in the database
        List<Subscribings> subscribingsList = subscribingsRepository.findAll();
        assertThat(subscribingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRoomIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscribingsRepository.findAll().size();
        // set the field null
        subscribings.setRoomId(null);

        // Create the Subscribings, which fails.


        restSubscribingsMockMvc.perform(post("/api/subscribings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscribings)))
            .andExpect(status().isBadRequest());

        List<Subscribings> subscribingsList = subscribingsRepository.findAll();
        assertThat(subscribingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscribingsRepository.findAll().size();
        // set the field null
        subscribings.setLogin(null);

        // Create the Subscribings, which fails.


        restSubscribingsMockMvc.perform(post("/api/subscribings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscribings)))
            .andExpect(status().isBadRequest());

        List<Subscribings> subscribingsList = subscribingsRepository.findAll();
        assertThat(subscribingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscribingsRepository.findAll().size();
        // set the field null
        subscribings.setCreateDate(null);

        // Create the Subscribings, which fails.


        restSubscribingsMockMvc.perform(post("/api/subscribings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscribings)))
            .andExpect(status().isBadRequest());

        List<Subscribings> subscribingsList = subscribingsRepository.findAll();
        assertThat(subscribingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubscribings() throws Exception {
        // Initialize the database
        subscribingsRepository.saveAndFlush(subscribings);

        // Get all the subscribingsList
        restSubscribingsMockMvc.perform(get("/api/subscribings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscribings.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomId").value(hasItem(DEFAULT_ROOM_ID)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].deleteDate").value(hasItem(sameInstant(DEFAULT_DELETE_DATE))));
    }
    
    @Test
    @Transactional
    public void getSubscribings() throws Exception {
        // Initialize the database
        subscribingsRepository.saveAndFlush(subscribings);

        // Get the subscribings
        restSubscribingsMockMvc.perform(get("/api/subscribings/{id}", subscribings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subscribings.getId().intValue()))
            .andExpect(jsonPath("$.roomId").value(DEFAULT_ROOM_ID))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)))
            .andExpect(jsonPath("$.deleteDate").value(sameInstant(DEFAULT_DELETE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingSubscribings() throws Exception {
        // Get the subscribings
        restSubscribingsMockMvc.perform(get("/api/subscribings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubscribings() throws Exception {
        // Initialize the database
        subscribingsRepository.saveAndFlush(subscribings);

        int databaseSizeBeforeUpdate = subscribingsRepository.findAll().size();

        // Update the subscribings
        Subscribings updatedSubscribings = subscribingsRepository.findById(subscribings.getId()).get();
        // Disconnect from session so that the updates on updatedSubscribings are not directly saved in db
        em.detach(updatedSubscribings);
        updatedSubscribings
            .roomId(UPDATED_ROOM_ID)
            .login(UPDATED_LOGIN)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .deleteDate(UPDATED_DELETE_DATE);

        restSubscribingsMockMvc.perform(put("/api/subscribings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubscribings)))
            .andExpect(status().isOk());

        // Validate the Subscribings in the database
        List<Subscribings> subscribingsList = subscribingsRepository.findAll();
        assertThat(subscribingsList).hasSize(databaseSizeBeforeUpdate);
        Subscribings testSubscribings = subscribingsList.get(subscribingsList.size() - 1);
        assertThat(testSubscribings.getRoomId()).isEqualTo(UPDATED_ROOM_ID);
        assertThat(testSubscribings.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testSubscribings.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testSubscribings.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testSubscribings.getDeleteDate()).isEqualTo(UPDATED_DELETE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSubscribings() throws Exception {
        int databaseSizeBeforeUpdate = subscribingsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubscribingsMockMvc.perform(put("/api/subscribings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscribings)))
            .andExpect(status().isBadRequest());

        // Validate the Subscribings in the database
        List<Subscribings> subscribingsList = subscribingsRepository.findAll();
        assertThat(subscribingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubscribings() throws Exception {
        // Initialize the database
        subscribingsRepository.saveAndFlush(subscribings);

        int databaseSizeBeforeDelete = subscribingsRepository.findAll().size();

        // Delete the subscribings
        restSubscribingsMockMvc.perform(delete("/api/subscribings/{id}", subscribings.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subscribings> subscribingsList = subscribingsRepository.findAll();
        assertThat(subscribingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
