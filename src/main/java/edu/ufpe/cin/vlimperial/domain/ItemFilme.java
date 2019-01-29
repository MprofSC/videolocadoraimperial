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

import edu.ufpe.cin.vlimperial.domain.enumeration.TipoMidia;

/**
 * A ItemFilme.
 */
@Entity
@Table(name = "item_filme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "itemfilme")
public class ItemFilme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero_serie", nullable = false)
    private String numeroSerie;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_midia", nullable = false)
    private TipoMidia tipoMidia;

    @NotNull
    @Column(name = "data_aquisicao", nullable = false)
    private ZonedDateTime dataAquisicao;

    @ManyToOne
    @JsonIgnoreProperties("itemfilmes")
    private Filme filme;

    @ManyToMany(mappedBy = "itemLocados")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Locacao> locacaos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("midiaDesejadas")
    private Reserva reserva;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public ItemFilme numeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
        return this;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public TipoMidia getTipoMidia() {
        return tipoMidia;
    }

    public ItemFilme tipoMidia(TipoMidia tipoMidia) {
        this.tipoMidia = tipoMidia;
        return this;
    }

    public void setTipoMidia(TipoMidia tipoMidia) {
        this.tipoMidia = tipoMidia;
    }

    public ZonedDateTime getDataAquisicao() {
        return dataAquisicao;
    }

    public ItemFilme dataAquisicao(ZonedDateTime dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
        return this;
    }

    public void setDataAquisicao(ZonedDateTime dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public Filme getFilme() {
        return filme;
    }

    public ItemFilme filme(Filme filme) {
        this.filme = filme;
        return this;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public Set<Locacao> getLocacaos() {
        return locacaos;
    }

    public ItemFilme locacaos(Set<Locacao> locacaos) {
        this.locacaos = locacaos;
        return this;
    }

    public ItemFilme addLocacao(Locacao locacao) {
        this.locacaos.add(locacao);
        locacao.getItemLocados().add(this);
        return this;
    }

    public ItemFilme removeLocacao(Locacao locacao) {
        this.locacaos.remove(locacao);
        locacao.getItemLocados().remove(this);
        return this;
    }

    public void setLocacaos(Set<Locacao> locacaos) {
        this.locacaos = locacaos;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public ItemFilme reserva(Reserva reserva) {
        this.reserva = reserva;
        return this;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
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
        ItemFilme itemFilme = (ItemFilme) o;
        if (itemFilme.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemFilme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemFilme{" +
            "id=" + getId() +
            ", numeroSerie='" + getNumeroSerie() + "'" +
            ", tipoMidia='" + getTipoMidia() + "'" +
            ", dataAquisicao='" + getDataAquisicao() + "'" +
            "}";
    }
}
