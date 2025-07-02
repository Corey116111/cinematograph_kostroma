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
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {  url = uri("https://maven.yandex.ru/repository/yandex-mobile-releases/") }
    }
}

rootProject.name = "intensiv2"
include(":app")
 