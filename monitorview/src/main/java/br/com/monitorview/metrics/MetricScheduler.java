package br.com.monitorview.metrics;//import br.com.monitor.br.com.monitorview.metrics.exception.MetricException;

import br.com.messagemetrics.exceptions.MetricException;
import br.com.messagemetrics.model.MetricService;
import br.com.monitorview.websocket.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MetricScheduler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MetricService metricService;

    private final Publisher publisher;

    public MetricScheduler(MetricService metricService, Publisher publisher) {
        this.metricService = metricService;
        this.publisher = publisher;
    }

    @Scheduled(fixedDelay = 60000L, initialDelay = 60000L)
    public void saveMetrics() {
        try {
            var topics = metricService.findAllMonitors();
            metricService.saveMetrics(topics);
            var metrics = metricService.findAverages(topics);
            publisher.send(metrics);
        } catch (MetricException e) {
            logger.error("{} :: {}", e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "");
        }
    }
}
