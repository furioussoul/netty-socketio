package com.server.Launcher;

import com.server.AckRequest;
import com.server.Configuration;
import com.server.SocketIOClient;
import com.server.SocketIOServer;
import com.server.listener.DataListener;

import java.io.InputStream;

public class SslChatLauncher {

    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(10443);

        config.setKeyStorePassword("test1234");
        InputStream stream = SslChatLauncher.class.getResourceAsStream("/keystore.jks");
        config.setKeyStore(stream);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                server.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

}
