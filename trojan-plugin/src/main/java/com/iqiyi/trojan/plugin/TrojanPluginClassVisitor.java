package com.iqiyi.trojan.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by zhaolifei on 2017/8/19.
 */

public class TrojanPluginClassVisitor extends ClassVisitor {

    private String className;

    public TrojanPluginClassVisitor(String name, ClassWriter cw) {
        super(Opcodes.ASM5, cw);
        this.className = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        System.out.println("Name " + name + " signature " + signature + " desc " + desc);
        return new TrojanPluginMethodVisitor(api, mv, access, className, name, desc);
    }
}
