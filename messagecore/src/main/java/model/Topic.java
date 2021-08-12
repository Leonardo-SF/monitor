package model;

import java.util.List;

public class Topic {

    private String name;

    private Long numberOfMessages = 0L;

    private Long read = 0L;

    private Long lag = 0L;

    private List<Partition> partitions;

    public Topic() {}

    public Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Topic setName(String name) {
        this.name = name;
        return this;
    }

    public Long getNumberOfMessages() {
        return numberOfMessages;
    }

    public Topic setNumberOfMessages(Long numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
        return this;
    }

    public List<Partition> getPartitions() {
        return partitions;
    }

    public Topic setPartitions(List<Partition> partitions) {
        this.partitions = partitions;
        return this;
    }

    public Long getRead() {
        return read;
    }

    public Topic setRead(Long read) {
        this.read = read;
        return this;
    }

    public Long getLag() {
        return lag;
    }

    public Topic setLag(Long lag) {
        this.lag = lag;
        return this;
    }
}
