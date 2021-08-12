package br.com.monitor.model;

public class Partition {

    private Integer index;

    private Long beginningOfsset;

    private Long endOfsset;

    private Long position;

    private Long lag;

    public Partition() {}

    public Partition(Integer index, Long beginningOfsset, Long endOfsset, Long position) {
        this.index = index;
        this.beginningOfsset = beginningOfsset;
        this.endOfsset = endOfsset;
        this.position = position;
        this.lag = endOfsset - position;
    }

    public Integer getIndex() {
        return index;
    }

    public Partition setIndex(Integer index) {
        this.index = index;
        return this;
    }

    public Long getBeginningOfsset() {
        return beginningOfsset;
    }

    public Partition setBeginningOfsset(Long beginningOfsset) {
        this.beginningOfsset = beginningOfsset;
        return this;
    }

    public Long getEndOfsset() {
        return endOfsset;
    }

    public Partition setEndOfsset(Long endOfsset) {
        this.endOfsset = endOfsset;
        return this;
    }

    public Long getPosition() {
        return position;
    }

    public Partition setPosition(Long position) {
        this.position = position;
        return this;
    }

    public Long getLag() {
        return lag;
    }

    public Partition setLag(Long lag) {
        this.lag = lag;
        return this;
    }
}
