import org.apache.tools.ant.taskdefs.condition.Os
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.utils.addToStdlib.ifTrue
import org.jetbrains.kotlin.utils.addToStdlib.runIf

plugins {
    kotlin("jvm") version "2.0.21"
}

allprojects {
    repositories {
        mavenCentral()
    }

    tasks {
        withType<Jar> {
            enabled = false
        }

        withType<War> {
            enabled = false
        }
    }
}

subprojects {
    runIf(project.buildFile.exists()) {
        apply {
            plugin("org.jetbrains.kotlin.jvm")
        }

        java.sourceCompatibility = JavaVersion.VERSION_21

        dependencies {
            implementation(kotlin("stdlib"))
            implementation(kotlin("reflect"))

            Os.isFamily(Os.FAMILY_MAC).ifTrue {
                when {
                    Os.isArch("x86_64") -> "osx-x86_64"
                    Os.isArch("aarch64") -> "osx-aarch_64"
                    else -> null
                }?.let { classifier ->
                    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.115.Final:${classifier}")
                }
            }
        }

        tasks {
            withType<KotlinCompile> {
                compilerOptions {
                    freeCompilerArgs.add("-Xjsr305=strict")
                    jvmTarget.set(JvmTarget.JVM_21)
                }
            }
        }
    }
}



