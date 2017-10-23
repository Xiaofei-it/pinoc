package com.iqiyi.trojan.plugin;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;

/**
 * Created by Eric on 2017/10/20.
 */

public class TestMethodVisitor2 extends MethodVisitor {
    private ArrayList<Label> labels = new ArrayList<>();

    public TestMethodVisitor2(int api, MethodVisitor mv) {
        super(api, mv);
    }

    @Override
    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        mv.visitFrame(type, nLocal, local, nStack, stack);
        System.out.println("Visit Frame type" + type + " " + nLocal + " " + nStack);
        for (Object o : local) {
            System.out.print(" " + o);
        }
        System.out.println();
        for (Object o : stack) {
            System.out.print(" " + o);
        }
        System.out.println();
    }

    @Override
    public void visitInsn(int opcode) {
        mv.visitInsn(opcode);
        System.out.println("visitInsn " + opcode);
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        mv.visitIntInsn(opcode, operand);
        System.out.println("visitIntInsn " + opcode + " " + operand);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        mv.visitVarInsn(opcode, var);
        System.out.println("visitVarInsn " + opcode + " " + var);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        mv.visitTypeInsn(opcode, type);
        System.out.println("visitTypeInsn " + opcode + " " + type);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        mv.visitFieldInsn(opcode, owner, name, desc);
        System.out.println("visitFieldInsn " + opcode + " " + owner + " " + name + " " + desc);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        mv.visitMethodInsn(opcode, owner, name, desc, itf);
        System.out.println("visitMethodInsn " + opcode + " " + owner + " " + name + " " + desc + " " + itf);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        System.out.println("visitJumpInsn " + opcode + " " + label);
        labels.add(label);
        mv.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitLabel(Label label) {
        System.out.println("visitLabel " + label);
        labels.add(label);
        mv.visitLabel(label);
    }

    @Override
    public void visitEnd() {
        System.out.println("VisitEnd");
        mv.visitEnd();
        for (Label label : labels) {
            System.out.println(label + " " + label.getOffset());
        }
    }

    @Override
    public void visitLdcInsn(Object cst) {
        System.out.println("visitLdcInsn " + cst);
        mv.visitLdcInsn(cst);
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        System.out.println("visitIincInsn " + var + " " + increment);
        mv.visitIincInsn(var, increment);
    }

}
