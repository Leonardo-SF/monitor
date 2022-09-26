package br.com.messagestream.converter;

import br.com.messagestream.model.ProtocolMessage;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ProtocolMessageTranslator {

    private Mapper mapper;

    public ProtocolMessageTranslator(Mapper mapper) {
        this.mapper = mapper;
    }

    public final Function<String, ProtocolMessage> toProtocol = (String message) ->
            (ProtocolMessage) mapper.convert.apply(message, ProtocolMessage.class);

    public final UnaryOperator<Comparable<?>> toComparable = message ->
            ((ProtocolMessage) mapper.convert.apply(message.toString(), ProtocolMessage.class)).getProcessoId();

}
