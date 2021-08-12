module monitorview {
    requires messagestream;
    requires messagemetrics;
    requires messagecore;

    requires spring.beans;
    requires spring.context;
    requires spring.web;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.websocket;
    requires spring.messaging;
    requires org.slf4j;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens br.com.monitorview;
    opens br.com.monitorview.model;

    //precisei abrir a config para reflection e exportar o packages por causa do spring
    opens br.com.monitorview.websocket.config;
    exports br.com.monitorview.message;
    exports br.com.monitorview.metrics;
    exports br.com.monitorview.websocket;
}