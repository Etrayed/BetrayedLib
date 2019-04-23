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
package de.etrayed.betrayedlib.reflectionhelp.helper;

import de.etrayed.betrayedlib.reflectionhelp.util.ReflectiveException;
import de.etrayed.betrayedlib.reflectionhelp.util.WrappedClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * (JavaDoc)
 *
 * @author Etrayed
 * Created for the project BetrayedLib on 22.03.2019
 * <p>
 * © Etrayed 2019
 */
public final class MethodHelper extends AbstractHelper {

    public Method getMethod(final WrappedClass<?> wrappedClass, final String name, final Class<?>... parameters) {
        try {
            return wrappedClass.getInstance().getMethod(name, parameters);
        } catch (NoSuchMethodException e) {
            throw new ReflectiveException(e);
        }
    }

    public Method getDeclaredMethod(final WrappedClass<?> wrappedClass, final String name, final Class<?>... parameters) {
        try {
            return wrappedClass.getInstance().getDeclaredMethod(name, parameters);
        } catch (NoSuchMethodException e) {
            throw new ReflectiveException(e);
        }
    }

    public <T> T executeMethod(final Method method, final Object instance, final Class<T> returnType, final Object... parameters) {
        try {
            return returnType.cast(method.invoke(instance, parameters));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ReflectiveException(e);
        }
    }
}
