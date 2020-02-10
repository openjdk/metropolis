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


















package jdk.internal.vm.compiler.nativeimage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;

@SuppressWarnings("static-method")
public final class ImageSingletonsTest {
    public interface MyService {
        void useMyService();
    }

    @Test
    public void containsCanBeUsedInRegularJDK() throws Exception {
        assertFalse("Service isn't in the ImageSingletons", ImageSingletons.contains(MyService.class));
    }

    @Test(expected = Error.class)
    public void lookupCannotBeUsedInRegularJDK() throws Exception {
        MyService myService = ImageSingletons.lookup(MyService.class);
        fail("No service expected: " + myService);
    }

    @Test
    public void checkAndUseWorksOnRegularJDK() throws Exception {
        // BEGIN: jdk.internal.vm.compiler.nativeimage.ImageSingletonsTest
        if (ImageSingletons.contains(MyService.class)) {
            MyService myService = ImageSingletons.lookup(MyService.class);
            myService.useMyService();
        }
        // END: jdk.internal.vm.compiler.nativeimage.ImageSingletonsTest
    }
}
