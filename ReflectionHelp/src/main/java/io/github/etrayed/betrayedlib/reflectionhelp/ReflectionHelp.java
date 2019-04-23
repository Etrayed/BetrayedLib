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
package io.github.etrayed.betrayedlib.reflectionhelp;

import io.github.etrayed.betrayedlib.reflectionhelp.helper.ConstructorHelper;
import io.github.etrayed.betrayedlib.reflectionhelp.helper.FieldHelper;
import io.github.etrayed.betrayedlib.reflectionhelp.helper.MethodHelper;

/**
 * (JavaDoc)
 *
 * @author Etrayed
 * Created for the project BetrayedLib on 22.03.2019
 * <p>
 * © Etrayed 2019
 */
public class ReflectionHelp {

    private static final ConstructorHelper CONSTRUCTOR_HELPER;
    private static final MethodHelper METHOD_HELPER;
    private static final FieldHelper FIELD_HELPER;

    static {
        CONSTRUCTOR_HELPER = new ConstructorHelper();
        METHOD_HELPER = new MethodHelper();
        FIELD_HELPER = new FieldHelper();
    }

    public static ConstructorHelper getConstructorHelper() {
        return CONSTRUCTOR_HELPER;
    }

    public static MethodHelper getMethodHelper() {
        return METHOD_HELPER;
    }

    public static FieldHelper getFieldHelper() {
        return FIELD_HELPER;
    }
}
