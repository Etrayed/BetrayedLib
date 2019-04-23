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

import java.lang.reflect.Field;

/**
 * (JavaDoc)
 *
 * @author Etrayed
 * Created for the project BetrayedLib on 23.03.2019
 * <p>
 * © Etrayed 2019
 */
public class FieldHelper extends AbstractHelper {

    public FieldHelper() {
        super();
    }

    public Field getField(final WrappedClass<?> wrappedClass, final String name) {
        try {
            return wrappedClass.getInstance().getField(name);
        } catch (NoSuchFieldException e) {
            throw new ReflectiveException(e);
        }
    }

    public Field getDeclaredField(final WrappedClass<?> wrappedClass, final String name) {
        try {
            return wrappedClass.getInstance().getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new ReflectiveException(e);
        }
    }

    public <T> T getValue(final Field field, final Object instance, final WrappedClass<T> typeClass) {
        try {
            return typeClass.getInstance().cast(field.get(instance));
        } catch (IllegalAccessException e) {
            throw new ReflectiveException(e);
        }
    }

    public void setValue(final Field field, final Object instance, final Object value) {
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new ReflectiveException(e);
        }
    }
}
