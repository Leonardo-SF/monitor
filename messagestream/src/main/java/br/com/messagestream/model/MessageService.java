package br.com.messagestream.model;


import br.com.messagestream.exception.MessageException;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<ProtocolMessageWrapper> getMessages(Filter filter, Long lastIndex, Integer size) throws MessageException;
    Optional<ProtocolMessageWrapper> getMessage(Filter filter) throws MessageException;
    Optional<String> getMessageByProtocol(Filter filter, SearchEngine searchEngine) throws MessageException;
    Topic getTopic(String topicName) throws MessageException;
    List<Topic> getAllTopics() throws MessageException;

    default Integer defaultMessagePullSize() {
        return 5;
    }
}
