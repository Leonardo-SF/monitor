import br.com.monitor.metrics.exception.MetricException;
import br.com.monitor.metrics.model.Metric;
import br.com.monitor.metrics.model.MetricAverage;
import br.com.monitor.metrics.repository.MetricRepository;
import br.com.monitor.model.MessageService;
import br.com.monitor.model.Topic;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MetricService {

    private final MetricRepository metricRepository;

    private final MessageService messageService;

    public MetricService(MetricRepository metricRepository, MessageService messageService) {
        this.metricRepository = metricRepository;
        this.messageService = messageService;
    }

    public void saveMonitor(String topic) throws MetricException {
        metricRepository.saveMonitor(topic);
    }

    public void removeMonitor(String topic) throws MetricException {
        metricRepository.removeMonitor(topic);
    }

    public List<String> findAllMonitors() throws MetricException {
        return metricRepository.findAllMonitors();
    }

    public synchronized void saveMetrics(List<String> topics) throws MetricException {
        for (String topicName : topics) {
            Topic topic = messageService.getTopic(topicName);
            metricRepository.saveTopicMetrics(topic);
        }
    }

    public List<MetricAverage> findAverages(List<String> topics) throws MetricException {
        List<MetricAverage> metricAverages = new ArrayList<>(topics.size());

        for (String topic : topics) {
            metricAverages.add(metricRepository.findAverage(topic));
        }

        return metricAverages;
    }

    public List<Metric> findMetricsByTopic(String topic) throws MetricException {
        return metricRepository.findMetrics(topic);
    }

}
