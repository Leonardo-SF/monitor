package br.com.monitor;

import java.util.ArrayList;
import java.util.List;

public class ProtocolMessage {
    private Long processoId;
    private Integer produtoId;
    private String versao;
    private String layout;
    private String xargs;
    private String nome;
    private String dataHoraCriacao;
    private Integer status = 100;
    private String mesRef;
    private String chave;
    List<Long> auditoriasIds = new ArrayList<>();
    private String gatilho;
    private String callback;
    private String accountKey;

    public Long getProcessoId() {
        return processoId;
    }

    public ProtocolMessage setProcessoId(Long processoId) {
        this.processoId = processoId;
        return this;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public ProtocolMessage setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
        return this;
    }

    public String getVersao() {
        return versao;
    }

    public ProtocolMessage setVersao(String versao) {
        this.versao = versao;
        return this;
    }

    public String getLayout() {
        return layout;
    }

    public ProtocolMessage setLayout(String layout) {
        this.layout = layout;
        return this;
    }

    public String getXargs() {
        return xargs;
    }

    public ProtocolMessage setXargs(String xargs) {
        this.xargs = xargs;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public ProtocolMessage setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public ProtocolMessage setDataHoraCriacao(String dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ProtocolMessage setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMesRef() {
        return mesRef;
    }

    public ProtocolMessage setMesRef(String mesRef) {
        this.mesRef = mesRef;
        return this;
    }

    public String getChave() {
        return chave;
    }

    public ProtocolMessage setChave(String chave) {
        this.chave = chave;
        return this;
    }

    public List<Long> getAuditoriasIds() {
        return auditoriasIds;
    }

    public ProtocolMessage setAuditoriasIds(List<Long> auditoriasIds) {
        this.auditoriasIds = auditoriasIds;
        return this;
    }

    public String getGatilho() {
        return gatilho;
    }

    public ProtocolMessage setGatilho(String gatilho) {
        this.gatilho = gatilho;
        return this;
    }

    public String getCallback() {
        return callback;
    }

    public ProtocolMessage setCallback(String callback) {
        this.callback = callback;
        return this;
    }

    public String getAccountKey() {
        return accountKey;
    }

    public ProtocolMessage setAccountKey(String accountKey) {
        this.accountKey = accountKey;
        return this;
    }
}
