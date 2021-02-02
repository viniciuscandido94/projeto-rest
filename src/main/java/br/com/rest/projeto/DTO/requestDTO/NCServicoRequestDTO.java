package br.com.rest.projeto.DTO.requestDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class NCServicoRequestDTO {

    private String status;
    private Integer torre;
    private PavimentoRequestDTO pavimento;
    private Integer pavimentoNumerico;
    private UnidadeRequestDTO unidade;
    private Integer unidadeNumerico;

    @NotNull
    private TipoServicoRequestDTO tipoServico;

    private String documentoPBQP;
    private Integer revisao;

    @NotBlank
    @Size(max=100)
    private String descricao;

    private FuncionarioRequestDTO funcionario;
    @NotNull
    private LocalDate dataInicio;

    @NotNull
    private LocalDate dataPrevisaoTermino;

    @NotNull
    private Integer prazo;
    @NotBlank
    private String tipoPrazo;
    @NotNull
    private Long idProjeto;
    @NotBlank
    private String tipoCadastro;
    private String prescricao;
    private String eficacia;
    private String justificativaEficacia;
    private List<String> foto;

    @NotNull
    private Boolean docQualidade;

    public NCServicoRequestDTO(String status, Integer torre, PavimentoRequestDTO pavimento, Integer pavimentoNumerico, UnidadeRequestDTO unidade, Integer unidadeNumerico, @NotNull TipoServicoRequestDTO tipoServico, String documentoPBQP, Integer revisao, @NotBlank @Size(max = 100) String descricao, FuncionarioRequestDTO funcionario, @NotNull LocalDate dataInicio, @NotNull LocalDate dataPrevisaoTermino, @NotNull Integer prazo, @NotBlank String tipoPrazo, @NotNull Long idProjeto, @NotBlank String tipoCadastro, String prescricao, String eficacia, String justificativaEficacia, List<String> foto, @NotNull Boolean docQualidade) {
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
        this.prazo = prazo;
        this.tipoPrazo = tipoPrazo;
        this.idProjeto = idProjeto;
        this.tipoCadastro = tipoCadastro;
        this.prescricao = prescricao;
        this.eficacia = eficacia;
        this.justificativaEficacia = justificativaEficacia;
        this.foto = foto;
        this.docQualidade = docQualidade;
    }

    public NCServicoRequestDTO() {
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

    public PavimentoRequestDTO getPavimento() {
        return pavimento;
    }

    public void setPavimento(PavimentoRequestDTO pavimento) {
        this.pavimento = pavimento;
    }

    public UnidadeRequestDTO getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeRequestDTO unidade) {
        this.unidade = unidade;
    }

    public TipoServicoRequestDTO getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServicoRequestDTO tipoServico) {
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

    public FuncionarioRequestDTO getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioRequestDTO funcionario) {
        this.funcionario = funcionario;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
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

    public Long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Long idProjeto) {
        this.idProjeto = idProjeto;
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

    public LocalDate getDataPrevisaoTermino() {
        return dataPrevisaoTermino;
    }

    public void setDataPrevisaoTermino(LocalDate dataPrevisaoTermino) {
        this.dataPrevisaoTermino = dataPrevisaoTermino;
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
}
