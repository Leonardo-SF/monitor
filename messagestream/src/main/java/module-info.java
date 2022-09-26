module messagestream {
    requires kafka.clients;
    requires com.fasterxml.jackson.databind;
    requires org.slf4j;

    exports br.com.messagestream.application;
    exports br.com.messagestream.exception;
    exports br.com.messagestream.model;
    exports br.com.messagestream.search;
}