/*
 * Copyright (c) 2007, 2019, Oracle and/or its affiliates. All rights reserved.
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


















package jdk.internal.vm.compiler.nativeimage.c.function;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jdk.internal.vm.compiler.nativeimage.c.CContext;
import jdk.internal.vm.compiler.nativeimage.c.constant.CEnum;
import jdk.internal.vm.compiler.word.WordBase;

/**
 * Denotes a {@code native} method that calls directly from Java to C, without following the JNI
 * protocol. This means that there are no artificial additional parameters such as the JNI
 * environment passed, and no marshaling or processing of arguments (such as creating handles for
 * objects) is performed. If the method is non-static, the receiver will be ignored.
 * <p>
 * Parameter types and return types must not be Java reference types, only
 * {@linkplain Class#isPrimitive() primitive} Java types, {@linkplain WordBase word types} and
 * {@link CEnum} types are allowed. The representation of passed primitive values matches exactly
 * how they are specified in the Java language, for example, {@code int} as a 32-bit signed integer
 * or {@code char} as a 16-bit unsigned integer. {@code boolean} is specified as a single byte that
 * corresponds to {@code true} if non-zero, and to {@code false} if zero. If a Word value is passed
 * that points to a Java object, no guarantees are taken regarding its integrity as a pointer.
 * <p>
 * The class containing the annotated method must be annotated with {@link CContext}.
 *
 * @since 19.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CFunction {

    /**
     * Describes the thread state transition performed when the C function is invoked.
     *
     * @since 19.0
     */
    enum Transition {
        /**
         * The thread state is transitioned from Java to C, and the Java parts of the stack are made
         * walkable. The C code can block and call back to Java.
         *
         * @since 19.0
         */
        TO_NATIVE,
        /**
         * No prologue and epilogue is emitted. The C code must not block and must not call back to
         * Java. Also, long running C code delays safepoints (and therefore garbage collection) of
         * other threads until the call returns.
         *
         * @since 19.0
         */
        NO_TRANSITION,
    }

    /**
     * The symbol name to use to link this method. If no value is specified, the name of the method
     * (without name mangling or a class name prefix) is used.
     *
     * @since 19.0
     */
    String value() default "";

    /**
     * The Java-to-C thread transition code used when calling the function.
     *
     * @since 19.0
     */
    Transition transition() default Transition.TO_NATIVE;
}
