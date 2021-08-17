package br.com.rotadomain;

import java.time.LocalDateTime;

public class Rota {

    private String topico;

    private String ref;

    private Boolean executando;

    private String descricao;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    private Boolean temMensagem;

    private Long qtde;

    private Boolean ativo;

    private String executorId;

    public String getTopico() {
        return topico;
    }

    public Rota setTopico(String topico) {
        this.topico = topico;
        return this;
    }

    public String getRef() {
        return ref;
    }

    public Rota setRef(String ref) {
        this.ref = ref;
        return this;
    }

    public Boolean getExecutando() {
        return executando;
    }

    public Rota setExecutando(Boolean executando) {
        this.executando = executando;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public Rota setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Rota setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public Rota setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
        return this;
    }

    public Boolean getTemMensagem() {
        return temMensagem;
    }

    public Rota setTemMensagem(Boolean temMensagem) {
        this.temMensagem = temMensagem;
        return this;
    }

    public Long getQtde() {
        return qtde;
    }

    public Rota setQtde(Long qtde) {
        this.qtde = qtde;
        return this;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public Rota setAtivo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public String getExecutorId() {
        return executorId;
    }

    public Rota setExecutorId(String executorId) {
        this.executorId = executorId;
        return this;
    }

    @Override
    public String toString() {
        return "Rota{" +
                "topico='" + topico + '\'' +
                ", ref='" + ref + '\'' +
                ", executando=" + executando +
                ", descricao='" + descricao + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                ", temMensagem=" + temMensagem +
                ", qtde=" + qtde +
                ", ativo=" + ativo +
                ", executorId='" + executorId + '\'' +
                '}';
    }
}
