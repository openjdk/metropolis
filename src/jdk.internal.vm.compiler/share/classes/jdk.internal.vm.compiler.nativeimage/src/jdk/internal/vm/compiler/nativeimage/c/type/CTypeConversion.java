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


















package jdk.internal.vm.compiler.nativeimage.c.type;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import jdk.internal.vm.compiler.nativeimage.ImageSingletons;
import jdk.internal.vm.compiler.nativeimage.PinnedObject;
import jdk.internal.vm.compiler.nativeimage.impl.CTypeConversionSupport;
import jdk.internal.vm.compiler.word.PointerBase;
import jdk.internal.vm.compiler.word.UnsignedWord;
import jdk.internal.vm.compiler.word.WordFactory;

/**
 * Utility methods to convert between Java types and C types.
 *
 * @since 19.0
 */
public final class CTypeConversion {

    private CTypeConversion() {
    }

    /**
     * An auto-closable that holds a Java {@link CharSequence} as a null-terminated C char[] array.
     * The C pointer is only valid as long as the auto-closeable has not been closed.
     *
     * @since 19.0
     */
    public interface CCharPointerHolder extends AutoCloseable {
        /**
         * Returns the C pointer to the null-terminated C char[] array.
         *
         * @since 19.0
         */
        CCharPointer get();

        /**
         * Discards the C pointer.
         *
         * @since 19.0
         */
        @Override
        void close();
    }

    /**
     * Provides access to a C pointer for the provided Java String, encoded with the default
     * charset.
     *
     * @since 19.0
     */
    public static CCharPointerHolder toCString(CharSequence javaString) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).toCString(javaString);
    }

    /**
     * Copies the provide {@code javaString} into the buffer up to the provide {@code bufferSize}
     * bytes encoded with the default character set.
     * <p>
     * In case the string is larger than the {@code buffer}, the {@code bufferSize} bytes are
     * copied.
     *
     * @param javaString managed Java string
     * @param buffer to store the bytes of javaString encoded with charset
     * @param bufferSize size of the buffer
     * @return number of bytes copied to the buffer
     *
     * @since 19.0
     */
    public static UnsignedWord toCString(CharSequence javaString, CCharPointer buffer, UnsignedWord bufferSize) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).toCString(javaString, buffer, bufferSize);
    }

    /**
     * Copies the {@code javaString} into the buffer encoded with the {@code charset} character set.
     * <p>
     * In case the string is larger than the {@code buffer}, the {@code bufferSize} bytes are
     * copied.
     *
     * @param javaString managed Java string
     * @param charset desired character set for the returned string
     * @param buffer to store the bytes of javaString encoded with charset
     * @param bufferSize size of the buffer
     * @return number of bytes copied to the buffer
     *
     * @since 19.0
     */
    public static UnsignedWord toCString(CharSequence javaString, Charset charset, CCharPointer buffer, UnsignedWord bufferSize) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).toCString(javaString, charset, buffer, bufferSize);
    }

    /**
     * Decodes a 0 terminated C {@code char*} to a Java string using the platform's default charset.
     *
     * @param cString the pointer to a 0 terminated C string
     * @return a Java string
     *
     * @since 19.0
     */
    public static String toJavaString(CCharPointer cString) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).toJavaString(cString);
    }

    /**
     * Decodes a C {@code char*} of length {@code length} to a Java string using the platform's
     * default charset.
     *
     * @param cString the pointer to a 0 terminated C string
     * @return a Java string
     *
     * @since 19.0
     */
    public static String toJavaString(CCharPointer cString, UnsignedWord length) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).toJavaString(cString, length);
    }

    /**
     * Decodes a C {@code char*} of length {@code length} to a Java string using {@code charset}.
     *
     * @param cString the pointer to a 0 terminated C string
     * @return a Java string
     *
     * @since 19.0
     */
    public static String toJavaString(CCharPointer cString, UnsignedWord length, Charset charset) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).toJavaString(cString, length, charset);
    }

    /**
     * Converts a Java boolean into a C int containing boolean values.
     *
     * @param value the Java boolean value
     * @return the C boolean value
     *
     * @since 19.0
     */
    public static byte toCBoolean(boolean value) {
        return (byte) (value ? 1 : 0);
    }

    /**
     * Converts a C int containing boolean values into a Java boolean.
     *
     * @since 19.0
     */
    public static boolean toBoolean(int value) {
        return value != 0;
    }

    /**
     * Converts a C pointer into a Java boolean.
     *
     * @since 19.0
     */
    public static boolean toBoolean(PointerBase pointer) {
        return pointer.isNonNull();
    }

    /**
     * An auto-closable that holds a Java {@link CharSequence}[] array as a null-terminated array of
     * null-terminated C char[]s. The C pointers are only valid as long as the auto-closeable has
     * not been closed.
     *
     * @since 19.0
     */
    public static final class CCharPointerPointerHolder implements AutoCloseable {

        private final CTypeConversion.CCharPointerHolder[] ccpHolderArray;
        private final PinnedObject pinnedCCPArray;

        /** Construct a pinned CCharPointers[] from a CharSequence[]. */
        private CCharPointerPointerHolder(CharSequence[] csArray) {
            /* An array to hold the pinned null-terminated C strings. */
            ccpHolderArray = new CTypeConversion.CCharPointerHolder[csArray.length + 1];
            /* An array to hold the &char[0] behind the corresponding C string. */
            final CCharPointer[] ccpArray = new CCharPointer[csArray.length + 1];
            for (int i = 0; i < csArray.length; i += 1) {
                /* Null-terminate and pin each of the CharSequences. */
                ccpHolderArray[i] = CTypeConversion.toCString(csArray[i]);
                /* Save the CCharPointer of each of the CharSequences. */
                ccpArray[i] = ccpHolderArray[i].get();
            }
            /* Null-terminate the CCharPointer[]. */
            ccpArray[csArray.length] = WordFactory.nullPointer();
            /* Pin the CCharPointer[] so I can get the &ccpArray[0]. */
            pinnedCCPArray = PinnedObject.create(ccpArray);
        }

        /**
         * Returns the C pointer to pointers of null-terminated C char[] arrays.
         *
         * @since 19.0
         */
        public CCharPointerPointer get() {
            return pinnedCCPArray.addressOfArrayElement(0);
        }

        /**
         * Discards the C pointers.
         *
         * @since 19.0
         */
        @Override
        public void close() {
            /* Close the pins on each of the pinned C strings. */
            for (int i = 0; i < ccpHolderArray.length - 1; i += 1) {
                ccpHolderArray[i].close();
            }
            /* Close the pin on the pinned CCharPointer[]. */
            pinnedCCPArray.close();
        }
    }

    /**
     * Provides access to C pointers for the provided Java Strings, encoded with the default
     * charset.
     *
     * @since 19.0
     */
    public static CCharPointerPointerHolder toCStrings(CharSequence[] javaStrings) {
        return new CCharPointerPointerHolder(javaStrings);
    }

    /**
     * Creates a {@link ByteBuffer} that refers to the native memory at the specified address. The
     * passed size becomes the {@linkplain ByteBuffer#capacity capacity} of the byte buffer, and the
     * buffer's {@linkplain ByteBuffer#order() byte order} is set to
     * {@linkplain ByteOrder#nativeOrder() native byte order}. The caller is responsible for
     * ensuring that the memory can be safely accessed while the ByteBuffer is used, and for freeing
     * the memory afterwards.
     *
     * @since 19.0
     */
    public static ByteBuffer asByteBuffer(PointerBase address, int size) {
        return ImageSingletons.lookup(CTypeConversionSupport.class).asByteBuffer(address, size);
    }
}
