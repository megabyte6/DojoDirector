plugins {
    kotlin("jvm") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.3.0"
}

group = "com.megabyte6"
version = "2.1.1"
var mcVersion = "1.21"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(21)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.shadowJar {
    archiveBaseName.set(rootProject.name)
    archiveAppendix.set("mc$mcVersion")
    archiveClassifier.set("")
}

tasks.runServer {
    minecraftVersion("1.21")
}
