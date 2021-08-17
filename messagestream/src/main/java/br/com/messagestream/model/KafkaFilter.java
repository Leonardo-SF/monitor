package br.com.messagestream.model;

public class KafkaFilter implements Filter {

    private String topic;
    private Integer partition;
    private Long offset;
    private Long protocol;

    public KafkaFilter() {}

    public KafkaFilter(String topic, Integer partition) {
        this.topic = topic;
        this.partition = partition;
    }

    public String getTopic() {
        return topic;
    }

    public KafkaFilter setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public Long getOffset() {
        return offset;
    }

    public KafkaFilter setOffset(Long offset) {
        this.offset = offset;
        return this;
    }

    public Long getProtocol() {
        return protocol;
    }

    public KafkaFilter setProtocol(Long protocol) {
        this.protocol = protocol;
        return this;
    }

    public Integer getPartition() {
        return partition;
    }

    public KafkaFilter setPartition(Integer partition) {
        this.partition = partition;
        return this;
    }
}
