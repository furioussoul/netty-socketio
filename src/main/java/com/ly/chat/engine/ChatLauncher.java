package com.ly.chat.engine;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatLauncher {

    static Map<String,Object> multiple = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9292);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                server.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                List<String> token = client.getHandshakeData().getUrlParams().get("token");
                if(null != token && token.size() != 0){
                    multiple.put(token.get(0),client);
                }
                server.getBroadcastOperations().sendEvent("connect", client.getSessionId());
            }
        });

        server.start();
//
//        Thread.sleep(Integer.MAX_VALUE);
//
//        server.stop();
    }

}
