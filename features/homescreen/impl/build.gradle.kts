import com.android.build.api.dsl.LibraryExtension

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

configure<LibraryExtension> {
    namespace = "com.brokenkernel.didacticdisco.homescreen.impl"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 23

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
    implementation(enforcedPlatform(libs.androidx.compose.bom))
    implementation(enforcedPlatform(libs.kotlin.bom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime.android)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation3.runtime)
    implementation(projects.components)
    implementation(projects.coreinfra)
    implementation(projects.features.homescreen.api)
    implementation(projects.features.spectogram.api)

    runtimeOnly(libs.kotlinx.coroutines.android)

    androidTestRuntimeOnly(libs.androidx.loader)
    androidTestRuntimeOnly(libs.androidx.runner)

    testImplementation(libs.androidx.compose.ui.unit)
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.junit)

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
