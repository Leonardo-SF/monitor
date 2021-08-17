package br.com.monitorview.message;

import br.com.messagestream.exception.MessageException;
import br.com.messagestream.model.KafkaFilter;
import br.com.messagestream.model.MessageService;
import br.com.messagestream.model.ProtocolMessageWrapper;
import br.com.messagestream.model.Topic;
import br.com.messagestream.search.DefaultSearchEngine;
import br.com.monitorview.model.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stream")
public class MessageController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;

    public MessageController(@Qualifier("kafkaService") MessageService messageService) {
        this.messageService = messageService;
    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ResponseMessage<Void>> handleException(MessageException e) {
        logger.error("{} :: {}", e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "");
        return ResponseMessage.internalServerError(e);
    }

    @GetMapping("/topic")
    public ResponseEntity<ResponseMessage<List<Topic>>> getAllTopics() throws MessageException {
        var topics = messageService.getAllTopics();
        return ResponseMessage.ok(topics);
    }

    @GetMapping("/topic/{topicName}")
    public ResponseEntity<ResponseMessage<Topic>> getTopic(@PathVariable String topicName) throws MessageException {
        var topic = messageService.getTopic(topicName);
        return  ResponseMessage.ok(topic);
    }

    @GetMapping("/message/offset")
    public ResponseEntity<ResponseMessage<ProtocolMessageWrapper>> getMessageByOffset(@RequestParam String topic,
                                                                                      @RequestParam Long offset,
                                                                                      @RequestParam Integer partition) throws MessageException {
        var kafkaFilter = new KafkaFilter(topic, partition);
        kafkaFilter.setOffset(offset);
        var message = messageService.getMessage(kafkaFilter);
        return message.map(ResponseMessage::ok).orElseGet(() -> ResponseEntity.ok().build());
    }

    @GetMapping("/message/protocol")
    public ResponseEntity<ResponseMessage<String>> getMessageByProtocol(@RequestParam String topic,
                                                                        @RequestParam Integer partition,
                                                                        @RequestParam Long protocol,
                                                                        @RequestParam(required = false) String search) throws MessageException {
        var kafkaFilter = new KafkaFilter(topic, partition);
        kafkaFilter.setProtocol(protocol);

        var searchEngine = search != null && search.equals("binary") ?
                DefaultSearchEngine.binary : DefaultSearchEngine.sequencial;

        var message = messageService.getMessageByProtocol(kafkaFilter, searchEngine);
        return message.map(ResponseMessage::ok).orElseGet(() -> ResponseEntity.ok().build());
    }
}
