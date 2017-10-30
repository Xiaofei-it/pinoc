package com.iqiyi.trojantest;

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
}
