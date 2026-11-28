pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("com.android.settings") version "9.5.3" apply false
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("com.android.settings")
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("NO_IMPLICIT_LOOKUP_IN_PARENT_PROJECTS")
enableFeaturePreview("ENHANCED_GRAPH_ORDERING")

android {
    compileSdk {
        version = release(37) {
            minorApiLevel = 2
        }
    }
    minSdk {
        version = release(24)
    }
    targetSdk {
        version = release(37)
    }
}

rootProject.name = "DidacticDisco"
include(":app")
include(":coreinfra")
include(":components")
include(":features:homescreen:api")
include(":features:homescreen:impl")
include(":features:spectogram:api")
include(":features:spectogram:impl")
include(":features:debug:api")
include(":features:debug:impl")
