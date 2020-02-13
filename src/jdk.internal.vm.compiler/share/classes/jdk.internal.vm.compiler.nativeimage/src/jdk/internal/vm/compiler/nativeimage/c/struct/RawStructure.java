/*
 * Copyright (c) 2014, 2019, Oracle and/or its affiliates. All rights reserved.
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


















package jdk.internal.vm.compiler.nativeimage.c.struct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.IntUnaryOperator;

import jdk.internal.vm.compiler.word.PointerBase;

/**
 * Denotes Java interface that represents C memory, but without a {@link CStruct C struct}
 * definition. The interface must extend {@link PointerBase}, i.e., it is a word type. There is
 * never a Java class that implements the interface.
 * <p>
 * Field accesses are done via interface methods that are annotated with {@link RawField}. All calls
 * of the interface methods are replaced with the appropriate memory operations.
 * <p>
 * The layout and size of the structure is inferred from the fields defined with {@link RawField}.
 * All fields are aligned according to the field size, i.e., 8-byte types are aligned at 8-byte
 * boundaries. It is currently not possible to influence the layout of fields. However, it is
 * possible to reserve extra space at the end of the structure by specifying a
 * {@link RawStructure#sizeProvider}.
 *
 * @since 19.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RawStructure {

    /**
     * Class of a function that computes the size of the structure. The input argument of the
     * function is the size computed based on the layout of the {@link RawField fields} of the
     * structure. The returned value must not be smaller than that provided argument.
     * <p>
     * By default, the size computed based on the layout is used.
     * <p>
     * The provided class must have a no-argument constructor.
     *
     * @since 19.2
     */
    Class<? extends IntUnaryOperator> sizeProvider() default IntUnaryOperator.class;
}
