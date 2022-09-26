package br.com.messagestream.api.kafka.fetcher;

import br.com.messagestream.model.MessageFetcher;
import br.com.messagestream.model.Partition;
import br.com.messagestream.model.Topic;
import br.com.messagestream.api.kafka.Kafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Collections;

public class KafkaMessageRecordFetcher implements MessageFetcher<ConsumerRecord<String,String>> {

    private final KafkaConsumer<String, String> kafkaConsumer;
    private final TopicPartition topicPartition;

    public KafkaMessageRecordFetcher(Topic topic, Partition partition) {
        this.kafkaConsumer = Kafka.getConsumer(topic.getName(), true);
        this.topicPartition = new TopicPartition(topic.getName(), partition.getIndex());

        this.kafkaConsumer.assign(Collections.singleton(topicPartition));
    }

    @Override
    public ConsumerRecord<String,String> fetch(Long offset) {
        kafkaConsumer.seek(topicPartition, offset);

        var records = kafkaConsumer.poll(Duration.ofSeconds(30));
        if (records.iterator().hasNext())
            return records.iterator().next();

        return null;
    }

    @Override
    public void close() {
        if (kafkaConsumer != null)
            kafkaConsumer.close();
    }
}
