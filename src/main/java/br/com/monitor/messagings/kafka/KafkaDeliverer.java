package br.com.monitor.messagings.kafka;

import br.com.monitor.searcher.Deliverer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.io.Closeable;
import java.time.Duration;

class KafkaDeliverer implements Deliverer<String>, Closeable {

    private final KafkaConsumer<String, String> kafkaConsumer;
    private final TopicPartition topicPartition;

    public KafkaDeliverer(Topic topic) {
        this.kafkaConsumer = Kafka.getConsumer(topic, true);
        this.topicPartition = kafkaConsumer.assignment().iterator().next();
    }

    @Override
    public String get(Long offset) {
        kafkaConsumer.seek(topicPartition, offset);

        var records = kafkaConsumer.poll(Duration.ofSeconds(30));
        if (records.iterator().hasNext()) {
            return records.iterator().next().value();
        }

        return null;
    }

    @Override
    public void close() {
        if (kafkaConsumer != null)
            kafkaConsumer.close();
    }
}
