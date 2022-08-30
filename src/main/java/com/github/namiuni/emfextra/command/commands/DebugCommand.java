package com.github.namiuni.emfextra.command.commands;

import cloud.commandframework.CommandManager;
import com.github.namiuni.emfextra.EMFExtra;
import com.github.namiuni.emfextra.command.EMFECommand;
import com.github.namiuni.emfextra.config.ConfigFactory;
import com.google.gson.Gson;
import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class DebugCommand extends EMFECommand {

    final EMFExtra emfExtra;
    final CommandManager<CommandSender> commandManager;
    final ConfigFactory configFactory;

    @Inject
    public DebugCommand(
            final EMFExtra emfExtra,
            final CommandManager<CommandSender> commandManager,
            final ConfigFactory configFactory
    ) {
        this.emfExtra = emfExtra;
        this.commandManager = commandManager;
        this.configFactory = configFactory;
    }

    @Override
    public void init() {
        final var command = this.commandManager.commandBuilder("emfextra", "emfe")
                .literal("debug")
                .permission("emfextra.debug")
                .senderType(CommandSender.class)
                .handler(handler -> {
                    var gson = new Gson();
                    var webhook = gson.toJson(configFactory.primaryConfig().webhookSettings());
                    Bukkit.broadcast(MiniMessage.miniMessage().deserialize(webhook));
                })
                .build();

        this.commandManager.command(command);
    }
}
