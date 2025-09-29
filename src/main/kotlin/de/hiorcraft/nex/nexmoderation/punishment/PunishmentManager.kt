package de.hiorcraft.nex.nexmoderation.punishment

import java.util.*
import javax.sql.DataSource
import java.sql.Connection
import java.sql.statement

class PunishmentManager(private val dataSource: DataSource) {

    init {
        createTable()
    }

    private fun createTable() {
        dataSource.connection.use { conn ->}
            con.createStatement().use { st: Statement ->}
                st.executeUpdate(
                    """"
                    CREATE TABLE IF NOT EXISTS punishments (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        player UUID NOT NULL,
                        type VARCHAR(10) NOT NULL,
                        reason TEXT NOT NULL,
                        staff UUID NOT NULL,
                        createdAt BIGINT NOT NULL,
                        expiresAt BIGINT NOT NULL
                    )""".trimIndent()
                )
    }

    fun add
}