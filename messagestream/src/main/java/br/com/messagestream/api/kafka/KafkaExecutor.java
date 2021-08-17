package br.com.messagestream.api.kafka;

import br.com.messagestream.exception.MessageException;
import br.com.messagestream.converter.Mapper;
import br.com.messagestream.converter.ProtocolMessageTranslator;
import br.com.messagestream.api.kafka.converter.KafkaMessageTranslator;
import br.com.messagestream.api.kafka.fetcher.KafkaMessageRecordFetcher;
import br.com.messagestream.api.kafka.fetcher.KafkaMessageStringFetcher;
import br.com.messagestream.model.*;
import br.com.messagestream.model.Filter;
import br.com.messagestream.model.MessageService;
import br.com.messagestream.model.SearchEngine;
import br.com.messagestream.model.KafkaFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class KafkaExecutor implements MessageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProtocolMessageTranslator protocolMessageTranslator;
    private final KafkaMessageTranslator kafkaMessageTranslator;

    public KafkaExecutor(ObjectMapper objectMapper) {
        this.protocolMessageTranslator = new ProtocolMessageTranslator(new Mapper(objectMapper));
        this.kafkaMessageTranslator = new KafkaMessageTranslator(protocolMessageTranslator);
    }

    @Override
    public List<ProtocolMessageWrapper> getMessages(Filter filter, Long lastIndex, Integer size) throws MessageException.InvalidFilterException, MessageException.PartitionNotFoundException, MessageException.TopicNotFoundException {
        var kafkaFilter = convertFilter(filter);

        var topic = getTopic(kafkaFilter.getTopic());
        var partition = getPartitionOf(kafkaFilter.getPartition(), topic);

        if (lastIndex == null)
            lastIndex = partition.getBeginningOfsset();

        if (size == null)
            size = this.defaultMessagePullSize();

        var messages = new ArrayList<ProtocolMessageWrapper>(size);

        try (var messageFetcher = new KafkaMessageRecordFetcher(topic, partition)) {
            for (var index = 0; index < size; index++) {
                var message = getMessageByOffset(++lastIndex, messageFetcher);
                message.ifPresent(messages::add);
            }
        }

        return messages;
    }

    @Override
    public Optional<ProtocolMessageWrapper> getMessage(Filter filter) throws MessageException.InvalidFilterException, MessageException.PartitionNotFoundException, MessageException.TopicNotFoundException {
        var kafkaFilter = convertFilter(filter);

        if (kafkaFilter.getOffset() == null) {
            throw new MessageException.InvalidFilterException("Missing offset");
        }

        var topic = getTopic(kafkaFilter.getTopic());
        var partition = getPartitionOf(kafkaFilter.getPartition(), topic);

        return getMessageByOffset(topic, partition, kafkaFilter.getOffset());
    }

    private Optional<ProtocolMessageWrapper> getMessageByOffset(Topic topic, Partition partition, Long offset) {
        try (var messageFetcher = new KafkaMessageRecordFetcher(topic, partition)) {
            return getMessageByOffset(offset, messageFetcher);
        }
    }

    private Optional<ProtocolMessageWrapper> getMessageByOffset(Long offset, KafkaMessageRecordFetcher messageFetcher) {
        var record = messageFetcher.fetch(offset);
        if (record == null) {
            return Optional.empty();
        }

        var protocolMessage = kafkaMessageTranslator.toProtocolWrapper.apply(record);
        return Optional.of(protocolMessage);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<String> getMessageByProtocol(Filter filter, SearchEngine searchEngine) throws MessageException.InvalidFilterException, MessageException.PartitionNotFoundException, MessageException.TopicNotFoundException {
        var kafkaFilter = convertFilter(filter);

        if (kafkaFilter.getProtocol() == null) {
            throw new MessageException.InvalidFilterException("Missing protocol");
        }

        var topic = getTopic(kafkaFilter.getTopic());
        var partition = getPartitionOf(kafkaFilter.getPartition(), topic);
        var translator = protocolMessageTranslator.toComparable;

        try (var messageFetcher = new KafkaMessageStringFetcher(topic, partition)) {
            return (Optional<String>) searchEngine.apply(partition.getBeginningOfsset(), partition.getEndOfsset(),
                            messageFetcher, translator, kafkaFilter.getProtocol(), -1);
        }
    }

    @Override
    public Topic getTopic(String topicName) throws MessageException.TopicNotFoundException {
        try (var consumer = Kafka.getConsumer(topicName, false)) {
            return getTopic(topicName, consumer);
        }
    }

    private Topic getTopic(String topicName, KafkaConsumer<String, String> consumer) throws MessageException.TopicNotFoundException {
        var topicPartitions = getPartitionsFor(consumer, topicName);
        consumer.assign(topicPartitions);

        var topic = new Topic(topicName);
        var beginningOffset = consumer.beginningOffsets(topicPartitions);
        var endOfsset = consumer.endOffsets(topicPartitions);
        var partitions = topicPartitions
                .stream()
                .map(partition -> new Partition(partition.partition(), beginningOffset.get(partition), endOfsset.get(partition), consumer.position(partition)))
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
            consumer.listTopics().forEach((topic, partition) -> System.out.println(partition.toString()));
//            consumer.listTopics().forEach((topicName, partitionTopic) -> {
//                Topic topic = null;
//                try {
//                    topic = getTopic(topicName, consumer);
//                } catch (MessageException.TopicNotFoundException e) {
//                    logger.error(e.getMessage());
//                }
//                topics.add(topic);
//            });
        }

        return topics;
    }

    private List<TopicPartition> getPartitionsFor(KafkaConsumer<String, String> consumer, String topic) throws MessageException.TopicNotFoundException {
        var partitionInfos = consumer.partitionsFor(topic);

        if (partitionInfos == null) {
            throw new MessageException.TopicNotFoundException(String.format("Topic %s does not exist or has expired", topic));
        }

        return partitionInfos
                .stream()
                .map(info -> new TopicPartition(topic, info.partition()))
                .collect(Collectors.toList());
    }

    private Partition getPartitionOf(Integer partition, Topic topic) throws MessageException.PartitionNotFoundException {
        return topic.getPartitions()
                .stream()
                .filter(p -> p.getIndex().equals(partition))
                .findFirst()
                .orElseThrow(() -> new MessageException.PartitionNotFoundException(String.format("Partition %s invalid for topic %s", partition, topic.getName())));

    }

    private KafkaFilter convertFilter(Filter filter) throws MessageException.InvalidFilterException {
        if (filter == null) {
            throw new MessageException.InvalidFilterException("Missing filter");
        }

        var kafkaFilter = (KafkaFilter) filter;

        if (kafkaFilter.getTopic() == null) {
            throw new MessageException.InvalidFilterException("Missing topic");
        }

        if (kafkaFilter.getPartition() == null) {
            throw new MessageException.InvalidFilterException("Missing partition");
        }

        return kafkaFilter;
    }

}
