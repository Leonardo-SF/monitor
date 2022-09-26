package br.com.monitorview.websocket;

import br.com.messagemetrics.model.MetricAverage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSocketPublisher implements Publisher {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper;

    public WebSocketPublisher(SimpMessagingTemplate template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    @Override
    public void send(List<MetricAverage> averageList) {
        try {
            var msg = mapper.writeValueAsString(averageList);
            template.convertAndSend("/queue/topics", msg);
        } catch (JsonProcessingException e) {
            logger.error("Could not send message :: {} ", e.getMessage());
        }
    }

}
