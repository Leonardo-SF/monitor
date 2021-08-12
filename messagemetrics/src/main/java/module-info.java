module messagemetrics {

    requires messagecore;

    requires spring.context;
    requires spring.beans;

    opens br.com.messagemetrics.repository.file;

    exports br.com.messagemetrics;
    exports br.com.messagemetrics.model;
    exports br.com.messagemetrics.exceptions;
}