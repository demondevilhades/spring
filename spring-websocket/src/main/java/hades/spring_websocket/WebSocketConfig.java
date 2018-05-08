package hades.spring_websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private MsgWebSocketHandler msgtWebSocketHandler = new MsgWebSocketHandler();;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        String path = "/chat";
        registry.addHandler(msgtWebSocketHandler, "/websocket" + path);
        registry.addHandler(msgtWebSocketHandler, "/sockjs" + path).withSockJS();
    }
    
    @Bean
    public MsgWebSocketHandler getMsgtWebSocketHandler(){
        return msgtWebSocketHandler;
    }
}
