package de.hiorcraft.nex.nexmoderation.commands

import com.github.shynixn.mccoroutine.folia.launch
import de.hiorcraft.nex.nexmoderation.database.PunishmentManager
import de.hiorcraft.nex.nexmoderation.plugin
import de.hiorcraft.nex.nexmoderation.punishment.Punishment
import de.hiorcraft.nex.nexmoderation.punishment.PunishmentType
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit

fun banCommand() = commandTree("playerban") {
    StringArgument("player")
    StringArgument("reason")

    playerExecutor { player, args ->
        val targetName: String by args
        val reason = args[1] as String
        val target = Bukkit.getOfflinePlayer(targetName)

        if (!target.hasPlayedBefore()) {
            player.sendText {
                appendPrefix()
                info("Der Spieler §c$targetName §fwurde nicht gefunden.")
            }
            return@playerExecutor
        }

        val punishment = Punishment(
            player = target.uniqueId,
            type = PunishmentType.BAN,
            reason = reason,
            staff = player.uniqueId,
            createdAt = System.currentTimeMillis(),
            expiresAt = -1L
        )

        plugin.launch {
            PunishmentManager.savePunishment(punishment)
            player.sendText {
                appendPrefix()
                success("Der Spieler §a$targetName §fwurde erfolgreich gebannt.")
            }
            Bukkit.broadcast(Component.text("§c$targetName wurde gebannt: $reason"))
        }
    }
}

