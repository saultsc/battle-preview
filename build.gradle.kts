import org.gradle.kotlin.dsl.maven
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
  kotlin("jvm") version "2.2.0"
  id("fabric-loom") version "1.11-SNAPSHOT"
  id("maven-publish")
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

base {
  archivesName.set(project.property("archives_base_name") as String)
}

val targetJavaVersion = 21
java {
  toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
  // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
  // if it is present.
  // If you remove this line, sources will not be generated.
  withSourcesJar()
}

loom {
  splitEnvironmentSourceSets()

  mods {
    register("kotlintemplate") {
      sourceSet("main")
    }
  }
}

repositories {
  mavenCentral()
  gradlePluginPortal()
  maven(url = "https://maven.impactdev.net/repository/development/")
  maven(url = "https://jitpack.io")
  maven(url = "https://cursemaven.com")
  maven(url = "https://maven.fabricmc.net/")
  maven(url = "https://maven.architectury.dev/")
  maven(url = "https://maven.ladysnake.org/releases")
  maven(url = "https://repo.maven.apache.org/maven2/")
  maven(url = "https://repo.spongepowered.org/maven/")
  maven(url = "https://repo.essentialsx.net/releases/")
  maven(url = "https://maven.neoforged.net/releases/")
  maven(url = "https://files.minecraftforge.net/maven/")
  maven(url = "https://thedarkcolour.github.io/KotlinForForge/")
  maven(url = "https://papermc.io/repository/maven-public/")
  maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
  maven(url = "https://repo.extendedclip.com/content/repositories/placeholderapi/")
  maven(url = "https://maven.nucleoid.xyz/") {
    name = "Nucleoid"
  }
  maven(url = "https://oss.sonatype.org/content/repositories/snapshots") {
    name = "Sonatype Snapshots"
  }
  maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots") {
    name = "Sonatype 01 Snapshots"
  }
  maven(url = "https://repos.cobbleworldmmo.com/snapshots") {
    name = "cwmmoRepositorySnapshots"
  }
}

dependencies {
  // To change the versions see the gradle.properties file
  minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
  mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
  modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
  modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")
  modImplementation("net.fabricmc:fabric-language-kotlin:${project.property("kotlin_loader_version")}")

  // Cobblemon
  modImplementation("com.cobblemon:fabric:${property("cobblemon_version")}")

  // Goeylibs
  modImplementation("ca.landonjw.gooeylibs:fabric-api-repack:${property("gooeylibs_version")}")

  // Kyory Adventure
  implementation("net.kyori:adventure-text-minimessage:${property("kyori_version")}")
  implementation("net.kyori:adventure-text-serializer-gson:${property("kyori_version")}")
  implementation("net.kyori:adventure-text-serializer-legacy:${property("kyori_version")}")
  // CWMMO Utils
  modImplementation( "com.cobbleworldmmo:cwmmoutils:${property("cwmmoutils_version")}")

  // Custom CWMMO UTILS
  // modImplementation(fileTree("libs") { include("*.jar") })
}

tasks.processResources {
  inputs.property("version", project.version)
  inputs.property("minecraft_version", project.property("minecraft_version"))
  inputs.property("loader_version", project.property("loader_version"))
  filteringCharset = "UTF-8"

  filesMatching("fabric.mod.json") {
    expand(
      "version" to project.version,
      "minecraft_version" to project.property("minecraft_version"),
      "loader_version" to project.property("loader_version"),
      "kotlin_loader_version" to project.property("kotlin_loader_version"),
      "cobblemon_version" to project.property("cobblemon_version"),
    )
  }
}

tasks.withType<JavaCompile>().configureEach {
  // ensure that the encoding is set to UTF-8, no matter what the system default is
  // this fixes some edge cases with special characters not displaying correctly
  // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
  // If Javadoc is generated, this must be specified in that task too.
  options.encoding = "UTF-8"
  options.release.set(targetJavaVersion)
}

tasks.withType<KotlinCompile>().configureEach {
  compilerOptions.jvmTarget.set(JvmTarget.fromTarget(targetJavaVersion.toString()))
}


// load local properties
val localProperties = Properties()
val localPropertiesFile = rootProject.file("gradle-local.properties")
if (localPropertiesFile.exists()) {
  localProperties.load(localPropertiesFile.inputStream())
}
tasks.jar {
  from("LICENSE") {
    rename { "${it}_${project.base.archivesName}" }
  }
  // grab path if available, otherwise default
  destinationDirectory.set(file(localProperties.getProperty("jar.output.dir", "build/libs")))
}

// configure the maven publication
publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      artifactId = project.property("archives_base_name") as String
      from(components["java"])
    }
  }

  // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
  repositories {
    // Add repositories to publish to here.
    // Notice: This block does NOT have the same function as the block in the top level.
    // The repositories here will be used for publishing your artifact, not for
    // retrieving dependencies.
  }
}
