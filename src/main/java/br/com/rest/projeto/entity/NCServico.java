package br.com.rest.projeto.entity;

import br.com.rest.projeto.entity.entityEnum.Eficacia;
import br.com.rest.projeto.entity.entityEnum.TipoCadastro;
import br.com.rest.projeto.entity.entityEnum.StatusServico;
import br.com.rest.projeto.entity.entityEnum.TipoPrazo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "ncservicos")
public class NCServico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusServico status;

    private Integer torre;
    @ManyToOne
    private Pavimento pavimento;
    private Integer pavimentoNumerico;

    @ManyToOne
    private Unidade unidade;

    private Integer unidadeNumerico;

    @ManyToOne
    private TipoServico tipoServico;
    private String documentoPbqp;
    private Integer revisao;
    private String descricao;
    @ManyToOne
    private Funcionario responsavel;
    private LocalDate dataInicio;
    private LocalDate dataPrevisaoTermino;
    private LocalDate dataConclusao;
    private Integer prazo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoPrazo tipoPrazo;

    @ManyToOne
    private Projeto projeto;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoCadastro tipoCadastro;

    private String prescricao;

    @Enumerated(EnumType.STRING)
    private Eficacia eficacia;

    private String justificativaEficacia;
    private Boolean docQualidade;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    public NCServico() {
    }

    public NCServico(@NotNull StatusServico status, Integer torre, Pavimento pavimento, Integer pavimentoNumerico, Unidade unidade, Integer unidadeNumerico, TipoServico tipoServico, String documentoPbqp, Integer revisao, String descricao, Funcionario responsavel, LocalDate dataInicio, LocalDate dataPrevisaoTermino, LocalDate dataConclusao, Integer prazo, @NotNull TipoPrazo tipoPrazo, Projeto projeto, @NotNull TipoCadastro tipoCadastro, String prescricao, Eficacia eficacia, String justificativaEficacia, Boolean docQualidade, @NotNull Usuario usuario) {
        this.status = status;
        this.torre = torre;
        this.pavimento = pavimento;
        this.pavimentoNumerico = pavimentoNumerico;
        this.unidade = unidade;
        this.unidadeNumerico = unidadeNumerico;
        this.tipoServico = tipoServico;
        this.documentoPbqp = documentoPbqp;
        this.revisao = revisao;
        this.descricao = descricao;
        this.responsavel = responsavel;
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
        this.docQualidade = docQualidade;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public StatusServico getStatus() {
        return status;
    }

    public void setStatus(StatusServico status) {
        this.status = status;
    }

    public Integer getTorre() {
        return torre;
    }

    public void setTorre(Integer torre) {
        this.torre = torre;
    }

    public Pavimento getPavimento() {
        return pavimento;
    }

    public void setPavimento(Pavimento pavimento) {
        this.pavimento = pavimento;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getDocumentoPbqp() {
        return documentoPbqp;
    }

    public void setDocumentoPbqp(String documentoPbqp) {
        this.documentoPbqp = documentoPbqp;
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

    public Funcionario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Funcionario responsavel) {
        this.responsavel = responsavel;
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

    public LocalDate getDataPrevisaoTermino() {
        return dataPrevisaoTermino;
    }

    public void setDataPrevisaoTermino(LocalDate dataPrevisaoTermino) {
        this.dataPrevisaoTermino = dataPrevisaoTermino;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }


    public TipoPrazo getTipoPrazo() {
        return tipoPrazo;
    }

    public void setTipoPrazo(TipoPrazo tipoPrazo) {
        this.tipoPrazo = tipoPrazo;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public TipoCadastro getTipoCadastro() {
        return tipoCadastro;
    }

    public void setTipoCadastro(TipoCadastro tipoCadastro) {
        this.tipoCadastro = tipoCadastro;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }

    public Eficacia getEficacia() {
        return eficacia;
    }

    public void setEficacia(Eficacia eficacia) {
        this.eficacia = eficacia;
    }

    public String getJustificativaEficacia() {
        return justificativaEficacia;
    }

    public void setJustificativaEficacia(String justificativaEficacia) {
        this.justificativaEficacia = justificativaEficacia;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
