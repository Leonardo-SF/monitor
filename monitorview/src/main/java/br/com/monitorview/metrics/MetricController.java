package br.com.monitorview.metrics;

import br.com.messagemetrics.exceptions.MetricException;
import br.com.messagemetrics.model.MetricAverage;
import br.com.messagemetrics.model.MetricService;
import br.com.monitorview.model.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/metric")
public class MetricController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MetricService metricService;

    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @ExceptionHandler(MetricException.class)
    public ResponseEntity<ResponseMessage<Void>> handleException(MetricException e) {
        logger.error("{} :: {}", e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "");
        return ResponseMessage.internalServerError(e);
    }

    @PostMapping(value = "/monitor", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<ResponseMessage<Void>> createMonitor(@RequestBody String topic) throws MetricException {
        metricService.saveMonitor(topic);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/monitor")
    public ResponseEntity<ResponseMessage<List<String>>> getMonitors() throws MetricException {
        var monitors = metricService.findAllMonitors();
        return ResponseMessage.ok(monitors);
    }

    @DeleteMapping(value = "/monitor", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<ResponseMessage<Void>> deleteMonitor(@RequestBody String topic) throws MetricException {
        metricService.removeMonitor(topic);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/average")
    public ResponseEntity<ResponseMessage<List<MetricAverage>>> average() throws MetricException {
        var topics = metricService.findAllMonitors();
        return ResponseMessage.ok(metricService.findAverages(topics));
    }

}
