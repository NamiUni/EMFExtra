package com.github.namiuni.emfextra.util;

import com.github.namiuni.emfextra.EMFExtra;
import com.oheers.fish.EvenMoreFish;
import com.oheers.fish.competition.AutoRunner;
import com.oheers.fish.competition.Competition;
import com.oheers.fish.competition.CompetitionType;
import com.oheers.fish.config.messages.ConfigMessage;
import com.oheers.fish.config.messages.Message;
import com.oheers.fish.fishing.items.Fish;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Objects;
import java.util.UUID;

public class EMFEExpansion extends PlaceholderExpansion {

    private final EMFExtra emfExtra;

    public EMFEExpansion(EMFExtra emfExtra) {
        this.emfExtra = emfExtra;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return emfExtra.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier() {
        return "emf";
    }

    @Override
    public String getVersion() {
        return emfExtra.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (player == null) {
            return "";
        }

        // %emf_competition_place_player_1% would return the player in first place of any possible competition.
        if (params.startsWith("competition_place_player_")) {
            if (Competition.isActive()) {
                // checking the leaderboard actually contains the value of place
                int place = Integer.parseInt(params.substring(25));
                if (EvenMoreFish.active.getLeaderboardSize() >= place) {
                    // getting "place" place in the competition
                    UUID uuid = EvenMoreFish.active.getLeaderboard().getPlayer(place);
                    if (uuid != null) {
                        // To be in the leaderboard the player must have joined
                        return Objects.requireNonNull(Bukkit.getOfflinePlayer(uuid)).getName();
                    }
                } else {
                    return new Message(ConfigMessage.PLACEHOLDER_NO_PLAYER_IN_PLACE).getRawMessage(true, false);
                }
            } else {
                return new Message(ConfigMessage.PLACEHOLDER_NO_COMPETITION_RUNNING).getRawMessage(true, false);
            }
        } else if (params.startsWith("competition_place_size_")) {
            if (Competition.isActive()) {
                if (EvenMoreFish.active.getCompetitionType() == CompetitionType.LARGEST_FISH) {
                    // checking the leaderboard actually contains the value of place
                    int place = Integer.parseInt(params.substring(23));
                    if (EvenMoreFish.active.getLeaderboardSize() >= place) {
                        // getting "place" place in the competition
                        float value = EvenMoreFish.active.getLeaderboard().getPlaceValue(place);

                        if (value != -1.0f) return Float.toString(value);
                        else return "";
                    } else {
                        return new Message(ConfigMessage.PLACEHOLDER_NO_PLAYER_IN_PLACE).getRawMessage(true, false);
                    }
                } else {
                    return new Message(ConfigMessage.PLACEHOLDER_SIZE_DURING_MOST_FISH).getRawMessage(true, false);
                }
            } else {
                return new Message(ConfigMessage.PLACEHOLDER_NO_COMPETITION_RUNNING).getRawMessage(true, false);
            }
        } else if (params.startsWith("competition_place_fish_")) {
            if (Competition.isActive()) {
                if (EvenMoreFish.active.getCompetitionType() == CompetitionType.LARGEST_FISH) {
                    // checking the leaderboard actually contains the value of place
                    int place = Integer.parseInt(params.substring(23));
                    if (EvenMoreFish.active.getLeaderboardSize() >= place) {
                        // getting "place" place in the competition
                        Fish fish = EvenMoreFish.active.getLeaderboard().getPlaceFish(place);
                        if (fish != null) {
                            Message message = new Message(ConfigMessage.PLACEHOLDER_FISH_FORMAT);
                            if (fish.getLength() == -1)
                                message.setMessage(ConfigMessage.PLACEHOLDER_FISH_LENGTHLESS_FORMAT);
                            else message.setLength(Float.toString(fish.getLength()));

                            message.setRarityColour(fish.getRarity().getColour());

                            if (fish.getDisplayName() != null) message.setFishCaught(fish.getDisplayName());
                            else message.setFishCaught(fish.getName());

                            if (fish.getRarity().getDisplayName() != null)
                                message.setRarity(fish.getRarity().getDisplayName());
                            else message.setRarity(fish.getRarity().getValue());

                            return message.getRawMessage(true, true);
                        }
                    } else {
                        return new Message(ConfigMessage.PLACEHOLDER_NO_PLAYER_IN_PLACE).getRawMessage(true, false);
                    }
                } else {
                    // checking the leaderboard actually contains the value of place
                    float value = Competition.leaderboard.getPlaceValue(Integer.parseInt(params.substring(23)));
                    if (value == -1)
                        return new Message(ConfigMessage.PLACEHOLDER_NO_PLAYER_IN_PLACE).getRawMessage(true, false);

                    Message message = new Message(ConfigMessage.PLACEHOLDER_FISH_MOST_FORMAT);
                    message.setAmount(Integer.toString((int) value));
                    return message.getRawMessage(true, true);
                }
            } else {
                return new Message(ConfigMessage.PLACEHOLDER_NO_COMPETITION_RUNNING).getRawMessage(true, false);
            }
        } else if (params.equals("competition_time_left")) {
            if (Competition.isActive()) {
                return new Message(ConfigMessage.PLACEHOLDER_TIME_REMAINING_DURING_COMP).getRawMessage(true, false);
            } else {
                int competitionStartTime = EvenMoreFish.competitionQueue.getNextCompetition();
                int currentTime = AutoRunner.getCurrentTimeCode();
                int remainingTime;

                if (competitionStartTime > currentTime) {
                    remainingTime = competitionStartTime - currentTime;
                } else {
                    // time left of the current week + the time next week until next competition
                    remainingTime = (10080 - currentTime) + competitionStartTime;
                }

                Message message = new Message(ConfigMessage.PLACEHOLDER_TIME_REMAINING);
                message.setDays(Integer.toString(remainingTime / 1440));
                message.setHours(Integer.toString((remainingTime % 1440) / 60));
                message.setMinutes(Integer.toString((((remainingTime % 1440) % 60) % 60)));

                return message.getRawMessage(true, true);
            }
        }

        // We return null if an invalid placeholder (f.e. %someplugin_placeholder3%)
        // was provided
        return null;
    }
}
