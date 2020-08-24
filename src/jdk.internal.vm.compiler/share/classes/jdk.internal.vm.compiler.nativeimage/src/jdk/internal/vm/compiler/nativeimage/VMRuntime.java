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


















package jdk.internal.vm.compiler.nativeimage;

import java.io.IOException;

import jdk.internal.vm.compiler.nativeimage.impl.HeapDumpSupport;
import jdk.internal.vm.compiler.nativeimage.impl.VMRuntimeSupport;

/**
 * Used for doing VM runtime operations.
 *
 * @since 19.0
 */
public final class VMRuntime {

    /**
     * Initializes the VM: Runs all startup hooks that were registered during image building.
     * Startup hooks usually depend on option values, so it is recommended (but not required) that
     * all option values are set before calling this method.
     * <p>
     * Invoking this method more than once has no effect, i.e., startup hooks are only executed at
     * the first invocation.
     *
     * @since 19.0
     */
    public static void initialize() {
        ImageSingletons.lookup(VMRuntimeSupport.class).executeStartupHooks();
    }

    /**
     * Shuts down the VM: Runs all shutdown hooks and waits for all finalization to complete.
     *
     * @since 19.0
     */
    public static void shutdown() {
        ImageSingletons.lookup(VMRuntimeSupport.class).shutdown();
    }

    /**
     * Dumps the heap to the {@code outputFile} file in the same format as the hprof heap dump.
     *
     * @throws UnsupportedOperationException if this operation is not supported.
     *
     * @since 20.1
     */
    public static void dumpHeap(String outputFile, boolean live) throws IOException {
        if (!ImageSingletons.contains(HeapDumpSupport.class)) {
            throw new UnsupportedOperationException();
        }
        ImageSingletons.lookup(HeapDumpSupport.class).dumpHeap(outputFile, live);
    }

    private VMRuntime() {
    }
}
