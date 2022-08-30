package com.github.namiuni.emfextra.listener;

import com.github.namiuni.emfextra.config.ConfigFactory;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.oheers.fish.api.EMFCompetitionEndEvent;
import com.oheers.fish.competition.CompetitionType;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FishCompetitionListener implements Listener {

    final ConfigFactory configFactory;

    @Inject
    public FishCompetitionListener(
            ConfigFactory configFactory
    ) {
        this.configFactory = configFactory;
    }

    @EventHandler
    public void onCompetitionEnd(EMFCompetitionEndEvent event) {
        if (configFactory.primaryConfig().webhookSettings().enabled() &&
                configFactory.primaryConfig().webhookSettings().webhooks().containsKey(event.getCompetition().getCompetitionType())
        ) {
            var webhook = createWebhook(event.getCompetition().getCompetitionType());
            sendWebhook(configFactory.primaryConfig().webhookSettings().webhookUrl(), webhook);
        }
    }

    private String createWebhook(CompetitionType competitionType) {
        var webhook = configFactory.primaryConfig().webhookSettings().webhooks().get(competitionType);
        var gson = new Gson();
        var json = gson.toJson(webhook);
        return PlaceholderAPI.setPlaceholders(Bukkit.getOnlinePlayers().stream().findFirst().get(), ChatColor.stripColor(json));
    }

    private void sendWebhook(String uri, String body) {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }
}
