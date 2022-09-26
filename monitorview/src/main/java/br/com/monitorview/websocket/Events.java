package br.com.monitorview.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class Events {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        logger.info("{} desconnected", new String(event.getMessage().getPayload()));
    }

    @EventListener
    public void onConnect(SessionConnectedEvent event) {
        logger.info("{} connected", new String(event.getMessage().getPayload()));
    }
}
