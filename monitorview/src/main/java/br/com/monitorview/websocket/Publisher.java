package br.com.monitorview.websocket;

import br.com.messagemetrics.model.MetricAverage;

import java.util.List;

public interface Publisher {

    void send(List<MetricAverage> averageList);

}
