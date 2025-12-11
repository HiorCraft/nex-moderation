package de.hiorcraft.nex.nexmoderation.database

import de.hiorcraft.nex.nexmoderation.database.tables.PunishmentEntryTable
import de.hiorcraft.nex.nexmoderation.punishment.Punishment
import dev.slne.surf.database.DatabaseManager
import dev.slne.surf.database.database.DatabaseProvider
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.nio.file.Path
import java.util.UUID

object PunishmentManager {

    lateinit var databaseProvider: DatabaseProvider

    fun connect(path: Path) {
        databaseProvider = DatabaseManager(path, path).databaseProvider
        databaseProvider.connect()
    }

    fun disconnect() {
        databaseProvider.disconnect()
    }

    fun createTables() {
        transaction {
            SchemaUtils.create(PunishmentEntryTable)
        }
    }

   suspend fun savePunishment(data: Punishment) = newSuspendedTransaction(Dispatchers.IO) {
       PunishmentEntryTable.insert {
           it[player] = data.player
           it[type] = data.type.name
           it[reason] = data.reason
           it[staff] = data.staff
           it[createdAt] = data.createdAt
           it[expiresAt] = data.expiresAt
       }
   }

    suspend fun loadPunishment(playerUuid: UUID) = newSuspendedTransaction(Dispatchers.IO) {
        PunishmentEntryTable.selectAll().where(PunishmentEntryTable.player eq playerUuid).map {
            Punishment(
                it[PunishmentEntryTable.player],
                de.hiorcraft.nex.nexmoderation.punishment.PunishmentType.valueOf(it[PunishmentEntryTable.type]),
                it[PunishmentEntryTable.reason],
                it[PunishmentEntryTable.staff],
                it[PunishmentEntryTable.createdAt],
                it[PunishmentEntryTable.expiresAt]
            )
        }
    }

    suspend fun loadPunishments() = newSuspendedTransaction(Dispatchers.IO) {
        PunishmentEntryTable.selectAll().map {
            Punishment(
                it[PunishmentEntryTable.player],
                de.hiorcraft.nex.nexmoderation.punishment.PunishmentType.valueOf(it[PunishmentEntryTable.type]),
                it[PunishmentEntryTable.reason],
                it[PunishmentEntryTable.staff],
                it[PunishmentEntryTable.createdAt],
                it[PunishmentEntryTable.expiresAt]
            )
        }
    }
}