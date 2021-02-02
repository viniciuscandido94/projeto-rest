package br.com.rest.projeto.DTO.responseDTO;

public class TipoServicoResponseDTO {

    private Long id;
    private String descricao;
    private Long idProjeto;
    private Boolean servicoDeQualidade;

    public TipoServicoResponseDTO() {
    }

    public TipoServicoResponseDTO(Long id, String descricao, Long idProjeto, Boolean servicoDeQualidade) {
        this.id = id;
        this.descricao = descricao;
        this.idProjeto = idProjeto;
        this.servicoDeQualidade = servicoDeQualidade;
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

    public Boolean getServicoDeQualidade() {
        return servicoDeQualidade;
    }

    public void setServicoDeQualidade(Boolean servicoDeQualidade) {
        this.servicoDeQualidade = servicoDeQualidade;
    }
}
