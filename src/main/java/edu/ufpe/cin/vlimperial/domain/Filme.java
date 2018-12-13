package edu.ufpe.cin.vlimperial.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import edu.ufpe.cin.vlimperial.domain.enumeration.Genero;

/**
 * A Filme.
 */
@Entity
@Table(name = "filme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Filme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo_original")
    private String tituloOriginal;

    @Column(name = "titulo_portugues")
    private String tituloPortugues;

    @Column(name = "paises")
    private String paises;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "direcao")
    private String direcao;

    @Column(name = "elenco")
    private String elenco;

    @Column(name = "sinopse")
    private String sinopse;

    @Column(name = "duracao")
    private String duracao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private Genero genero;

    @OneToMany(mappedBy = "filme")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemFilme> itemfilmes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public Filme tituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
        return this;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public String getTituloPortugues() {
        return tituloPortugues;
    }

    public Filme tituloPortugues(String tituloPortugues) {
        this.tituloPortugues = tituloPortugues;
        return this;
    }

    public void setTituloPortugues(String tituloPortugues) {
        this.tituloPortugues = tituloPortugues;
    }

    public String getPaises() {
        return paises;
    }

    public Filme paises(String paises) {
        this.paises = paises;
        return this;
    }

    public void setPaises(String paises) {
        this.paises = paises;
    }

    public Integer getAno() {
        return ano;
    }

    public Filme ano(Integer ano) {
        this.ano = ano;
        return this;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getDirecao() {
        return direcao;
    }

    public Filme direcao(String direcao) {
        this.direcao = direcao;
        return this;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    public String getElenco() {
        return elenco;
    }

    public Filme elenco(String elenco) {
        this.elenco = elenco;
        return this;
    }

    public void setElenco(String elenco) {
        this.elenco = elenco;
    }

    public String getSinopse() {
        return sinopse;
    }

    public Filme sinopse(String sinopse) {
        this.sinopse = sinopse;
        return this;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getDuracao() {
        return duracao;
    }

    public Filme duracao(String duracao) {
        this.duracao = duracao;
        return this;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public Genero getGenero() {
        return genero;
    }

    public Filme genero(Genero genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Set<ItemFilme> getItemfilmes() {
        return itemfilmes;
    }

    public Filme itemfilmes(Set<ItemFilme> itemFilmes) {
        this.itemfilmes = itemFilmes;
        return this;
    }

    public Filme addItemfilme(ItemFilme itemFilme) {
        this.itemfilmes.add(itemFilme);
        itemFilme.setFilme(this);
        return this;
    }

    public Filme removeItemfilme(ItemFilme itemFilme) {
        this.itemfilmes.remove(itemFilme);
        itemFilme.setFilme(null);
        return this;
    }

    public void setItemfilmes(Set<ItemFilme> itemFilmes) {
        this.itemfilmes = itemFilmes;
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
        Filme filme = (Filme) o;
        if (filme.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Filme{" +
            "id=" + getId() +
            ", tituloOriginal='" + getTituloOriginal() + "'" +
            ", tituloPortugues='" + getTituloPortugues() + "'" +
            ", paises='" + getPaises() + "'" +
            ", ano=" + getAno() +
            ", direcao='" + getDirecao() + "'" +
            ", elenco='" + getElenco() + "'" +
            ", sinopse='" + getSinopse() + "'" +
            ", duracao='" + getDuracao() + "'" +
            ", genero='" + getGenero() + "'" +
            "}";
    }
}
