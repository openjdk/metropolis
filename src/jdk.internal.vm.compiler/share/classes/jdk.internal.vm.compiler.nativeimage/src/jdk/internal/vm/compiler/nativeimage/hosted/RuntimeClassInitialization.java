/*
 * Copyright (c) 2018, 2019, Oracle and/or its affiliates. All rights reserved.
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


















package jdk.internal.vm.compiler.nativeimage.hosted;

import jdk.internal.vm.compiler.nativeimage.ImageSingletons;
import jdk.internal.vm.compiler.nativeimage.Platform;
import jdk.internal.vm.compiler.nativeimage.Platforms;
import jdk.internal.vm.compiler.nativeimage.impl.RuntimeClassInitializationSupport;

/**
 * This class provides methods that can be called during native-image building to configure class
 * initialization behavior. By default, all JDK classes that are seen as reachable for a native
 * image are initialized during image building, i.e. the class initialization method is executed
 * during image building and is not seen as a reachable method at runtime. Application classes, on
 * the other hand, are initialized during image building if they can be proven safe. Unsafe classes,
 * e.g. ones that create threads, will be initialized at image run time.
 *
 * For classes that can't be proven safe, it is sometimes beneficial to ensure initialization during
 * image building, and for some that are safe, it is still necessary to initialize at runtime (e.g.,
 * the order of initializer execution matters).
 * <p>
 * This class provides two different registration methods: Classes registered via
 * {@link #initializeAtRunTime} are not initialized at all during image generation, and only
 * initialized at runtime, i.e., the class initializer is executed once at runtime. Classes
 * registered via {@link #initializeAtBuildTime} will be initialized during image building.
 *
 * It is also possible define initialization for whole packages with
 * {@link #initializeAtRunTime(String[])} and {@link #initializeAtBuildTime(String[])}. The rules
 * for packages can be further refined by using methods for individual classes.
 *
 * Initializing classes at runtime comes with some costs and restrictions:
 * <ul>
 * <li>The class initialization status must be checked before a static field access, static method
 * call, and object allocation of such classes. This has an impact on performance.</li>
 * <li>Instances of such classes are not allowed on the image heap, i.e., on the initial heap that
 * is part of the native executable. Otherwise instances would exist before the class is
 * initialized, which violates the class initialization specification.</li>
 * <ul>
 *
 * @since 19.0
 */
@Platforms(Platform.HOSTED_ONLY.class)
public final class RuntimeClassInitialization {

    /**
     * Registers the provided classes, and all of their subclasses, for class initialization at
     * runtime. The classes are not initialized automatically during image generation, and also must
     * not be initialized manually by the user during image generation.
     * <p>
     * Unfortunately, classes are initialized for many reasons, and it is not possible to intercept
     * class initialization and report an error at this time. If a registered class gets
     * initialized, an error can be reported only later and the user must manually debug the reason
     * for class initialization. This can be done by, e.g., setting a breakpoint in the class
     * initializer or adding debug printing (print the stack trace) in the class initializer.
     *
     * @since 19.0
     */
    public static void initializeAtRunTime(Class<?>... classes) {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        for (Class<?> aClass : classes) {
            ImageSingletons.lookup(RuntimeClassInitializationSupport.class).initializeAtRunTime(aClass, MESSAGE + getCaller(stacktrace));
        }
    }

    /**
     * Registers the provided classes as eagerly initialized during image-build time.
     * <p>
     * All static initializers of {@code classes} will be executed during image-build time and
     * static fields that are assigned values will be available at runtime. {@code static final}
     * fields will be considered as constant.
     * <p>
     * It is up to the user to ensure that this behavior makes sense and does not lead to wrong
     * application behavior.
     *
     * @since 19.0
     */
    public static void initializeAtBuildTime(Class<?>... classes) {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        for (Class<?> aClass : classes) {
            ImageSingletons.lookup(RuntimeClassInitializationSupport.class).initializeAtBuildTime(aClass, MESSAGE + getCaller(stacktrace));
        }
    }

    /**
     * Registers all classes in provided packages, and all of their subclasses, for class
     * initialization at runtime. The classes are not initialized automatically during image
     * generation, and also must not be initialized manually by the user during image generation.
     * <p>
     * Unfortunately, classes are initialized for many reasons, and it is not possible to intercept
     * class initialization and report an error at this time. If a registered class gets
     * initialized, an error can be reported only later and the user must manually debug the reason
     * for class initialization. This can be done by, e.g., setting a breakpoint in the class
     * initializer or adding debug printing (print the stack trace) in the class initializer.
     *
     * @since 19.0
     */
    public static void initializeAtRunTime(String... packages) {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        for (String aPackage : packages) {
            ImageSingletons.lookup(RuntimeClassInitializationSupport.class).initializeAtRunTime(aPackage, MESSAGE + getCaller(stacktrace));
        }
    }

    /**
     * Registers all classes in provided packages as eagerly initialized during image-build time.
     * <p>
     * All static initializers of {@code classes} will be executed during image-build time and
     * static fields that are assigned values will be available at runtime. {@code static final}
     * fields will be considered as constant.
     * <p>
     * It is up to the user to ensure that this behavior makes sense and does not lead to wrong
     * application behavior.
     *
     * @since 19.0
     */
    public static void initializeAtBuildTime(String... packages) {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        for (String aPackage : packages) {
            ImageSingletons.lookup(RuntimeClassInitializationSupport.class).initializeAtBuildTime(aPackage, MESSAGE + getCaller(stacktrace));
        }
    }

    private static String getCaller(StackTraceElement[] stackTrace) {
        StackTraceElement e = stackTrace[2];
        return e.getClassName() + "." + e.getMethodName();
    }

    private static final String MESSAGE = "from feature ";

    private RuntimeClassInitialization() {
    }
}
