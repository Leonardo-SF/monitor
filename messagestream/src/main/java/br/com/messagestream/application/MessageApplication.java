package br.com.messagestream.application;

import br.com.messagestream.model.MessageService;
import br.com.messagestream.api.kafka.KafkaExecutor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageApplication {

    private final ObjectMapper objectMapper;

    public MessageApplication() {
        this.objectMapper = new ObjectMapper();
    }

    public MessageService createKafkaService() {
        return new KafkaExecutor(objectMapper);
    }

}
