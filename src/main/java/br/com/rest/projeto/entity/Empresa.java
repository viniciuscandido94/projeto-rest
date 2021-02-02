package br.com.rest.projeto.entity;

import br.com.rest.projeto.entity.entityEnum.StatusEmpresa;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "empresas")
public class Empresa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String razaoSocial;
    private String cnpj;
    private String nomeFantasia;
    private String inscEstadual;

    @Enumerated(EnumType.STRING)
    private StatusEmpresa status = StatusEmpresa.ATIVO;

    public Empresa(String razaoSocial, String cnpj, String nomeFantasia, String inscEstadual, StatusEmpresa status) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
        this.inscEstadual = inscEstadual;
        this.status = status;
    }

    public Empresa() {
    }

    public Long getId() {
        return id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getInscEstadual() {
        return inscEstadual;
    }

    public void setInscEstadual(String inscEstadual) {
        this.inscEstadual = inscEstadual;
    }

    public StatusEmpresa getStatus() {
        return status;
    }

    public void setStatus(StatusEmpresa status) {
        this.status = status;
    }
}
