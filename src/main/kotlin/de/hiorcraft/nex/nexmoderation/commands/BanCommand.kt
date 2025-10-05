package de.hiorcraft.nex.nexmoderation.commands

import de.hiorcraft.nex.nexmoderation.NexModeration
import de.nexoria.moderation.punishment.Punishment
import de.nexoria.moderation.punishment.PunishmentManager
import de.nexoria.moderation.punishment.PunishmentType
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.GreedyStringArgument
import dev.jorel.commandapi.arguments.PlayerArgument
import net.kyori.adventure.text.Component
import org.bukkit.entity.Playerer
import org.bukkit.Bukkit

class  BanCommand(private val manager: PunishmentManager) {

    fun register() {

        commandAPIcommand("ban")

            .withArguments(PlayerArgument("target"))
            .withArguments(GreedyStringArgument("reason"))
            .executes { sender, args ->
                val target = args[0] as Player
                val reason = args[1] as String

                val punishment = Punishment(
                    target.uniqueID,
                    PunishmentType.BAN,
                    reason,
                    if (sender is Player) sender.uniqueID else NexModeration.instance.server.consoleSender.uniqueID,
                    System.currentTimeMillis(),
                    -1L

                )

                manager.addPunishment(punishment)

                val msg = ModerationPlugin.instance.config
                    .getString("messages.ban")
                    ?.replace("{player}", target.name)
                    ?.replace("{reason}", reason)
                    ?.let { Component.text(it) } ?: Component.text("You have been banned for: $reason")

    }
}