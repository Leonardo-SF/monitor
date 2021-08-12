package model;

import java.math.BigDecimal;

public class MetricAverage {

    private String topic;
    private BigDecimal inputMessageAverage;
    private BigDecimal outputMessageAverage;
    private BigDecimal estimatedTimeToEnd;

    public MetricAverage() {}

    public MetricAverage(String topic, BigDecimal inputMessageAverage, BigDecimal outputMessageAverage, BigDecimal estimatedTimeToEnd) {
        this.topic = topic;
        this.inputMessageAverage = inputMessageAverage;
        this.outputMessageAverage = outputMessageAverage;
        this.estimatedTimeToEnd = estimatedTimeToEnd;
    }

    public String getTopic() {
        return topic;
    }

    public MetricAverage setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public BigDecimal getInputMessageAverage() {
        return inputMessageAverage;
    }

    public MetricAverage setInputMessageAverage(BigDecimal inputMessageAverage) {
        this.inputMessageAverage = inputMessageAverage;
        return this;
    }

    public BigDecimal getOutputMessageAverage() {
        return outputMessageAverage;
    }

    public MetricAverage setOutputMessageAverage(BigDecimal outputMessageAverage) {
        this.outputMessageAverage = outputMessageAverage;
        return this;
    }

    public BigDecimal getEstimatedTimeToEnd() {
        return estimatedTimeToEnd;
    }

    public MetricAverage setEstimatedTimeToEnd(BigDecimal estimatedTimeToEnd) {
        this.estimatedTimeToEnd = estimatedTimeToEnd;
        return this;
    }
}
