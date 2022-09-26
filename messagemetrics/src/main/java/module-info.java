module messagemetrics {

    requires messagestream;

    opens br.com.messagemetrics.repository.file;

    exports br.com.messagemetrics.config;
    exports br.com.messagemetrics.model;
    exports br.com.messagemetrics.exceptions;
}