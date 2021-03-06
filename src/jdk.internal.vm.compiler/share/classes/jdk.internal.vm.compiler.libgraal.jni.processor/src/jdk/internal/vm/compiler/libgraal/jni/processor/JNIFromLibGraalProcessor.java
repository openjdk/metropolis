/*
 * Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
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


package jdk.internal.vm.compiler.libgraal.jni.processor;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import jdk.internal.vm.compiler.libgraal.jni.annotation.JNIFromLibGraal;
import jdk.internal.vm.compiler.libgraal.jni.annotation.JNIFromLibGraal.Id;

/**
 * Processor for the {@link JNIFromLibGraal} annotation that generates code to push JNI arguments to
 * the stack and make a JNI call corresponding to a {@link Id}. This helps mitigate bugs where
 * incorrect arguments are pushed for a JNI call. Given the low level nature of
 * {@code jdk.internal.vm.compiler.nativeimage.StackValue}, it's very hard to use runtime assertion checking.
 */
@SupportedAnnotationTypes({"jdk.internal.vm.compiler.libgraal.jni.annotation.JNIFromLibGraal",
                "jdk.internal.vm.compiler.libgraal.jni.annotation.JNIFromLibGraalRepeated"})
public class JNIFromLibGraalProcessor extends AbstractFromLibGraalProcessor<Id> {

    public JNIFromLibGraalProcessor() {
        super(Id.class);
    }

    @Override
    protected boolean accept(ExecutableElement annotatedElement) {
        Element owner = annotatedElement.getEnclosingElement();
        return owner != null && !((TypeElement) owner).getQualifiedName().contentEquals("jdk.internal.vm.compiler.libgraal.jni.JNIFromLibGraalEntryPoints");
    }
}
