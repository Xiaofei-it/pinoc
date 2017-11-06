package com.iqiyi.trojan.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
/**
 * Created by Eric on 2017/11/6.
 */

class JarProcessor {
    public static void process(File source, File target) throws Exception {
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
