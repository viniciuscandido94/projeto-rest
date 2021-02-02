package br.com.rest.projeto.DTO.responseDTO;

public class LoginResponseDTO {

    private Long id;
    private String nome;
    private String telefoneLogin;
    private String token;
    private EmpresaResponseDTO empresa;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(Long id, String nome, String telefoneLogin, String token, EmpresaResponseDTO empresa) {
        this.id = id;
        this.nome = nome;
        this.telefoneLogin = telefoneLogin;
        this.token = token;
        this.empresa = empresa;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public EmpresaResponseDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaResponseDTO empresa) {
        this.empresa = empresa;
    }

    public String getTelefoneLogin() {
        return telefoneLogin;
    }

    public void setTelefoneLogin(String telefoneLogin) {
        this.telefoneLogin = telefoneLogin;
    }
}
