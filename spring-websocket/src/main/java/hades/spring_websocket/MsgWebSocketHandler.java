package hades.spring_websocket;

import hades.spring_websocket.bean.WSMsg;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MsgWebSocketHandler extends TextWebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Set<WebSocketSession> sessionSet = Collections.synchronizedSet(new HashSet<WebSocketSession>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("ConnectionEstablished : " + session.getLocalAddress().getHostName());
        sessionSet.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("ConnectionClosed : " + session.getLocalAddress().getHostName());
        sessionSet.remove(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable t) throws Exception {
        if (t instanceof IOException) {
            logger.info("TransportError : " + session.getLocalAddress().getHostName() + " " + t.getMessage());
        } else {
            logger.info("TransportError : " + session.getLocalAddress().getHostName(), t);
        }
        if (!session.isOpen()) {
            sessionSet.remove(session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        StringBuilder sb = new StringBuilder();
        sb.append("TextMessage=").append(session.getLocalAddress().getHostName()).append(" payload=").append(payload);
        logger.info(sb.toString());

        try {
            WSMsg wsMsg = (WSMsg) JSONObject.toBean(JSONObject.fromObject(payload), WSMsg.class);
            if (WSMsg.TYPE_MSG.equals(wsMsg.getType())) {
                if (StringUtils.isEmpty(wsMsg.getFrom())) {
                    wsMsg.setFrom(session.getLocalAddress().getHostName());
                }
                String resMsg = JSONObject.fromObject(wsMsg).toString();
                for (WebSocketSession webSocketSession : sessionSet) {
                    webSocketSession.sendMessage(new TextMessage(resMsg));
                }
            } else {

            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
