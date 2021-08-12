module messagestream {
    requires messagecore;

    requires kafka.clients;
    requires spring.context;
    requires com.fasterxml.jackson.databind;

    opens br.com.messagestream.converter;

    exports br.com.messagestream.kafka;
    exports br.com.messagestream.search;
}