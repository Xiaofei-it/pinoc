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

import org.gradle.api.Project

class PinocExtension {
    final def KEY_ENABLE = "pinoc-plugin.enabled"
    final def KEY_PINOC_LIBRARY_VERSION = "pinoc-version"
    final def KEY_ZLANG_VERSION = "zlang-version"
    final def KEY_IS_DEPENDENCIES_ENABLED = "pinoc-dependencies.enabled"

    def isEnabled
    def pinocLibraryVersion
    def zlangLibraryVersion
    def isDependenciesEnabled = true

    PinocExtension(Project project) {
        this.isEnabled =
                project.hasProperty(KEY_ENABLE) ? Boolean.parseBoolean(project.property(KEY_ENABLE)) : true
        this.pinocLibraryVersion = project.hasProperty(KEY_PINOC_LIBRARY_VERSION) ?
                project.property(KEY_PINOC_LIBRARY_VERSION) : "0.2.0"
        this.zlangLibraryVersion =
                project.hasProperty(KEY_ZLANG_VERSION) ? project.property(KEY_ZLANG_VERSION) : "0.2.0"
        this.isDependenciesEnabled = project.hasProperty(KEY_IS_DEPENDENCIES_ENABLED) ?
                Boolean.parseBoolean(project.property(KEY_IS_DEPENDENCIES_ENABLED)) : true
    }

    def isEnabled() {
        return isEnabled
    }

    void setEnabled(isEnabled) {
        this.isEnabled = isEnabled
    }

    def getPinocLibraryVersion() {
        return pinocLibraryVersion
    }

    void setPinocLibraryVersion(pinocLibraryVersion) {
        this.pinocLibraryVersion = pinocLibraryVersion
    }

    def getZlangLibraryVersion() {
        return zlangLibraryVersion
    }

    void setZlangLibraryVersion(zlangLibraryVersion) {
        this.zlangLibraryVersion = zlangLibraryVersion
    }
}