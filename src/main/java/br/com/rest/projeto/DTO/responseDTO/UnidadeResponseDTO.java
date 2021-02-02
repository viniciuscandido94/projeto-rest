package br.com.rest.projeto.DTO.responseDTO;

public class UnidadeResponseDTO {

    private Long id;
    private String descricao;
    private Long idProjeto;

    public UnidadeResponseDTO(Long id, String descricao, Long idProjeto) {
        this.id = id;
        this.descricao = descricao;
        this.idProjeto = idProjeto;
    }

    public UnidadeResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
