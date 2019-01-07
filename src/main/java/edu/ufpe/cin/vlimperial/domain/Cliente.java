package edu.ufpe.cin.vlimperial.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import edu.ufpe.cin.vlimperial.domain.enumeration.GeneroPessoa;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero_incricao", nullable = false)
    private Long numeroIncricao;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf")
    private Integer cpf;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "telefone_residencial")
    private String telefoneResidencial;

    @Column(name = "telefone_comercial")
    private String telefoneComercial;

    @Column(name = "telefone_celular")
    private String telefoneCelular;

    @Column(name = "local_trabalho")
    private String localTrabalho;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private GeneroPessoa sexo;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private ZonedDateTime dataNascimento;

    @Column(name = "ativo")
    private Boolean ativo;

    @ManyToOne
    @JsonIgnoreProperties("clientes")
    private Cliente cliente;

    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cliente> clientes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroIncricao() {
        return numeroIncricao;
    }

    public Cliente numeroIncricao(Long numeroIncricao) {
        this.numeroIncricao = numeroIncricao;
        return this;
    }

    public void setNumeroIncricao(Long numeroIncricao) {
        this.numeroIncricao = numeroIncricao;
    }

    public String getNome() {
        return nome;
    }

    public Cliente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCpf() {
        return cpf;
    }

    public Cliente cpf(Integer cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public Cliente email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public Cliente endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefoneResidencial() {
        return telefoneResidencial;
    }

    public Cliente telefoneResidencial(String telefoneResidencial) {
        this.telefoneResidencial = telefoneResidencial;
        return this;
    }

    public void setTelefoneResidencial(String telefoneResidencial) {
        this.telefoneResidencial = telefoneResidencial;
    }

    public String getTelefoneComercial() {
        return telefoneComercial;
    }

    public Cliente telefoneComercial(String telefoneComercial) {
        this.telefoneComercial = telefoneComercial;
        return this;
    }

    public void setTelefoneComercial(String telefoneComercial) {
        this.telefoneComercial = telefoneComercial;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public Cliente telefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
        return this;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getLocalTrabalho() {
        return localTrabalho;
    }

    public Cliente localTrabalho(String localTrabalho) {
        this.localTrabalho = localTrabalho;
        return this;
    }

    public void setLocalTrabalho(String localTrabalho) {
        this.localTrabalho = localTrabalho;
    }

    public GeneroPessoa getSexo() {
        return sexo;
    }

    public Cliente sexo(GeneroPessoa sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(GeneroPessoa sexo) {
        this.sexo = sexo;
    }

    public ZonedDateTime getDataNascimento() {
        return dataNascimento;
    }

    public Cliente dataNascimento(ZonedDateTime dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public void setDataNascimento(ZonedDateTime dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Cliente ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Cliente cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public Cliente clientes(Set<Cliente> clientes) {
        this.clientes = clientes;
        return this;
    }

    public Cliente addCliente(Cliente cliente) {
        this.clientes.add(cliente);
        cliente.setCliente(this);
        return this;
    }

    public Cliente removeCliente(Cliente cliente) {
        this.clientes.remove(cliente);
        cliente.setCliente(null);
        return this;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cliente cliente = (Cliente) o;
        if (cliente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cliente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", numeroIncricao=" + getNumeroIncricao() +
            ", nome='" + getNome() + "'" +
            ", cpf=" + getCpf() +
            ", email='" + getEmail() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", telefoneResidencial='" + getTelefoneResidencial() + "'" +
            ", telefoneComercial='" + getTelefoneComercial() + "'" +
            ", telefoneCelular='" + getTelefoneCelular() + "'" +
            ", localTrabalho='" + getLocalTrabalho() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
