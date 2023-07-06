import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.1"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	`maven-publish`
	id("org.jetbrains.gradle.docker") version "1.6.0-RC.5"
	id("org.flywaydb.flyway")  version "6.4.3"
}

group = "de.rohitmisra"
version = "1.0-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation(kotlin("script-runtime"))
	implementation("com.squareup.okhttp:okhttp:2.7.5")
	implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")
	implementation("se.transmode.gradle:gradle-docker:1.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
