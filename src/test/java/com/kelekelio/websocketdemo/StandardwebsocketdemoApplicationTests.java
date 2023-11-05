package com.kelekelio.websocketdemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.WsWebSocketContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class WebsocketdemoApplicationTests {

        @Autowired private ServerWebSocketHandler handler;
        @LocalServerPort
        private int port;


        @Test
        void subscribe() throws Exception {
            StandardWebSocketClient client = new StandardWebSocketClient(new WsWebSocketContainer());
            WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
            WebSocketSession session = client.execute(handler, headers, URI.create(String.format("ws://localhost:%d/websocket/%d", port, 1L))).get();

            log.info("Session Id in test: {}, URI: {}", session.getId(), session.getUri());
            Assertions.assertNotNull(session.getUri());
            Assertions.assertFalse(session.getAttributes().isEmpty());
        }
}
