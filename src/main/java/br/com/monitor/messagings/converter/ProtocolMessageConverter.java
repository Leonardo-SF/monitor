package br.com.monitor.messagings.converter;

import br.com.monitor.ProtocolMessage;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.function.UnaryOperator;

@Component
public class ProtocolMessageConverter {

    private Mapper mapper;

    public ProtocolMessageConverter(Mapper mapper) {
        this.mapper = mapper;
    }

    public final Function<String, ProtocolMessage> convert = (String message) ->
            (ProtocolMessage) mapper.convert.apply(message, ProtocolMessage.class);

    public final UnaryOperator<Comparable<?>> convertToProtocol = message ->
            ((ProtocolMessage) mapper.convert.apply(message.toString(), ProtocolMessage.class)).getProcessoId();

}
