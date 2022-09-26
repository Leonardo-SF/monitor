package br.com.monitorview.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

public class ResponseMessage<T> {

    private T body;

    private List<String> errors;

    public ResponseMessage() {}

    public ResponseMessage(T body) {
        this.body = body;
    }

    public ResponseMessage(T body, List<String> errors) {
        this.body = body;
        this.errors = errors;
    }

    public ResponseMessage(T body, String errors) {
        this.body = body;
        this.errors = Collections.singletonList(errors);
    }

    public T getBody() {
        return body;
    }

    public ResponseMessage<T> setBody(T body) {
        this.body = body;
        return this;
    }

    public List<String> getErrors() {
        return errors;
    }

    public ResponseMessage<T> setErrors(List<String> errors) {
        this.errors = errors;
        return this;
    }

    public static ResponseEntity<ResponseMessage<Void>> internalServerError(Exception e) {
        return new ResponseEntity<>(new ResponseMessage<>(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity<ResponseMessage<T>> ok(T t){
        return ResponseEntity.ok(new ResponseMessage<>(t));
    }
}
