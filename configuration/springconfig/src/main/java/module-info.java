module springconfig {
    requires messagestream;
    requires messagemetrics;
    requires rota.usecase;
    requires rota.jpa.repository;

    requires spring.beans;
    requires spring.context;
    requires spring.data.jpa;
    requires spring.boot.autoconfigure;

    exports br.com.springconfig;
}