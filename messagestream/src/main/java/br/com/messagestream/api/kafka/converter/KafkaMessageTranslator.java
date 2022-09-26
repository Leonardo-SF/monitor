package br.com.messagestream.api.kafka.converter;

import br.com.messagestream.model.ProtocolMessageWrapper;
import br.com.messagestream.converter.ProtocolMessageTranslator;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.function.Function;

public class KafkaMessageTranslator {

    private ProtocolMessageTranslator protocolMessageTranslator;

    public KafkaMessageTranslator(ProtocolMessageTranslator protocolMessageTranslator) {
        this.protocolMessageTranslator = protocolMessageTranslator;
    }

    public final Function<ConsumerRecord<String, String>, ProtocolMessageWrapper> toProtocolWrapper = (record) -> {
        var protocol = protocolMessageTranslator.toProtocol.apply(record.value());
        var wrapper = new ProtocolMessageWrapper(record.offset(), record.timestamp(), protocol);

        if (record.headers() != null) {
            while(record.headers().iterator().hasNext()) {
                var header = record.headers().iterator().next();
                wrapper.getHeaders().put(header.key(), header.value());
            }
        }

        return wrapper;
    };
}
