package br.com.messagemetrics.model;

import br.com.messagemetrics.exceptions.MetricException;

import java.util.List;

public interface MetricService {
    void saveMonitor(String topic) throws MetricException;

    void removeMonitor(String topic) throws MetricException;

    List<String> findAllMonitors() throws MetricException;

    void saveMetrics(List<String> topics) throws MetricException;

    List<MetricAverage> findAverages(List<String> topics) throws MetricException;

    List<Metric> findMetricsByTopic(String topic) throws MetricException;
}
