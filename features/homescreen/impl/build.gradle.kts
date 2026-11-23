plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.sortDependencies)
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    kotlin("plugin.power-assert") version libs.versions.kotlin.get()
}

android {
    namespace = "com.brokenkernel.didacticdisco.homescreen.impl"
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
    api(libs.androidx.compose.runtime.android)
    api(libs.androidx.foundation.layout)
    api(libs.androidx.navigation3.runtime)

    implementation(enforcedPlatform(libs.androidx.compose.bom))
    implementation(enforcedPlatform(libs.kotlin.bom))
    implementation(projects.features.homescreen.api)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.lifecycle.runtime.compose)

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

    lintChecks(libs.android.securityLint)
    lintChecks(libs.androidx.lint.gradle)
    lintChecks(libs.slack.lint.checks)
    lintChecks(libs.slack.lint.checks.compose)
}

composeCompiler {
    includeSourceInformation = true
    includeTraceMarkers = true
    featureFlags = setOf()
}

kotlin {
    compilerOptions {
        allWarningsAsErrors = true
    }
    explicitApi()
}
