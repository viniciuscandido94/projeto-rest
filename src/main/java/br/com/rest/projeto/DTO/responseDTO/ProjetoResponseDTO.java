package br.com.rest.projeto.DTO.responseDTO;

public class ProjetoResponseDTO {

    private Long id;
    private String descricao;

    public ProjetoResponseDTO() {
    }

    public ProjetoResponseDTO(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
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

}
