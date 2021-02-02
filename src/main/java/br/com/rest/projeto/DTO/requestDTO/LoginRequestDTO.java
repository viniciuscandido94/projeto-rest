package br.com.rest.projeto.DTO.requestDTO;

import javax.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank
    private String telefoneLogin;

    @NotBlank
    private String senha;
    private String telefoneUsuarioChatBot;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(@NotBlank String telefoneLogin, @NotBlank String senha, String telefoneUsuarioChatBot) {
        this.telefoneLogin = telefoneLogin;
        this.senha = senha;
        this.telefoneUsuarioChatBot = telefoneUsuarioChatBot;
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

    public String getTelefoneUsuarioChatBot() {
        return telefoneUsuarioChatBot;
    }

    public void setTelefoneUsuarioChatBot(String telefoneUsuarioChatBot) {
        this.telefoneUsuarioChatBot = telefoneUsuarioChatBot;
    }
}
