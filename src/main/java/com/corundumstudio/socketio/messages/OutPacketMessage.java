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
package com.corundumstudio.socketio.messages;

import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.handler.ClientHead;

public class OutPacketMessage extends HttpMessage {

    private final ClientHead clientHead;
    private final Transport transport;

    public OutPacketMessage(ClientHead clientHead, Transport transport) {
        super(clientHead.getOrigin(), clientHead.getSessionId());

        this.clientHead = clientHead;
        this.transport = transport;
    }

    public Transport getTransport() {
        return transport;
    }

    public ClientHead getClientHead() {
        return clientHead;
    }

}
