package br.com.monitor.messagings.kafka;

import br.com.monitor.ProtocolMessage;
import br.com.monitor.functional.SafeRunner;
import br.com.monitor.messagings.converter.ProtocolMessageConverter;
import br.com.monitor.searcher.Searcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class KafkaExecutor {

    private final ObjectMapper objectMapper;
    private final ProtocolMessageConverter protocolMessageConverter;

    public KafkaExecutor(ObjectMapper objectMapper, ProtocolMessageConverter protocolMessageConverter) {
        this.objectMapper = objectMapper;
        this.protocolMessageConverter = protocolMessageConverter;
    }

    public Optional<ProtocolMessage> getMessageByOffset(Topic topic, Long offset) {
        try (var deliverer = new KafkaDeliverer(topic)) {
            var message = deliverer.get(offset);

            return SafeRunner.run(() -> objectMapper.readValue(message, ProtocolMessage.class))
                    .orElseLog()
                    .getOptional();
        }
    }

    @SuppressWarnings("unchecked")
    public Optional<String> getMessageByProtocol(Topic topic, Long protocol, Searcher searcher) {
        try (var deliverer = new KafkaDeliverer(topic)) {
            var converter = protocolMessageConverter.convertToProtocol;
            return (Optional<String>) searcher.apply(topic.getBeginningOfsset(), topic.getEndOfsset(), deliverer, converter, protocol);
        }
    }

    public Topic getTopicByName(String topicName) {
        return this.getTopicByNameAndPartition(topicName, 0);
    }

    public Topic getTopicByNameAndPartition(String topicName, Integer partition) {
        var topic = new Topic(topicName, partition);
        try (var consumer = Kafka.getConsumer(topic, false)) {
            return getTopicByNameAndPartition(topicName, partition, consumer);
        }
    }

    private Topic getTopicByNameAndPartition(String topicName, Integer partition, KafkaConsumer<String, String> consumer) {
        var topicPartition = consumer.assignment().iterator().next();
        var topicPartitionSet = Collections.singleton(consumer.assignment().iterator().next());

        var beginningOffset = consumer.beginningOffsets(topicPartitionSet).get(topicPartition);
        var endOfsset = consumer.endOffsets(topicPartitionSet).get(topicPartition);
        var position = consumer.position(topicPartition);

        return new Topic()
                .setName(topicName)
                .setBeginningOfsset(beginningOffset)
                .setEndOfsset(endOfsset)
                .setPosition(position)
                .setPartition(partition);
    }

    public List<Topic> getAllTopics() {
        var topics = new ArrayList<Topic>();
        try (var consumer = Kafka.getConsumer()) {
            consumer.listTopics().forEach((topicName, partitionTopic) -> {
                var topic = getTopicByNameAndPartition(topicName, partitionTopic.get(0).partition(), consumer);
                topics.add(topic);
            });
        }

        return topics;
    }

}
