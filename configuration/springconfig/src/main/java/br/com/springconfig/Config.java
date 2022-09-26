package br.com.springconfig;

import br.com.messagemetrics.config.MetricConfig;
import br.com.messagemetrics.exceptions.FileException;
import br.com.messagemetrics.model.MetricService;
import br.com.messagestream.application.MessageApplication;
import br.com.messagestream.model.MessageService;
import br.com.rotacore.usecase.RotaService;
import br.com.rotajparepository.repository.jpa.RotaJPARepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration
@EntityScan("br.com")
@EnableJpaRepositories(basePackages = "br.com.rotajparepository.repository")
@PropertySources({
        @PropertySource("classpath:application-config.properties"),
        @PropertySource(value = "classpath:application-${spring.profiles.active}-config.properties", ignoreResourceNotFound = true)
})
public class Config {

    private final MessageApplication messageApplication;
    private final MetricConfig metricConfig;
    private final String metricsPath;

    public Config(@Value("${metrics.path}") String metricsPath) {
        System.out.println(metricsPath);
        this.messageApplication = new MessageApplication();
        this.metricConfig = new MetricConfig();
        this.metricsPath = metricsPath;
    }

    @Bean("kafkaService")
    public MessageService createKafkaService() {
        return messageApplication.createKafkaService();
    }

    @Bean
    public MetricService createMetricService(@Qualifier("kafkaService") MessageService messageService) throws FileException.FolderCreationException {
        return metricConfig.createMetricOnFileSytem(messageService, metricsPath);
    }

    @Bean
    public RotaService rotaService(RotaJPARepository rotaJPARepository) {
        return new RotaService(rotaJPARepository);
    }


}
