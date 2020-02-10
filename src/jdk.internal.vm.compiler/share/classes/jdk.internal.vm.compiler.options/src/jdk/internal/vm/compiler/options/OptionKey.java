/*
 * Copyright (c) 2017, 2019, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Objects;

/**
 * Represents the option key for an option specification.
 *
 * @since 19.0
 */
public final class OptionKey<T> {

    private final OptionType<T> type;
    private final T defaultValue;

    /**
     * Constructs a new option key given a default value. Throws {@link IllegalArgumentException} if
     * no default {@link OptionType} could be {@link OptionType#defaultType(Object) resolved} for
     * the given type. The default value must not be <code>null</code>.
     *
     * @since 19.0
     */
    public OptionKey(T defaultValue) {
        Objects.requireNonNull(defaultValue);
        this.defaultValue = defaultValue;
        this.type = OptionType.defaultType(defaultValue);
        if (type == null) {
            throw new IllegalArgumentException("No default type specified for type " + defaultValue.getClass().getName() + ". Specify the option type explicitly to resolve this.");
        }
    }

    /**
     * Constructs a new option key given a default value and option key.
     *
     * @since 19.0
     */
    public OptionKey(T defaultValue, OptionType<T> type) {
        Objects.requireNonNull(type);
        this.defaultValue = defaultValue;
        this.type = type;
    }

    /**
     * Constructs a new option key to group/accumulate options with common prefixes. This type of
     * options allow to collect key=value pairs whose keys are unknown beforehand e.g. user defined
     * properties. See {@link OptionMap}.
     *
     * Example usage:
     *
     * <pre>
     * &#64;Option.Group("mylang")
     * public class MiscOptions {
     *     &#64;Option(help = &quot;User-defined properties&quot;, category = OptionCategory.USER) //
     *     public static final OptionKey&lt;OptionMap&lt;String&gt;&gt; Properties = OptionKey.mapOf(String.class);
     *
     *     ...
     * }
     * </pre>
     *
     * Properties can be set using the {@code mylang.Properties} prefix.
     *
     * <pre>
     * Context context = Context.newBuilder() //
     *                 .option(&quot;mylang.Properties.key&quot;, &quot;value&quot;) //
     *                 .option(&quot;mylang.Properties.user.name&quot;, &quot;guest&quot;) //
     *                 .build();
     * </pre>
     *
     * The option map can be consumed as follows:
     *
     * <pre>
     * OptionMap&lt;String&gt; properties = getOptions().get(MiscOptions.Properties);
     * properties.get(&quot;key&quot;);       // value
     * properties.get(&quot;user.name&quot;); // guest
     * properties.get(&quot;undefined&quot;); // null
     * </pre>
     *
     * Throws {@link IllegalArgumentException} if no default {@link OptionType} could be
     * {@link OptionType#defaultType(Object) resolved} for the value type.
     *
     * @since 19.2
     */
    public static <V> OptionKey<OptionMap<V>> mapOf(Class<V> valueClass) {
        OptionType<OptionMap<V>> type = OptionType.mapOf(valueClass);
        if (type == null) {
            throw new IllegalArgumentException("No default type specified for type " + valueClass.getName());
        }
        return new OptionKey<>(OptionMap.empty(), type);
    }

    /**
     * Returns the option type of this key.
     *
     * @since 19.0
     */
    public OptionType<T> getType() {
        return type;
    }

    /**
     * Returns the default value for this option.
     *
     * @since 19.0
     */
    public T getDefaultValue() {
        return defaultValue;
    }

    /**
     * Returns the value of this key given the {@link OptionValues values}.
     *
     * @since 19.0
     */
    public T getValue(OptionValues values) {
        return values.get(this);
    }

    /**
     * Returns <code>true</code> if a value for this key has been set for the given option values or
     * <code>false</code> if no value has been set.
     *
     * @since 19.0
     */
    public boolean hasBeenSet(OptionValues values) {
        return values.hasBeenSet(this);
    }

}
