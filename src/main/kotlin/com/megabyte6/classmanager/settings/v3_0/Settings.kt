package com.megabyte6.classmanager.settings.v3_0

import com.megabyte6.classmanager.inWholeTicks
import com.megabyte6.classmanager.ticks
import org.bukkit.configuration.serialization.ConfigurationSerializable
import java.time.DayOfWeek
import java.time.LocalTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds


data class Settings(
    var endOfClassTimes: EndOfClassTimes = EndOfClassTimes(),
    var autoKick: AutoKick = AutoKick(),
    var resetDay: ResetDay = ResetDay(),
    var profanityFilter: ProfanityFilter = ProfanityFilter(),
) : ConfigurationSerializable {
    companion object {
        @JvmStatic
        fun deserialize(args: Map<String, Any>) = Settings()
    }

    override fun serialize() = mutableMapOf<String, Any>()

    data class AutoKick(
        var enabled: Boolean = true,
        var message: String = "Server is now closed. Time to exit the Dojo!",
        var beforeEndOfClass: Duration = 1.minutes,
        var showWarning: Boolean = true,
        var enableWhiteListOnKick: Boolean = true,
        var disableWhitelistAfter: Duration = 6.minutes,
    ) : ConfigurationSerializable {
        companion object {
            @JvmStatic
            fun deserialize(args: Map<String, Any>) = AutoKick().apply {
                args["enabled"]?.let { enabled = it as Boolean }
                args["message"]?.let { message = it as String }
                args["before-end-of-class"]?.let { beforeEndOfClass = (it as Int).seconds }
                args["show-warning"]?.let { showWarning = it as Boolean }
                args["enable-whitelist-on-kick"]?.let { enableWhiteListOnKick = it as Boolean }
                args["disable-whitelist-after"]?.let { disableWhitelistAfter = (it as Int).seconds }
            }
        }

        override fun serialize() = mutableMapOf(
            "enabled" to enabled,
            "message" to message,
            "before-end-of-class" to beforeEndOfClass.inWholeSeconds,
            "show-warning" to showWarning,
            "enable-whitelist-on-kick" to enableWhiteListOnKick,
            "disable-whitelist-after" to disableWhitelistAfter.inWholeSeconds,
        )
    }

    data class ResetDay(
        var enabled: Boolean = true,
        var beforeEndOfClass: Duration = 10.minutes,
        var minecraftTime: Duration = 6000.ticks,
        var useAbsoluteTime: Boolean = false,
        var minecraftWorldName: String = "world",
    ) : ConfigurationSerializable {
        companion object {
            @JvmStatic
            fun deserialize(args: Map<String, Any>) = ResetDay().apply {
                args["enabled"]?.let { enabled = it as Boolean }
                args["before-end-of-class"]?.let { beforeEndOfClass = (it as Int).seconds }
                args["minecraft-time"]?.let { minecraftTime = (it as Int).ticks }
                args["use-absolute-time"]?.let { useAbsoluteTime = it as Boolean }
                args["minecraft-world-name"]?.let { minecraftWorldName = it as String }
            }
        }

        override fun serialize() = mutableMapOf(
            "enabled" to enabled,
            "before-end-of-class" to beforeEndOfClass.inWholeSeconds,
            "minecraft-time" to minecraftTime.inWholeTicks,
            "use-absolute-time" to useAbsoluteTime,
            "minecraft-world-name" to minecraftWorldName,
        )
    }

    data class EndOfClassTimes(
        var monday: List<LocalTime> = listOf(
            LocalTime.of(16, 30),
            LocalTime.of(17, 30),
            LocalTime.of(18, 30),
            LocalTime.of(19, 30),
        ),
        var tuesday: List<LocalTime> = listOf(
            LocalTime.of(16, 30),
            LocalTime.of(17, 30),
            LocalTime.of(18, 30),
            LocalTime.of(19, 30),
        ),
        var wednesday: List<LocalTime> = listOf(
            LocalTime.of(16, 30),
            LocalTime.of(17, 30),
            LocalTime.of(18, 30),
            LocalTime.of(19, 30),
        ),
        var thursday: List<LocalTime> = listOf(
            LocalTime.of(16, 30),
            LocalTime.of(17, 30),
            LocalTime.of(18, 30),
            LocalTime.of(19, 30),
        ),
        var friday: List<LocalTime> = listOf(
            LocalTime.of(16, 30),
            LocalTime.of(17, 30),
            LocalTime.of(18, 30),
            LocalTime.of(19, 30),
        ),
        var saturday: List<LocalTime> = listOf(
            LocalTime.of(11, 0),
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            LocalTime.of(14, 0),
        ),
        var sunday: List<LocalTime> = emptyList(),
    ) : ConfigurationSerializable {
        companion object {
            @JvmStatic
            fun deserialize(args: Map<String, Any>) = EndOfClassTimes().apply {
                args["monday"]?.let { monday = (it as List<*>).map { time -> LocalTime.parse(time as String) } }
                args["tuesday"]?.let { tuesday = (it as List<*>).map { time -> LocalTime.parse(time as String) } }
                args["wednesday"]?.let { wednesday = (it as List<*>).map { time -> LocalTime.parse(time as String) } }
                args["thursday"]?.let { thursday = (it as List<*>).map { time -> LocalTime.parse(time as String) } }
                args["friday"]?.let { friday = (it as List<*>).map { time -> LocalTime.parse(time as String) } }
                args["saturday"]?.let { saturday = (it as List<*>).map { time -> LocalTime.parse(time as String) } }
                args["sunday"]?.let { sunday = (it as List<*>).map { time -> LocalTime.parse(time as String) } }
            }
        }

        override fun serialize() = mutableMapOf(
            "monday" to monday.map { it.toString() },
            "tuesday" to tuesday.map { it.toString() },
            "wednesday" to wednesday.map { it.toString() },
            "thursday" to thursday.map { it.toString() },
            "friday" to friday.map { it.toString() },
            "saturday" to saturday.map { it.toString() },
            "sunday" to sunday.map { it.toString() },
        )

        fun getTimes(dayOfWeek: DayOfWeek) = when (dayOfWeek) {
            DayOfWeek.MONDAY -> monday
            DayOfWeek.TUESDAY -> tuesday
            DayOfWeek.WEDNESDAY -> wednesday
            DayOfWeek.THURSDAY -> thursday
            DayOfWeek.FRIDAY -> friday
            DayOfWeek.SATURDAY -> saturday
            DayOfWeek.SUNDAY -> sunday
        }
    }

    data class ProfanityFilter(
        var filterChat: Boolean = true,
        var filterUsernames: Boolean = true,
        val prohibitedWords: List<String> = listOf(
            "asshole",
            "ass_hole",
            "bastard",
            "bitch",
            "blowjob",
            "boob",
            "clit",
            "cock",
            "cunt",
            "damn",
            "dick",
            "faggot",
            "fuck",
            "nigga",
            "nigger",
            "penis",
            "pussy",
            "retard",
            "shit",
            "slut",
            "wanker",
            "whore",
            "vagina",
        ),
    ) : ConfigurationSerializable {
        companion object {
            @JvmStatic
            fun deserialize(args: Map<String, Any>) = ProfanityFilter().apply {
                args["filter-chat"]?.let { filterChat = it as Boolean }
                args["filter-username"]?.let { filterUsernames = it as Boolean }
            }
        }

        override fun serialize() = mutableMapOf(
            "filter-chat" to filterChat,
            "filter-usernames" to filterUsernames,
        )
    }
}
