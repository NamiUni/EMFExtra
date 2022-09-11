package com.github.namiuni.emfextra;

import cloud.commandframework.CommandManager;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.nio.file.Path;
import java.util.function.Function;

@DefaultQualifier(NonNull.class)
public class EMFExtraModule extends AbstractModule {
    private final EMFExtra emfExtra;
    private final Path dataDirectory;

    public EMFExtraModule(
            final EMFExtra emfExtra,
            final Path dataDirectory
    ) {
        this.emfExtra = emfExtra;
        this.dataDirectory = dataDirectory;
    }

    @Provides
    @Singleton
    public CommandManager<CommandSender> commandManager() {
        final PaperCommandManager<CommandSender> commandManager;

        try {
            commandManager = new PaperCommandManager<>(
                    this.emfExtra,
                    AsynchronousCommandExecutionCoordinator.<CommandSender>newBuilder().build(),
                    Function.identity(),
                    Function.identity()
            );
        } catch (final Exception ex) {
            throw new RuntimeException("Failed to initialize command manager.", ex);
        }

        return commandManager;
    }

    @Override
    public void configure() {
        this.bind(EMFExtra.class).toInstance(this.emfExtra);
        this.bind(Path.class).toInstance(this.dataDirectory);
    }
}
