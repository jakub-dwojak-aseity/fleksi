plugins {
    java
    groovy
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.openapi.generator") version "7.8.0"
    id("com.avast.gradle.docker-compose") version "0.17.7"
}

group = "pl.com.aseity"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")
    
    // OpenAPI generated code dependencies
    implementation("io.swagger.core.v3:swagger-annotations:2.2.22")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
    
    // Liquibase for database migrations
    implementation("org.liquibase:liquibase-core")
    
    // MapStruct for mapping DTOs
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    
    // QueryDSL for type-safe queries
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    
    // Spock Framework
    testImplementation("org.spockframework:spock-core:2.3-groovy-4.0")
    testImplementation("org.spockframework:spock-spring:2.3-groovy-4.0")
    
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("$rootDir/../openapi/fleksi.yml")
    outputDir.set("${layout.buildDirectory.get()}/generated/openapi")
    apiPackage.set("pl.com.aseity.generated.api")
    modelPackage.set("pl.com.aseity.generated.model")
    configOptions.set(mapOf(
        "interfaceOnly" to "true",
        "useSpringBoot3" to "true",
        "delegatePattern" to "true",
        "dateLibrary" to "java8",
        "useBeanValidation" to "true",
        "useTags" to "true",
        "useOptional" to "false",
        "reactive" to "false"
    ))
}

sourceSets {
    main {
        java {
            srcDirs("${layout.buildDirectory.get()}/generated/openapi/src/main/java")
        }
    }
}

tasks.compileJava {
    dependsOn(tasks.openApiGenerate)
}

dockerCompose {
    useComposeFiles.set(listOf("../infra/docker-compose.yml"))
    startedServices.set(listOf("postgres", "redis"))
    waitForTcpPorts.set(false)
}

tasks.named("bootRun") {
    dependsOn("composeUp")
}

tasks.named("test") {
    dependsOn("composeUp")
}