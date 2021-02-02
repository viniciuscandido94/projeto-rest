package br.com.rest.projeto.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "funcionarios")
public class Funcionario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @Size(max=14)
    private String telefone;

    @ManyToOne
    private Empresa empresa;

    public Funcionario(@NotBlank String nome, @NotBlank @Size(max = 14) String telefone, Empresa empresa) {
        this.nome = nome;
        this.telefone = telefone;
        this.empresa = empresa;
    }

    public Funcionario() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
