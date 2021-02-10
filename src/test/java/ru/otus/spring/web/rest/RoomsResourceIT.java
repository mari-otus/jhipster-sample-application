package ru.otus.spring.web.rest;

import ru.otus.spring.JbookingApp;
import ru.otus.spring.domain.Rooms;
import ru.otus.spring.repository.RoomsRepository;

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
 * Integration tests for the {@link RoomsResource} REST controller.
 */
@SpringBootTest(classes = JbookingApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RoomsResourceIT {

    private static final String DEFAULT_ROOM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROOM_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    private static final Boolean DEFAULT_HAS_CONDITIONER = false;
    private static final Boolean UPDATED_HAS_CONDITIONER = true;

    private static final Boolean DEFAULT_HAS_VIDEOCONFERENCE = false;
    private static final Boolean UPDATED_HAS_VIDEOCONFERENCE = true;

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomsMockMvc;

    private Rooms rooms;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rooms createEntity(EntityManager em) {
        Rooms rooms = new Rooms()
            .roomName(DEFAULT_ROOM_NAME)
            .capacity(DEFAULT_CAPACITY)
            .hasConditioner(DEFAULT_HAS_CONDITIONER)
            .hasVideoconference(DEFAULT_HAS_VIDEOCONFERENCE)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .deleteDate(DEFAULT_DELETE_DATE);
        return rooms;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rooms createUpdatedEntity(EntityManager em) {
        Rooms rooms = new Rooms()
            .roomName(UPDATED_ROOM_NAME)
            .capacity(UPDATED_CAPACITY)
            .hasConditioner(UPDATED_HAS_CONDITIONER)
            .hasVideoconference(UPDATED_HAS_VIDEOCONFERENCE)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .deleteDate(UPDATED_DELETE_DATE);
        return rooms;
    }

    @BeforeEach
    public void initTest() {
        rooms = createEntity(em);
    }

    @Test
    @Transactional
    public void createRooms() throws Exception {
        int databaseSizeBeforeCreate = roomsRepository.findAll().size();
        // Create the Rooms
        restRoomsMockMvc.perform(post("/api/rooms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rooms)))
            .andExpect(status().isCreated());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeCreate + 1);
        Rooms testRooms = roomsList.get(roomsList.size() - 1);
        assertThat(testRooms.getRoomName()).isEqualTo(DEFAULT_ROOM_NAME);
        assertThat(testRooms.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testRooms.isHasConditioner()).isEqualTo(DEFAULT_HAS_CONDITIONER);
        assertThat(testRooms.isHasVideoconference()).isEqualTo(DEFAULT_HAS_VIDEOCONFERENCE);
        assertThat(testRooms.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testRooms.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testRooms.getDeleteDate()).isEqualTo(DEFAULT_DELETE_DATE);
    }

    @Test
    @Transactional
    public void createRoomsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roomsRepository.findAll().size();

        // Create the Rooms with an existing ID
        rooms.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomsMockMvc.perform(post("/api/rooms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rooms)))
            .andExpect(status().isBadRequest());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRoomNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomsRepository.findAll().size();
        // set the field null
        rooms.setRoomName(null);

        // Create the Rooms, which fails.


        restRoomsMockMvc.perform(post("/api/rooms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rooms)))
            .andExpect(status().isBadRequest());

        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomsRepository.findAll().size();
        // set the field null
        rooms.setCapacity(null);

        // Create the Rooms, which fails.


        restRoomsMockMvc.perform(post("/api/rooms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rooms)))
            .andExpect(status().isBadRequest());

        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomsRepository.findAll().size();
        // set the field null
        rooms.setCreateDate(null);

        // Create the Rooms, which fails.


        restRoomsMockMvc.perform(post("/api/rooms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rooms)))
            .andExpect(status().isBadRequest());

        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRooms() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        // Get all the roomsList
        restRoomsMockMvc.perform(get("/api/rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rooms.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomName").value(hasItem(DEFAULT_ROOM_NAME)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].hasConditioner").value(hasItem(DEFAULT_HAS_CONDITIONER.booleanValue())))
            .andExpect(jsonPath("$.[*].hasVideoconference").value(hasItem(DEFAULT_HAS_VIDEOCONFERENCE.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].deleteDate").value(hasItem(sameInstant(DEFAULT_DELETE_DATE))));
    }
    
    @Test
    @Transactional
    public void getRooms() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        // Get the rooms
        restRoomsMockMvc.perform(get("/api/rooms/{id}", rooms.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rooms.getId().intValue()))
            .andExpect(jsonPath("$.roomName").value(DEFAULT_ROOM_NAME))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY))
            .andExpect(jsonPath("$.hasConditioner").value(DEFAULT_HAS_CONDITIONER.booleanValue()))
            .andExpect(jsonPath("$.hasVideoconference").value(DEFAULT_HAS_VIDEOCONFERENCE.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)))
            .andExpect(jsonPath("$.deleteDate").value(sameInstant(DEFAULT_DELETE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingRooms() throws Exception {
        // Get the rooms
        restRoomsMockMvc.perform(get("/api/rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRooms() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        int databaseSizeBeforeUpdate = roomsRepository.findAll().size();

        // Update the rooms
        Rooms updatedRooms = roomsRepository.findById(rooms.getId()).get();
        // Disconnect from session so that the updates on updatedRooms are not directly saved in db
        em.detach(updatedRooms);
        updatedRooms
            .roomName(UPDATED_ROOM_NAME)
            .capacity(UPDATED_CAPACITY)
            .hasConditioner(UPDATED_HAS_CONDITIONER)
            .hasVideoconference(UPDATED_HAS_VIDEOCONFERENCE)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .deleteDate(UPDATED_DELETE_DATE);

        restRoomsMockMvc.perform(put("/api/rooms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRooms)))
            .andExpect(status().isOk());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeUpdate);
        Rooms testRooms = roomsList.get(roomsList.size() - 1);
        assertThat(testRooms.getRoomName()).isEqualTo(UPDATED_ROOM_NAME);
        assertThat(testRooms.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testRooms.isHasConditioner()).isEqualTo(UPDATED_HAS_CONDITIONER);
        assertThat(testRooms.isHasVideoconference()).isEqualTo(UPDATED_HAS_VIDEOCONFERENCE);
        assertThat(testRooms.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testRooms.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testRooms.getDeleteDate()).isEqualTo(UPDATED_DELETE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRooms() throws Exception {
        int databaseSizeBeforeUpdate = roomsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomsMockMvc.perform(put("/api/rooms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rooms)))
            .andExpect(status().isBadRequest());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRooms() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        int databaseSizeBeforeDelete = roomsRepository.findAll().size();

        // Delete the rooms
        restRoomsMockMvc.perform(delete("/api/rooms/{id}", rooms.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
