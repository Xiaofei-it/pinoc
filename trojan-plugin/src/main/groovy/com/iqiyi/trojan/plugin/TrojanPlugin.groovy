package com.iqiyi.trojan.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES

//import org.objectweb.asm.MethodVisitor
//import org.objectweb.asm.Opcodes
public class TrojanPlugin extends Transform implements org.gradle.api.Plugin<Project> {
    void apply(Project project) {
        def android = project.extensions.getByType(AppExtension);
        android.registerTransform(this)
    }

    @Override
    public String getName() {
        return "TrojanPlugin";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        println '//===============asm visit start===============//'
        //遍历inputs里的TransformInput
        inputs.each {
            TransformInput input ->
                println 0
                //遍历input里边的DirectoryInput
                input.directoryInputs.each {
                    DirectoryInput directoryInput ->
                        //是否是目录
                        if (directoryInput.file.isDirectory()) {
                            //遍历目录
                            directoryInput.file.eachFileRecurse {
                                File file ->
                                    def name = file.name
                                    println name
                                    //这里进行我们的处理 TODO
                                    if (name.endsWith(".class") && !name.startsWith("R\$") &&
                                            !"R.class".equals(name) && !"BuildConfig.class".equals(name)) {
                                        ClassReader classReader = new ClassReader(file.bytes)
                                        ClassWriter classWriter = new ClassWriter(classReader,ClassWriter.COMPUTE_MAXS)
//                                        ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, classWriter) {
//                                            @Override
//                                            void visit(int version, int access, String name2, String signature, String superName, String[] interfaces) {
//                                                println 'visit' + name2 + ' ' + signature
//                                                super.visit(version, access, name2, signature, superName, interfaces)
//                                            }
//
//                                            @Override
//                                            MethodVisitor visitMethod(int access, String name2, String desc, String signature, String[] exceptions) {
//                                                println 'visit method ' + name2 + ' ' + desc + ' ' + signature
//                                                /**
//                                                 *
//                                                 visit com/iqiyi/fpsmonitortest/MyApp null
//                                                 visit method <init> ()V null
//                                                 visit method onCreate ()V null
//                                                 */
//                                                return super.visitMethod(access, name2, desc, signature, exceptions)
//                                            }
//                                        }
                                        int pos = name.indexOf(".class");
                                        String className = name.substring(0, pos);
                                        System.out.println(className);
                                        ClassVisitor cv = new TrojanPluginClassVisitor(className, classWriter)
                                        classReader.accept(cv, EXPAND_FRAMES)
                                        byte[] code = classWriter.toByteArray()
                                        FileOutputStream fos = new FileOutputStream(
                                                file.parentFile.absolutePath + File.separator + name)
                                        fos.write(code)
                                        fos.close()
                                    }
                                    println '//PluginImpl find file:' + file.getAbsolutePath()
                            }
                        }
                        //处理完输入文件之后，要把输出给下一个任务
                        def dest = outputProvider.getContentLocation(directoryInput.name,
                                directoryInput.contentTypes, directoryInput.scopes,
                                Format.DIRECTORY)
                        FileUtils.copyDirectory(directoryInput.file, dest)
                        println 'Finish copy ' + directoryInput.name + '\n' + directoryInput.file.getAbsolutePath() + '\n' + dest.getAbsolutePath()
                }

                println '1'
                input.jarInputs.each {
                    JarInput jarInput ->
                        /**
                         * 重名名输出文件,因为可能同名,会覆盖
                         */
                        def jarName = jarInput.name
                        def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                        if (jarName.endsWith(".jar")) {
                            jarName = jarName.substring(0, jarName.length() - 4)
                        }
                        println '//PluginImpl find Jar:' + jarInput.getFile().getAbsolutePath()

                        //处理jar进行字节码注入处理 TODO

                        def dest = outputProvider.getContentLocation(jarName + md5Name,
                                jarInput.contentTypes, jarInput.scopes, Format.JAR)

                        FileUtils.copyFile(jarInput.file, dest)
                        println 'Finish copy ' + jarInput.name
                }
                println '2'
        }
        println '//===============asm visit end===============//'

    }
}