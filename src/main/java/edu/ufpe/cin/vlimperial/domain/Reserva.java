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
 * A Reserva.
 */
@Entity
@Table(name = "reserva")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reserva")
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data_solicitacao", nullable = false)
    private ZonedDateTime dataSolicitacao;

    @ManyToOne
    @JsonIgnoreProperties("reservas")
    private Cliente cliente;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "reserva_midia_desejada",
               joinColumns = @JoinColumn(name = "reservas_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "midia_desejadas_id", referencedColumnName = "id"))
    private Set<ItemFilme> midiaDesejadas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public Reserva dataSolicitacao(ZonedDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
        return this;
    }

    public void setDataSolicitacao(ZonedDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Reserva cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<ItemFilme> getMidiaDesejadas() {
        return midiaDesejadas;
    }

    public Reserva midiaDesejadas(Set<ItemFilme> itemFilmes) {
        this.midiaDesejadas = itemFilmes;
        return this;
    }

    public Reserva addMidiaDesejada(ItemFilme itemFilme) {
        this.midiaDesejadas.add(itemFilme);
        itemFilme.getReservas().add(this);
        return this;
    }

    public Reserva removeMidiaDesejada(ItemFilme itemFilme) {
        this.midiaDesejadas.remove(itemFilme);
        itemFilme.getReservas().remove(this);
        return this;
    }

    public void setMidiaDesejadas(Set<ItemFilme> itemFilmes) {
        this.midiaDesejadas = itemFilmes;
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
        Reserva reserva = (Reserva) o;
        if (reserva.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reserva.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reserva{" +
            "id=" + getId() +
            ", dataSolicitacao='" + getDataSolicitacao() + "'" +
            "}";
    }
}
