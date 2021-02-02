package br.com.rest.projeto.DTO.responseDTO;

public class EmpresaResponseDTO {

    private Long id;
    private String razaoSocial;
    private String nomeFantasia;

    public EmpresaResponseDTO() {
    }

    public EmpresaResponseDTO(Long id, String razaoSocial, String nomeFantasia) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
}
