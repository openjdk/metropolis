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


















package jdk.internal.vm.compiler.nativeimage.impl;

import jdk.internal.vm.compiler.nativeimage.Platform;

public interface DeprecatedPlatform {

    /**
     * Supported operating system: Linux.
     *
     * @since 19.0
     */
    interface LINUX_SUBSTITUTION extends Platform {
    }

    /**
     * Supported operating system: Darwin (MacOS).
     *
     * @since 19.0
     */
    interface DARWIN_SUBSTITUTION extends Platform {
    }

    /**
     * Supported leaf platform: Linux on x86 64-bit.
     *
     * @since 19.0
     */
    class LINUX_SUBSTITUTION_AMD64 implements DeprecatedPlatform.LINUX_SUBSTITUTION, InternalPlatform.LINUX_JNI_AND_SUBSTITUTIONS, Platform.AMD64 {

        /**
         * Instantiates a marker instance of this platform.
         *
         * @since 19.0
         */
        public LINUX_SUBSTITUTION_AMD64() {
        }
    }

    /**
     * Supported leaf platform: Linux on AArch64 64-bit.
     *
     * @since 19.0
     */
    class LINUX_SUBSTITUTION_AARCH64 implements DeprecatedPlatform.LINUX_SUBSTITUTION, InternalPlatform.LINUX_JNI_AND_SUBSTITUTIONS, Platform.AARCH64 {

        /**
         * Instantiates a marker instance of this platform.
         *
         * @since 19.0
         */
        public LINUX_SUBSTITUTION_AARCH64() {
        }
    }

    /**
     * Supported leaf platform: Darwin (MacOS) on x86 64-bit.
     *
     * @since 19.0
     */
    class DARWIN_SUBSTITUTION_AMD64 implements DeprecatedPlatform.DARWIN_SUBSTITUTION, InternalPlatform.DARWIN_JNI_AND_SUBSTITUTIONS, Platform.AMD64 {

        /**
         * Instantiates a marker instance of this platform.
         *
         * @since 19.0
         */
        public DARWIN_SUBSTITUTION_AMD64() {
        }
    }

    /**
     * Supported leaf platform: Darwin (MacOS) on AArch 64-bit.
     *
     * @since 2.0
     */
    class DARWIN_SUBSTITUTION_AARCH64 implements DeprecatedPlatform.DARWIN_SUBSTITUTION, InternalPlatform.DARWIN_JNI_AND_SUBSTITUTIONS, Platform.AARCH64 {

        /**
         * Instantiates a marker instance of this platform.
         *
         * @since 2.0
         */
        public DARWIN_SUBSTITUTION_AARCH64() {
        }
    }
}
