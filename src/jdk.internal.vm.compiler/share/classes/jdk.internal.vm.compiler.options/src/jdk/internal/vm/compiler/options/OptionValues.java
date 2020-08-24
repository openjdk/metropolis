/*
 * Copyright (c) 2017, 2020, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Represents a set of option values based on an {@link OptionDescriptor}.
 *
 * @since 19.0
 */
public interface OptionValues {

    /**
     * Returns all available options.
     *
     * @since 19.0
     */
    OptionDescriptors getDescriptors();

    /**
     * Sets the value of {@code optionKey} to {@code value}.
     *
     * @throws UnsupportedOperationException because this operation has been deprecated and is no
     *             longer supported, in order for OptionValues to be read-only.
     *
     * @since 19.0
     * @deprecated {@link OptionValues} should be read-only. If the value of an option needs to be
     *             altered after options are set, then the new value should be stored in the
     *             language's context or instrument fields and read from there.
     */
    @Deprecated
    <T> void set(OptionKey<T> optionKey, T value);

    /**
     * Returns the value of a given option. {@link #hasBeenSet(OptionKey)} can be used to know
     * whether the value was explicitly set, or is the {@link OptionKey#getDefaultValue() default
     * value}.
     *
     * @since 19.0
     */
    <T> T get(OptionKey<T> optionKey);

    /**
     * Determines if a value for {@code optionKey} has been set explicitly by the {@code Context} or
     * {@code Engine}, and therefore {@link #get(OptionKey)} does not call
     * {@link OptionKey#getDefaultValue()}.
     *
     * @since 19.0
     */
    boolean hasBeenSet(OptionKey<?> optionKey);

    /**
     * Determines if a value for any of the option keys in {@link #getDescriptors() option
     * descriptors} {@link #hasBeenSet(OptionKey) has been set}.
     *
     * @since 19.0
     */
    default boolean hasSetOptions() {
        for (OptionDescriptor descriptor : getDescriptors()) {
            if (hasBeenSet(descriptor.getKey())) {
                return true;
            }
        }
        return false;
    }

}
