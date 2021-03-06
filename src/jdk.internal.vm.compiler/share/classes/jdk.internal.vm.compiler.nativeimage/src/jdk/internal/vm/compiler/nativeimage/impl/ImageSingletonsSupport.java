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


















package jdk.internal.vm.compiler.nativeimage.impl;

import jdk.internal.vm.compiler.nativeimage.ImageSingletons;
import jdk.internal.vm.compiler.nativeimage.Platform;
import jdk.internal.vm.compiler.nativeimage.Platforms;

@Platforms(Platform.HOSTED_ONLY.class)
public abstract class ImageSingletonsSupport {

    /** Implementation-specific singleton that stores the registration data. */
    private static ImageSingletonsSupport support;

    protected static void installSupport(ImageSingletonsSupport imageSingletonsSupport) {
        assert imageSingletonsSupport != null : "ImageSingletonsSupport cannot be null.";
        support = imageSingletonsSupport;
    }

    public static boolean isInstalled() {
        return support != null;
    }

    public static ImageSingletonsSupport get() {
        checkInstalled();
        return support;
    }

    private static void checkInstalled() {
        if (support == null) {
            throw new Error("The class " + ImageSingletons.class.getSimpleName() + " can only be used when building native images, i.e., when using the native-image command.");
        }
    }

    protected ImageSingletonsSupport() {
    }

    public abstract <T> void add(Class<T> key, T value);

    public abstract <T> T lookup(Class<T> key);

    public abstract boolean contains(Class<?> key);
}
