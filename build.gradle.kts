plugins {
    java
}

tasks.withType(Wrapper::class) {
    gradleVersion = "8.5"
}

group = "io.eroshenkoam"
version = version

val allureVersion = "2.24.0"
val aspectJVersion = "1.9.20.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

tasks.test {
    ignoreFailures = true
    useJUnitPlatform()
    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )
    systemProperty("junit.jupiter.execution.parallel.enabled", "true")
    systemProperty("junit.jupiter.execution.parallel.config.strategy", "dynamic")
}

dependencies {
    agent("org.aspectj:aspectjweaver:$aspectJVersion")

    implementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    implementation("io.qameta.allure:allure-junit5")

    implementation(platform("org.junit:junit-bom:5.10.1"))
    implementation("org.junit.jupiter:junit-jupiter-api")
    implementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    implementation("commons-io:commons-io:2.15.1")
    testImplementation("org.slf4j:slf4j-simple:2.0.9")
}

repositories {
    mavenCentral()
}
