package br.com.rotajparepository.repository.model;

import br.com.rotadomain.Rota;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "rota")
public class RotaDb {

    @Id
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

    public RotaDb() {}

    public Rota toRota() {
        Rota rota = new Rota();
        rota.setAtivo(this.getAtivo());
        rota.setDataAtualizacao(this.getDataAtualizacao());
        rota.setDescricao(this.getDescricao());
        rota.setExecutando(this.getExecutando());
        rota.setDataCriacao(this.getDataCriacao());
        rota.setExecutorId(this.getExecutorId());
        rota.setQtde(this.getQtde());
        rota.setRef(this.getRef());
        rota.setTopico(this.getTopico());
        rota.setTemMensagem(this.getTemMensagem());

        return rota;
    }

    public String getTopico() {
        return topico;
    }

    public RotaDb setTopico(String topico) {
        this.topico = topico;
        return this;
    }

    public String getRef() {
        return ref;
    }

    public RotaDb setRef(String ref) {
        this.ref = ref;
        return this;
    }

    public Boolean getExecutando() {
        return executando;
    }

    public RotaDb setExecutando(Boolean executando) {
        this.executando = executando;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public RotaDb setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public RotaDb setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public RotaDb setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
        return this;
    }

    public Boolean getTemMensagem() {
        return temMensagem;
    }

    public RotaDb setTemMensagem(Boolean temMensagem) {
        this.temMensagem = temMensagem;
        return this;
    }

    public Long getQtde() {
        return qtde;
    }

    public RotaDb setQtde(Long qtde) {
        this.qtde = qtde;
        return this;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public RotaDb setAtivo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public String getExecutorId() {
        return executorId;
    }

    public RotaDb setExecutorId(String executorId) {
        this.executorId = executorId;
        return this;
    }
}
