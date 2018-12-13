package edu.ufpe.cin.vlimperial.web.rest;

import edu.ufpe.cin.vlimperial.VlimperialApp;

import edu.ufpe.cin.vlimperial.domain.Cliente;
import edu.ufpe.cin.vlimperial.repository.ClienteRepository;
import edu.ufpe.cin.vlimperial.service.ClienteService;
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
import java.time.temporal.ChronoUnit;
import java.util.List;


import static edu.ufpe.cin.vlimperial.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.ufpe.cin.vlimperial.domain.enumeration.GeneroPessoa;
/**
 * Test class for the ClienteResource REST controller.
 *
 * @see ClienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VlimperialApp.class)
public class ClienteResourceIntTest {

    private static final Long DEFAULT_NUMERO_INCRICAO = 1L;
    private static final Long UPDATED_NUMERO_INCRICAO = 2L;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CPF = 1;
    private static final Integer UPDATED_CPF = 2;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_RESIDENCIAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_RESIDENCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_COMERCIAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_COMERCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_CELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL_TRABALHO = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_TRABALHO = "BBBBBBBBBB";

    private static final GeneroPessoa DEFAULT_SEXO = GeneroPessoa.MASCULINO;
    private static final GeneroPessoa UPDATED_SEXO = GeneroPessoa.FEMININO;

    private static final Instant DEFAULT_DATA_NASCIMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_NASCIMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteResource clienteResource = new ClienteResource(clienteService);
        this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(clienteResource)
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
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .numeroIncricao(DEFAULT_NUMERO_INCRICAO)
            .nome(DEFAULT_NOME)
            .cpf(DEFAULT_CPF)
            .email(DEFAULT_EMAIL)
            .endereco(DEFAULT_ENDERECO)
            .telefoneResidencial(DEFAULT_TELEFONE_RESIDENCIAL)
            .telefoneComercial(DEFAULT_TELEFONE_COMERCIAL)
            .telefoneCelular(DEFAULT_TELEFONE_CELULAR)
            .localTrabalho(DEFAULT_LOCAL_TRABALHO)
            .sexo(DEFAULT_SEXO)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .ativo(DEFAULT_ATIVO);
        return cliente;
    }

    @Before
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNumeroIncricao()).isEqualTo(DEFAULT_NUMERO_INCRICAO);
        assertThat(testCliente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCliente.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testCliente.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCliente.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testCliente.getTelefoneResidencial()).isEqualTo(DEFAULT_TELEFONE_RESIDENCIAL);
        assertThat(testCliente.getTelefoneComercial()).isEqualTo(DEFAULT_TELEFONE_COMERCIAL);
        assertThat(testCliente.getTelefoneCelular()).isEqualTo(DEFAULT_TELEFONE_CELULAR);
        assertThat(testCliente.getLocalTrabalho()).isEqualTo(DEFAULT_LOCAL_TRABALHO);
        assertThat(testCliente.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testCliente.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testCliente.isAtivo()).isEqualTo(DEFAULT_ATIVO);
    }

    @Test
    @Transactional
    public void createClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente with an existing ID
        cliente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroIncricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNumeroIncricao(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNome(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setEmail(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setEndereco(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setSexo(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setDataNascimento(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroIncricao").value(hasItem(DEFAULT_NUMERO_INCRICAO.intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].telefoneResidencial").value(hasItem(DEFAULT_TELEFONE_RESIDENCIAL.toString())))
            .andExpect(jsonPath("$.[*].telefoneComercial").value(hasItem(DEFAULT_TELEFONE_COMERCIAL.toString())))
            .andExpect(jsonPath("$.[*].telefoneCelular").value(hasItem(DEFAULT_TELEFONE_CELULAR.toString())))
            .andExpect(jsonPath("$.[*].localTrabalho").value(hasItem(DEFAULT_LOCAL_TRABALHO.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.numeroIncricao").value(DEFAULT_NUMERO_INCRICAO.intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.telefoneResidencial").value(DEFAULT_TELEFONE_RESIDENCIAL.toString()))
            .andExpect(jsonPath("$.telefoneComercial").value(DEFAULT_TELEFONE_COMERCIAL.toString()))
            .andExpect(jsonPath("$.telefoneCelular").value(DEFAULT_TELEFONE_CELULAR.toString()))
            .andExpect(jsonPath("$.localTrabalho").value(DEFAULT_LOCAL_TRABALHO.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCliente() throws Exception {
        // Initialize the database
        clienteService.save(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findById(cliente.getId()).get();
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente
            .numeroIncricao(UPDATED_NUMERO_INCRICAO)
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .email(UPDATED_EMAIL)
            .endereco(UPDATED_ENDERECO)
            .telefoneResidencial(UPDATED_TELEFONE_RESIDENCIAL)
            .telefoneComercial(UPDATED_TELEFONE_COMERCIAL)
            .telefoneCelular(UPDATED_TELEFONE_CELULAR)
            .localTrabalho(UPDATED_LOCAL_TRABALHO)
            .sexo(UPDATED_SEXO)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .ativo(UPDATED_ATIVO);

        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCliente)))
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNumeroIncricao()).isEqualTo(UPDATED_NUMERO_INCRICAO);
        assertThat(testCliente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCliente.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testCliente.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCliente.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testCliente.getTelefoneResidencial()).isEqualTo(UPDATED_TELEFONE_RESIDENCIAL);
        assertThat(testCliente.getTelefoneComercial()).isEqualTo(UPDATED_TELEFONE_COMERCIAL);
        assertThat(testCliente.getTelefoneCelular()).isEqualTo(UPDATED_TELEFONE_CELULAR);
        assertThat(testCliente.getLocalTrabalho()).isEqualTo(UPDATED_LOCAL_TRABALHO);
        assertThat(testCliente.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testCliente.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testCliente.isAtivo()).isEqualTo(UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Create the Cliente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCliente() throws Exception {
        // Initialize the database
        clienteService.save(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Get the cliente
        restClienteMockMvc.perform(delete("/api/clientes/{id}", cliente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cliente.class);
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        Cliente cliente2 = new Cliente();
        cliente2.setId(cliente1.getId());
        assertThat(cliente1).isEqualTo(cliente2);
        cliente2.setId(2L);
        assertThat(cliente1).isNotEqualTo(cliente2);
        cliente1.setId(null);
        assertThat(cliente1).isNotEqualTo(cliente2);
    }
}
