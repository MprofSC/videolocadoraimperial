package com.sde.videolocadoraimperial.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sde.videolocadoraimperial.domain.enumeration.GeneroPessoa;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Instant dataNascimento;

    @ManyToOne
    @JsonIgnoreProperties("numeroIncricaos")
    private Cliente cliente;

    @OneToMany(mappedBy = "cliente")
    private Set<Cliente> numeroIncricaos = new HashSet<>();
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

    public Instant getDataNascimento() {
        return dataNascimento;
    }

    public Cliente dataNascimento(Instant dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public void setDataNascimento(Instant dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public Set<Cliente> getNumeroIncricaos() {
        return numeroIncricaos;
    }

    public Cliente numeroIncricaos(Set<Cliente> clientes) {
        this.numeroIncricaos = clientes;
        return this;
    }

    public Cliente addNumeroIncricao(Cliente cliente) {
        this.numeroIncricaos.add(cliente);
        cliente.setCliente(this);
        return this;
    }

    public Cliente removeNumeroIncricao(Cliente cliente) {
        this.numeroIncricaos.remove(cliente);
        cliente.setCliente(null);
        return this;
    }

    public void setNumeroIncricaos(Set<Cliente> clientes) {
        this.numeroIncricaos = clientes;
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
            "}";
    }
}
