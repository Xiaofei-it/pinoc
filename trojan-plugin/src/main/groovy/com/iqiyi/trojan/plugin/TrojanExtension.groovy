
package com.iqiyi.trojan.plugin

import org.gradle.api.Project

class TrojanExtension {

  def isEnabled

  TrojanExtension(Project project) {
    this.isEnabled = project.hasProperty("trojan-plugin.enabled") ? Boolean.parseBoolean(
        project.property("trojan-plugin.enabled")) : true
  }

  def isEnabled() {
    return isEnabled
  }

  void setEnabled(isEnabled) {
    this.isEnabled = isEnabled
  }
}