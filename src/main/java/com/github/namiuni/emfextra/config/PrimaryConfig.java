package com.github.namiuni.emfextra.config;

import com.oheers.fish.competition.CompetitionType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.Map;

@ConfigSerializable
public class PrimaryConfig {

    @Comment("""
            Words needed to recognize shop signs.
            Note that legacy color codes are not supported.
            Use minimessage.
            https://webui.adventure.kyori.net/
            permission
            place: emfe.sign.place
            use  : emfe.sign.use
            """)
    private Component signName = MiniMessage.miniMessage().deserialize("<aqua>[EMFShop]</aqua>");

    @Comment("Configuration for webhook")
    private WebhookSettings webhookSettings = new WebhookSettings();

    public Component signName() {
        return this.signName;
    }

    public WebhookSettings webhookSettings() {
        return webhookSettings;
    }

    @ConfigSerializable
    public static class WebhookSettings {

        @Comment("If you do not want to send webhooks, set the value to false.")
        private boolean enabled = true;

        @Comment("""
            Enter here the webhook URL of the channel to which you want to send the competition results.
            https://support.discord.com/hc/en-us/articles/228383668-Intro-to-Webhooks
            """)
        private String webhookUrl = "https://discordapp.com/api/webhooks/000000000000000000/abcdefghijklmnopqrstuvwxyz";

        @Comment("""
            Format per competition.
            e.g. LARGEST_FISH, SPECIFIC_FISH, MOST_FISH, SPECIFIC_RARITY, LARGEST_TOTAL
            """)
        private Map<CompetitionType, Webhook> webhooks = Map.of(CompetitionType.LARGEST_FISH, new Webhook());

        public boolean enabled() {
            return this.enabled;
        }

        public String webhookUrl() {
            return this.webhookUrl;
        }

        public Map<CompetitionType, Webhook> webhooks() {
            return this.webhooks;
        }
    }
}
