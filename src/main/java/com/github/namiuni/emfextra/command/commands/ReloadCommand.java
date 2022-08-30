package com.github.namiuni.emfextra.command.commands;

import cloud.commandframework.CommandManager;
import com.github.namiuni.emfextra.EMFExtra;
import com.github.namiuni.emfextra.command.EMFECommand;
import com.github.namiuni.emfextra.event.events.EMFEReloadEvent;
import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class ReloadCommand extends EMFECommand {

    final EMFExtra emfExtra;
    final CommandManager<CommandSender> commandManager;

    @Inject
    public ReloadCommand(
            final EMFExtra emfExtra,
            final CommandManager<CommandSender> commandManager
    ) {
        this.emfExtra = emfExtra;
        this.commandManager = commandManager;
    }

    @Override
    public void init() {
        final var command = this.commandManager.commandBuilder("emfextra", "emfe")
                .literal("reload")
                .permission("emfextra.reload")
                .senderType(CommandSender.class)
                .handler(handler -> {
                    this.emfExtra.eventHandler().emit(new EMFEReloadEvent());
                    handler.getSender().sendRichMessage("<red>[EMFE] Config reloaded.");
                })
                .build();

        this.commandManager.command(command);
        this.commandManager.executeCommand(Bukkit.getConsoleSender(), "emfe reload");
    }
}
