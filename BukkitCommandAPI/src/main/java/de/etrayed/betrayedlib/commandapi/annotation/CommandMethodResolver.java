/*
MIT License

Copyright (c) 2019 Miklas

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package de.etrayed.betrayedlib.commandapi.annotation;

import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Etrayed
 */
public class CommandMethodResolver {

    private Class<?> objectClass;

    public void prepare(final Class<?> objectClass) {
        this.objectClass = objectClass;
    }

    public void prepare(final Object objectInstance) {
        this.objectClass = objectInstance.getClass();
    }

    @SuppressWarnings("ToArrayCallWithZeroLengthArrayArgument")
    public Map<Method, CommandMethod> resolveCommandMethods() {
        final Method[] methods = objectClass.getDeclaredMethods();
        final Map<Method, CommandMethod> commandMethods = Maps.newHashMap();

        for (Method method : methods) {
            final CommandMethod commandMethod = method.getDeclaredAnnotation(CommandMethod.class);

            if (commandMethod != null)
                commandMethods.put(method, commandMethod);
        }

        return commandMethods;
    }
}
