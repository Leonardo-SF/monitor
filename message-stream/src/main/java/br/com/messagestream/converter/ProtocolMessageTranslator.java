package converter;

import br.com.messagecore.model.ProtocolMessage;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.function.UnaryOperator;

@Component
public class ProtocolMessageTranslator {

    private Mapper mapper;

    public ProtocolMessageTranslator(Mapper mapper) {
        this.mapper = mapper;
    }

    public final Function<String, ProtocolMessage> convert = (String message) ->
            (ProtocolMessage) mapper.convert.apply(message, ProtocolMessage.class);

    public final UnaryOperator<Comparable<?>> convertToProtocol = message ->
            ((ProtocolMessage) mapper.convert.apply(message.toString(), ProtocolMessage.class)).getProcessoId();

}
