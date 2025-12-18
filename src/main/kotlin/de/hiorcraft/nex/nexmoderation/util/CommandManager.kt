package de.hiorcraft.nex.nexmoderation.util

import de.hiorcraft.nex.nexmoderation.commands.banCommand
import org.bukkit.plugin.java.JavaPlugin

object CommandManager {
    fun registerAll(plugin: JavaPlugin) {
        banCommand()
    }
}