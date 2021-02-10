package ru.otus.spring.web.rest;

import ru.otus.spring.JbookingApp;
import ru.otus.spring.domain.Profiles;
import ru.otus.spring.repository.ProfilesRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProfilesResource} REST controller.
 */
@SpringBootTest(classes = JbookingApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProfilesResourceIT {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_EMAIL_NOTIFY = false;
    private static final Boolean UPDATED_IS_EMAIL_NOTIFY = true;

    private static final Boolean DEFAULT_IS_PHONE_NOTIFY = false;
    private static final Boolean UPDATED_IS_PHONE_NOTIFY = true;

    @Autowired
    private ProfilesRepository profilesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfilesMockMvc;

    private Profiles profiles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profiles createEntity(EntityManager em) {
        Profiles profiles = new Profiles()
            .login(DEFAULT_LOGIN)
            .email(DEFAULT_EMAIL)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .isEmailNotify(DEFAULT_IS_EMAIL_NOTIFY)
            .isPhoneNotify(DEFAULT_IS_PHONE_NOTIFY);
        return profiles;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profiles createUpdatedEntity(EntityManager em) {
        Profiles profiles = new Profiles()
            .login(UPDATED_LOGIN)
            .email(UPDATED_EMAIL)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .isEmailNotify(UPDATED_IS_EMAIL_NOTIFY)
            .isPhoneNotify(UPDATED_IS_PHONE_NOTIFY);
        return profiles;
    }

    @BeforeEach
    public void initTest() {
        profiles = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfiles() throws Exception {
        int databaseSizeBeforeCreate = profilesRepository.findAll().size();
        // Create the Profiles
        restProfilesMockMvc.perform(post("/api/profiles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profiles)))
            .andExpect(status().isCreated());

        // Validate the Profiles in the database
        List<Profiles> profilesList = profilesRepository.findAll();
        assertThat(profilesList).hasSize(databaseSizeBeforeCreate + 1);
        Profiles testProfiles = profilesList.get(profilesList.size() - 1);
        assertThat(testProfiles.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testProfiles.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProfiles.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testProfiles.isIsEmailNotify()).isEqualTo(DEFAULT_IS_EMAIL_NOTIFY);
        assertThat(testProfiles.isIsPhoneNotify()).isEqualTo(DEFAULT_IS_PHONE_NOTIFY);
    }

    @Test
    @Transactional
    public void createProfilesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profilesRepository.findAll().size();

        // Create the Profiles with an existing ID
        profiles.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfilesMockMvc.perform(post("/api/profiles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profiles)))
            .andExpect(status().isBadRequest());

        // Validate the Profiles in the database
        List<Profiles> profilesList = profilesRepository.findAll();
        assertThat(profilesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = profilesRepository.findAll().size();
        // set the field null
        profiles.setLogin(null);

        // Create the Profiles, which fails.


        restProfilesMockMvc.perform(post("/api/profiles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profiles)))
            .andExpect(status().isBadRequest());

        List<Profiles> profilesList = profilesRepository.findAll();
        assertThat(profilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profilesRepository.saveAndFlush(profiles);

        // Get all the profilesList
        restProfilesMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profiles.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE)))
            .andExpect(jsonPath("$.[*].isEmailNotify").value(hasItem(DEFAULT_IS_EMAIL_NOTIFY.booleanValue())))
            .andExpect(jsonPath("$.[*].isPhoneNotify").value(hasItem(DEFAULT_IS_PHONE_NOTIFY.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProfiles() throws Exception {
        // Initialize the database
        profilesRepository.saveAndFlush(profiles);

        // Get the profiles
        restProfilesMockMvc.perform(get("/api/profiles/{id}", profiles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profiles.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE))
            .andExpect(jsonPath("$.isEmailNotify").value(DEFAULT_IS_EMAIL_NOTIFY.booleanValue()))
            .andExpect(jsonPath("$.isPhoneNotify").value(DEFAULT_IS_PHONE_NOTIFY.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProfiles() throws Exception {
        // Get the profiles
        restProfilesMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfiles() throws Exception {
        // Initialize the database
        profilesRepository.saveAndFlush(profiles);

        int databaseSizeBeforeUpdate = profilesRepository.findAll().size();

        // Update the profiles
        Profiles updatedProfiles = profilesRepository.findById(profiles.getId()).get();
        // Disconnect from session so that the updates on updatedProfiles are not directly saved in db
        em.detach(updatedProfiles);
        updatedProfiles
            .login(UPDATED_LOGIN)
            .email(UPDATED_EMAIL)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .isEmailNotify(UPDATED_IS_EMAIL_NOTIFY)
            .isPhoneNotify(UPDATED_IS_PHONE_NOTIFY);

        restProfilesMockMvc.perform(put("/api/profiles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfiles)))
            .andExpect(status().isOk());

        // Validate the Profiles in the database
        List<Profiles> profilesList = profilesRepository.findAll();
        assertThat(profilesList).hasSize(databaseSizeBeforeUpdate);
        Profiles testProfiles = profilesList.get(profilesList.size() - 1);
        assertThat(testProfiles.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testProfiles.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProfiles.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testProfiles.isIsEmailNotify()).isEqualTo(UPDATED_IS_EMAIL_NOTIFY);
        assertThat(testProfiles.isIsPhoneNotify()).isEqualTo(UPDATED_IS_PHONE_NOTIFY);
    }

    @Test
    @Transactional
    public void updateNonExistingProfiles() throws Exception {
        int databaseSizeBeforeUpdate = profilesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfilesMockMvc.perform(put("/api/profiles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profiles)))
            .andExpect(status().isBadRequest());

        // Validate the Profiles in the database
        List<Profiles> profilesList = profilesRepository.findAll();
        assertThat(profilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfiles() throws Exception {
        // Initialize the database
        profilesRepository.saveAndFlush(profiles);

        int databaseSizeBeforeDelete = profilesRepository.findAll().size();

        // Delete the profiles
        restProfilesMockMvc.perform(delete("/api/profiles/{id}", profiles.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profiles> profilesList = profilesRepository.findAll();
        assertThat(profilesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
