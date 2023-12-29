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

package org.apache.shardingsphere.sharding.api.config.rule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.shardingsphere.sharding.api.config.strategy.audit.ShardingAuditStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.keygen.KeyGenerateStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ShardingStrategyConfiguration;

/**
 * Sharding table rule configuration.、
 * 数据分片规则配置绑定类
 */
@RequiredArgsConstructor
@Getter
@Setter
public final class ShardingTableRuleConfiguration {

    /**
     * 逻辑表名称
     */
    private final String logicTable;

    /**
     * 实际数据节点
     * 由数据源名 + 表名组成（参考 Inline 语法规则）
     */
    private final String actualDataNodes;

    /**
     * 分库策略
     */
    private ShardingStrategyConfiguration databaseShardingStrategy;

    /**
     * 分表策略
     */
    private ShardingStrategyConfiguration tableShardingStrategy;

    /**
     * 分布式序列策略
     */
    private KeyGenerateStrategyConfiguration keyGenerateStrategy;

    /**
     * 分片审计策略
     */
    private ShardingAuditStrategyConfiguration auditStrategy;
}
