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

import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import org.apache.shardingsphere.infra.spi.annotation.SingletonSPI;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ServiceLoader;

/**
 * Registered ShardingSphere SPI.
 * 已经加载过的SPI实现类的抽象
 * @param <T> type of service
 */
class RegisteredShardingSphereSPI<T> {

    /**
     * SPI接口Class对象
     */
    private final Class<T> serviceInterface;

    /**
     * 已经加载的SPI实现类
     */
    private final Collection<T> services;
    
    RegisteredShardingSphereSPI(final Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
        // 检查参数是否正确（对SPI接口进行合法性检查）
        validate();
        // 加载SPI实现类
        services = load();
    }
    
    private void validate() {
        Preconditions.checkNotNull(serviceInterface, "SPI interface is null.");
        Preconditions.checkArgument(serviceInterface.isInterface(), "SPI `%s` is not an interface.", serviceInterface);
    }
    
    private Collection<T> load() {
        Collection<T> result = new LinkedList<>();
        // 利用Java原生SPI能力实现SPI加载
        for (T each : ServiceLoader.load(serviceInterface)) {
            result.add(each);
        }
        return result;
    }
    
    Collection<T> getServiceInstances() {
        // 根据是否有@SingletonSPI注解，决定是否单例加载实例
        return null == serviceInterface.getAnnotation(SingletonSPI.class) ? createNewServiceInstances() : getSingletonServiceInstances();
    }
    
    @SneakyThrows(ReflectiveOperationException.class)
    @SuppressWarnings("unchecked")
    private Collection<T> createNewServiceInstances() {
        // 重新加载一次
        Collection<T> result = new LinkedList<>();
        for (Object each : services) {
            result.add((T) each.getClass().getDeclaredConstructor().newInstance());
        }
        return result;
    }
    
    private Collection<T> getSingletonServiceInstances() {
        // 只从缓存中取
        return services;
    }
}
