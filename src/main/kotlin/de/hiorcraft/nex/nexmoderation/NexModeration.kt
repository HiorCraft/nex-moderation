package de.hiorcraft.nex.nexmoderation

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.folia.launch
import de.hiorcraft.nex.nexmoderation.database.PunishmentManager
import de.hiorcraft.nex.nexmoderation.util.CommandManager
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(NexModeration::class.java)

class NexModeration : SuspendingJavaPlugin() {

    override fun onLoad() {
        PunishmentManager.connect(dataFolder.toPath())
        PunishmentManager.createTables()
    }

    override fun onEnable() {
        logger.info("NexModeratio  ist starting.....")

        val manager = server.pluginManager

        CommandManager.registerAll(plugin)


        plugin.launch {

            val data = PunishmentManager.loadPunishments()
            for (punishment in data) {
                punishment.player
                Bukkit.broadcast(Component.text("${punishment.player}"))
            }
        }
        logger.info("NexModeration is started")
    }

    override fun onDisable() {
        logger.info("NexModeration disabled")
        PunishmentManager.disconnect()
        logger.info("Bye <3")
    }
}