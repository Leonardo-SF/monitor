package br.com.monitor.messagings.kafka;

public class Topic {

    private String name;

    private Long beginningOfsset;

    private Long endOfsset;

    private Long position;

    private Integer partition;

    public Topic() {}

    public Topic(String name, Integer partition) {
        this.name = name;
        this.partition = partition;
    }

    public String getName() {
        return name;
    }

    public Topic setName(String name) {
        this.name = name;
        return this;
    }

    public Long getBeginningOfsset() {
        return beginningOfsset;
    }

    public Topic setBeginningOfsset(Long beginningOfsset) {
        this.beginningOfsset = beginningOfsset;
        return this;
    }

    public Long getEndOfsset() {
        return endOfsset;
    }

    public Topic setEndOfsset(Long endOfsset) {
        this.endOfsset = endOfsset;
        return this;
    }

    public Long getPosition() {
        return position;
    }

    public Topic setPosition(Long position) {
        this.position = position;
        return this;
    }

    public Integer getPartition() {
        return partition;
    }

    public Topic setPartition(Integer partition) {
        this.partition = partition;
        return this;
    }
}
