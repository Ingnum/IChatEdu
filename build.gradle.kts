import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "net.ingnum"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven ("https://papermc.io/repo/repository/maven-public/")
    maven ("https://oss.sonatype.org/content/groups/public/")
    maven ("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("com.github.hazae41", "mc-kutils", "master-SNAPSHOT")
    compileOnly(dependencyNotation = "io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        archiveBaseName.set("IChatEdu")
        archiveVersion.set(project.version.toString())
        archiveClassifier.set("")

        mergeServiceFiles()

        manifest {
            attributes(mapOf("Main-Class" to "net.ingnum.ichatedu.IChatEduKt"))
        }
    }

    processResources {
        filteringCharset = "UTF-8"
        from(sourceSets["main"].resources.srcDirs) {
            include("**/*.yml")
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
            filter<ReplaceTokens>("tokens" to mapOf("version" to project.version))
            filter<ReplaceTokens>("tokens" to mapOf("name" to "IceCat"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}