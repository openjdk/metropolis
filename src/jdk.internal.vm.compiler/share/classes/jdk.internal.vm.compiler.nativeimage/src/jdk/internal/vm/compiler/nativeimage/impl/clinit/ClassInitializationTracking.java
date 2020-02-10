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


















package jdk.internal.vm.compiler.nativeimage.impl.clinit;

import jdk.internal.vm.compiler.nativeimage.ImageSingletons;
import jdk.internal.vm.compiler.nativeimage.impl.ImageSingletonsSupport;
import jdk.internal.vm.compiler.nativeimage.impl.RuntimeClassInitializationSupport;

public class ClassInitializationTracking {

    /**
     * Field that is true during native image generation (even during system class loading), but
     * false at run time.
     *
     * Static initializer runs on the hosting VM, setting field value to true during native image
     * generation. At run time, the substituted value is used, setting the field value to false.
     */
    public static final boolean IS_IMAGE_BUILD_TIME;

    static {
        /*
         * Prevents javac from constant folding use of this field. It is set to true by the process
         * that builds the shared library.
         */
        IS_IMAGE_BUILD_TIME = true;
    }

    /**
     * This method is called from the instrumented class initialization methods.
     */
    @SuppressWarnings({"unused", "ConstantConditions"})
    public static void reportClassInitialized(Class<?> c) {
        if (ImageSingletonsSupport.isInstalled() && ImageSingletons.contains(RuntimeClassInitializationSupport.class)) {
            RuntimeClassInitializationSupport runtimeClassInitialization = ImageSingletons.lookup(RuntimeClassInitializationSupport.class);
            runtimeClassInitialization.reportClassInitialized(c);
        }
    }

    /**
     * This method is called from the instrumented class initialization methods.
     */
    @SuppressWarnings({"unused"})
    public static void reportObjectInstantiated(Object o) {
        if (ImageSingletonsSupport.isInstalled() && ImageSingletons.contains(RuntimeClassInitializationSupport.class)) {
            RuntimeClassInitializationSupport runtimeClassInitialization = ImageSingletons.lookup(RuntimeClassInitializationSupport.class);
            runtimeClassInitialization.reportObjectInstantiated(o);
        }
    }

}
