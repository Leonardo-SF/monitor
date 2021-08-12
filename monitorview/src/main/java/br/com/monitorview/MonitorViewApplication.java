package br.com.monitorview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(proxyBeanMethods = false)
@ComponentScan(value = "br.com")
public class MonitorViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorViewApplication.class, args);
    }

}
