package com.sde.videoImperial.model;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.lang.String; 
import java.lang.String; 
import java.lang.String; 
import java.lang.String; 
import java.lang.String; 
import java.lang.String; 
import java.lang.String; 
import java.lang.String; 
import java.util.Date; 
import java.lang.Integer; 


@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "telefoneRes")
    private String telefoneRes;

    @Column(name = "telefoneCom")
    private String telefoneCom;

    @Column(name = "telefoneCel")
    private String telefoneCel;

    @Column(name = "localTrabalho")
    private String localTrabalho;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "dataNascimento")
    private Date dataNascimento;

    @Column(name = "cpf")
    private Integer cpf;

	public void setNome(String nome) {this.nome = nome;}
	public String getNome() {return nome;}
	public void setEmail(String email) {this.email = email;}
	public String getEmail() {return email;}
	public void setEndereco(String endereco) {this.endereco = endereco;}
	public String getEndereco() {return endereco;}
	public void setTelefoneRes(String telefoneRes) {this.telefoneRes = telefoneRes;}
	public String getTelefoneRes() {return telefoneRes;}
	public void setTelefoneCom(String telefoneCom) {this.telefoneCom = telefoneCom;}
	public String getTelefoneCom() {return telefoneCom;}
	public void setTelefoneCel(String telefoneCel) {this.telefoneCel = telefoneCel;}
	public String getTelefoneCel() {return telefoneCel;}
	public void setLocalTrabalho(String localTrabalho) {this.localTrabalho = localTrabalho;}
	public String getLocalTrabalho() {return localTrabalho;}
	public void setSexo(String sexo) {this.sexo = sexo;}
	public String getSexo() {return sexo;}
	public void setDataNascimento(Date dataNascimento) {this.dataNascimento = dataNascimento;}
	public Date getDataNascimento() {return dataNascimento;}
	public void setCpf(Integer cpf) {this.cpf = cpf;}
	public Integer getCpf() {return cpf;}

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	
}