package com.panroets.aroundtheworld.web.rest;

import com.panroets.aroundtheworld.AroundTheWorldApp;

import com.panroets.aroundtheworld.domain.CountryPath;
import com.panroets.aroundtheworld.repository.CountryPathRepository;
import com.panroets.aroundtheworld.service.dto.CountryPathDTO;
import com.panroets.aroundtheworld.service.mapper.CountryPathMapper;
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
 * Test class for the CountryPathResource REST controller.
 *
 * @see CountryPathResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AroundTheWorldApp.class)
public class CountryPathResourceIntTest {

    private static final String DEFAULT_ISO_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ISO_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_INTEREST_POINTS = "AAAAAAAAAA";
    private static final String UPDATED_INTEREST_POINTS = "BBBBBBBBBB";

    @Autowired
    private CountryPathRepository countryPathRepository;

    @Autowired
    private CountryPathMapper countryPathMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restCountryPathMockMvc;

    private CountryPath countryPath;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CountryPathResource countryPathResource = new CountryPathResource(countryPathRepository, countryPathMapper);
        this.restCountryPathMockMvc = MockMvcBuilders.standaloneSetup(countryPathResource)
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
    public static CountryPath createEntity() {
        CountryPath countryPath = new CountryPath()
            .isoCode(DEFAULT_ISO_CODE)
            .interestPoints(DEFAULT_INTEREST_POINTS);
        return countryPath;
    }

    @Before
    public void initTest() {
        countryPathRepository.deleteAll();
        countryPath = createEntity();
    }

    @Test
    public void createCountryPath() throws Exception {
        int databaseSizeBeforeCreate = countryPathRepository.findAll().size();

        // Create the CountryPath
        CountryPathDTO countryPathDTO = countryPathMapper.toDto(countryPath);
        restCountryPathMockMvc.perform(post("/api/country-paths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryPathDTO)))
            .andExpect(status().isCreated());

        // Validate the CountryPath in the database
        List<CountryPath> countryPathList = countryPathRepository.findAll();
        assertThat(countryPathList).hasSize(databaseSizeBeforeCreate + 1);
        CountryPath testCountryPath = countryPathList.get(countryPathList.size() - 1);
        assertThat(testCountryPath.getIsoCode()).isEqualTo(DEFAULT_ISO_CODE);
        assertThat(testCountryPath.getInterestPoints()).isEqualTo(DEFAULT_INTEREST_POINTS);
    }

    @Test
    public void createCountryPathWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countryPathRepository.findAll().size();

        // Create the CountryPath with an existing ID
        countryPath.setId("existing_id");
        CountryPathDTO countryPathDTO = countryPathMapper.toDto(countryPath);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryPathMockMvc.perform(post("/api/country-paths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryPathDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CountryPath in the database
        List<CountryPath> countryPathList = countryPathRepository.findAll();
        assertThat(countryPathList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCountryPaths() throws Exception {
        // Initialize the database
        countryPathRepository.save(countryPath);

        // Get all the countryPathList
        restCountryPathMockMvc.perform(get("/api/country-paths?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryPath.getId())))
            .andExpect(jsonPath("$.[*].isoCode").value(hasItem(DEFAULT_ISO_CODE.toString())))
            .andExpect(jsonPath("$.[*].interestPoints").value(hasItem(DEFAULT_INTEREST_POINTS.toString())));
    }

    @Test
    public void getCountryPath() throws Exception {
        // Initialize the database
        countryPathRepository.save(countryPath);

        // Get the countryPath
        restCountryPathMockMvc.perform(get("/api/country-paths/{id}", countryPath.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(countryPath.getId()))
            .andExpect(jsonPath("$.isoCode").value(DEFAULT_ISO_CODE.toString()))
            .andExpect(jsonPath("$.interestPoints").value(DEFAULT_INTEREST_POINTS.toString()));
    }

    @Test
    public void getNonExistingCountryPath() throws Exception {
        // Get the countryPath
        restCountryPathMockMvc.perform(get("/api/country-paths/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCountryPath() throws Exception {
        // Initialize the database
        countryPathRepository.save(countryPath);
        int databaseSizeBeforeUpdate = countryPathRepository.findAll().size();

        // Update the countryPath
        CountryPath updatedCountryPath = countryPathRepository.findOne(countryPath.getId());
        updatedCountryPath
            .isoCode(UPDATED_ISO_CODE)
            .interestPoints(UPDATED_INTEREST_POINTS);
        CountryPathDTO countryPathDTO = countryPathMapper.toDto(updatedCountryPath);

        restCountryPathMockMvc.perform(put("/api/country-paths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryPathDTO)))
            .andExpect(status().isOk());

        // Validate the CountryPath in the database
        List<CountryPath> countryPathList = countryPathRepository.findAll();
        assertThat(countryPathList).hasSize(databaseSizeBeforeUpdate);
        CountryPath testCountryPath = countryPathList.get(countryPathList.size() - 1);
        assertThat(testCountryPath.getIsoCode()).isEqualTo(UPDATED_ISO_CODE);
        assertThat(testCountryPath.getInterestPoints()).isEqualTo(UPDATED_INTEREST_POINTS);
    }

    @Test
    public void updateNonExistingCountryPath() throws Exception {
        int databaseSizeBeforeUpdate = countryPathRepository.findAll().size();

        // Create the CountryPath
        CountryPathDTO countryPathDTO = countryPathMapper.toDto(countryPath);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCountryPathMockMvc.perform(put("/api/country-paths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryPathDTO)))
            .andExpect(status().isCreated());

        // Validate the CountryPath in the database
        List<CountryPath> countryPathList = countryPathRepository.findAll();
        assertThat(countryPathList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteCountryPath() throws Exception {
        // Initialize the database
        countryPathRepository.save(countryPath);
        int databaseSizeBeforeDelete = countryPathRepository.findAll().size();

        // Get the countryPath
        restCountryPathMockMvc.perform(delete("/api/country-paths/{id}", countryPath.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CountryPath> countryPathList = countryPathRepository.findAll();
        assertThat(countryPathList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryPath.class);
        CountryPath countryPath1 = new CountryPath();
        countryPath1.setId("id1");
        CountryPath countryPath2 = new CountryPath();
        countryPath2.setId(countryPath1.getId());
        assertThat(countryPath1).isEqualTo(countryPath2);
        countryPath2.setId("id2");
        assertThat(countryPath1).isNotEqualTo(countryPath2);
        countryPath1.setId(null);
        assertThat(countryPath1).isNotEqualTo(countryPath2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryPathDTO.class);
        CountryPathDTO countryPathDTO1 = new CountryPathDTO();
        countryPathDTO1.setId("id1");
        CountryPathDTO countryPathDTO2 = new CountryPathDTO();
        assertThat(countryPathDTO1).isNotEqualTo(countryPathDTO2);
        countryPathDTO2.setId(countryPathDTO1.getId());
        assertThat(countryPathDTO1).isEqualTo(countryPathDTO2);
        countryPathDTO2.setId("id2");
        assertThat(countryPathDTO1).isNotEqualTo(countryPathDTO2);
        countryPathDTO1.setId(null);
        assertThat(countryPathDTO1).isNotEqualTo(countryPathDTO2);
    }
}
