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
 **/

package com.iqiyi.pinoc.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES

class PinocPlugin extends Transform implements Plugin<Project> {
    def pinocExt

    void apply(Project project) {
        pinocExt = new PinocExtension(project)
        project.repositories {
            google()
            maven {
                url "https://dl.bintray.com/iqiyi/pinoc"
            }
        }
        project.dependencies {
            if (!pinocExt.isDependenciesEnabled) {
                println "internal dependency is disable, you need add pinoc-library dependency in your build.gradle"
                return
            }
            implementation("com.iqiyi:pinoc-library:$pinocExt.pinocLibraryVersion", {
                exclude module: "zlang"
            })
            implementation "xiaofei.library:zlang:$pinocExt.zlangLibraryVersion"
        }

        if (!pinocExt.isEnabled) {
            println "pinoc is disable,then return"
            return
        }

        def android = project.extensions.getByType(AppExtension);
        android.registerTransform(this)
    }

    @Override
    String getName() {
        return "PinocPlugin"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider, boolean isIncremental)
            throws IOException, TransformException, InterruptedException {
        println 'Pinoc Plugin Starts.'
        inputs.each { TransformInput input ->
            input.directoryInputs.each { DirectoryInput directoryInput ->
                if (directoryInput.file.isDirectory()) {
                    directoryInput.file.eachFileRecurse { File file ->
                        def name = file.name
                        if (name.endsWith(".class") && !name.startsWith("R\$") &&
                                !"R.class".equals(name) &&
                                !"BuildConfig.class".equals(name)) {
                            ClassReader classReader = new ClassReader(file.bytes)
                            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                            int pos = name.indexOf(".class");
                            String className = name.substring(0, pos);
                            ClassVisitor cv = new PinocPluginClassVisitor(className, classWriter)
                            classReader.accept(cv, EXPAND_FRAMES)
                            byte[] code = classWriter.toByteArray()
                            FileOutputStream fos = new FileOutputStream(file)
                            fos.write(code)
                            fos.close()
                        }
                    }
                }
                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            input.jarInputs.each { JarInput jarInput ->
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }

                def dest = outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                JarProcessor.process(jarInput.file, dest)
            }
        }
        println 'Pinoc Plugin Ends.'
    }
}