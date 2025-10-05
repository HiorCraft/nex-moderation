package de.hiorcraft.nex.nexmoderation

import org.bukkit.plugin.java.JavaPlugin
import de.hiorcraft.nex.nexmoderation.punishment.PunishmentManager
import de.hiorcraft.nex.nexmoderation.commands.BanCommand
import de.hiorcraft.nex.nexmoderation.punishment.PunishmentType
import de.hiorcraft.nex.nexmoderation.punishment.Punishment
import  de.hiorcraft.nex.nexmoderation.punishment.PunishmentManager

class NexModeration : JavaPlugin() {

    companion object{
        lateinit var instance: NexModeration
            private set
    }

    lateinit var dataSourece: DataSource
    lateinit var punishmentManager: PunishmentManager


    override fun onEnable() {
        instance = this


        logger.info("NexModeration enabled")
    }

    private fun setupDatabase() {
        val cfg = HikariConfig().apply {
            jdcUrl = "jdbc:mysql://${getConfig().getString("database.host")}:${getConfig().getInt("database.port")}/${getConfig().getString("database.name")}"
            username = getConfig().getString("database.user")
            password = getConfig().getString("database.password")

           }
        dataSourece = HikariDataSource(cfg)
        }
    }

    override fun onDisable() {
        logger.info("NexModeration disabled")


        logger.info("Bye <3")
    }
}
