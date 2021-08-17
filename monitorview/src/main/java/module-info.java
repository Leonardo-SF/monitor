module monitorview {
    //monitor
    requires springconfig;
    requires messagestream;
    requires messagemetrics;
    requires rota.usecase;
    requires rota.domain;

    //Log4j
    requires org.slf4j;

    //Spring
    requires spring.beans;
    requires spring.context;
    requires spring.web;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.websocket;
    requires spring.messaging;

    //Jackson
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens br.com.monitorview;
    opens br.com.monitorview.model to spring.beans, com.fasterxml.jackson.databind, com.fasterxml.jackson.core;
    opens br.com.monitorview.websocket.config to spring.beans;
    opens templates; //because of thymeleaf

    exports br.com.monitorview.message to spring.beans;
    exports br.com.monitorview.metrics to spring.beans, spring.context;
    exports br.com.monitorview.websocket to spring.beans;
}