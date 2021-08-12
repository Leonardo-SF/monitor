//import br.com.monitor.ProtocolMessage;
//import br.com.monitor.message.br.com.messagestream.search.DefaultSearchEngine;
//import br.com.monitor.br.com.messagecore.model.MessageService;
//import br.com.monitor.br.com.messagecore.model.ResponseMessage;
//import br.com.monitor.br.com.messagecore.model.Topic;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/stream")
//public class MessageController {
//
//    private final MessageService messageService;
//
//    public MessageController(MessageService messageService) {
//        this.messageService = messageService;
//    }
//
//    @GetMapping("/topic")
//    public ResponseEntity<ResponseMessage<List<Topic>>> getAllTopics() {
//        var topics = messageService.getAllTopics();
//        return ResponseMessage.ok(topics);
//    }
//
//    @GetMapping("/topic/{topicName}")
//    public ResponseEntity<ResponseMessage<Topic>> getTopic(@PathVariable String topicName) {
//        var topic = messageService.getTopic(topicName);
//        return  ResponseMessage.ok(topic);
//    }
//
//    @GetMapping("/message/offset")
//    public ResponseEntity<ResponseMessage<ProtocolMessage>> getMessageByOffset(@RequestParam String topicName,
//                                                                               @RequestParam Long offset,
//                                                                               @RequestParam(required = false) Integer partitionIndex) {
//        Topic topic = messageService.getTopic(topicName);
//
//        if (partitionIndex == null) {
//            var message = messageService.getMessageByOffset(topic, offset);
//            return message.map(msg -> ResponseMessage.ok(message.get())).orElseGet(() -> ResponseEntity.ok().build());
//        }
//
//        var partition = topic.getPartitions()
//                .stream()
//                .filter(p -> p.getIndex().equals(partitionIndex))
//                .findFirst();
//
//        if (partition.isEmpty())
//            return ResponseEntity.ok().build();
//
//        var message = messageService.getMessageByOffset(topic, partition.get(), offset);
//        return message.map(ResponseMessage::ok).orElseGet(() -> ResponseEntity.ok().build());
//    }
//
//    @GetMapping("/message/protocol")
//    public ResponseEntity<ResponseMessage<String>> getMessageByProtocol(@RequestParam String topicName,
//                                                                                 @RequestParam Long protocol,
//                                                                                 @RequestParam(required = false) String br.com.messagestream.search) {
//        Topic topic = messageService.getTopic(topicName);
//
//        var searchEngine = br.com.messagestream.search != null && br.com.messagestream.search.equals("binary") ?
//                DefaultSearchEngine.binary : DefaultSearchEngine.sequencial;
//
//        var message = messageService.getMessageByProtocol(topic, protocol, searchEngine);
//        return message.map(ResponseMessage::ok).orElseGet(() -> ResponseEntity.ok().build());
//    }
//}
