package com.iqiyi.trojantest;

import org.junit.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Eric on 2017/10/20.
 */

public class SmallTest {
    private static final HashMap<Character, String> PRIMITIVE_SIGNATURES = new HashMap<Character, String>() {
        {
            put('Z', "boolean");
            put('B', "byte");
            put('C', "char");
            put('S', "short");
            put('I', "int");
            put('J', "long");
            put('F', "float");
            put('D', "double");
            put('V', "void");
        }
    };
    private ArrayList<String> parameterTypes;
    private String returnType;
    private Object[] obtainType(String desc, int start) {
        char ch = desc.charAt(start);
        String sig = PRIMITIVE_SIGNATURES.get(ch);
        if (sig != null) {
            return new Object[]{start + 1, sig};
        }
        if (ch == 'L') {
            int pos = start + 1;
            while (desc.charAt(pos) != ';') {
                ++pos;
            }
            return new Object[]{pos + 1, desc.substring(start + 1, pos)};
        }
        if (ch == '[') {
            Object[] tmp = obtainType(desc, start + 1);
            return new Object[]{tmp[0], tmp[1] + "[]"};
        }
        throw new IllegalArgumentException("What the fuck!");
    }

    private void initParameterTypes(String desc) {
        parameterTypes = new ArrayList<>();
        int pos = 1;
        while (desc.charAt(pos) != ')') {
            Object[] tmp = obtainType(desc, pos);
            parameterTypes.add((String) tmp[1]);
            pos = (int) tmp[0];
        }
        ++pos;
        returnType = (String) obtainType(desc, pos)[1];
    }

    private void process(String desc) {
        System.out.println(desc);
        initParameterTypes(desc);
        for (String type : parameterTypes) {
            System.out.println(type);
        }
        System.out.println(returnType);
    }
    @org.junit.Test
    public void test1() {
        process("()V");
        process("(I)V");
        process("(Ljava/lang/Integer;)I");
        process("(ZBCS[Ljava/lang/Integer;[[[Ljava/lang/B;IJF[[[D)I");
        process("(ZBCS[Ljava/lang/Integer;[[[Ljava/lang/B;IJF[[[D)[[I");
        process("(ZBCS[Ljava/lang/Integer;[[[Ljava/lang/B;IJF[[[D)La/b/c;");
        process("(ZBCSLjava/lang/Integer;[[[Ljava/lang/B;IJF[[[D)[La/b/c;");
        process("(ZBCSLjava/lang/Integer;[[[Ljava/lang/B;IJF[[[D)[[La/b/c;");
    }

    private static final HashMap<String, Character> PRIMITIVE_SIGNATURES_SHORT_FORMS = new HashMap<String, Character>() {
        {
            put("boolean", 'Z');
            put("byte", 'B');
            put("char", 'C');
            put("short", 'S');
            put("int", 'I');
            put("long", 'J');
            put("float", 'F');
            put("double", 'D');
            put("void", 'V');
        }
    };

    private static String convertIfArray(String parameterType) {
        int pos = parameterType.length() - 1;
        int dimens = 0;
        while (parameterType.charAt(pos) == ']') {
            ++dimens;
            pos -= 2;
        }
        if (dimens == 0) {
            return parameterType;
        }
        String objectType = parameterType.substring(0, pos + 1);
        if (PRIMITIVE_SIGNATURES_SHORT_FORMS.containsKey(objectType)) {
            String result = "";
            for (int i = 1; i <= dimens; ++i) {
                result += "[";
            }
            result = result + PRIMITIVE_SIGNATURES_SHORT_FORMS.get(objectType);
            return result;
        }
        String result = "";
        for (int i = 1; i <= dimens; ++i) {
            result += "[";
        }
        result = result + "L" + objectType + ";";
        return result;
    }
    @org.junit.Test
    public void test4() {
        System.out.println(convertIfArray("java/lang/Object"));
        System.out.println(convertIfArray("java/lang/Object[][][]"));
        System.out.println(convertIfArray("boolean"));
        System.out.println(convertIfArray("boolean[][]"));
        System.out.println(convertIfArray("boolean[]"));
    }
}
