open module rota.jpa.repository {

    requires rota.domain;
    requires rota.usecase;

    requires spring.beans;
    requires spring.data.jpa;
    requires spring.context;
    requires java.persistence;
    requires org.hibernate.orm.core;

    exports br.com.rotajparepository.repository.jpa to springconfig;
    exports br.com.rotajparepository.repository.model to springconfig;

}