/*
 * Copyright (c) 2018, 2020, Oracle and/or its affiliates. All rights reserved.
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

import java.nio.file.Path;

import jdk.internal.vm.compiler.nativeimage.c.function.CEntryPointLiteral;
import jdk.internal.vm.compiler.nativeimage.impl.ProcessPropertiesSupport;

/**
 * Utility class to get and set properties of the OS process at run time.
 *
 * @since 19.0
 */
public final class ProcessProperties {
    /**
     * Return the canonicalized absolute pathname of the executable.
     *
     * @since 19.0
     */
    public static String getExecutableName() {
        return ImageSingletons.lookup(ProcessPropertiesSupport.class).getExecutableName();
    }

    /**
     * Get the Process ID of the process executing the image.
     *
     * @since 19.0
     */
    public static long getProcessID() {
        return ImageSingletons.lookup(ProcessPropertiesSupport.class).getProcessID();
    }

    /**
     * Get the Process ID of the given process object.
     *
     * @since 19.0
     */
    public static long getProcessID(Process process) {
        return ImageSingletons.lookup(ProcessPropertiesSupport.class).getProcessID(process);
    }

    /**
     * Wait for process termination and return its exit status.
     *
     * @since 19.0
     */
    public static int waitForProcessExit(long processID) {
        return ImageSingletons.lookup(ProcessPropertiesSupport.class).waitForProcessExit(processID);
    }

    /**
     * Kills the process. Whether the process represented by the given Process ID is normally
     * terminated or not is implementation dependent.
     *
     * @since 19.0
     */
    public static boolean destroy(long processID) {
        return ImageSingletons.lookup(ProcessPropertiesSupport.class).destroy(processID);
    }

    /**
     * Kills the process forcibly. The process represented by the given Process ID is forcibly
     * terminated. Forcible process destruction is defined as the immediate termination of a
     * process, whereas normal termination allows the process to shut down cleanly.
     *
     * @since 19.0
     */
    public static boolean destroyForcibly(long processID) {
        return ImageSingletons.lookup(ProcessPropertiesSupport.class).destroyForcibly(processID);
    }

    /**
     * Tests whether the process represented by the given Process ID is alive.
     *
     * @return true if the process represented by the given Process ID object has not yet
     *         terminated.
     *
     * @since 19.0
     */
    public static boolean isAlive(long processID) {
        return ImageSingletons.lookup(ProcessPropertiesSupport.class).isAlive(processID);
    }

    /**
     * Return the path of the object file defining the symbol specified as a {@link String}
     * containing the symbol name.
     *
     * @since 19.0
     */
    public static String getObjectFile(String symbol) {
        return ImageSingletons.lookup(ProcessPropertiesSupport.class).getObjectFile(symbol);
    }

    /**
     * Return the path of the object file defining the symbol specified as a
     * {@link CEntryPointLiteral} containing a function pointer to symbol.
     *
     * @since 19.0
     */
    public static String getObjectFile(CEntryPointLiteral<?> symbol) {
        return ImageSingletons.lookup(ProcessPropertiesSupport.class).getObjectFile(symbol);
    }

    /**
     * Set the program locale.
     *
     * @since 19.0
     */
    public static String setLocale(String category, String locale) {
        return ImageSingletons.lookup(ProcessPropertiesSupport.class).setLocale(category, locale);
    }

    /**
     * Replaces the current process image with the process image specified by the given path invoked
     * with the given args. This method does not return if the call is successful.
     *
     * @since 19.0
     */
    public static void exec(Path executable, String... args) {
        ImageSingletons.lookup(ProcessPropertiesSupport.class).exec(executable, args);
    }

    /**
     * If the running image is an executable the program name that is stored in the argument vector
     * of the running process gets returned.
     *
     * @throws UnsupportedOperationException if called from a platform that does not support
     *             argument vector manipulation (Windows) or if called from a shared library image.
     *
     * @since 20.1
     */
    public static String getArgumentVectorProgramName() {
        return ImageSingletons.lookup(ProcessPropertiesSupport.class).getArgumentVectorProgramName();
    }

    /**
     * If the running image is an executable the program name that is stored in the argument vector
     * of the running process gets replaced with the given name. If the size of the argument vector
     * is too small for the given name it gets truncated so that the environment vector next to the
     * argument vector does not get corrupted.
     *
     * @return true, if given name had to be truncated to fit in the argument vector
     * @throws UnsupportedOperationException if called from a platform that does not support
     *             argument vector manipulation (Windows) or if called from a shared library image.
     *
     * @since 20.1
     */
    public static boolean setArgumentVectorProgramName(String name) {
        return ImageSingletons.lookup(ProcessPropertiesSupport.class).setArgumentVectorProgramName(name);
    }

    /**
     * If the running image is an executable the total size of the argument vector of the running
     * process gets returned.
     *
     * @return the total size of the argument vector. Returns -1 if not supported on platform or
     *         called from a shared library image.
     *
     * @since 20.1
     */
    public static int getArgumentVectorBlockSize() {
        return ImageSingletons.lookup(ProcessPropertiesSupport.class).getArgumentVectorBlockSize();
    }

    private ProcessProperties() {
    }
}
