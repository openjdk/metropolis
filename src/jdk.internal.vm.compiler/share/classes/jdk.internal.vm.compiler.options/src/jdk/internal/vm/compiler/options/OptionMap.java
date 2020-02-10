/*
 * Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */


















package jdk.internal.vm.compiler.options;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Represents a mapping between {@link String} keys and values. Allows to create {@link OptionKey
 * options} to group/accumulate {@code key=value} pairs, with a common prefix; whose keys are not
 * known beforehand e.g. user defined properties.
 *
 * @param <T> the class of the map values
 *
 * @since 19.2
 */
public final class OptionMap<T> {

    private static final OptionMap<?> EMPTY = new OptionMap<>(Collections.emptyMap());

    final Map<String, T> backingMap;
    final Map<String, T> readonlyMap;

    OptionMap(Map<String, T> map) {
        this.backingMap = map;
        this.readonlyMap = Collections.unmodifiableMap(map);
    }

    /**
     * Returns an empty option map (immutable).
     *
     * @param <T> the class of the map values
     * @return an empty option map
     * @since 19.2
     */
    @SuppressWarnings("unchecked")
    public static <T> OptionMap<T> empty() {
        return (OptionMap<T>) EMPTY;
    }

    /**
     * Returns the value to which the specified key is mapped, or {@code null} if this option map
     * contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or {@code null} if this map contains
     *         no mapping for the key
     *
     * @since 19.2
     */
    public T get(String key) {
        return readonlyMap.get(key);
    }

    /**
     * Returns an unmodifiable {@link Set} view of the mappings contained in this map.
     *
     * @return a set view of the mappings contained in this map
     *
     * @since 19.2
     */
    public Set<Map.Entry<String, T>> entrySet() {
        return readonlyMap.entrySet();
    }

    /**
     * @since 19.2
     */
    @Override
    public int hashCode() {
        return readonlyMap.hashCode();
    }

    /**
     * @since 19.2
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OptionMap) {
            return readonlyMap.equals(((OptionMap<T>) obj).readonlyMap);
        }
        return false;
    }
}
