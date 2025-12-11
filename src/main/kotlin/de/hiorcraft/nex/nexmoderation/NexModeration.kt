package de.hiorcraft.nex.nexmoderation

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.folia.launch
import de.hiorcraft.nex.nexmoderation.database.PunishmentManager
import de.hiorcraft.nex.nexmoderation.punishment.Punishment
import de.hiorcraft.nex.nexmoderation.punishment.PunishmentType
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(NexModeration::class.java)

class NexModeration : JavaPlugin() {

    override fun onLoad() {
        PunishmentManager.connect(dataFolder.toPath())
        PunishmentManager.createTables()
    }

    override fun onEnable() {
        logger.info("NexModeratio  ist starting.....")
        val test = Punishment(
            Bukkit.getOfflinePlayer("Hiorcraft").uniqueId,
            PunishmentType.BAN,
            "Testing",
            Bukkit.getOfflinePlayer("Hiorcraft").uniqueId,
            System.currentTimeMillis(),
            -1L
        )

        plugin.launch {
            PunishmentManager.savePunishment(test)

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