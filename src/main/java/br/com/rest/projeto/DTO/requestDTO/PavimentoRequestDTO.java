package br.com.rest.projeto.DTO.requestDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class PavimentoRequestDTO {

    private Long id; // Apenas para o cadastro principal de servico.

    @NotBlank
    @Size(max=100)
    private String descricao;

    @NotNull
    @Positive
    private Long idProjeto;

    public PavimentoRequestDTO(@NotBlank @Size(max = 100) String descricao, @NotNull @Positive Long idProjeto) {
        this.descricao = descricao;
        this.idProjeto = idProjeto;
    }

    public PavimentoRequestDTO() {
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
}
