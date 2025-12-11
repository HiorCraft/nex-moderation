import dev.slne.surf.surfapi.gradle.util.withSurfApiBukkit

plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

dependencies {
    api("dev.slne.surf:surf-database:2.2.1-SNAPSHOT")
}

group = "de.hiorcraft.nex"
version = findProperty("version") as String

surfPaperPluginApi {
    mainClass("de.hiorcraft.nex.nexmoderation.NexModeration")
    generateLibraryLoader(false)
    authors.add("HiorCraft")

    runServer {
        withSurfApiBukkit()
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
    }
}