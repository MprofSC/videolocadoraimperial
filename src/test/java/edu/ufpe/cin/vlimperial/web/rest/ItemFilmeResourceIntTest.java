package edu.ufpe.cin.vlimperial.web.rest;

import edu.ufpe.cin.vlimperial.VlimperialApp;

import edu.ufpe.cin.vlimperial.domain.ItemFilme;
import edu.ufpe.cin.vlimperial.repository.ItemFilmeRepository;
import edu.ufpe.cin.vlimperial.repository.search.ItemFilmeSearchRepository;
import edu.ufpe.cin.vlimperial.service.ItemFilmeService;
import edu.ufpe.cin.vlimperial.web.rest.errors.ExceptionTranslator;

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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static edu.ufpe.cin.vlimperial.web.rest.TestUtil.sameInstant;
import static edu.ufpe.cin.vlimperial.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.ufpe.cin.vlimperial.domain.enumeration.TipoMidia;
/**
 * Test class for the ItemFilmeResource REST controller.
 *
 * @see ItemFilmeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VlimperialApp.class)
public class ItemFilmeResourceIntTest {

    private static final String DEFAULT_NUMERO_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SERIE = "BBBBBBBBBB";

    private static final TipoMidia DEFAULT_TIPO_MIDIA = TipoMidia.DVD;
    private static final TipoMidia UPDATED_TIPO_MIDIA = TipoMidia.VHS;

    private static final ZonedDateTime DEFAULT_DATA_AQUISICAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_AQUISICAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ItemFilmeRepository itemFilmeRepository;

    @Autowired
    private ItemFilmeService itemFilmeService;

    /**
     * This repository is mocked in the edu.ufpe.cin.vlimperial.repository.search test package.
     *
     * @see edu.ufpe.cin.vlimperial.repository.search.ItemFilmeSearchRepositoryMockConfiguration
     */
    @Autowired
    private ItemFilmeSearchRepository mockItemFilmeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restItemFilmeMockMvc;

    private ItemFilme itemFilme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemFilmeResource itemFilmeResource = new ItemFilmeResource(itemFilmeService);
        this.restItemFilmeMockMvc = MockMvcBuilders.standaloneSetup(itemFilmeResource)
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
    public static ItemFilme createEntity(EntityManager em) {
        ItemFilme itemFilme = new ItemFilme()
            .numeroSerie(DEFAULT_NUMERO_SERIE)
            .tipoMidia(DEFAULT_TIPO_MIDIA)
            .dataAquisicao(DEFAULT_DATA_AQUISICAO);
        return itemFilme;
    }

    @Before
    public void initTest() {
        itemFilme = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemFilme() throws Exception {
        int databaseSizeBeforeCreate = itemFilmeRepository.findAll().size();

        // Create the ItemFilme
        restItemFilmeMockMvc.perform(post("/api/item-filmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemFilme)))
            .andExpect(status().isCreated());

        // Validate the ItemFilme in the database
        List<ItemFilme> itemFilmeList = itemFilmeRepository.findAll();
        assertThat(itemFilmeList).hasSize(databaseSizeBeforeCreate + 1);
        ItemFilme testItemFilme = itemFilmeList.get(itemFilmeList.size() - 1);
        assertThat(testItemFilme.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testItemFilme.getTipoMidia()).isEqualTo(DEFAULT_TIPO_MIDIA);
        assertThat(testItemFilme.getDataAquisicao()).isEqualTo(DEFAULT_DATA_AQUISICAO);

        // Validate the ItemFilme in Elasticsearch
        verify(mockItemFilmeSearchRepository, times(1)).save(testItemFilme);
    }

    @Test
    @Transactional
    public void createItemFilmeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemFilmeRepository.findAll().size();

        // Create the ItemFilme with an existing ID
        itemFilme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemFilmeMockMvc.perform(post("/api/item-filmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemFilme)))
            .andExpect(status().isBadRequest());

        // Validate the ItemFilme in the database
        List<ItemFilme> itemFilmeList = itemFilmeRepository.findAll();
        assertThat(itemFilmeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ItemFilme in Elasticsearch
        verify(mockItemFilmeSearchRepository, times(0)).save(itemFilme);
    }

    @Test
    @Transactional
    public void checkNumeroSerieIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemFilmeRepository.findAll().size();
        // set the field null
        itemFilme.setNumeroSerie(null);

        // Create the ItemFilme, which fails.

        restItemFilmeMockMvc.perform(post("/api/item-filmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemFilme)))
            .andExpect(status().isBadRequest());

        List<ItemFilme> itemFilmeList = itemFilmeRepository.findAll();
        assertThat(itemFilmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoMidiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemFilmeRepository.findAll().size();
        // set the field null
        itemFilme.setTipoMidia(null);

        // Create the ItemFilme, which fails.

        restItemFilmeMockMvc.perform(post("/api/item-filmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemFilme)))
            .andExpect(status().isBadRequest());

        List<ItemFilme> itemFilmeList = itemFilmeRepository.findAll();
        assertThat(itemFilmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataAquisicaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemFilmeRepository.findAll().size();
        // set the field null
        itemFilme.setDataAquisicao(null);

        // Create the ItemFilme, which fails.

        restItemFilmeMockMvc.perform(post("/api/item-filmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemFilme)))
            .andExpect(status().isBadRequest());

        List<ItemFilme> itemFilmeList = itemFilmeRepository.findAll();
        assertThat(itemFilmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemFilmes() throws Exception {
        // Initialize the database
        itemFilmeRepository.saveAndFlush(itemFilme);

        // Get all the itemFilmeList
        restItemFilmeMockMvc.perform(get("/api/item-filmes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemFilme.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE.toString())))
            .andExpect(jsonPath("$.[*].tipoMidia").value(hasItem(DEFAULT_TIPO_MIDIA.toString())))
            .andExpect(jsonPath("$.[*].dataAquisicao").value(hasItem(sameInstant(DEFAULT_DATA_AQUISICAO))));
    }
    
    @Test
    @Transactional
    public void getItemFilme() throws Exception {
        // Initialize the database
        itemFilmeRepository.saveAndFlush(itemFilme);

        // Get the itemFilme
        restItemFilmeMockMvc.perform(get("/api/item-filmes/{id}", itemFilme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemFilme.getId().intValue()))
            .andExpect(jsonPath("$.numeroSerie").value(DEFAULT_NUMERO_SERIE.toString()))
            .andExpect(jsonPath("$.tipoMidia").value(DEFAULT_TIPO_MIDIA.toString()))
            .andExpect(jsonPath("$.dataAquisicao").value(sameInstant(DEFAULT_DATA_AQUISICAO)));
    }

    @Test
    @Transactional
    public void getNonExistingItemFilme() throws Exception {
        // Get the itemFilme
        restItemFilmeMockMvc.perform(get("/api/item-filmes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemFilme() throws Exception {
        // Initialize the database
        itemFilmeService.save(itemFilme);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockItemFilmeSearchRepository);

        int databaseSizeBeforeUpdate = itemFilmeRepository.findAll().size();

        // Update the itemFilme
        ItemFilme updatedItemFilme = itemFilmeRepository.findById(itemFilme.getId()).get();
        // Disconnect from session so that the updates on updatedItemFilme are not directly saved in db
        em.detach(updatedItemFilme);
        updatedItemFilme
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .tipoMidia(UPDATED_TIPO_MIDIA)
            .dataAquisicao(UPDATED_DATA_AQUISICAO);

        restItemFilmeMockMvc.perform(put("/api/item-filmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemFilme)))
            .andExpect(status().isOk());

        // Validate the ItemFilme in the database
        List<ItemFilme> itemFilmeList = itemFilmeRepository.findAll();
        assertThat(itemFilmeList).hasSize(databaseSizeBeforeUpdate);
        ItemFilme testItemFilme = itemFilmeList.get(itemFilmeList.size() - 1);
        assertThat(testItemFilme.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testItemFilme.getTipoMidia()).isEqualTo(UPDATED_TIPO_MIDIA);
        assertThat(testItemFilme.getDataAquisicao()).isEqualTo(UPDATED_DATA_AQUISICAO);

        // Validate the ItemFilme in Elasticsearch
        verify(mockItemFilmeSearchRepository, times(1)).save(testItemFilme);
    }

    @Test
    @Transactional
    public void updateNonExistingItemFilme() throws Exception {
        int databaseSizeBeforeUpdate = itemFilmeRepository.findAll().size();

        // Create the ItemFilme

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemFilmeMockMvc.perform(put("/api/item-filmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemFilme)))
            .andExpect(status().isBadRequest());

        // Validate the ItemFilme in the database
        List<ItemFilme> itemFilmeList = itemFilmeRepository.findAll();
        assertThat(itemFilmeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ItemFilme in Elasticsearch
        verify(mockItemFilmeSearchRepository, times(0)).save(itemFilme);
    }

    @Test
    @Transactional
    public void deleteItemFilme() throws Exception {
        // Initialize the database
        itemFilmeService.save(itemFilme);

        int databaseSizeBeforeDelete = itemFilmeRepository.findAll().size();

        // Get the itemFilme
        restItemFilmeMockMvc.perform(delete("/api/item-filmes/{id}", itemFilme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemFilme> itemFilmeList = itemFilmeRepository.findAll();
        assertThat(itemFilmeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ItemFilme in Elasticsearch
        verify(mockItemFilmeSearchRepository, times(1)).deleteById(itemFilme.getId());
    }

    @Test
    @Transactional
    public void searchItemFilme() throws Exception {
        // Initialize the database
        itemFilmeService.save(itemFilme);
        when(mockItemFilmeSearchRepository.search(queryStringQuery("id:" + itemFilme.getId())))
            .thenReturn(Collections.singletonList(itemFilme));
        // Search the itemFilme
        restItemFilmeMockMvc.perform(get("/api/_search/item-filmes?query=id:" + itemFilme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemFilme.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE)))
            .andExpect(jsonPath("$.[*].tipoMidia").value(hasItem(DEFAULT_TIPO_MIDIA.toString())))
            .andExpect(jsonPath("$.[*].dataAquisicao").value(hasItem(sameInstant(DEFAULT_DATA_AQUISICAO))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemFilme.class);
        ItemFilme itemFilme1 = new ItemFilme();
        itemFilme1.setId(1L);
        ItemFilme itemFilme2 = new ItemFilme();
        itemFilme2.setId(itemFilme1.getId());
        assertThat(itemFilme1).isEqualTo(itemFilme2);
        itemFilme2.setId(2L);
        assertThat(itemFilme1).isNotEqualTo(itemFilme2);
        itemFilme1.setId(null);
        assertThat(itemFilme1).isNotEqualTo(itemFilme2);
    }
}
