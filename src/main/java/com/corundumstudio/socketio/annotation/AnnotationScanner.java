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
package com.corundumstudio.socketio.annotation;

import com.corundumstudio.socketio.namespace.Namespace;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/** 监听器注解扫描接口 **/
public interface AnnotationScanner {

    //获取支持被扫描的注解类型。
    Class<? extends Annotation> getScanAnnotation();

    /**
     * 在命名空间上注册监听器。
     * @param namespace 命名空间
     * @param listener 监听器
     * @param method 监听器的每个方法
     * @param annotation 监听器的回调方法的注解
     */
    void addListener(Namespace namespace, Object listener, Method method, Annotation annotation);

    /**
     * 验证监听器方法上的注解是否配置正确。
     * @param method
     * @param clazz
     */
    void validate(Method method, Class<?> clazz);
}