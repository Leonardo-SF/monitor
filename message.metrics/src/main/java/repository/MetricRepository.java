package repository;

import exceptions.MetricException;
import model.Metric;
import model.MetricAverage;

import java.util.List;

public interface MetricRepository {
    List<String> findAllMonitors() throws MetricException;
    void saveMonitor(String topic) throws MetricException;
    void removeMonitor(String topic) throws MetricException;
    void saveTopicMetrics(Topic topic) throws MetricException;
    List<Metric> findMetrics(String topic) throws MetricException;
    MetricAverage findAverage(String topic) throws MetricException;
}
