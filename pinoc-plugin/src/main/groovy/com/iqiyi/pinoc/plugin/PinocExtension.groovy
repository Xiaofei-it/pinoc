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