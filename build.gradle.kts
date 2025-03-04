plugins {
    application
    alias(libs.plugins.javafx)
}

repositories {
    mavenCentral()
}

group = "me.dovias"
version = "1.0-SNAPSHOT"

application {
    mainClass = "me.dovias.battlecity.Bootstrap"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

javafx {
    version = libs.versions.javafx.asProvider().get()
    modules = listOf("javafx.controls", "javafx.fxml")
}