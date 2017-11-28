/**
 *
 * Copyright 2017 iQIYI.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.iqiyi.trojan.plugin;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;

import java.util.ArrayList;

/**
 * Created by Xiaofei on 2017/10/20.
 */

public class TestMethodVisitor extends MethodVisitor {
    private ArrayList<Label> labels = new ArrayList<>();
    public TestMethodVisitor(int api) {
        super(api);
    }

    public TestMethodVisitor(int api, MethodVisitor mv) {
        super(api, mv);
    }

    @Override
    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        super.visitFrame(type, nLocal, local, nStack, stack);
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
        super.visitInsn(opcode);
        System.out.println("visitInsn " + opcode);
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        super.visitIntInsn(opcode, operand);
        System.out.println("visitIntInsn " + opcode + " " + operand);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        super.visitVarInsn(opcode, var);
        System.out.println("visitVarInsn " + opcode + " " + var);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        super.visitTypeInsn(opcode, type);
        System.out.println("visitTypeInsn " + opcode + " " + type);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        super.visitFieldInsn(opcode, owner, name, desc);
        System.out.println("visitFieldInsn " + opcode + " " + owner + " " + name + " " + desc);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        super.visitMethodInsn(opcode, owner, name, desc, itf);
        System.out.println("visitMethodInsn " + opcode + " " + owner + " " + name + " " + desc + " " + itf);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        System.out.println("visitJumpInsn " + opcode + " " + label);
        labels.add(label);
        super.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitLabel(Label label) {
        System.out.println("visitLabel " + label);
        labels.add(label);
        super.visitLabel(label);
    }

    @Override
    public void visitEnd() {
        System.out.println("VisitEnd");
        super.visitEnd();
        for (Label label : labels) {
            System.out.println(label + " " + label.getOffset());
        }
    }

    @Override
    public void visitLdcInsn(Object cst) {
        System.out.println("visitLdcInsn " + cst);
        super.visitLdcInsn(cst);
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        System.out.println("visitIincInsn " + var + " " + increment);
        super.visitIincInsn(var, increment);
    }

    @Override
    public void visitParameter(String name, int access) {
        System.out.println("visitParameter " + name + " " + access);
        super.visitParameter(name, access);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        System.out.println("visitAnnotation " + desc + " " + visible);
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitAttribute(Attribute attr) {
        System.out.println("visitAttribute " + attr.type + " " + attr.isCodeAttribute() + " " + attr.isUnknown());
        super.visitAttribute(attr);
    }

    @Override
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        System.out.println("visitLocalVariable " + name + " " + desc + " " + signature + " " + start + " " + end + " " + index);
        super.visitLocalVariable(name, desc, signature, start, end, index);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        System.out.println("visitMaxs " + maxStack + " " + maxLocals);
        super.visitMaxs(maxStack, maxLocals);
    }
}
