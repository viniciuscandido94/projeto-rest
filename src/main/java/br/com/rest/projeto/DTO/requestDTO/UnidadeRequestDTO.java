package br.com.rest.projeto.DTO.requestDTO;


import javax.validation.constraints.*;

public class UnidadeRequestDTO {

    private Long id; // Apenas para o cadastro principal de Servico.

    @NotBlank
    @Size(max=100)
    private String descricao;

    @NotNull
    @Positive
    private Long idProjeto;

    public UnidadeRequestDTO(@NotBlank @Size(max = 100) String descricao, @NotNull @Positive Long idProjeto) {
        this.descricao = descricao;
        this.idProjeto = idProjeto;
    }

    public UnidadeRequestDTO() {
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
