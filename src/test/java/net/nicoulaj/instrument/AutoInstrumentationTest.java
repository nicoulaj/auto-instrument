/*
 * Copyright (c) 2012.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.nicoulaj.instrument;

import org.testng.annotations.Test;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

import static net.nicoulaj.instrument.AutoInstrumentation.*;
import static org.testng.Assert.*;

/**
 * Tests for {@link AutoInstrumentation}.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
public class AutoInstrumentationTest {

    @Test(groups = "getPid")
    public void testGetPid() throws Exception {
        assertTrue(getPid() > 0, "getPid() returns a non strictly-positive value");
        // FIXME Actual value correctness untested
    }

    @Test(groups = "getPid")
    public void testGetPidFromRuntimeBeanName() throws Exception {
        assertTrue(getPidFromRuntimeBeanName() > 0, "getPidFromRuntimeBeanName() returns a non strictly-positive value");
        // FIXME Actual value correctness untested
    }

    @Test(groups = "getPid")
    public void testGetPidFromVMManagement() throws Exception {
        assertTrue(getPidFromVMManagement() > 0, "getPidFromVMManagement() returns a non strictly-positive value");
        // FIXME Actual value correctness untested
    }

    @Test(groups = "getPid", dependsOnMethods = {"testGetPidFromRuntimeBeanName", "testGetPidFromVMManagement"})
    public void testGetPidMethodsMatch() throws Exception {
        assertEquals(getPidFromRuntimeBeanName(), getPidFromVMManagement(), "PIDs do not match");
    }

    @Test(groups = "generateAgentJar")
    public void testGenerateAgentJar() throws Exception {
        final File agentJarFile = generateAgentJar();
        assertNotNull(agentJarFile, "agent jar file is null");
        assertTrue(agentJarFile.exists(), "agent jar file does not exist");
        assertTrue(agentJarFile.isFile(), "agent jar is not a file");
        final JarFile jarFile = new JarFile(agentJarFile);
        assertNotNull(jarFile.getManifest().getMainAttributes().get(new Attributes.Name("Agent-Class")), "manifest has no Agent-Class attribute");
        assertTrue(jarFile.getManifest().getMainAttributes().get(new Attributes.Name("Agent-Class")).equals(AutoInstrumentation.class.getName()), "manifest Agent-Class attribute value is invalid");
        assertNotNull(jarFile.getManifest().getMainAttributes().get(new Attributes.Name("Can-Redefine-Classes")), "manifest has no Can-Redefine-Classes attribute");
        assertTrue(jarFile.getManifest().getMainAttributes().get(new Attributes.Name("Can-Redefine-Classes")).equals(Boolean.TRUE.toString()), "manifest Can-Redefine-Classes attribute value is invalid");
        assertNotNull(jarFile.getManifest().getMainAttributes().get(new Attributes.Name("Can-Retransform-Classes")), "manifest has no Can-Retransform-Classes attribute");
        assertTrue(jarFile.getManifest().getMainAttributes().get(new Attributes.Name("Can-Retransform-Classes")).equals(Boolean.TRUE.toString()), "manifest Can-Retransform-Classes attribute value is invalid");
        assertNotNull(jarFile.getManifest().getMainAttributes().get(new Attributes.Name("Can-Set-Native-Method-Prefix")), "manifest has no Can-Set-Native-Method-Prefix attribute");
        assertTrue(jarFile.getManifest().getMainAttributes().get(new Attributes.Name("Can-Set-Native-Method-Prefix")).equals(Boolean.TRUE.toString()), "manifest Can-Set-Native-Method-Prefix attribute value is invalid");
    }

    @Test(dependsOnGroups = {"getPid", "generateAgentJar"}, invocationCount = 2)
    public void testGetInstrumentation() throws Exception {
        final Instrumentation instrumentation = getInstrumentation();
        assertNotNull(instrumentation, "getInstrumentation() returns null value");
    }
}
