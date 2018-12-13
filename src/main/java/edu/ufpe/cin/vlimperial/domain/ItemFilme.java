package edu.ufpe.cin.vlimperial.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import edu.ufpe.cin.vlimperial.domain.enumeration.TipoMidia;

/**
 * A ItemFilme.
 */
@Entity
@Table(name = "item_filme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemFilme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Instant dataAquisicao;

    @ManyToOne
    @JsonIgnoreProperties("itemfilmes")
    private Filme filme;

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

    public Instant getDataAquisicao() {
        return dataAquisicao;
    }

    public ItemFilme dataAquisicao(Instant dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
        return this;
    }

    public void setDataAquisicao(Instant dataAquisicao) {
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
