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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class for converting property file contents into command line arguments in long option name format.
 * <p>
 * Krzysztof Smigielski 2/21/2017.
 */
public class PropertiesProcessor {

    public String[] process(Path path) throws IOException {
        return Files.readAllLines(path, StandardCharsets.UTF_8).stream()
                .filter(s -> !s.isEmpty() && !s.startsWith("#") && s.contains("="))
                .map(s -> Stream.of("--" + s.substring(0, s.indexOf("=")), s.substring(s.indexOf("=") + 1)))
                .flatMap(Function.identity())
                .collect(Collectors.toList())
                .toArray(new String[]{});
    }
}
