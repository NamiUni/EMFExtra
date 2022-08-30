plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.github.namiuni"
version = "0.1.3"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")

    // Paper
    maven("https://repo.papermc.io/repository/maven-public/")

    // papi
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    // Guice
    implementation("com.google.inject", "guice", "5.1.0")

    // Paper
    compileOnly("io.papermc.paper", "paper-api", "1.19.2-R0.1-SNAPSHOT")

    // Config
    implementation("org.spongepowered", "configurate-hocon", "4.1.2")
    implementation("net.kyori", "adventure-serializer-configurate4", "4.11.0")

    // Config
    implementation("cloud.commandframework", "cloud-paper", "1.7.0")

    // EventAPI
    implementation("net.kyori", "event-api","5.0.0-SNAPSHOT")

    // EMF
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // papi
    compileOnly("me.clip", "placeholderapi", "2.11.2")
}

bukkit {
    name = rootProject.name
    version = project.version as String
    main = "${group}.emfextra.EMFExtra"
    apiVersion = "1.19"
    depend = listOf("EvenMoreFish", "PlaceholderAPI")
    description = "Even More Fish's little extra features"
    author = "Unitarou"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        this.archiveClassifier.set(null as String?)
    }

    withType<xyz.jpenilla.runpaper.task.RunServerTask> {
        this.minecraftVersion("1.19.2")
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}
