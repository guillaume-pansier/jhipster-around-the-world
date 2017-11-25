package com.panroets.aroundtheworld.web.rest;

import com.panroets.aroundtheworld.AroundTheWorldApp;

import com.panroets.aroundtheworld.domain.Trip;
import com.panroets.aroundtheworld.repository.TripRepository;
import com.panroets.aroundtheworld.service.TripService;
import com.panroets.aroundtheworld.service.dto.TripDTO;
import com.panroets.aroundtheworld.service.mapper.TripMapper;
import com.panroets.aroundtheworld.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.panroets.aroundtheworld.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.panroets.aroundtheworld.domain.enumeration.TripStatus;
/**
 * Test class for the TripResource REST controller.
 *
 * @see TripResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AroundTheWorldApp.class)
public class TripResourceIntTest {

    private static final String DEFAULT_TRIP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRIP_NAME = "BBBBBBBBBB";

    private static final TripStatus DEFAULT_STATUS = TripStatus.DONE;
    private static final TripStatus UPDATED_STATUS = TripStatus.PLANNED;

    private static final String DEFAULT_TRIP_PATH_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRIP_PATH_ID = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_PATHS = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_PATHS = "BBBBBBBBBB";

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripMapper tripMapper;

    @Autowired
    private TripService tripService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restTripMockMvc;

    private Trip trip;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TripResource tripResource = new TripResource(tripService);
        this.restTripMockMvc = MockMvcBuilders.standaloneSetup(tripResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trip createEntity() {
        Trip trip = new Trip()
            .tripName(DEFAULT_TRIP_NAME)
            .status(DEFAULT_STATUS)
            .tripPathId(DEFAULT_TRIP_PATH_ID)
            .countryPaths(DEFAULT_COUNTRY_PATHS);
        return trip;
    }

    @Before
    public void initTest() {
        tripRepository.deleteAll();
        trip = createEntity();
    }

    @Test
    public void createTrip() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // Create the Trip
        TripDTO tripDTO = tripMapper.toDto(trip);
        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isCreated());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeCreate + 1);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.getTripName()).isEqualTo(DEFAULT_TRIP_NAME);
        assertThat(testTrip.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTrip.getTripPathId()).isEqualTo(DEFAULT_TRIP_PATH_ID);
        assertThat(testTrip.getCountryPaths()).isEqualTo(DEFAULT_COUNTRY_PATHS);
    }

    @Test
    public void createTripWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // Create the Trip with an existing ID
        trip.setId("existing_id");
        TripDTO tripDTO = tripMapper.toDto(trip);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllTrips() throws Exception {
        // Initialize the database
        tripRepository.save(trip);

        // Get all the tripList
        restTripMockMvc.perform(get("/api/trips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trip.getId())))
            .andExpect(jsonPath("$.[*].tripName").value(hasItem(DEFAULT_TRIP_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].tripPathId").value(hasItem(DEFAULT_TRIP_PATH_ID.toString())))
            .andExpect(jsonPath("$.[*].countryPaths").value(hasItem(DEFAULT_COUNTRY_PATHS.toString())));
    }

    @Test
    public void getTrip() throws Exception {
        // Initialize the database
        tripRepository.save(trip);

        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", trip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trip.getId()))
            .andExpect(jsonPath("$.tripName").value(DEFAULT_TRIP_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.tripPathId").value(DEFAULT_TRIP_PATH_ID.toString()))
            .andExpect(jsonPath("$.countryPaths").value(DEFAULT_COUNTRY_PATHS.toString()));
    }

    @Test
    public void getNonExistingTrip() throws Exception {
        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTrip() throws Exception {
        // Initialize the database
        tripRepository.save(trip);
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Update the trip
        Trip updatedTrip = tripRepository.findOne(trip.getId());
        updatedTrip
            .tripName(UPDATED_TRIP_NAME)
            .status(UPDATED_STATUS)
            .tripPathId(UPDATED_TRIP_PATH_ID)
            .countryPaths(UPDATED_COUNTRY_PATHS);
        TripDTO tripDTO = tripMapper.toDto(updatedTrip);

        restTripMockMvc.perform(put("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isOk());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.getTripName()).isEqualTo(UPDATED_TRIP_NAME);
        assertThat(testTrip.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrip.getTripPathId()).isEqualTo(UPDATED_TRIP_PATH_ID);
        assertThat(testTrip.getCountryPaths()).isEqualTo(UPDATED_COUNTRY_PATHS);
    }

    @Test
    public void updateNonExistingTrip() throws Exception {
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Create the Trip
        TripDTO tripDTO = tripMapper.toDto(trip);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTripMockMvc.perform(put("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isCreated());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTrip() throws Exception {
        // Initialize the database
        tripRepository.save(trip);
        int databaseSizeBeforeDelete = tripRepository.findAll().size();

        // Get the trip
        restTripMockMvc.perform(delete("/api/trips/{id}", trip.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trip.class);
        Trip trip1 = new Trip();
        trip1.setId("id1");
        Trip trip2 = new Trip();
        trip2.setId(trip1.getId());
        assertThat(trip1).isEqualTo(trip2);
        trip2.setId("id2");
        assertThat(trip1).isNotEqualTo(trip2);
        trip1.setId(null);
        assertThat(trip1).isNotEqualTo(trip2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TripDTO.class);
        TripDTO tripDTO1 = new TripDTO();
        tripDTO1.setId("id1");
        TripDTO tripDTO2 = new TripDTO();
        assertThat(tripDTO1).isNotEqualTo(tripDTO2);
        tripDTO2.setId(tripDTO1.getId());
        assertThat(tripDTO1).isEqualTo(tripDTO2);
        tripDTO2.setId("id2");
        assertThat(tripDTO1).isNotEqualTo(tripDTO2);
        tripDTO1.setId(null);
        assertThat(tripDTO1).isNotEqualTo(tripDTO2);
    }
}
