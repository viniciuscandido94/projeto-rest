package br.com.rest.projeto.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "fotos")
public class Foto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nome;
    @NotNull
    @ManyToOne
    private NCServico servico;

    public Foto(@NotBlank String nome, @NotNull NCServico servico) {
        this.nome = nome;
        this.servico = servico;
    }

    public Foto() {
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

    public NCServico getServico() {
        return servico;
    }

    public void setServico(NCServico servico) {
        this.servico = servico;
    }
}
