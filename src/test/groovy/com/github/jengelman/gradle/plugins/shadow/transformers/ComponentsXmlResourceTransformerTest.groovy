/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License") you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.jengelman.gradle.plugins.shadow.transformers

import com.github.jengelman.gradle.plugins.shadow.ShadowStats

import org.custommonkey.xmlunit.Diff
import org.custommonkey.xmlunit.XMLAssert
import org.custommonkey.xmlunit.XMLUnit
import com.github.jengelman.gradle.plugins.shadow.relocation.Relocator
import org.codehaus.plexus.util.IOUtil
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test for {@link ComponentsXmlResourceTransformer}.
 *
 * @author Brett Porter
 * @version $Id: ComponentsXmlResourceTransformerTest.java 1379994 2012-09-02 15:22:49Z hboutemy $
 *
 * Modified from org.apache.maven.plugins.shade.resource.ComponentsXmlResourceTransformerTest.java
 */
class ComponentsXmlResourceTransformerTest extends TransformerTestSupport<ComponentsXmlResourceTransformer> {
    private static ShadowStats stats

    @BeforeEach
    void setUp() {
        transformer = new ComponentsXmlResourceTransformer()
        stats = new ShadowStats()
    }

    @Test
    void testConfigurationMerging() {

        XMLUnit.setNormalizeWhitespace(true)

        transformer.transform(
                TransformerContext.builder()
                        .path("components-1.xml")
                        .inputStream(getClass().getResourceAsStream("/components-1.xml"))
                        .relocators(Collections.<Relocator> emptyList())
                        .stats(stats)
                        .build())
        transformer.transform(
                TransformerContext.builder()
                        .path("components-1.xml")
                        .inputStream(getClass().getResourceAsStream("/components-2.xml"))
                        .relocators(Collections.<Relocator> emptyList())
                        .stats(stats)
                        .build())
        Diff diff = XMLUnit.compareXML(
                IOUtil.toString(getClass().getResourceAsStream("/components-expected.xml"), "UTF-8"),
                IOUtil.toString(transformer.getTransformedResource(), "UTF-8"))
        //assertEquals( IOUtil.toString( getClass().getResourceAsStream( "/components-expected.xml" ), "UTF-8" ),
        //              IOUtil.toString( transformer.getTransformedResource(), "UTF-8" ).replaceAll("\r\n", "\n") )
        XMLAssert.assertXMLIdentical(diff, true)
    }
}
