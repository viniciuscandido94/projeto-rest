package br.com.rest.projeto.DTO.requestDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsuarioRequestDTO {

    @NotBlank
    private String nome;

    @Size(max=14)
    @NotBlank
    private String telefoneLogin;

    @NotBlank
    private String senha;

    @NotNull
    private Long empresa;

    @NotNull
    private String tipoUsuario;

    public UsuarioRequestDTO() {
    }

    public UsuarioRequestDTO(@NotBlank String nome, @NotBlank String telefoneLogin, @NotBlank String senha, @NotBlank Long empresa, @NotNull String tipoUsuario) {
        this.nome = nome;
        this.telefoneLogin = telefoneLogin;
        this.senha = senha;
        this.empresa = empresa;
        this.tipoUsuario = tipoUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefoneLogin() {
        return telefoneLogin;
    }

    public void setTelefoneLogin(String telefoneLogin) {
        this.telefoneLogin = telefoneLogin;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Long empresa) {
        this.empresa = empresa;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
