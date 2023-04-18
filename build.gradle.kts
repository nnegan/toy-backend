import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.8.0"
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version "1.7.20"
}

group = "com.toy"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.experimental:spring-modulith-bom:0.2.0")
    }
}

dependencies {
    val querydslVersion = "5.0.0" //querydsl

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.experimental:spring-modulith-starter-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("biz.paluch.redis:lettuce:4.5.0.Final")

    implementation("org.springframework.boot:spring-boot-starter-validation")


    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    developmentOnly("org.springframework.boot:spring-boot-devtools")


    // 분산락 구현을 위한 Redisson
    implementation("org.redisson:redisson-spring-boot-starter:3.20.0")
//    implementation("org.redisson:redisson-spring-boot-starter:3.16.4")

    // runtimeOnly("com.h2database:h2")

    //implementation("org.springdoc:springdoc-openapi-kotlin:1.6.14")
    //implementation("org.springdoc:springdoc-openapi-ui:1.6.14")
    //implementation("org.springdoc:springdoc-openapi-common:1.6.14")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
    //implementation("io.springfox:springfox-boot-starter:3.0.0")
    //implementation("io.springfox:springfox-swagger-ui:3.0.0")

//    implementation("com.github.f4b6a3:ulid-creator:5.1.0")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("com.querydsl:querydsl-jpa:$querydslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$querydslVersion:jakarta")

//    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.0")
//    implementation("io.github.openfeign:feign-jackson:12.1")
//    implementation("com.google.code.gson:gson:2.10.1")

/*    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")*/

    implementation("org.springframework.session:spring-session-data-redis")

    implementation("org.mapstruct:mapstruct:1.5.2.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.2.Final")
    kaptTest("org.mapstruct:mapstruct-processor:1.5.2.Final")



    // Spring Security
/*    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    runtimeOnly("com.nimbusds:oauth2-oidc-sdk:10.7")*/

    // 코루틴
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.6.0")

//    implementation ("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.7.1")

    // implementation("aws.sdk.kotlin:s3:0.19.0-beta") --> Beta이고 레퍼런스 부족

    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
    implementation ("mysql:mysql-connector-java")



//    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
//    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
//    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.experimental:spring-modulith-starter-test")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")

    // mockk
    testImplementation("io.mockk:mockk:1.12.1")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}