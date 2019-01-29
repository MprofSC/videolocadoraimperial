package edu.ufpe.cin.vlimperial.domain;

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

/**
 * A Locacao.
 */
@Entity
@Table(name = "locacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "locacao")
public class Locacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data_locacao", nullable = false)
    private ZonedDateTime dataLocacao;

    @NotNull
    @Column(name = "valor", nullable = false)
    private Double valor;

    @ManyToOne
    @JsonIgnoreProperties("locacaos")
    private Cliente cliente;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "locacao_item_locado",
               joinColumns = @JoinColumn(name = "locacaos_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "item_locados_id", referencedColumnName = "id"))
    private Set<ItemFilme> itemLocados = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataLocacao() {
        return dataLocacao;
    }

    public Locacao dataLocacao(ZonedDateTime dataLocacao) {
        this.dataLocacao = dataLocacao;
        return this;
    }

    public void setDataLocacao(ZonedDateTime dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public Double getValor() {
        return valor;
    }

    public Locacao valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Locacao cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<ItemFilme> getItemLocados() {
        return itemLocados;
    }

    public Locacao itemLocados(Set<ItemFilme> itemFilmes) {
        this.itemLocados = itemFilmes;
        return this;
    }

    public Locacao addItemLocado(ItemFilme itemFilme) {
        this.itemLocados.add(itemFilme);
        itemFilme.getLocacaos().add(this);
        return this;
    }

    public Locacao removeItemLocado(ItemFilme itemFilme) {
        this.itemLocados.remove(itemFilme);
        itemFilme.getLocacaos().remove(this);
        return this;
    }

    public void setItemLocados(Set<ItemFilme> itemFilmes) {
        this.itemLocados = itemFilmes;
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
        Locacao locacao = (Locacao) o;
        if (locacao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), locacao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Locacao{" +
            "id=" + getId() +
            ", dataLocacao='" + getDataLocacao() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
