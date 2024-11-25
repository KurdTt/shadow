package com.github.jengelman.gradle.plugins.shadow.relocation

/**
 * Marks that a given instance of [Relocator] is compatible with the Gradle build cache.
 * In other words, it has its appropriate inputs annotated so that Gradle can consider them when
 * determining the cache key.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
public annotation class CacheableRelocator