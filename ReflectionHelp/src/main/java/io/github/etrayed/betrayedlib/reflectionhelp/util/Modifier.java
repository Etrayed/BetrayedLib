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
package io.github.etrayed.betrayedlib.reflectionhelp.util;

import java.lang.reflect.Field;
import java.lang.reflect.Member;

/**
 * (JavaDoc)
 *
 * @author Etrayed
 * Created for the project Plorax3_PloraxCloudAPI on 19.02.2019
 */
public enum Modifier {

    ABSTRACT(java.lang.reflect.Modifier.ABSTRACT),
    FINAL(java.lang.reflect.Modifier.FINAL),
    INTERFACE(java.lang.reflect.Modifier.INTERFACE),
    NATIVE(java.lang.reflect.Modifier.NATIVE),
    PRIVATE(java.lang.reflect.Modifier.PRIVATE),
    PROTECTED(java.lang.reflect.Modifier.PROTECTED),
    PUBLIC(java.lang.reflect.Modifier.PUBLIC),
    STATIC(java.lang.reflect.Modifier.STATIC),
    STRICT(java.lang.reflect.Modifier.STRICT),
    SYNCHRONIZED(java.lang.reflect.Modifier.SYNCHRONIZED),
    TRANSIENT(java.lang.reflect.Modifier.TRANSIENT),
    VOLATILE(java.lang.reflect.Modifier.VOLATILE);

    private final int intValue;

    Modifier(final int intValue) {
        this.intValue = intValue;
    }

    public final int intValue() {
        return intValue;
    }

    @Override
    public final String toString() {
        return super.toString().toLowerCase();
    }

    public static Modifier modifierFromInt(final int intValue) {
        for (Modifier value : values()) {
            if(value.intValue == intValue)
                return value;
        }

        return null;
    }

    public static boolean isAbstract(final int intValue) {
        return java.lang.reflect.Modifier.isAbstract(intValue);
    }

    public static boolean isFinal(final int intValue) {
        return java.lang.reflect.Modifier.isFinal(intValue);
    }

    public static boolean isInterface(final int intValue) {
        return java.lang.reflect.Modifier.isInterface(intValue);
    }

    public static boolean isNative(final int intValue) {
        return java.lang.reflect.Modifier.isNative(intValue);
    }

    public static boolean isPrivate(final int intValue) {
        return java.lang.reflect.Modifier.isPrivate(intValue);
    }

    public static boolean isProtected(final int intValue) {
        return java.lang.reflect.Modifier.isProtected(intValue);
    }

    public static boolean isPublic(final int intValue) {
        return java.lang.reflect.Modifier.isPublic(intValue);
    }

    public static boolean isStatic(final int intValue) {
        return java.lang.reflect.Modifier.isStatic(intValue);
    }

    public static boolean isStrict(final int intValue) {
        return java.lang.reflect.Modifier.isStrict(intValue);
    }

    public static boolean isSynchronized(final int intValue) {
        return java.lang.reflect.Modifier.isSynchronized(intValue);
    }

    public static boolean isTransient(final int intValue) {
        return java.lang.reflect.Modifier.isTransient(intValue);
    }

    public static boolean isVolatile(final int intValue) {
        return java.lang.reflect.Modifier.isVolatile(intValue);
    }

    public static Modifier[] fromIntToArray(final int intModifier) {
        final Modifier[] modifiers = new Modifier[12];

        int i = 0;

        if(Modifier.isAbstract(intModifier))
            modifiers[i++] = Modifier.ABSTRACT;

        if(Modifier.isFinal(intModifier))
            modifiers[i++] = Modifier.FINAL;

        if(Modifier.isInterface(intModifier))
            modifiers[i++] = Modifier.INTERFACE;

        if(Modifier.isNative(intModifier))
            modifiers[i++] = Modifier.NATIVE;

        if(Modifier.isPrivate(intModifier))
            modifiers[i++] = Modifier.PRIVATE;

        if(Modifier.isProtected(intModifier))
            modifiers[i++] = Modifier.PROTECTED;

        if(Modifier.isPublic(intModifier))
            modifiers[i++] = Modifier.PUBLIC;

        if(Modifier.isStatic(intModifier))
            modifiers[i++] = Modifier.STATIC;

        if(Modifier.isStrict(intModifier))
            modifiers[i++] = Modifier.STRICT;

        if(Modifier.isSynchronized(intModifier))
            modifiers[i++] = Modifier.SYNCHRONIZED;

        if(Modifier.isTransient(intModifier))
            modifiers[i++] = Modifier.TRANSIENT;

        if(Modifier.isVolatile(intModifier))
            modifiers[i] = Modifier.VOLATILE;

        return modifiers;
    }

    public static Modifier[] getModifiersAsArray(final Member member) {
        try {
            final Field field = member.getClass().getDeclaredField("modifiers");

            field.setAccessible(true);

            return fromIntToArray(field.getInt(member));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return new Modifier[0];
    }

    public static int modifiersToInt(final Modifier[] modifiers) {
        int i = 0;

        for (Modifier modifier : modifiers)
            i |= modifier.intValue;

        return i;
    }

    public static int removeModifier(final int modifiers, final Modifier modifier) {
        return modifiers &~ modifier.intValue;
    }
}
