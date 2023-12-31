package com.megabyte6.dojodirector.settings.v2_0

import com.megabyte6.dojodirector.Version
import com.megabyte6.dojodirector.isAfterIgnorePatch
import com.megabyte6.dojodirector.isBeforeIgnorePatch
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerialization

object SettingsManager {
    var settings = Settings()

    private val version = Version(2)

    fun registerClasses() {
        ConfigurationSerialization.registerClass(Settings::class.java)
        ConfigurationSerialization.registerClass(Settings.AutoKick::class.java)
        ConfigurationSerialization.registerClass(Settings.AutoResetDay::class.java)
    }

    fun load(config: FileConfiguration, configVersion: Version) {
        if (configVersion isAfterIgnorePatch version) {
            throw IllegalArgumentException("Invalid version. Latest is $version")
        }

        if (configVersion isBeforeIgnorePatch version) {
            // Load the previous version's settings.
            // In this case, there is no previous version.
            throw IllegalArgumentException("Invalid version. Current is $version")
        }

        config.getSerializable("auto-kick", Settings.AutoKick::class.java)?.let { settings.autoKick = it }
        config.getSerializable("auto-reset-day", Settings.AutoResetDay::class.java)?.let { settings.autoResetDay = it }
    }

    fun writeToConfig(config: FileConfiguration) {
        config.set("auto-kick", settings.autoKick)
        config.set("auto-reset-day", settings.autoResetDay)
    }
}