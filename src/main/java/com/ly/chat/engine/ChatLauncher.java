package com.ly.chat.engine;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatLauncher {


    public static void main(String[] args) throws InterruptedException {
        final Logger log = LoggerFactory.getLogger(ChatLauncher.class);

        Map<String,Object> multiple = new ConcurrentHashMap<>();
        final int[] receivedCount = {0};

        Configuration config = new Configuration();
        config.setHostname("172.16.75.62");
        config.setPort(9292);
        config.setBossThreads(1);
        config.setWorkerThreads(8);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                receivedCount[0]++;
                log.debug("receivedCount: " + receivedCount[0]);
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
