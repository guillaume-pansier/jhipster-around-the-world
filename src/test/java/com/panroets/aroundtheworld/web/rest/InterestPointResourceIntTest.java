package com.panroets.aroundtheworld.web.rest;

import com.panroets.aroundtheworld.AroundTheWorldApp;

import com.panroets.aroundtheworld.domain.InterestPoint;
import com.panroets.aroundtheworld.repository.InterestPointRepository;
import com.panroets.aroundtheworld.service.InterestPointService;
import com.panroets.aroundtheworld.service.dto.InterestPointDTO;
import com.panroets.aroundtheworld.service.mapper.InterestPointMapper;
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

/**
 * Test class for the InterestPointResource REST controller.
 *
 * @see InterestPointResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AroundTheWorldApp.class)
public class InterestPointResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COORDINATES = "AAAAAAAAAA";
    private static final String UPDATED_COORDINATES = "BBBBBBBBBB";

    @Autowired
    private InterestPointRepository interestPointRepository;

    @Autowired
    private InterestPointMapper interestPointMapper;

    @Autowired
    private InterestPointService interestPointService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restInterestPointMockMvc;

    private InterestPoint interestPoint;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InterestPointResource interestPointResource = new InterestPointResource(interestPointService);
        this.restInterestPointMockMvc = MockMvcBuilders.standaloneSetup(interestPointResource)
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
    public static InterestPoint createEntity() {
        InterestPoint interestPoint = new InterestPoint()
            .name(DEFAULT_NAME)
            .coordinates(DEFAULT_COORDINATES);
        return interestPoint;
    }

    @Before
    public void initTest() {
        interestPointRepository.deleteAll();
        interestPoint = createEntity();
    }

    @Test
    public void createInterestPoint() throws Exception {
        int databaseSizeBeforeCreate = interestPointRepository.findAll().size();

        // Create the InterestPoint
        InterestPointDTO interestPointDTO = interestPointMapper.toDto(interestPoint);
        restInterestPointMockMvc.perform(post("/api/interest-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interestPointDTO)))
            .andExpect(status().isCreated());

        // Validate the InterestPoint in the database
        List<InterestPoint> interestPointList = interestPointRepository.findAll();
        assertThat(interestPointList).hasSize(databaseSizeBeforeCreate + 1);
        InterestPoint testInterestPoint = interestPointList.get(interestPointList.size() - 1);
        assertThat(testInterestPoint.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInterestPoint.getCoordinates()).isEqualTo(DEFAULT_COORDINATES);
    }

    @Test
    public void createInterestPointWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = interestPointRepository.findAll().size();

        // Create the InterestPoint with an existing ID
        interestPoint.setId("existing_id");
        InterestPointDTO interestPointDTO = interestPointMapper.toDto(interestPoint);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterestPointMockMvc.perform(post("/api/interest-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interestPointDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InterestPoint in the database
        List<InterestPoint> interestPointList = interestPointRepository.findAll();
        assertThat(interestPointList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllInterestPoints() throws Exception {
        // Initialize the database
        interestPointRepository.save(interestPoint);

        // Get all the interestPointList
        restInterestPointMockMvc.perform(get("/api/interest-points?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interestPoint.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].coordinates").value(hasItem(DEFAULT_COORDINATES.toString())));
    }

    @Test
    public void getInterestPoint() throws Exception {
        // Initialize the database
        interestPointRepository.save(interestPoint);

        // Get the interestPoint
        restInterestPointMockMvc.perform(get("/api/interest-points/{id}", interestPoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(interestPoint.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.coordinates").value(DEFAULT_COORDINATES.toString()));
    }

    @Test
    public void getNonExistingInterestPoint() throws Exception {
        // Get the interestPoint
        restInterestPointMockMvc.perform(get("/api/interest-points/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateInterestPoint() throws Exception {
        // Initialize the database
        interestPointRepository.save(interestPoint);
        int databaseSizeBeforeUpdate = interestPointRepository.findAll().size();

        // Update the interestPoint
        InterestPoint updatedInterestPoint = interestPointRepository.findOne(interestPoint.getId());
        updatedInterestPoint
            .name(UPDATED_NAME)
            .coordinates(UPDATED_COORDINATES);
        InterestPointDTO interestPointDTO = interestPointMapper.toDto(updatedInterestPoint);

        restInterestPointMockMvc.perform(put("/api/interest-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interestPointDTO)))
            .andExpect(status().isOk());

        // Validate the InterestPoint in the database
        List<InterestPoint> interestPointList = interestPointRepository.findAll();
        assertThat(interestPointList).hasSize(databaseSizeBeforeUpdate);
        InterestPoint testInterestPoint = interestPointList.get(interestPointList.size() - 1);
        assertThat(testInterestPoint.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInterestPoint.getCoordinates()).isEqualTo(UPDATED_COORDINATES);
    }

    @Test
    public void updateNonExistingInterestPoint() throws Exception {
        int databaseSizeBeforeUpdate = interestPointRepository.findAll().size();

        // Create the InterestPoint
        InterestPointDTO interestPointDTO = interestPointMapper.toDto(interestPoint);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInterestPointMockMvc.perform(put("/api/interest-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interestPointDTO)))
            .andExpect(status().isCreated());

        // Validate the InterestPoint in the database
        List<InterestPoint> interestPointList = interestPointRepository.findAll();
        assertThat(interestPointList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteInterestPoint() throws Exception {
        // Initialize the database
        interestPointRepository.save(interestPoint);
        int databaseSizeBeforeDelete = interestPointRepository.findAll().size();

        // Get the interestPoint
        restInterestPointMockMvc.perform(delete("/api/interest-points/{id}", interestPoint.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InterestPoint> interestPointList = interestPointRepository.findAll();
        assertThat(interestPointList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterestPoint.class);
        InterestPoint interestPoint1 = new InterestPoint();
        interestPoint1.setId("id1");
        InterestPoint interestPoint2 = new InterestPoint();
        interestPoint2.setId(interestPoint1.getId());
        assertThat(interestPoint1).isEqualTo(interestPoint2);
        interestPoint2.setId("id2");
        assertThat(interestPoint1).isNotEqualTo(interestPoint2);
        interestPoint1.setId(null);
        assertThat(interestPoint1).isNotEqualTo(interestPoint2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterestPointDTO.class);
        InterestPointDTO interestPointDTO1 = new InterestPointDTO();
        interestPointDTO1.setId("id1");
        InterestPointDTO interestPointDTO2 = new InterestPointDTO();
        assertThat(interestPointDTO1).isNotEqualTo(interestPointDTO2);
        interestPointDTO2.setId(interestPointDTO1.getId());
        assertThat(interestPointDTO1).isEqualTo(interestPointDTO2);
        interestPointDTO2.setId("id2");
        assertThat(interestPointDTO1).isNotEqualTo(interestPointDTO2);
        interestPointDTO1.setId(null);
        assertThat(interestPointDTO1).isNotEqualTo(interestPointDTO2);
    }
}
