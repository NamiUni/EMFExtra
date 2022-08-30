package com.github.namiuni.emfextra.listener;

import com.github.namiuni.emfextra.config.ConfigFactory;
import com.google.inject.Inject;
import com.oheers.fish.selling.SellGUI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class FishShopSignListener implements Listener {

    final ConfigFactory configFactory;

    @Inject
    public FishShopSignListener(
            ConfigFactory configFactory
    ) {
        this.configFactory = configFactory;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (event.line(0) == null) return;

        var plainShopName = PlainTextComponentSerializer.plainText().serialize(configFactory.primaryConfig().signName());
        var plainSignText = PlainTextComponentSerializer.plainText().serialize(Objects.requireNonNull(event.line(0)));

        if (plainShopName.equals(plainSignText)) {
            var player = event.getPlayer();
            if (player.hasPermission("emfe.sign.place")) {
                event.line(0, configFactory.primaryConfig().signName());
                player.sendRichMessage("<green>[EMFE] Fish shop sign created.");
            } else {
                event.line(0, MiniMessage.miniMessage().deserialize("Permission err"));
                player.sendRichMessage("<red>[EMFE] You do not have the permission.");
            }
        }
    }

    @EventHandler
    public void onClickedSign(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK &&
                event.getClickedBlock().getState() instanceof Sign sign &&
                event.getPlayer().hasPermission("emfe.sign.use") &&
                sign.line(0).equals(Objects.requireNonNull(configFactory.primaryConfig()).signName())) {

            new SellGUI(event.getPlayer());
        }
    }
}
