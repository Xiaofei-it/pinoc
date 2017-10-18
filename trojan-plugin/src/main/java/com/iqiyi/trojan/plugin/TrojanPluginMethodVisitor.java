package com.iqiyi.trojan.plugin;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by zhaolifei on 2017/8/21.
 */

public class TrojanPluginMethodVisitor extends AdviceAdapter {
    private String mName;
    public TrojanPluginMethodVisitor(int api, MethodVisitor mv, int access, String name, String desc, String signature) {
        super(api, mv, access, name, desc);
        mName = name;
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
    }

    @Override
    protected void onMethodExit(int opcode) {
//        // This is wrong!!!
//        if (!mName.equals("g")) {
//            mv.visitLdcInsn("end of" + mName);
//            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/iqiyi/fpsmonitortest/Fun", "g", "(Ljava/lang/String;)V", false);
//        }
    }

    @Override
    public void visitVarInsn(int i, int i1) {
        System.out.println("visit var insn " + i +  " " + i1);
        super.visitVarInsn(i, i1);
    }

    @Override
    public void visitIincInsn(int i, int i1) {
        System.out.println("visit iinc insn " + i +  " " + i1);
        super.visitIincInsn(i, i1);
    }

    @Override
    public void visitLocalVariable(String s, String s1, String s2, Label label, Label label1, int i) {
        System.out.println("visitLocalVariable " + s +  " " + s1 + " " + s2 + " " + label + " " + label1 + "  " + i);
        if (!s.equals("result")) {
            super.visitLocalVariable(s, s1, s2, label, label1, i);
        }
    }

    @Override
    public AnnotationVisitor visitLocalVariableAnnotation(int i, TypePath typePath, Label[] labels, Label[] labels1, int[] ints, String s, boolean b) {

        System.out.println("visitLocalVariableAnnotation " + i +  " " + s);
        return super.visitLocalVariableAnnotation(i, typePath, labels, labels1, ints, s, b);
    }
}
