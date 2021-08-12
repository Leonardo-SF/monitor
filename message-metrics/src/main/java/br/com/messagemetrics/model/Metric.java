package br.com.messagecore.model;

import java.time.LocalTime;

public class Metric {

    private Long id;
    private Long totalRead;
    private LocalTime updateTime;
    private Long totalUnread;
    private Long totalIn;
    private Long totalOut;
    private Long total;

    public Metric() {}

    public Metric(Long id, Long totalRead, LocalTime updateTime, Long totalUnread, Long totalIn, Long totalOut, Long total) {
        this.id = id;
        this.totalRead = totalRead;
        this.updateTime = updateTime;
        this.totalUnread = totalUnread;
        this.totalIn = totalIn;
        this.totalOut = totalOut;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public Metric setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getTotalRead() {
        return totalRead;
    }

    public Metric setTotalRead(Long totalRead) {
        this.totalRead = totalRead;
        return this;
    }

    public LocalTime getUpdateTime() {
        return updateTime;
    }

    public Metric setUpdateTime(LocalTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Long getTotalUnread() {
        return totalUnread;
    }

    public Metric setTotalUnread(Long totalUnread) {
        this.totalUnread = totalUnread;
        return this;
    }

    public Long getTotalIn() {
        return totalIn;
    }

    public Metric setTotalIn(Long totalIn) {
        this.totalIn = totalIn;
        return this;
    }

    public Long getTotalOut() {
        return totalOut;
    }

    public Metric setTotalOut(Long totalOut) {
        this.totalOut = totalOut;
        return this;
    }

    public Long getTotal() {
        return total;
    }

    public Metric setTotal(Long total) {
        this.total = total;
        return this;
    }

    @Override
    public String toString() {
        return "Metric{" +
                "id=" + id +
                ", totalRead=" + totalRead +
                ", updateTime=" + updateTime +
                ", totalUnread=" + totalUnread +
                ", totalIn=" + totalIn +
                ", totalOut=" + totalOut +
                ", total=" + total +
                '}';
    }
}
