package br.com.rest.projeto.entity;

import br.com.rest.projeto.entity.entityEnum.TipoUsuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Size(max=14)
    @NotBlank
    private String telefoneLogin;

    @NotBlank
    private String senha;

    @NotNull
    @ManyToOne
    private Empresa empresa;

    private LocalDateTime ultimoLogin;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    public Usuario(@NotBlank String nome, @NotBlank String telefoneLogin, @NotBlank String senha, @NotBlank Empresa empresa, LocalDateTime ultimoLogin, @NotNull TipoUsuario tipoUsuario) {
        this.nome = nome;
        this.telefoneLogin = telefoneLogin;
        this.senha = senha;
        this.empresa = empresa;
        this.ultimoLogin = ultimoLogin;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario() {
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
