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
/*
 @ApiInfo(
 group="Graal SDK"
 )
 */
/**
 * The Graal-SDK native-image API allows to customize the native image generation, i.e., the
 * ahead-of-time compilation of Java code to standalone executables:
 * <ul>
 * <li>{@link jdk.internal.vm.compiler.nativeimage.hosted.Feature}s allow clients to intercept the native image
 * generation and run custom initialization code at various stages.</li>
 * <li>{@link jdk.internal.vm.compiler.nativeimage.hosted.RuntimeClassInitialization} is used to configure when
 * classes are initialized.</li>
 * <li>{@link jdk.internal.vm.compiler.nativeimage.hosted.RuntimeReflection} to register classes, methods, and
 * fields for reflection at run time.</li>
 * </ul>
 *
 * @since 19.0
 */


















package jdk.internal.vm.compiler.nativeimage.hosted;
