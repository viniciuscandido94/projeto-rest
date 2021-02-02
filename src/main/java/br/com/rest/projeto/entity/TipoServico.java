package br.com.rest.projeto.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "tipoServicos")
public class TipoServico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String descricao;
    @NotNull
    private Boolean servicoQualidade;
    @ManyToOne
    private Projeto projeto;

    public TipoServico() {
    }

    public TipoServico(@NotBlank String descricao, @NotNull Boolean servicoQualidade, Projeto projeto) {
        this.descricao = descricao;
        this.servicoQualidade = servicoQualidade;
        this.projeto = projeto;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getServicoQualidade() {
        return servicoQualidade;
    }

    public void setServicoQualidade(Boolean servicoQualidade) {
        this.servicoQualidade = servicoQualidade;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
}
