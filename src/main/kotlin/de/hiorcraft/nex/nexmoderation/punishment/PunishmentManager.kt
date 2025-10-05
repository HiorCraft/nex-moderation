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

    fun addPunishment(p: Punishment) {
        dataSource.connection.use { con ->
            con.prepareStatement(
                "INSERT INTO punishments (player_uuid, type, reason, staff_uuid, created_at, expires_at) VALUES (?, ?, ?, ?, ?, ?)"
                ps.setString(1, uuid.toString())
                ps.setString(2, type.name)
                val rs = ps.executeQuery()
                while (rs.next()) {
                    val expires = rs.getLong("expires_at")
                    if (expires == -1L || expires > system.currentTimeMillis()) {
                        return Punishment(
                            uuid,
                            type,
                            rs.getString("reason"),
                            rs.getString("staff_uuid")?.let { UUID.fromString(it) },
                            rs.setString("created_at"),
                        )
                    }
                }
            )
        }
        return null
    }
}