package br.com.messagestream.model;

import java.util.HashMap;
import java.util.Map;

public class ProtocolMessageWrapper {

    private Long offset;
    private Long timestamp;
    private ProtocolMessage message;
    private Map<Object, Object> headers = new HashMap<>();

    public ProtocolMessageWrapper() {}

    public ProtocolMessageWrapper(Long offset, Long timestamp, ProtocolMessage message) {
        this.offset = offset;
        this.timestamp = timestamp;
        this.message = message;
    }

    public Long getOffset() {
        return offset;
    }

    public ProtocolMessageWrapper setOffset(Long offset) {
        this.offset = offset;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public ProtocolMessageWrapper setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ProtocolMessage getMessage() {
        return message;
    }

    public ProtocolMessageWrapper setMessage(ProtocolMessage message) {
        this.message = message;
        return this;
    }

    public Map<Object, Object> getHeaders() {
        return headers;
    }

    public ProtocolMessageWrapper setHeaders(Map<Object, Object> headers) {
        this.headers = headers;
        return this;
    }
}
