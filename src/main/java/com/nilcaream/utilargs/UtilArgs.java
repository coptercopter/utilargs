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

import com.nilcaream.utilargs.core.StaticValueOfBinder;
import com.nilcaream.utilargs.core.StringConstructorBinder;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Main, single-use, stateful class for processing command line arguments and automatic binding
 * to user-provided object. Uses predefined set of {@link com.nilcaream.utilargs.core.ArgumentBinder}
 * instances.
 * <p/>
 * For more control over the binding process consider using {@link ArgumentProcessor}.
 * <p/>
 * Krzysztof Smigielski 10/28/12 7:29 PM
 *
 * @see <a href="http://pubs.opengroup.org/onlinepubs/9699919799/basedefs/V1_chap12.html">http://pubs.opengroup.org/onlinepubs/9699919799/basedefs/V1_chap12.html</a>
 */
public class UtilArgs {

    private String[] arguments;
    private Object wrapper;
    private ArgumentProcessor processor = new ArgumentProcessor();

    /**
     * This method is an equivalent of calling {@link UtilArgs#UtilArgs(String[], Object)} constructor.
     * <p/>
     * This method will never throw an exception when field binding process fails.
     * <p/>
     *
     * @param arguments command line arguments
     * @param wrapper user-provided arguments wrapper
     * @return associated {@link UtilArgs} instance
     */
    public static UtilArgs process(String[] arguments, Object wrapper) {
        return new UtilArgs(arguments, wrapper);
    }

    /**
     * Reads provided path as a properties file (in key=value format) and transforms it into command-line-like
     * arguments. Options are generated in long format (e.g. --name) based on keys in file. Lines that are not in
     * key=value format are ignored. Generated arguments are processed as an equivalent of calling
     * {@link UtilArgs#UtilArgs(String[], Object)} constructor.
     * <p/>
     * It is not possible to provide an operand by calling this method.
     * <p/>
     * This method will never throw an exception when field binding process fails.
     *
     * @param path path to properties file
     * @param wrapper user-provided arguments wrapper
     * @return associated {@link UtilArgs} instance
     * @throws IOException if any I/O errors occurs during file read
     */
    public static UtilArgs process(Path path, Object wrapper) throws IOException {
        return new UtilArgs(new PropertiesProcessor().process(path), wrapper);
    }

    /**
     * Resolves given arguments and updates user-provided object fields. The object fields should be
     * annotated with {@link com.nilcaream.utilargs.model.Option} annotation. They don't have to be public.
     * <p/>
     * This constructor will never throw an exception when field binding process fails.
     *
     * @param arguments command line arguments
     * @param wrapper   user-provided arguments wrapper
     */
    public UtilArgs(String[] arguments, Object wrapper) {
        this.arguments = arguments;
        this.wrapper = wrapper;
        processor.getBinders().add(new StaticValueOfBinder());
        processor.getBinders().add(new StringConstructorBinder());
        processor.initialize(arguments, wrapper);
    }

    /**
     * Gets user-provided (command line) arguments.
     *
     * @return arguments array
     */
    public String[] getArguments() {
        return arguments;
    }

    /**
     * Gets user-provided wrapper object with {@link com.nilcaream.utilargs.model.Option} annotated fields
     * bound with provided arguments array.
     *
     * @return updated instance of provided wrapper object
     */
    public Object getWrapper() {
        return wrapper;
    }

    /**
     * Gets operands - String with arguments that were unable to be bound to provided wrapper object. For more details
     * refer to the POSIX documentation.
     *
     * @return not-null String
     * @see <a href="http://pubs.opengroup.org/onlinepubs/9699919799/basedefs/V1_chap12.html">http://pubs.opengroup.org/onlinepubs/9699919799/basedefs/V1_chap12.html</a>
     */
    public String getOperands() {
        return processor.getOperands();
    }
}
