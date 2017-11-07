package com.iqiyi.trojan;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void test1() throws Exception {
        JarFile jarFile = new JarFile(new File("jar-test/e2.jar"), false);
        File dir = new File("jar-test/e");
        File testFile = new File(dir, "ttt/test.class");
        System.out.println(dir.getAbsoluteFile());
        System.out.println(testFile.getAbsoluteFile());
        Enumeration<JarEntry> entries = jarFile.entries();
        long s1 = 0L, s2 = 0L;
        System.out.println(jarFile.getManifest() + " " + File.separator + " " + File.pathSeparator);
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
                File tmp = new File(dir, entry.getName());
                tmp.mkdirs();
            } else {
                s1 += entry.getSize();
                s2 += entry.getCompressedSize();
                File tmp = new File(dir, entry.getName());
                File parent = tmp.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                InputStream is = jarFile.getInputStream(entry);
                OutputStream os = new FileOutputStream(new File(dir, entry.getName()));
                byte[] bytes = new byte[1024];
                int num;
                while ((num = is.read(bytes, 0, 1024)) != -1) {
                    os.write(bytes, 0, num);
                }
                os.close();
                is.close();
            }
        }
        jarFile.close();
        System.out.println(s1 + " " + s2);
    }

    @Test
    public void test2() throws Exception {
        JarFile jarFile = new JarFile(new File("jar-test/MiPush_SDK_Client.jar"), false);
        File target = new File("jar-test/MiPush_SDK_Client2.jar");
        Enumeration<JarEntry> entries = jarFile.entries();
        long s1 = 0L, s2 = 0L;
        System.out.println(jarFile.getManifest() + " " + File.separator + " " + File.pathSeparator);
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
                            + "\t" + entry.getCrc()+ "\t" + entry.getMethod() + "\t" + entry.getName());
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
                if (tmp.getMethod() == ZipEntry.STORED) {
                    tmp.setSize(entry.getSize());
                    tmp.setCompressedSize(entry.getCompressedSize());
                    tmp.setCrc(entry.getCrc());
                }
//                JarEntry tmp = new JarEntry(entry);
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