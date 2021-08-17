package br.com.messagemetrics.service;

import br.com.messagemetrics.exceptions.MetricException;
import br.com.messagemetrics.model.Metric;
import br.com.messagemetrics.model.MetricAverage;
import br.com.messagemetrics.model.MetricService;
import br.com.messagemetrics.repository.MetricRepository;
import br.com.messagestream.exception.MessageException;
import br.com.messagestream.model.MessageService;
import br.com.messagestream.model.Topic;

import java.util.ArrayList;
import java.util.List;

public class MetricServiceImpl implements MetricService {

    private final MetricRepository metricRepository;

    private final MessageService messageService;

    public MetricServiceImpl(MetricRepository metricRepository, MessageService messageService){
        this.metricRepository = metricRepository;
        this.messageService = messageService;
    }

    @Override
    public void saveMonitor(String topic) throws MetricException {
        metricRepository.saveMonitor(topic);
    }

    @Override
    public void removeMonitor(String topic) throws MetricException {
        metricRepository.removeMonitor(topic);
    }

    @Override
    public List<String> findAllMonitors() throws MetricException {
        return metricRepository.findAllMonitors();
    }

    @Override
    public synchronized void saveMetrics(List<String> topics) throws MetricException {
        for (String topicName : topics) {
            Topic topic = null;
            try {
                topic = messageService.getTopic(topicName);
            } catch (MessageException e) {
                e.printStackTrace();
            }
            metricRepository.saveTopicMetrics(topic);
        }
    }

    @Override
    public List<MetricAverage> findAverages(List<String> topics) throws MetricException {
        List<MetricAverage> metricAverages = new ArrayList<>(topics.size());

        for (String topic : topics) {
            metricAverages.add(metricRepository.findAverage(topic));
        }

        return metricAverages;
    }

    @Override
    public List<Metric> findMetricsByTopic(String topic) throws MetricException {
        return metricRepository.findMetrics(topic);
    }

}
