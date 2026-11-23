plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.sortDependencies)
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.parcelize)
    kotlin("plugin.power-assert") version libs.versions.kotlin.get()
}

android {
    namespace = "com.brokenkernel.didacticdisco.homescreen.api"
    compileSdk {
        version =
            release(37) {
                minorApiLevel = 2
            }
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    lint {
        lintConfig = file("lint.xml")
        baseline = file("lint-baseline.xml")
        checkDependencies = true
        warningsAsErrors = true
    }
}

dependencies {
    api(projects.coreinfra)

    implementation(enforcedPlatform(libs.androidx.compose.bom))
    implementation(enforcedPlatform(libs.kotlin.bom))
    implementation(libs.kotlin.parcelize.runtime)

    runtimeOnly(libs.kotlinx.coroutines.android)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.junit)

    androidTestRuntimeOnly(libs.androidx.loader)
    androidTestRuntimeOnly(libs.androidx.runner)

    detektPlugins(libs.composeDetektRules)
    detektPlugins(libs.detektRulesLibraries)

    ktlintRuleset(libs.ktlintCompose)

    lintChecks(libs.bundles.androidLintChecks)
}

kotlin {
    compilerOptions {
        allWarningsAsErrors = true
    }
    explicitApi()
}
