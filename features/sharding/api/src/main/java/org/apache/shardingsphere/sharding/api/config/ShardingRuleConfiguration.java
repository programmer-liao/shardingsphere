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

package org.apache.shardingsphere.sharding.api.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.infra.config.algorithm.AlgorithmConfiguration;
import org.apache.shardingsphere.infra.config.rule.function.DistributedRuleConfiguration;
import org.apache.shardingsphere.infra.config.rule.scope.DatabaseRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.cache.ShardingCacheConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingAutoTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableReferenceRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.audit.ShardingAuditStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.keygen.KeyGenerateStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ShardingStrategyConfiguration;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Sharding rule configuration.
 * 分片规则配置绑定类
 */
@Getter
@Setter
public final class ShardingRuleConfiguration implements DatabaseRuleConfiguration, DistributedRuleConfiguration {

    /**
     * 数据分片规则配置
     */
    private Collection<ShardingTableRuleConfiguration> tables = new LinkedList<>();

    /**
     * 自动分片表规则配置
     */
    private Collection<ShardingAutoTableRuleConfiguration> autoTables = new LinkedList<>();
    
    private Collection<ShardingTableReferenceRuleConfiguration> bindingTableGroups = new LinkedList<>();

    /**
     * 默认数据库分片策略
     */
    private ShardingStrategyConfiguration defaultDatabaseShardingStrategy;

    /**
     * 默认表分片策略
     */
    private ShardingStrategyConfiguration defaultTableShardingStrategy;

    /**
     * 默认的分布式序列策略
     */
    private KeyGenerateStrategyConfiguration defaultKeyGenerateStrategy;
    
    private ShardingAuditStrategyConfiguration defaultAuditStrategy;

    /**
     * 默认分片列名称
     */
    private String defaultShardingColumn;

    /**
     * 分片算法配置
     */
    private Map<String, AlgorithmConfiguration> shardingAlgorithms = new LinkedHashMap<>();

    /**
     * 分布式序列算法配置
     */
    private Map<String, AlgorithmConfiguration> keyGenerators = new LinkedHashMap<>();

    /**
     * 分片审计算法配置
     */
    private Map<String, AlgorithmConfiguration> auditors = new LinkedHashMap<>();
    
    private ShardingCacheConfiguration shardingCache;
    
    @Override
    public boolean isEmpty() {
        return tables.isEmpty() && autoTables.isEmpty() && null == defaultDatabaseShardingStrategy && null == defaultTableShardingStrategy;
    }
}
