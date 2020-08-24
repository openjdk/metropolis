/*
 * Copyright (c) 2016, 2020, Oracle and/or its affiliates. All rights reserved.
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

import jdk.internal.vm.compiler.nativeimage.impl.UnmanagedMemorySupport;
import jdk.internal.vm.compiler.word.PointerBase;
import jdk.internal.vm.compiler.word.UnsignedWord;
import jdk.internal.vm.compiler.word.WordFactory;

/**
 * Contains static methods that allow allocate/free of unmanaged memory, i.e., memory that is not
 * under the control of the garbage collector. In a typical C environment, these are the malloc/free
 * functions of the standard C library, however this class makes no assumptions or guarantees about
 * how the memory is managed. In particular, it is not allowed to free memory returned by these
 * allocation function directly using the standard C library (or vice versa).
 *
 * @since 19.0
 */
public final class UnmanagedMemory {

    private UnmanagedMemory() {
    }

    /**
     * Allocates {@code size} bytes of unmanaged memory. The content of the memory is undefined.
     * <p>
     * If {@code size} is 0, the method is allowed but not required to return the null pointer. This
     * method never returns a the null pointer, but instead throws a {@link OutOfMemoryError} when
     * allocation fails.
     *
     * @since 19.0
     */
    public static <T extends PointerBase> T malloc(UnsignedWord size) {
        T result = ImageSingletons.lookup(UnmanagedMemorySupport.class).malloc(size);
        if (result.isNull()) {
            throw new OutOfMemoryError("malloc of unmanaged memory");
        }
        return result;
    }

    /**
     * Allocates {@code size} bytes of unmanaged memory. The content of the memory is undefined.
     * <p>
     * If {@code size} is 0, the method is allowed but not required to return the null pointer. This
     * method never returns a the null pointer, but instead throws a {@link OutOfMemoryError} when
     * allocation fails.
     *
     * @since 19.0
     */
    public static <T extends PointerBase> T malloc(int size) {
        return malloc(WordFactory.unsigned(size));
    }

    /**
     * Allocates {@code size} bytes of unmanaged memory. The content of the memory is set to 0.
     * <p>
     * If {@code size} is 0, the method is allowed but not required to return the null pointer. This
     * method never returns a the null pointer, but instead throws a {@link OutOfMemoryError} when
     * allocation fails.
     *
     * @since 19.0
     */
    public static <T extends PointerBase> T calloc(UnsignedWord size) {
        T result = ImageSingletons.lookup(UnmanagedMemorySupport.class).calloc(size);
        if (result.isNull()) {
            throw new OutOfMemoryError("calloc of unmanaged memory");
        }
        return result;
    }

    /**
     * Allocates {@code size} bytes of unmanaged memory. The content of the memory is set to 0.
     * <p>
     * If {@code size} is 0, the method is allowed but not required to return the null pointer. This
     * method never returns a the null pointer, but instead throws a {@link OutOfMemoryError} when
     * allocation fails.
     *
     * @since 19.0
     */
    public static <T extends PointerBase> T calloc(int size) {
        return calloc(WordFactory.unsigned(size));
    }

    /**
     * Changes the size of the provided unmanaged memory to {@code size} bytes of unmanaged memory.
     * If the new size is larger than the old size, the content of the additional memory is
     * undefined.
     * <p>
     * If {@code size} is 0, the method is allowed but not required to return the null pointer. This
     * method never returns a the null pointer, but instead throws a {@link OutOfMemoryError} when
     * allocation fails.
     *
     * @since 19.0
     */
    public static <T extends PointerBase> T realloc(T ptr, UnsignedWord size) {
        T result = ImageSingletons.lookup(UnmanagedMemorySupport.class).realloc(ptr, size);
        if (result.isNull()) {
            throw new OutOfMemoryError("realloc of unmanaged memory");
        }
        return result;
    }

    /**
     * Frees unmanaged memory that was previously allocated using methods of this class.
     *
     * @since 19.0
     */
    public static void free(PointerBase ptr) {
        ImageSingletons.lookup(UnmanagedMemorySupport.class).free(ptr);
    }
}
