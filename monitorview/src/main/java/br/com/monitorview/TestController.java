package br.com.monitorview;

import br.com.messagestream.exception.MessageException;
import br.com.messagestream.model.MessageService;
import br.com.messagestream.model.ProtocolMessageWrapper;
import br.com.messagemetrics.model.MetricService;
import br.com.messagestream.model.KafkaFilter;
import br.com.monitorview.model.ResponseMessage;
import br.com.rotacore.usecase.RotaService;
import br.com.rotadomain.Rota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping
public class TestController {


    @Autowired
    private MessageService messageService;

    @Autowired
    private MetricService metricService;

    @Autowired
    private RotaService rotaService;


    @GetMapping("/home")
    public String home() throws MessageException {
        messageService.getAllTopics();
        Rota rota = rotaService.findRotaByTopic("1595876977848");
        System.out.println(rota.toString());
        return "home";
    }


    @ResponseBody
    @GetMapping
    public ResponseEntity<ResponseMessage<List<ProtocolMessageWrapper>>> teste(@RequestParam String topic, @RequestParam Integer partition, @RequestParam(required = false) Long lastIndex, @RequestParam(required = false) Integer size) throws MessageException {
        var kafkaFilter = new KafkaFilter(topic, partition);
        return ResponseMessage.ok(messageService.getMessages(kafkaFilter, lastIndex, size));
//        return "html/teste.html";
    }

}
