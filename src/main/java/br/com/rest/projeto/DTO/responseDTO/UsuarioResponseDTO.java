package br.com.rest.projeto.DTO.responseDTO;

import java.time.LocalDateTime;

public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String telefoneLogin;
    private EmpresaResponseDTO empresa;
    private LocalDateTime ultimoLogin;
    private String tipoUsuario;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(Long id, String nome, String telefoneLogin, EmpresaResponseDTO empresa, LocalDateTime ultimoLogin, String tipoUsuario) {
        this.id = id;
        this.nome = nome;
        this.telefoneLogin = telefoneLogin;
        this.empresa = empresa;
        this.ultimoLogin = ultimoLogin;
        this.tipoUsuario = tipoUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public EmpresaResponseDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaResponseDTO empresa) {
        this.empresa = empresa;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
