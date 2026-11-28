#!/usr/bin/env zsh

./gradlew sortDependencies versionCatalogFormat ktlintFormat
./gradlew ktlintGenerateBaseline updateLintBaseline detektBaseline
./gradlew createModuleGraph
./gradlew lint projectHealth
