/**
 * Copyright 2012 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.corundumstudio.socketio;


/**
 * ack回调接口
 *
 * ack触发时调用{@link #onSuccess(Object)}方法
 *
 * 客户端连接存活时，它会被客户端发送的ack触发。或者超时（超时时间内客户端没有发送ack）时触发（{@link #timeout}）。
 *
 * 每个ack对象都是新的，当{@link #onSuccess(Object)}被触发后本对象会被移除。
 *
 * @param <T> - any serializable type
 *
 * @see com.corundumstudio.socketio.VoidAckCallback
 * @see com.corundumstudio.socketio.MultiTypeAckCallback
 *
 */
public abstract class AckCallback<T> {

    protected final Class<T> resultClass;
    protected final int timeout;

    /**
     * Create AckCallback
     *
     * @param resultClass - result class
     */
    public AckCallback(Class<T> resultClass) {
        this(resultClass, -1);
    }

    /**
     * Creates AckCallback with timeout
     *
     * @param resultClass - result class
     * @param timeout - callback timeout in seconds
     */
    public AckCallback(Class<T> resultClass, int timeout) {
        this.resultClass = resultClass;
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout;
    }

    /**
     * Executes only once when acknowledgement received from client.
     *
     * @param result - object sended by client
     */
    public abstract void onSuccess(T result);

    /**
     * Invoked only once then <code>timeout</code> defined
     *
     */
    public void onTimeout() {

    }

    /**
     * Returns class of argument in {@link #onSuccess} method
     *
     * @return - result class
     */
    public Class<T> getResultClass() {
        return resultClass;
    }

}
