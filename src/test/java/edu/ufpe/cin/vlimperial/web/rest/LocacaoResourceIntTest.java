package edu.ufpe.cin.vlimperial.web.rest;

import edu.ufpe.cin.vlimperial.VlimperialApp;

import edu.ufpe.cin.vlimperial.domain.Locacao;
import edu.ufpe.cin.vlimperial.repository.LocacaoRepository;
import edu.ufpe.cin.vlimperial.repository.search.LocacaoSearchRepository;
import edu.ufpe.cin.vlimperial.service.LocacaoService;
import edu.ufpe.cin.vlimperial.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
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

/**
 * Test class for the LocacaoResource REST controller.
 *
 * @see LocacaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VlimperialApp.class)
public class LocacaoResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATA_LOCACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_LOCACAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Mock
    private LocacaoRepository locacaoRepositoryMock;

    @Mock
    private LocacaoService locacaoServiceMock;

    @Autowired
    private LocacaoService locacaoService;

    /**
     * This repository is mocked in the edu.ufpe.cin.vlimperial.repository.search test package.
     *
     * @see edu.ufpe.cin.vlimperial.repository.search.LocacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private LocacaoSearchRepository mockLocacaoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocacaoMockMvc;

    private Locacao locacao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocacaoResource locacaoResource = new LocacaoResource(locacaoService);
        this.restLocacaoMockMvc = MockMvcBuilders.standaloneSetup(locacaoResource)
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
    public static Locacao createEntity(EntityManager em) {
        Locacao locacao = new Locacao()
            .dataLocacao(DEFAULT_DATA_LOCACAO)
            .valor(DEFAULT_VALOR);
        return locacao;
    }

    @Before
    public void initTest() {
        locacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocacao() throws Exception {
        int databaseSizeBeforeCreate = locacaoRepository.findAll().size();

        // Create the Locacao
        restLocacaoMockMvc.perform(post("/api/locacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locacao)))
            .andExpect(status().isCreated());

        // Validate the Locacao in the database
        List<Locacao> locacaoList = locacaoRepository.findAll();
        assertThat(locacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Locacao testLocacao = locacaoList.get(locacaoList.size() - 1);
        assertThat(testLocacao.getDataLocacao()).isEqualTo(DEFAULT_DATA_LOCACAO);
        assertThat(testLocacao.getValor()).isEqualTo(DEFAULT_VALOR);

        // Validate the Locacao in Elasticsearch
        verify(mockLocacaoSearchRepository, times(1)).save(testLocacao);
    }

    @Test
    @Transactional
    public void createLocacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locacaoRepository.findAll().size();

        // Create the Locacao with an existing ID
        locacao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocacaoMockMvc.perform(post("/api/locacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locacao)))
            .andExpect(status().isBadRequest());

        // Validate the Locacao in the database
        List<Locacao> locacaoList = locacaoRepository.findAll();
        assertThat(locacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Locacao in Elasticsearch
        verify(mockLocacaoSearchRepository, times(0)).save(locacao);
    }

    @Test
    @Transactional
    public void checkDataLocacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = locacaoRepository.findAll().size();
        // set the field null
        locacao.setDataLocacao(null);

        // Create the Locacao, which fails.

        restLocacaoMockMvc.perform(post("/api/locacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locacao)))
            .andExpect(status().isBadRequest());

        List<Locacao> locacaoList = locacaoRepository.findAll();
        assertThat(locacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = locacaoRepository.findAll().size();
        // set the field null
        locacao.setValor(null);

        // Create the Locacao, which fails.

        restLocacaoMockMvc.perform(post("/api/locacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locacao)))
            .andExpect(status().isBadRequest());

        List<Locacao> locacaoList = locacaoRepository.findAll();
        assertThat(locacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocacaos() throws Exception {
        // Initialize the database
        locacaoRepository.saveAndFlush(locacao);

        // Get all the locacaoList
        restLocacaoMockMvc.perform(get("/api/locacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataLocacao").value(hasItem(sameInstant(DEFAULT_DATA_LOCACAO))))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllLocacaosWithEagerRelationshipsIsEnabled() throws Exception {
        LocacaoResource locacaoResource = new LocacaoResource(locacaoServiceMock);
        when(locacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restLocacaoMockMvc = MockMvcBuilders.standaloneSetup(locacaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restLocacaoMockMvc.perform(get("/api/locacaos?eagerload=true"))
        .andExpect(status().isOk());

        verify(locacaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllLocacaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        LocacaoResource locacaoResource = new LocacaoResource(locacaoServiceMock);
            when(locacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restLocacaoMockMvc = MockMvcBuilders.standaloneSetup(locacaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restLocacaoMockMvc.perform(get("/api/locacaos?eagerload=true"))
        .andExpect(status().isOk());

            verify(locacaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getLocacao() throws Exception {
        // Initialize the database
        locacaoRepository.saveAndFlush(locacao);

        // Get the locacao
        restLocacaoMockMvc.perform(get("/api/locacaos/{id}", locacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(locacao.getId().intValue()))
            .andExpect(jsonPath("$.dataLocacao").value(sameInstant(DEFAULT_DATA_LOCACAO)))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLocacao() throws Exception {
        // Get the locacao
        restLocacaoMockMvc.perform(get("/api/locacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocacao() throws Exception {
        // Initialize the database
        locacaoService.save(locacao);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockLocacaoSearchRepository);

        int databaseSizeBeforeUpdate = locacaoRepository.findAll().size();

        // Update the locacao
        Locacao updatedLocacao = locacaoRepository.findById(locacao.getId()).get();
        // Disconnect from session so that the updates on updatedLocacao are not directly saved in db
        em.detach(updatedLocacao);
        updatedLocacao
            .dataLocacao(UPDATED_DATA_LOCACAO)
            .valor(UPDATED_VALOR);

        restLocacaoMockMvc.perform(put("/api/locacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocacao)))
            .andExpect(status().isOk());

        // Validate the Locacao in the database
        List<Locacao> locacaoList = locacaoRepository.findAll();
        assertThat(locacaoList).hasSize(databaseSizeBeforeUpdate);
        Locacao testLocacao = locacaoList.get(locacaoList.size() - 1);
        assertThat(testLocacao.getDataLocacao()).isEqualTo(UPDATED_DATA_LOCACAO);
        assertThat(testLocacao.getValor()).isEqualTo(UPDATED_VALOR);

        // Validate the Locacao in Elasticsearch
        verify(mockLocacaoSearchRepository, times(1)).save(testLocacao);
    }

    @Test
    @Transactional
    public void updateNonExistingLocacao() throws Exception {
        int databaseSizeBeforeUpdate = locacaoRepository.findAll().size();

        // Create the Locacao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocacaoMockMvc.perform(put("/api/locacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locacao)))
            .andExpect(status().isBadRequest());

        // Validate the Locacao in the database
        List<Locacao> locacaoList = locacaoRepository.findAll();
        assertThat(locacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Locacao in Elasticsearch
        verify(mockLocacaoSearchRepository, times(0)).save(locacao);
    }

    @Test
    @Transactional
    public void deleteLocacao() throws Exception {
        // Initialize the database
        locacaoService.save(locacao);

        int databaseSizeBeforeDelete = locacaoRepository.findAll().size();

        // Get the locacao
        restLocacaoMockMvc.perform(delete("/api/locacaos/{id}", locacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Locacao> locacaoList = locacaoRepository.findAll();
        assertThat(locacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Locacao in Elasticsearch
        verify(mockLocacaoSearchRepository, times(1)).deleteById(locacao.getId());
    }

    @Test
    @Transactional
    public void searchLocacao() throws Exception {
        // Initialize the database
        locacaoService.save(locacao);
        when(mockLocacaoSearchRepository.search(queryStringQuery("id:" + locacao.getId())))
            .thenReturn(Collections.singletonList(locacao));
        // Search the locacao
        restLocacaoMockMvc.perform(get("/api/_search/locacaos?query=id:" + locacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataLocacao").value(hasItem(sameInstant(DEFAULT_DATA_LOCACAO))))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Locacao.class);
        Locacao locacao1 = new Locacao();
        locacao1.setId(1L);
        Locacao locacao2 = new Locacao();
        locacao2.setId(locacao1.getId());
        assertThat(locacao1).isEqualTo(locacao2);
        locacao2.setId(2L);
        assertThat(locacao1).isNotEqualTo(locacao2);
        locacao1.setId(null);
        assertThat(locacao1).isNotEqualTo(locacao2);
    }
}
