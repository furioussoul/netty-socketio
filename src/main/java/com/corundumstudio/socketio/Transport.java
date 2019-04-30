/**
 * Copyright 2012 Nikita Koksharov
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.corundumstudio.socketio;

import com.corundumstudio.socketio.transport.PollingTransport;
import com.corundumstudio.socketio.transport.WebSocketTransport;

/**
 * 协议
 * WEBSOCKET,POLLING
 */
public enum Transport {

    /**
     * websocket协议
     */
    WEBSOCKET(WebSocketTransport.NAME),
    /**
     * 轮询
     */
    POLLING(PollingTransport.NAME);

    private final String value;

    Transport(String value) {
        this.value = value;
    }

    public static Transport byName(String value) {
        for (Transport t : Transport.values()) {
            if (t.getValue().equals(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Can't find " + value + " transport");
    }

    public String getValue() {
        return value;
    }

}
