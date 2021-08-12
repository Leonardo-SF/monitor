package br.com.monitorview;

import br.com.messagecore.model.MessageService;
import br.com.messagemetrics.MetricService;
import br.com.messagemetrics.exceptions.MetricException;
import br.com.messagemetrics.model.MetricAverage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/")
public class TestController {


    @Autowired
    private MessageService messageService;

    @Autowired
    private MetricService metricService;

    @GetMapping
    public String teste() {
        try {
            var averages = metricService.findAverages(Collections.singletonList("1604058293499"));
            System.out.println(averages.get(0).getInputMessageAverage());
        } catch (MetricException e) {
            e.printStackTrace();
        }
        var topics = messageService.getTopic("1621601308308");
        System.out.println(topics.getName());
        return "html/teste.html";
    }

}
