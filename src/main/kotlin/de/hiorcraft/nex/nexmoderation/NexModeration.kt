package de.hiorcraft.nex.nexmoderation

import org.bukkit.plugin.java.JavaPlugin

class NexModeration : JavaPlugin() {

    companion object{
        lateinit var instance: NexModeration
            private set
    }

    lateinit var dataSourece: DataSource
    lateinit var punishmentManager: PunishmentManager


    override fun onEnable() {
        logger.info("NexModeration Starting...")
        instance = this
        saveDefaultConfig()

        setupDatabase()
        punishmentManager = PunishmentManager(dataSourece)

        // Comand registration
        BanCommand(punishmentManager).register()
        MuteCommand(punishmentManager).register()
        WarnCommand(punishmentManager).register()


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
