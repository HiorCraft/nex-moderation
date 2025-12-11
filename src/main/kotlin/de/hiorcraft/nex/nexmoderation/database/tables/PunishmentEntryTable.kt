package de.hiorcraft.nex.nexmoderation.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object PunishmentEntryTable : IntIdTable("punishments_entries") {
    val player = uuid("player")
    val type = varchar("type", 4)
    val reason = varchar("reason", 255)
    val staff = uuid("staff")
    val createdAt = long("created_at")
    val expiresAt = long("expires_at")
}
