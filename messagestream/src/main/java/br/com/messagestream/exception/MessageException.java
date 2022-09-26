package br.com.messagestream.exception;

public class MessageException extends Exception {

    private MessageException(String message) {
        super(message);
    }

    private MessageException(String message, Exception e) {
        super(message, e);
    }

    public static class InvalidFilterException extends MessageException {
        public InvalidFilterException(String message) {
            super(message);
        }
    }

    public static class PartitionNotFoundException extends MessageException {
        public PartitionNotFoundException(String message) {
            super(message);
        }
    }

    public static class TopicNotFoundException extends MessageException {
        public TopicNotFoundException(String message) {
            super(message);
        }
    }
}
