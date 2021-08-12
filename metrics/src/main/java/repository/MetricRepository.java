package br.com.monitor.metrics.repository;

import br.com.monitor.model.Topic;
import br.com.monitor.metrics.exception.MetricException;
import br.com.monitor.metrics.model.Metric;
import br.com.monitor.metrics.model.MetricAverage;

import java.util.List;

public interface MetricRepository {
    List<String> findAllMonitors() throws MetricException;
    void saveMonitor(String topic) throws MetricException;
    void removeMonitor(String topic) throws MetricException;
    void saveTopicMetrics(Topic topic) throws MetricException;
    List<Metric> findMetrics(String topic) throws MetricException;
    MetricAverage findAverage(String topic) throws MetricException;
}
