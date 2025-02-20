/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.paimon.flink.utils;

import org.apache.flink.metrics.Gauge;
import org.apache.flink.metrics.Metric;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.runtime.metrics.groups.AbstractMetricGroup;

import java.lang.reflect.Field;
import java.util.Map;

/** Test utils for Flink's {@link Metric}s. */
public class MetricUtils {

    public static Gauge<?> getGauge(MetricGroup group, String metricName) {
        return (Gauge<?>) getMetric(group, metricName);
    }

    @SuppressWarnings("unchecked")
    private static Metric getMetric(MetricGroup group, String metricName) {
        try {
            Field field = AbstractMetricGroup.class.getDeclaredField("metrics");
            field.setAccessible(true);
            return ((Map<String, Metric>) field.get(group)).get(metricName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
