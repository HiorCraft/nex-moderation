package de.hiorcraft.nex.nexmoderation.punishment

import java.util.*

data class Punishment(
    val player: UUID,
    val type: PunishmentType,
    val reason: String,
    val staff: UUID,
    val createdAt: Long,
    val expiresAt: Long // -1 for permanent

) {
    fun isActive(): Boolean {
        return expiresAt == -1L || System.currentTimeMillis() < expiresAt
    }
}