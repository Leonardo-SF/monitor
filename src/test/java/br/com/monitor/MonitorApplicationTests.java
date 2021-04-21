package br.com.monitor;

import br.com.monitor.messagings.converter.ProtocolMessageConverter;
import br.com.monitor.messagings.kafka.KafkaExecutor;
import br.com.monitor.messagings.kafka.Topic;
import br.com.monitor.searcher.Search;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MonitorApplicationTests {

    @Autowired
    private KafkaExecutor kafkaExecutor;

    @Autowired
    ProtocolMessageConverter protocolMessageConverter;

    @Test
    void contextLoads() {

//        Kafka kafka = new Kafka();
        Topic topico = kafkaExecutor.getTopicByName("central");

        var msg = kafkaExecutor.getMessageByProtocol(topico, 24618582L, Search.binary);
        System.out.println(msg);


//         kafkaExecutor.getAllTopics();



//        convert(protocolMessageConverter.convert);
//
//        var c = new KafkaDeliverer<String>(null, null);
//        var a = Search.binary.apply(0, 1, c, null,0L);
//        a.isEmpty();


    }

}
