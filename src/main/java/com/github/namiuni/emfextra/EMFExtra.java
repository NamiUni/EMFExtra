package com.github.namiuni.emfextra;

import com.github.namiuni.emfextra.command.EMFECommand;
import com.github.namiuni.emfextra.command.commands.DebugCommand;
import com.github.namiuni.emfextra.command.commands.ReloadCommand;
import com.github.namiuni.emfextra.config.ConfigFactory;
import com.github.namiuni.emfextra.event.EMFEEventHandler;
import com.github.namiuni.emfextra.listener.FishCompetitionListener;
import com.github.namiuni.emfextra.listener.FishShopSignListener;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class EMFExtra extends JavaPlugin {

    private static final Set<Class<? extends Listener>> Bukkit_LISTENER_CLASSES = Set.of(
            FishShopSignListener.class, FishCompetitionListener.class
    );

    public static final List<Class<? extends EMFECommand>> COMMAND_CLASSES = List.of(
            ReloadCommand.class, DebugCommand.class
    );

    private final EMFEEventHandler eventHandler = new EMFEEventHandler();
    private @MonotonicNonNull Injector injector;

    @Override
    public void onLoad() {
        this.injector = Guice.createInjector(new EMFExtraModule(this, this.dataDirectory()));
    }

    @Override
    public void onEnable() {

        for (final Class<? extends Listener> listenerClass : Bukkit_LISTENER_CLASSES) {
            var listener = this.injector.getInstance(listenerClass);
            this.getServer().getPluginManager().registerEvents(listener, this);
        }

        for (final Class<? extends EMFECommand> commandClass : COMMAND_CLASSES) {
            var command = this.injector.getInstance(commandClass);
            command.init();
        }
    }

    public @NonNull EMFEEventHandler eventHandler() {
        return this.eventHandler;
    }

    public Path dataDirectory() {
        return this.getDataFolder().toPath();
    }

    public boolean papiLoaded() {
        return Bukkit.getPluginManager().isPluginEnabled("EvenMoreFish");
    }
}
