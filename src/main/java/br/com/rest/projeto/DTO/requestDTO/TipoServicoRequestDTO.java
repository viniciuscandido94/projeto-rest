package br.com.rest.projeto.DTO.requestDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TipoServicoRequestDTO {

    private Long id; // Apenas para o cadastro principal de servico.

    @NotBlank
    @Size(max=100)
    private String descricao;

    @NotNull
    @Positive
    private Long idProjeto;

    @NotNull
    private Boolean servicoDeQualidade;

    public TipoServicoRequestDTO(@NotBlank @Size(max = 100) String descricao, @NotNull @Positive Long idProjeto, @NotNull Boolean servicoDeQualidade) {
        this.descricao = descricao;
        this.idProjeto = idProjeto;
        this.servicoDeQualidade = servicoDeQualidade;
    }

    public TipoServicoRequestDTO() {
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

    public Long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Long idProjeto) {
        this.idProjeto = idProjeto;
    }

    public Boolean getServicoDeQualidade() {
        return servicoDeQualidade;
    }

    public void setServicoDeQualidade(Boolean servicoDeQualidade) {
        this.servicoDeQualidade = servicoDeQualidade;
    }
}
