package api.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import converter.ProtocolMessageTranslator;
import functional.SafeRunner;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class KafkaExecutor implements MessageService {

    private final ObjectMapper objectMapper;
    private final ProtocolMessageTranslator protocolMessageTranslator;

    public KafkaExecutor(ObjectMapper objectMapper, ProtocolMessageTranslator protocolMessageTranslator) {
        this.objectMapper = objectMapper;
        this.protocolMessageTranslator = protocolMessageTranslator;
    }

    @Override
    public Optional<ProtocolMessage> getMessageByOffset(Topic topic, Partition partition, Long offset) {
        try (var messageFetcher = new KafkaMessageFetcher(topic, partition)) {
            var message = messageFetcher.fetch(offset);

            return SafeRunner.run(() -> objectMapper.readValue(message, ProtocolMessage.class))
                    .orElseLog()
                    .getOptional();
        }
    }

    @Override
    public Optional<ProtocolMessage> getMessageByOffset(Topic topic, Long offset) {
        for (var partition : topic.getPartitions()) {
            var message = this.getMessageByOffset(topic, partition, offset);
            if (message.isPresent()) {
                return message;
            }
        }

        return Optional.empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<String> getMessageByProtocol(Topic topic, Long protocol, SearchEngine searchEngine) {
        var translator = protocolMessageTranslator.convertToProtocol;

        for (var partition : topic.getPartitions()) {
            try (var messageFetcher = new KafkaMessageFetcher(topic, partition)) {
                var msg = (Optional<String>) searchEngine.apply(partition.getBeginningOfsset(), partition.getEndOfsset(), messageFetcher, translator, protocol, -1);
                if (msg.isPresent()) {
                    return msg;
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public Topic getTopic(String topicName) {
        try (var consumer = Kafka.getConsumer(topicName, false)) {
            return getTopic(topicName, consumer);
        }
    }

    private Topic getTopic(String topicName, KafkaConsumer<String, String> consumer) {
        var topicPartitions = getPartitionsFor(consumer, topicName);
        consumer.assign(topicPartitions);

        var topic = new Topic(topicName);

        var beginningOffset = consumer.beginningOffsets(topicPartitions);
        var endOfsset = consumer.endOffsets(topicPartitions);

        var partitions = topicPartitions.stream()
                .map(partition -> new Partition(partition.partition(),
                        beginningOffset.get(partition),
                        endOfsset.get(partition),
                        consumer.position(partition)))
                .collect(Collectors.toList());

        topic.setPartitions(partitions);

        partitions.forEach(partition -> {
            topic.setRead(topic.getRead() + partition.getPosition());
            topic.setLag(topic.getLag() + partition.getLag());
            topic.setNumberOfMessages(topic.getNumberOfMessages() + partition.getEndOfsset());
        });

        return topic;
    }

    @Override
    public List<Topic> getAllTopics() {
        var topics = new ArrayList<Topic>();
        try (var consumer = Kafka.getConsumer()) {
            consumer.listTopics().forEach((topicName, partitionTopic) -> {
                var topic = getTopic(topicName, consumer);
                topics.add(topic);
            });
        }

        return topics;
    }

    private List<TopicPartition> getPartitionsFor(KafkaConsumer<String, String> consumer, String topic) {
        var partitionInfos = consumer.partitionsFor(topic);
        return partitionInfos.stream()
                .map(info -> new TopicPartition(topic, info.partition()))
                .collect(Collectors.toList());
    }

}
