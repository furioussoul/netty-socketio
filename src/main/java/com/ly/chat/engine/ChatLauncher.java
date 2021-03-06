package com.ly.chat.engine;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import com.corundumstudio.socketio.store.pubsub.ConnectMessage;
import com.corundumstudio.socketio.store.pubsub.PubSubListener;
import com.corundumstudio.socketio.store.pubsub.PubSubType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatLauncher {


    public static void main(String[] args) throws InterruptedException {
        final Logger log = LoggerFactory.getLogger(ChatLauncher.class);

        Map<String, Object> multiple = new ConcurrentHashMap<>();
        final int[] receivedCount = {0};

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9292);
        config.setBossThreads(1);
        config.setWorkerThreads(8);

        Config rConfig = new Config();
        rConfig.useSingleServer().setAddress("redis://47.111.27.208:6379");
        RedissonClient redissonClient = Redisson.create(rConfig);
        config.setStoreFactory(new RedissonStoreFactory(redissonClient));

        final SocketIOServer server = new SocketIOServer(config);

        server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                receivedCount[0]++;
                log.debug("receivedCount: " + receivedCount[0]);
                server.getBroadcastOperations().sendEvent("chatevent", data);
//                System.out.println(server.getClient(UUID.fromString(data.getSessionId())));
            }
        });

        server.getConfiguration().getStoreFactory().pubSubStore().subscribe(PubSubType.CONNECT, new PubSubListener<ConnectMessage>() {
            @Override
            public void onMessage(ConnectMessage data) {
                try {
                    System.out.println(new ObjectMapper().writeValueAsString(data));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }, ConnectMessage.class);

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                List<String> token = client.getHandshakeData().getUrlParams().get("token");
                if (null != token && token.size() != 0) {
                    multiple.put(token.get(0), client);
                }

                server.getConfiguration().getStoreFactory().pubSubStore().publish(PubSubType.CONNECT, new ConnectMessage(client.getSessionId()));

            }
        });

        server.start();
//
//        Thread.sleep(Integer.MAX_VALUE);
//
//        server.stop();
    }

}
