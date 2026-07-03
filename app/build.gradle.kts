import com.android.build.api.dsl.ApplicationExtension

plugins {
    alias(libs.plugins.aboutLibraries)
    alias(libs.plugins.aboutLibrariesAndroid)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.sortDependencies)
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.ktlint)
    id("org.jetbrains.dokka")
    alias(libs.plugins.detekt)
    kotlin("plugin.power-assert") version libs.versions.kotlin.get()
}

configure<ApplicationExtension> {
    namespace = "com.brokenkernel.didacticdisco"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.brokenkernel.didacticdisco"
        minSdk = 23
        targetSdk = 37
        versionCode = 1
        versionName = "0.0.$versionCode"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
        debug {
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    dependenciesInfo {
        includeInApk = true
        includeInBundle = true
    }
    lint {
        lintConfig = file("lint.xml")
        baseline = file("lint-baseline.xml")
        checkDependencies = true
        warningsAsErrors = true
    }
    packaging {
        jniLibs {
            // unable to strip, so shut up warnings
            keepDebugSymbols.add("**/libandroidx.graphics.path.so")
            keepDebugSymbols.add("**/libdatastore_shared_counter.so")
            keepDebugSymbols.add("**/libgraphics-core.so")
            keepDebugSymbols.add("**/libink.so")
        }
    }
    androidResources {
//        generateLocaleConfig = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(projects.components)
    implementation(projects.coreinfra)
    implementation(projects.features.homescreen)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}
