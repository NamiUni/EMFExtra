package com.github.namiuni.emfextra.config;

import com.google.gson.annotations.SerializedName;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;

@ConfigSerializable
public final class Webhook {
    private String username = "";
    @SerializedName("avatar_url") private String avatarUrl = "";
    private String content = "";
    private List<Embed> embeds = List.of(new Embed());

    public String username() {
        return this.username;
    }

    public String avatarUrl() {
        return this.avatarUrl;
    }

    public String content() {
        return this.content;
    }

    public List<Embed> embeds() {
        return this.embeds;
    }

    @ConfigSerializable
    public static class Embed {
        private String title = "🎉 さかな釣り大会結果発表 🎉";
        private String url = "";
        private int color = 12892415;
        private String description = "1位は %emf_competition_place_size_1%cm の %emf_competition_place_fish_1% を釣り上げた %emf_competition_place_player_1% さんです！おめでとうございます！！";
        private Author author = new Author();
        private Image image = new Image();
        private Thumbnail thumbnail = new Thumbnail();
        private Footer footer = new Footer();
        private List<Field> fields = List.of(new Field());

        public String title() {
            return this.title;
        }

        public String url() {
            return this.url;
        }

        public int color() {
            return this.color;
        }

        public String description() {
            return this.description;
        }

        public Author author() {
            return this.author;
        }

        public Image image() {
            return this.image;
        }

        public Thumbnail thumbnail() {
            return this.thumbnail;
        }

        public Footer footer() {
            return this.footer;
        }

        public List<Field> fields() {
            return this.fields;
        }

        @ConfigSerializable
        public static class Author {
            private String name = "";
            @SerializedName("icon_url") private String iconUrl = "";

            public String name() {
                return this.name;
            }

            public String iconUrl() {
                return this.iconUrl;
            }
        }

        @ConfigSerializable
        public static class Image {
            private String url = "https://media.discordapp.net/attachments/576377377161216010/670677714964840489/taikai.png";

            public String url() {
                return this.url;
            }
        }

        @ConfigSerializable
        public static class Thumbnail {
            private String url = "https://cravatar.eu/helmhead/%emf_competition_place_player_1%/128.png";

            public String url() {
                return this.url;
            }
        }

        @ConfigSerializable
        public static class Footer {
            private String text = "";

            public String text() {
                return this.text;
            }
        }

        @ConfigSerializable
        public static class Field {
            private String name = ":one: %emf_competition_place_player_1%";
            private String value = "%emf_competition_place_fish_1%";
            boolean inline = false;

            public String name() {
                return this.name;
            }

            public String value() {
                return this.value;
            }

            public boolean inline() {
                return this.inline;
            }
        }
    }
}
