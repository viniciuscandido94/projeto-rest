package br.com.rest.projeto.DTO.requestDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class FuncionarioRequestDTO {

    private Long id; // Apenas para o cadastro principal de servico.

    @NotBlank
    @Size(max=100)
    private String nome;

    @NotBlank
    @Size(max=14)
    private String telefone;

    public FuncionarioRequestDTO(@NotBlank @Size(max = 100) String nome, @NotBlank @Size(max = 14) String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public FuncionarioRequestDTO() {
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

}
