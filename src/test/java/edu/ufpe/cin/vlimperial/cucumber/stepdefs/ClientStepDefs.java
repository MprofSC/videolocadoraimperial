package edu.ufpe.cin.vlimperial.cucumber.stepdefs;

import static edu.ufpe.cin.vlimperial.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ufpe.cin.vlimperial.domain.Cliente;
import edu.ufpe.cin.vlimperial.domain.Locacao;
import edu.ufpe.cin.vlimperial.domain.Reserva;
import edu.ufpe.cin.vlimperial.domain.enumeration.GeneroPessoa;
import edu.ufpe.cin.vlimperial.repository.ClienteRepository;
import edu.ufpe.cin.vlimperial.web.rest.ClienteResource;
import edu.ufpe.cin.vlimperial.web.rest.TestUtil;
import edu.ufpe.cin.vlimperial.web.rest.errors.ExceptionTranslator;

public class ClientStepDefs extends StepDefs {

	private static final Long ID = 100L;
	private static final Long DEPENDENTE_ID = 101L;

	private static final Long DEFAULT_NUMERO_INCRICAO = 1L;
	private static final Long UPDATED_NUMERO_INCRICAO = 2L;
	private static final Long DEPENDENTE_NUMERO_INCRICAO = 3L;

	private static final String DEFAULT_NOME = "AAAAAAAAAA";
	private static final String UPDATED_NOME = "BBBBBBBBBB";
	private static final String DEPENDENTE_NOME = "CCCCCCCCC";

	private static final Long DEFAULT_CPF = 1L;
	private static final Long UPDATED_CPF = 2L;
	private static final Long DEPENDENTE_CPF = 3L;

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

	private static final ZonedDateTime DEFAULT_DATA_NASCIMENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L),
			ZoneOffset.UTC);
	private static final ZonedDateTime UPDATED_DATA_NASCIMENTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

	private static final Boolean DEFAULT_ATIVO = true;
	private static final Boolean UPDATED_ATIVO = false;

	@Autowired
	private ClienteResource clienteResource;

	@Autowired
	private EntityManager em;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private ClienteRepository clienteRepository;
	
	private MockMvc restClienteMockMvc;

	private Cliente cliente;
	
	private Cliente updatedCliente; 

	private Cliente dependente;
	
	private Cliente updateDependente;

	public static Cliente createEntity(EntityManager em) {
		Cliente cliente = new Cliente().numeroIncricao(DEFAULT_NUMERO_INCRICAO).nome(DEFAULT_NOME).cpf(DEFAULT_CPF)
				.email(DEFAULT_EMAIL).endereco(DEFAULT_ENDERECO).telefoneResidencial(DEFAULT_TELEFONE_RESIDENCIAL)
				.telefoneComercial(DEFAULT_TELEFONE_COMERCIAL).telefoneCelular(DEFAULT_TELEFONE_CELULAR)
				.localTrabalho(DEFAULT_LOCAL_TRABALHO).sexo(DEFAULT_SEXO).dataNascimento(DEFAULT_DATA_NASCIMENTO)
				.ativo(DEFAULT_ATIVO);
		cliente.setReservas(new HashSet<Reserva>());
		return cliente;
	}

	public static Cliente createDependente(EntityManager em, Cliente cliente) {
		Cliente dependente = new Cliente().numeroIncricao(DEPENDENTE_NUMERO_INCRICAO).nome(DEPENDENTE_NOME)
				.cpf(DEPENDENTE_CPF).email(DEFAULT_EMAIL).endereco(DEFAULT_ENDERECO)
				.telefoneResidencial(DEFAULT_TELEFONE_RESIDENCIAL).telefoneComercial(DEFAULT_TELEFONE_COMERCIAL)
				.telefoneCelular(DEFAULT_TELEFONE_CELULAR).localTrabalho(DEFAULT_LOCAL_TRABALHO).sexo(DEFAULT_SEXO)
				.dataNascimento(DEFAULT_DATA_NASCIMENTO).ativo(DEFAULT_ATIVO).cliente(cliente);
		dependente.setReservas(new HashSet<Reserva>());
		return dependente;
	}

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(clienteResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
				.setConversionService(createFormattingConversionService()).setMessageConverters(jacksonMessageConverter)
				.build();
		this.cliente = createEntity(em);
		this.dependente = createDependente(em, cliente);
		restClienteMockMvc.perform(post("/api/clientes").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(cliente))).andExpect(status().isCreated());
		
		List<Cliente> clienteList = clienteRepository.findAll();
        updatedCliente = clienteList.get(clienteList.size() - 1);
		updatedCliente.setReservas(new HashSet<Reserva>());
		updatedCliente.setLocacaos(new HashSet<Locacao>());
        dependente.setCliente(updatedCliente);
        
		restClienteMockMvc.perform(post("/api/clientes").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dependente))).andExpect(status().isCreated());
		List<Cliente> dependenteList = clienteRepository.findAll();
        updateDependente = dependenteList.get(dependenteList.size() - 1);
		
	}

	@When("Eu inativo um cliente com dependente")
	public void i_update_client() throws Exception {
		// seta para inativo
		updatedCliente.setAtivo(UPDATED_ATIVO);
		updatedCliente.setClientes(new HashSet<Cliente>());
		// atualiza cliente
		actions = restClienteMockMvc.perform(put("/api/clientes/").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(updatedCliente)))
				.andExpect(status().isOk());
		System.out.println("fedidao");
	}

	@Then("o cliente fica com o status {string}")
	public void o_cliente_fica_com_o_status(String string)  throws Throwable{
		actions = restClienteMockMvc
				.perform(get("/api/clientes/" + updatedCliente.getId()).accept(MediaType.APPLICATION_JSON));
		actions.andExpect(status().isOk()).andExpect(jsonPath("$.ativo").value(string));
	}

	@Then("seus dependentes ficam com o status {string}")
	public void dependente_status_inative(String status) throws Exception {
		actions = restClienteMockMvc
				.perform(get("/api/clientes/" + updateDependente.getId()).accept(MediaType.APPLICATION_JSON));
		actions.andExpect(status().isOk()).andExpect(jsonPath("$.ativo").value(status));
	}

}
