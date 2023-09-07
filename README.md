# Clean Architecture Android Application Template

## Overview

A Jetpack Compose Android App

## Requirements

- Android Studio (Recommended: Android Studio Giraffe 2022.3.1)

- Kotlin

- JVM version 11

## Config environment

- Replace ***local.properties*** file to project folder.

## Config google services

- Move ***google-services.json*** files to:
    - /app/src/dev/google-services.json
    - /app/src/qa/google-services.json
    - /app/src/staging/google-services.json
    - /app/src/production/google-services.json

## For release build

- Move ***keystore.jks*** file to /app/production/keystore.jks.

## How to use lib, common at template

- [Readme Common](README_COMMON.md)

## Coding conventions

- [Readme Conventions](README_CONVENTIONS.md)
