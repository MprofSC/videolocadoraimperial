package edu.ufpe.cin.vlimperial.web.rest;

import edu.ufpe.cin.vlimperial.VlimperialApp;

import edu.ufpe.cin.vlimperial.domain.Filme;
import edu.ufpe.cin.vlimperial.repository.FilmeRepository;
import edu.ufpe.cin.vlimperial.service.FilmeService;
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
import java.util.List;


import static edu.ufpe.cin.vlimperial.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.ufpe.cin.vlimperial.domain.enumeration.Genero;
/**
 * Test class for the FilmeResource REST controller.
 *
 * @see FilmeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VlimperialApp.class)
public class FilmeResourceIntTest {

    private static final String DEFAULT_TITULO_ORIGINAL = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_ORIGINAL = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO_PORTUGUES = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_PORTUGUES = "BBBBBBBBBB";

    private static final String DEFAULT_PAISES = "AAAAAAAAAA";
    private static final String UPDATED_PAISES = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANO = 1;
    private static final Integer UPDATED_ANO = 2;

    private static final String DEFAULT_DIRECAO = "AAAAAAAAAA";
    private static final String UPDATED_DIRECAO = "BBBBBBBBBB";

    private static final String DEFAULT_ELENCO = "AAAAAAAAAA";
    private static final String UPDATED_ELENCO = "BBBBBBBBBB";

    private static final String DEFAULT_SINOPSE = "AAAAAAAAAA";
    private static final String UPDATED_SINOPSE = "BBBBBBBBBB";

    private static final String DEFAULT_DURACAO = "AAAAAAAAAA";
    private static final String UPDATED_DURACAO = "BBBBBBBBBB";

    private static final Genero DEFAULT_GENERO = Genero.ACAO;
    private static final Genero UPDATED_GENERO = Genero.ANIMACAO;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFilmeMockMvc;

    private Filme filme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilmeResource filmeResource = new FilmeResource(filmeService);
        this.restFilmeMockMvc = MockMvcBuilders.standaloneSetup(filmeResource)
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
    public static Filme createEntity(EntityManager em) {
        Filme filme = new Filme()
            .tituloOriginal(DEFAULT_TITULO_ORIGINAL)
            .tituloPortugues(DEFAULT_TITULO_PORTUGUES)
            .paises(DEFAULT_PAISES)
            .ano(DEFAULT_ANO)
            .direcao(DEFAULT_DIRECAO)
            .elenco(DEFAULT_ELENCO)
            .sinopse(DEFAULT_SINOPSE)
            .duracao(DEFAULT_DURACAO)
            .genero(DEFAULT_GENERO);
        return filme;
    }

    @Before
    public void initTest() {
        filme = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilme() throws Exception {
        int databaseSizeBeforeCreate = filmeRepository.findAll().size();

        // Create the Filme
        restFilmeMockMvc.perform(post("/api/filmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filme)))
            .andExpect(status().isCreated());

        // Validate the Filme in the database
        List<Filme> filmeList = filmeRepository.findAll();
        assertThat(filmeList).hasSize(databaseSizeBeforeCreate + 1);
        Filme testFilme = filmeList.get(filmeList.size() - 1);
        assertThat(testFilme.getTituloOriginal()).isEqualTo(DEFAULT_TITULO_ORIGINAL);
        assertThat(testFilme.getTituloPortugues()).isEqualTo(DEFAULT_TITULO_PORTUGUES);
        assertThat(testFilme.getPaises()).isEqualTo(DEFAULT_PAISES);
        assertThat(testFilme.getAno()).isEqualTo(DEFAULT_ANO);
        assertThat(testFilme.getDirecao()).isEqualTo(DEFAULT_DIRECAO);
        assertThat(testFilme.getElenco()).isEqualTo(DEFAULT_ELENCO);
        assertThat(testFilme.getSinopse()).isEqualTo(DEFAULT_SINOPSE);
        assertThat(testFilme.getDuracao()).isEqualTo(DEFAULT_DURACAO);
        assertThat(testFilme.getGenero()).isEqualTo(DEFAULT_GENERO);
    }

    @Test
    @Transactional
    public void createFilmeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filmeRepository.findAll().size();

        // Create the Filme with an existing ID
        filme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilmeMockMvc.perform(post("/api/filmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filme)))
            .andExpect(status().isBadRequest());

        // Validate the Filme in the database
        List<Filme> filmeList = filmeRepository.findAll();
        assertThat(filmeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFilmes() throws Exception {
        // Initialize the database
        filmeRepository.saveAndFlush(filme);

        // Get all the filmeList
        restFilmeMockMvc.perform(get("/api/filmes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filme.getId().intValue())))
            .andExpect(jsonPath("$.[*].tituloOriginal").value(hasItem(DEFAULT_TITULO_ORIGINAL.toString())))
            .andExpect(jsonPath("$.[*].tituloPortugues").value(hasItem(DEFAULT_TITULO_PORTUGUES.toString())))
            .andExpect(jsonPath("$.[*].paises").value(hasItem(DEFAULT_PAISES.toString())))
            .andExpect(jsonPath("$.[*].ano").value(hasItem(DEFAULT_ANO)))
            .andExpect(jsonPath("$.[*].direcao").value(hasItem(DEFAULT_DIRECAO.toString())))
            .andExpect(jsonPath("$.[*].elenco").value(hasItem(DEFAULT_ELENCO.toString())))
            .andExpect(jsonPath("$.[*].sinopse").value(hasItem(DEFAULT_SINOPSE.toString())))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())));
    }
    
    @Test
    @Transactional
    public void getFilme() throws Exception {
        // Initialize the database
        filmeRepository.saveAndFlush(filme);

        // Get the filme
        restFilmeMockMvc.perform(get("/api/filmes/{id}", filme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filme.getId().intValue()))
            .andExpect(jsonPath("$.tituloOriginal").value(DEFAULT_TITULO_ORIGINAL.toString()))
            .andExpect(jsonPath("$.tituloPortugues").value(DEFAULT_TITULO_PORTUGUES.toString()))
            .andExpect(jsonPath("$.paises").value(DEFAULT_PAISES.toString()))
            .andExpect(jsonPath("$.ano").value(DEFAULT_ANO))
            .andExpect(jsonPath("$.direcao").value(DEFAULT_DIRECAO.toString()))
            .andExpect(jsonPath("$.elenco").value(DEFAULT_ELENCO.toString()))
            .andExpect(jsonPath("$.sinopse").value(DEFAULT_SINOPSE.toString()))
            .andExpect(jsonPath("$.duracao").value(DEFAULT_DURACAO.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFilme() throws Exception {
        // Get the filme
        restFilmeMockMvc.perform(get("/api/filmes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilme() throws Exception {
        // Initialize the database
        filmeService.save(filme);

        int databaseSizeBeforeUpdate = filmeRepository.findAll().size();

        // Update the filme
        Filme updatedFilme = filmeRepository.findById(filme.getId()).get();
        // Disconnect from session so that the updates on updatedFilme are not directly saved in db
        em.detach(updatedFilme);
        updatedFilme
            .tituloOriginal(UPDATED_TITULO_ORIGINAL)
            .tituloPortugues(UPDATED_TITULO_PORTUGUES)
            .paises(UPDATED_PAISES)
            .ano(UPDATED_ANO)
            .direcao(UPDATED_DIRECAO)
            .elenco(UPDATED_ELENCO)
            .sinopse(UPDATED_SINOPSE)
            .duracao(UPDATED_DURACAO)
            .genero(UPDATED_GENERO);

        restFilmeMockMvc.perform(put("/api/filmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFilme)))
            .andExpect(status().isOk());

        // Validate the Filme in the database
        List<Filme> filmeList = filmeRepository.findAll();
        assertThat(filmeList).hasSize(databaseSizeBeforeUpdate);
        Filme testFilme = filmeList.get(filmeList.size() - 1);
        assertThat(testFilme.getTituloOriginal()).isEqualTo(UPDATED_TITULO_ORIGINAL);
        assertThat(testFilme.getTituloPortugues()).isEqualTo(UPDATED_TITULO_PORTUGUES);
        assertThat(testFilme.getPaises()).isEqualTo(UPDATED_PAISES);
        assertThat(testFilme.getAno()).isEqualTo(UPDATED_ANO);
        assertThat(testFilme.getDirecao()).isEqualTo(UPDATED_DIRECAO);
        assertThat(testFilme.getElenco()).isEqualTo(UPDATED_ELENCO);
        assertThat(testFilme.getSinopse()).isEqualTo(UPDATED_SINOPSE);
        assertThat(testFilme.getDuracao()).isEqualTo(UPDATED_DURACAO);
        assertThat(testFilme.getGenero()).isEqualTo(UPDATED_GENERO);
    }

    @Test
    @Transactional
    public void updateNonExistingFilme() throws Exception {
        int databaseSizeBeforeUpdate = filmeRepository.findAll().size();

        // Create the Filme

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilmeMockMvc.perform(put("/api/filmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filme)))
            .andExpect(status().isBadRequest());

        // Validate the Filme in the database
        List<Filme> filmeList = filmeRepository.findAll();
        assertThat(filmeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFilme() throws Exception {
        // Initialize the database
        filmeService.save(filme);

        int databaseSizeBeforeDelete = filmeRepository.findAll().size();

        // Get the filme
        restFilmeMockMvc.perform(delete("/api/filmes/{id}", filme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Filme> filmeList = filmeRepository.findAll();
        assertThat(filmeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Filme.class);
        Filme filme1 = new Filme();
        filme1.setId(1L);
        Filme filme2 = new Filme();
        filme2.setId(filme1.getId());
        assertThat(filme1).isEqualTo(filme2);
        filme2.setId(2L);
        assertThat(filme1).isNotEqualTo(filme2);
        filme1.setId(null);
        assertThat(filme1).isNotEqualTo(filme2);
    }
}
