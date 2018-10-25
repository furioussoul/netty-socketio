package com.server.Launcher;

import com.server.AckRequest;
import com.server.Configuration;
import com.server.SocketIOClient;
import com.server.SocketIOServer;
import com.server.listener.DataListener;

import java.io.UnsupportedEncodingException;

public class BinaryEventLauncher {

    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);

        final SocketIOServer server = new SocketIOServer(config);

        server.addEventListener("msg", byte[].class, new DataListener<byte[]>() {
            @Override
            public void onData(SocketIOClient client, byte[] data, AckRequest ackRequest) {
                client.sendEvent("msg", data);
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

}
