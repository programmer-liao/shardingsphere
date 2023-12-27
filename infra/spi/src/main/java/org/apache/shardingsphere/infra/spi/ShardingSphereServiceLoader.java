/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.infra.spi;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ShardingSphere service loader.
 * SPI实现类加载器
 */
// 构造器私有化
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ShardingSphereServiceLoader {

    /**
     * 存放已经加载的SPI接口实现类集合
     * key: SPI接口class对象
     * value: SPI实现类封装
     */
    private static final Map<Class<?>, RegisteredShardingSphereSPI<?>> REGISTERED_SERVICES = new ConcurrentHashMap<>();

    /**
     * 锁对象
     */
    private static final Object LOAD_LOCK = new Object();
    
    /**
     * Get service instances.
     * 获取服务实例
     * @param serviceInterface service interface
     * @param <T> type of service interface
     * @return service instances
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> getServiceInstances(final Class<T> serviceInterface) {
        return (Collection<T>) getRegisteredSPI(serviceInterface).getServiceInstances();
    }
    
    /*
     * @see <a href="https://bugs.openjdk.java.net/browse/JDK-8161372">JDK-8161372</a>
     */
    private static <T> RegisteredShardingSphereSPI<?> getRegisteredSPI(final Class<T> serviceInterface) {
        RegisteredShardingSphereSPI<?> result = REGISTERED_SERVICES.get(serviceInterface);
        // 从缓存中取
        if (null != result) {
            return result;
        }
        synchronized (LOAD_LOCK) {
            // 加载SPI实现
            if (!REGISTERED_SERVICES.containsKey(serviceInterface)) {
                REGISTERED_SERVICES.put(serviceInterface, new RegisteredShardingSphereSPI<>(serviceInterface));
            }
        }
        // 返回已经加载的SPI对象
        return REGISTERED_SERVICES.get(serviceInterface);
    }
}
