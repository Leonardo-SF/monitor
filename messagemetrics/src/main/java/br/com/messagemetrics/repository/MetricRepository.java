package br.com.messagemetrics.repository;

import br.com.messagemetrics.exceptions.MetricException;
import br.com.messagemetrics.model.Metric;
import br.com.messagemetrics.model.MetricAverage;
import br.com.messagecore.model.Topic;

import java.util.List;

public interface MetricRepository {
    List<String> findAllMonitors() throws MetricException;
    void saveMonitor(String topic) throws MetricException;
    void removeMonitor(String topic) throws MetricException;
    void saveTopicMetrics(Topic topic) throws MetricException;
    List<Metric> findMetrics(String topic) throws MetricException;
    MetricAverage findAverage(String topic) throws MetricException;
}
