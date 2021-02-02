package br.com.rest.projeto.DTO.responseDTO;

import java.util.List;

public class NCServicoResponseDTO {

    private Long id;
    private String status;
    private Integer torre;
    private PavimentoResponseDTO pavimento;
    private Integer pavimentoNumerico;
    private UnidadeResponseDTO unidade;
    private Integer unidadeNumerico;
    private TipoServicoResponseDTO tipoServico;
    private String documentoPBQP;
    private Integer revisao;
    private String descricao;
    private FuncionarioResponseDTO funcionario;
    private String dataInicio;
    private String dataPrevisaoTermino;
    private String dataConclusao;
    private Integer prazo;
    private String tipoPrazo;
    private ProjetoResponseDTO projeto;
    private String tipoCadastro;
    private String prescricao;
    private String eficacia;
    private String justificativaEficacia;
    private List<String> foto;
    private Boolean docQualidade;
    private String telefoneUsuario;
    private String nomeUsuario;

    public NCServicoResponseDTO() {
    }

    public NCServicoResponseDTO(Long id, String status, Integer torre, PavimentoResponseDTO pavimento, Integer pavimentoNumerico, UnidadeResponseDTO unidade, Integer unidadeNumerico, TipoServicoResponseDTO tipoServico, String documentoPBQP, Integer revisao, String descricao, FuncionarioResponseDTO funcionario, String dataInicio, String dataPrevisaoTermino, String dataConclusao, Integer prazo, String tipoPrazo, ProjetoResponseDTO projeto, String tipoCadastro, String prescricao, String eficacia, String justificativaEficacia, List<String> foto, Boolean docQualidade, String telefoneUsuario, String nomeUsuario) {
        this.id = id;
        this.status = status;
        this.torre = torre;
        this.pavimento = pavimento;
        this.pavimentoNumerico = pavimentoNumerico;
        this.unidade = unidade;
        this.unidadeNumerico = unidadeNumerico;
        this.tipoServico = tipoServico;
        this.documentoPBQP = documentoPBQP;
        this.revisao = revisao;
        this.descricao = descricao;
        this.funcionario = funcionario;
        this.dataInicio = dataInicio;
        this.dataPrevisaoTermino = dataPrevisaoTermino;
        this.dataConclusao = dataConclusao;
        this.prazo = prazo;
        this.tipoPrazo = tipoPrazo;
        this.projeto = projeto;
        this.tipoCadastro = tipoCadastro;
        this.prescricao = prescricao;
        this.eficacia = eficacia;
        this.justificativaEficacia = justificativaEficacia;
        this.foto = foto;
        this.docQualidade = docQualidade;
        this.telefoneUsuario = telefoneUsuario;
        this.nomeUsuario = nomeUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTorre() {
        return torre;
    }

    public void setTorre(Integer torre) {
        this.torre = torre;
    }

    public PavimentoResponseDTO getPavimento() {
        return pavimento;
    }

    public void setPavimento(PavimentoResponseDTO pavimento) {
        this.pavimento = pavimento;
    }

    public UnidadeResponseDTO getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeResponseDTO unidade) {
        this.unidade = unidade;
    }

    public TipoServicoResponseDTO getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServicoResponseDTO tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getDocumentoPBQP() {
        return documentoPBQP;
    }

    public void setDocumentoPBQP(String documentoPBQP) {
        this.documentoPBQP = documentoPBQP;
    }

    public Integer getRevisao() {
        return revisao;
    }

    public void setRevisao(Integer revisao) {
        this.revisao = revisao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public FuncionarioResponseDTO getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioResponseDTO funcionario) {
        this.funcionario = funcionario;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataPrevisaoTermino() {
        return dataPrevisaoTermino;
    }

    public void setDataPrevisaoTermino(String dataPrevisaoTermino) {
        this.dataPrevisaoTermino = dataPrevisaoTermino;
    }

    public String getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(String dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public Integer getPrazo() {
        return prazo;
    }

    public void setPrazo(Integer prazo) {
        this.prazo = prazo;
    }

    public String getTipoPrazo() {
        return tipoPrazo;
    }

    public void setTipoPrazo(String tipoPrazo) {
        this.tipoPrazo = tipoPrazo;
    }

    public ProjetoResponseDTO getProjeto() {
        return projeto;
    }

    public void setProjeto(ProjetoResponseDTO projeto) {
        this.projeto = projeto;
    }

    public String getTipoCadastro() {
        return tipoCadastro;
    }

    public void setTipoCadastro(String tipoCadastro) {
        this.tipoCadastro = tipoCadastro;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }

    public String getEficacia() {
        return eficacia;
    }

    public void setEficacia(String eficacia) {
        this.eficacia = eficacia;
    }

    public String getJustificativaEficacia() {
        return justificativaEficacia;
    }

    public void setJustificativaEficacia(String justificativaEficacia) {
        this.justificativaEficacia = justificativaEficacia;
    }

    public List<String> getFoto() {
        return foto;
    }

    public void setFoto(List<String> foto) {
        this.foto = foto;
    }

    public Boolean getDocQualidade() {
        return docQualidade;
    }

    public void setDocQualidade(Boolean docQualidade) {
        this.docQualidade = docQualidade;
    }

    public Integer getPavimentoNumerico() {
        return pavimentoNumerico;
    }

    public void setPavimentoNumerico(Integer pavimentoNumerico) {
        this.pavimentoNumerico = pavimentoNumerico;
    }

    public Integer getUnidadeNumerico() {
        return unidadeNumerico;
    }

    public void setUnidadeNumerico(Integer unidadeNumerico) {
        this.unidadeNumerico = unidadeNumerico;
    }

    public String getTelefoneUsuario() {
        return telefoneUsuario;
    }

    public void setTelefoneUsuario(String telefoneUsuario) {
        this.telefoneUsuario = telefoneUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
}
