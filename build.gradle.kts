plugins {
    id("java")
    id("org.openjfx.javafxplugin") version "0.1.0"
}


group = "de.Roboter007"
version = "0.1.0-Alpha"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

javafx {
    version = "22.0.2"
    modules = listOf("javafx.media")
}


tasks.test {
    useJUnitPlatform()
}