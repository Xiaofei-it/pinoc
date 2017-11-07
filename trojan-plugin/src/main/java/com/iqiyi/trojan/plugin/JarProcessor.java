package com.iqiyi.trojan.plugin;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES;

/**
 * Created by Eric on 2017/11/6.
 */

class JarProcessor {
    private static String getClassName(String name) {
        if (name.endsWith(".class")) {
            if (name.startsWith("com/iqiyi/trojan/")) {
                return null;
            }
            if (name.startsWith("org/")) {
                return null;
            }
            if (name.startsWith("android/")) {
                return null;
            }
            int pos = name.indexOf(".class");
            int i;
            boolean found = false;
            for (i = pos - 1; i >= 0; --i) {
                if (name.charAt(i) == '/') {
                    found = true;
                    break;
                }
            }
            String className = found ? name.substring(i + 1, pos) : name.substring(0, pos);
            if (!className.startsWith("R$") && !"R".equals(className) && !"BuildConfig".equals(className)) {
                return className;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void process(File source, File target) throws Exception {
        JarFile jarFile = new JarFile(source, false);
        Enumeration<JarEntry> entries = jarFile.entries();
        long s1 = 0L, s2 = 0L;
        System.out.println(jarFile.getManifest() + " " + File.separator + " " + File.pathSeparator);
        File parent = target.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        JarOutputStream jos = new JarOutputStream(new FileOutputStream(target));
//        jos.setMethod(JarOutputStream.DEFLATED);
//        jos.setLevel(Deflater.NO_COMPRESSION);
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                jos.putNextEntry(new JarEntry(entry));
            } else {
                String className;
//                if ((className = getClassName(entry.getName())) != null) {
//                JarEntry tmp = new JarEntry(entry.getName());
//                tmp.setComment(entry.getComment());
//                tmp.setExtra(entry.getExtra());
//                tmp.setMethod(ZipEntry.DEFLATED);
//                tmp.setTime(entry.getTime());
//                jos.putNextEntry(tmp);
//                tmp.set




                JarEntry tmp = new JarEntry(entry.getName());
                tmp.setComment(entry.getComment());
                tmp.setExtra(entry.getExtra());
                tmp.setMethod(entry.getMethod());
                tmp.setTime(entry.getTime());
                if (tmp.getMethod() == ZipEntry.STORED) {
                    tmp.setSize(entry.getSize());
                    tmp.setCompressedSize(entry.getCompressedSize());
                    tmp.setCrc(entry.getCrc());
                }
                jos.putNextEntry(tmp);
                    InputStream is = jarFile.getInputStream(entry);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int num;
                    int offset = 0;
                    while ((num = is.read(bytes, offset, 1024)) != -1) {
                        baos.write(bytes, 0, num);
                    }
                    is.close();
//                String className;
//                if ((className = getClassName(entry.getName())) != null) {
//                    ClassReader classReader = new ClassReader(baos.toByteArray());
//                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
//                    System.out.println(className);
//                    ClassVisitor cv = new TrojanPluginClassVisitor(className, classWriter);
//                    classReader.accept(cv, EXPAND_FRAMES);
//                    byte[] code = classWriter.toByteArray();
//                    jos.write(code, 0, code.length);
//                } else {
                    jos.write(baos.toByteArray());
//                }
            }
        }
        System.out.println(s1 + " " + s2);
        jos.close();
        jarFile.close();
    }
    private static void process2(File source, File target) throws Exception {
        JarFile jarFile = new JarFile(source, false);
        Enumeration<JarEntry> entries = jarFile.entries();
        long s1 = 0L, s2 = 0L;
        System.out.println(jarFile.getManifest() + " " + File.separator + " " + File.pathSeparator);
        File parent = target.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        JarOutputStream jos = new JarOutputStream(new FileOutputStream(target));//, jarFile.getManifest());
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
//            System.out.println(
//                    "name = " + entry.getName()
//                            + " isDir = " + entry.isDirectory()
//                            + " attr = " + entry.getAttributes()
//                            + " cert = " + entry.getCertificates()
//                            + " sign = " + entry.getCodeSigners()
//                            + " size = " + entry.getSize()
//                            + " time = " + entry.getTime()
//                            + " method = " + entry.getMethod()
//                            + " extra = " + entry.getExtra()
//                            + " comment = " + entry.getComment()
//                            + " com = " + entry.getCompressedSize()
//                            + " crc = " + entry.getCrc());
            System.out.println(

                    "\t" + entry.getSize()
                            + "\t" + entry.getCompressedSize()
                            + "\t" + entry.getCrc()+entry.getName());
            if (entry.isDirectory()) {
                jos.putNextEntry(new JarEntry(entry));
            } else {
                s1 += entry.getSize();
                s2 += entry.getCompressedSize();
                JarEntry tmp = new JarEntry(entry.getName());
                tmp.setComment(entry.getComment());
                tmp.setExtra(entry.getExtra());
                tmp.setMethod(entry.getMethod());
                tmp.setTime(entry.getTime());
                jos.putNextEntry(tmp);
                InputStream is = jarFile.getInputStream(entry);
                byte[] bytes = new byte[1024 * 100];
                int num;
                int offset = 0;
                while ((num = is.read(bytes, offset, 1024)) != -1) {
                    offset += num;
                }
                if (offset != entry.getSize()) {
                    System.out.println("Error " + offset + " " + entry.getSize());
                }
                is.close();
                jos.write(bytes, 0, offset);
            }
        }
        System.out.println(s1 + " " + s2);
        jos.close();
        jarFile.close();
    }
}
