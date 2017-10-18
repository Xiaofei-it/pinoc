package com.iqiyi.trojan.plugin;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
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

}
