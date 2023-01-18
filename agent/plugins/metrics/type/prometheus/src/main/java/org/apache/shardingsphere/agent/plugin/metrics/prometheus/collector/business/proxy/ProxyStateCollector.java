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

package org.apache.shardingsphere.agent.plugin.metrics.prometheus.collector.business.proxy;

import io.prometheus.client.Collector;
import io.prometheus.client.GaugeMetricFamily;
import org.apache.shardingsphere.agent.plugin.metrics.core.collector.MetricsCollectorRegistry;
import org.apache.shardingsphere.agent.plugin.metrics.prometheus.collector.type.PrometheusGaugeMetricFamilyCollector;
import org.apache.shardingsphere.infra.state.StateContext;
import org.apache.shardingsphere.proxy.backend.context.ProxyContext;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Proxy state collector.
 */
public final class ProxyStateCollector extends Collector {
    
    public static final String PROXY_STATE_METRIC_KEY = "proxy_state";
    
    @Override
    public List<MetricFamilySamples> collect() {
        List<MetricFamilySamples> result = new LinkedList<>();
        if (null == ProxyContext.getInstance().getContextManager()) {
            return result;
        }
        Optional<StateContext> stateContext = ProxyContext.getInstance().getStateContext();
        if (!stateContext.isPresent()) {
            return result;
        }
        PrometheusGaugeMetricFamilyCollector collector = MetricsCollectorRegistry.get(PROXY_STATE_METRIC_KEY, "Prometheus");
        collector.addMetric(Collections.emptyList(), stateContext.get().getCurrentState().ordinal());
        result.add((GaugeMetricFamily) collector.getRawMetricFamilyObject());
        return result;
    }
}
