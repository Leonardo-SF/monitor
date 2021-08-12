package br.com.messagestream.kafka;

import functional.SafeRunner;
import functional.SupplierThrowable;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Properties;

class Kafka {
    private static final String BOOTSTRAP_SERVER = "35.184.91.170:9092";
    //    private static final String BOOTSTRAP_SERVER = "localhost:9092";
    private static final String GROUP_EXECUTOR = "executor-1";
    private static final String GROUP_DISTRIBUIDOR = "grp1";
    private static final String GROUP_MONITOR = "monitor";

    public static KafkaConsumer<String, String> getConsumer() {
        return SafeRunner.run((SupplierThrowable<Properties, Exception>) Kafka::getProperties)
                .flatMap(Kafka::create)
                .orElseLog()
                .get();
    }

    public static KafkaConsumer<String, String> getConsumer(String topicName, boolean read) {
        return SafeRunner.run(() -> Kafka.getGroup(topicName, read))
                .flatMap(Kafka::getProperties)
                .flatMap(Kafka::create)
                .orElseLog()
                .get();
    }

    private static KafkaConsumer<String, String> create(Properties properties) {
        return new KafkaConsumer<>(properties);
    }

    private static Properties getProperties() {
        return Kafka.getProperties(null);
    }

    private static Properties getProperties(String group) {
        var properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, (int) Duration.ofSeconds(30).toMillis());
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        if (group != null && !group.isEmpty())
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, group);

        return properties;
    }

    private static String getGroup(String topic, boolean read) {
        if (read)
            return GROUP_MONITOR;

        return topic.equals("central") ? GROUP_DISTRIBUIDOR : GROUP_EXECUTOR;
    }

}