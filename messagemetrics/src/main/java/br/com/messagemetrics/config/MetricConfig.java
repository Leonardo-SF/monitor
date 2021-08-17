package br.com.messagemetrics.config;

import br.com.messagemetrics.exceptions.FileException;
import br.com.messagemetrics.model.MetricService;
import br.com.messagemetrics.repository.file.FileMetricRepository;
import br.com.messagemetrics.service.MetricServiceImpl;
import br.com.messagestream.model.MessageService;

public class MetricConfig {

    public MetricService createMetricOnFileSytem(MessageService messageService, String filePath) throws FileException.FolderCreationException {
        return new MetricServiceImpl(new FileMetricRepository(filePath), messageService);
    }

}
