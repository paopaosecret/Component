package com.example.transformlib;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LogClassVisitor extends ClassVisitor {
    private String className;
    private String superName;

    public LogClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
        this.superName = superName;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("ClassVisitor visitMethod name-------" + name + ", className:" + className + ", superName:" + superName);
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

        if (superName.endsWith("BaseActivity")) {
            if (name.startsWith("onCreate")) {
                //处理onCreate()方法
                return new LogMethodVisitor(mv, className, name);
            }
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}
