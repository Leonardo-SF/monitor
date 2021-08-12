package br.com.messagecore.model;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Optional<ProtocolMessage> getMessageByOffset(Topic topic, Partition partition, Long offset);
    Optional<ProtocolMessage> getMessageByOffset(Topic topic, Long offset);
    Optional<String> getMessageByProtocol(Topic topic, Long protocol, SearchEngine searchEngine);
    Topic getTopic(String topicName);
    List<Topic> getAllTopics();
}
