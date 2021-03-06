/*
 * Copyright (c) 2013, 2019, Oracle and/or its affiliates. All rights reserved.
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

import jdk.internal.vm.compiler.nativeimage.c.CContext;
import jdk.internal.vm.compiler.word.PointerBase;

/**
 * Denotes Java interface that imports a C struct. The interface must extend {@link PointerBase},
 * i.e., it is a word type. There is never a Java class that implements the interface.
 * <p>
 * Field accesses are done via interface methods that are annotated with {@link CField},
 * {@link CFieldAddress}, or {@link CFieldOffset}. All calls of the interface methods are replaced
 * with the appropriate memory or address arithmetic operations. Here is an example to define a
 * complex number structure:
 *
 * {@codesnippet jdk.internal.vm.compiler.nativeimage.StackValueSnippets.ComplexValue}
 *
 * The annotated interface, or an outer class that contains the interface, must be annotated with
 * {@link CContext}. Allocate an instances of the {@code struct} either by
 * {@link jdk.internal.vm.compiler.nativeimage.StackValue#get(java.lang.Class)} or by
 * {@link jdk.internal.vm.compiler.nativeimage.UnmanagedMemory#malloc(jdk.internal.vm.compiler.word.UnsignedWord)}.
 *
 * To access an array of structs one can define a special {@code addressOf} method:
 * <p>
 *
 * {@codesnippet jdk.internal.vm.compiler.nativeimage.StackValueSnippets.IntOrDouble}
 *
 * Implementation of such method then allows one to do <em>array arithmetics</em> - e.g. obtain
 * pointer to the first element of the array and then access the others:
 * <p>
 *
 * {@codesnippet jdk.internal.vm.compiler.nativeimage.StackValueSnippets.acceptIntIntDouble}
 *
 * @since 19.0
 * @see jdk.internal.vm.compiler.nativeimage.StackValue
 * @see jdk.internal.vm.compiler.nativeimage.UnmanagedMemory
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CStruct {

    /**
     * Specifies the name of the imported C struct type. If no name is provided, the type name is
     * used as the struct name.
     *
     * @since 19.0
     */
    String value() default "";

    /**
     * If marked as incomplete, we will not try to determine the size of the struct.
     *
     * @since 19.0
     */
    boolean isIncomplete() default false;

    /**
     * Add the C "struct" keyword to the name specified in {@link #value()}.
     *
     * @since 19.0
     */
    boolean addStructKeyword() default false;
}
