/*
 * Copyright 2017 Krzysztof Smigielski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nilcaream.utilargs;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Krzysztof Smigielski 2/21/2017.
 */
public class PropertiesProcessorTest {

    private PropertiesProcessor underTest = new PropertiesProcessor();

    @Test
    public void shouldProcessFile() throws Exception {
        // given
        Path path = Paths.get(ClassLoader.getSystemResource("test.properties").toURI());

        // when
        String[] arguments = underTest.process(path);
        
        // then
        assertThat(arguments).isNotEmpty();
        
        assertThat(arguments[0]).isEqualTo("--city");
        assertThat(arguments[1]).isEqualTo("ABC City");

        assertThat(arguments[2]).isEqualTo("--random");
        assertThat(arguments[3]).isEqualTo("23838213");

        assertThat(arguments[4]).isEqualTo("--name");
        assertThat(arguments[5]).isEqualTo("Mike");
    }
}