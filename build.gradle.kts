plugins {
    java
}

tasks.withType(Wrapper::class) {
    gradleVersion = "8.5"
}

group = "io.eroshenkoam"
version = version

val allureVersion = "2.32.0"
val aspectJVersion = "1.9.25.1"

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

    implementation(platform("org.junit:junit-bom:6.0.2"))
    implementation("org.junit.jupiter:junit-jupiter-api")
    implementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    implementation("commons-io:commons-io:2.21.0")
    testImplementation("org.slf4j:slf4j-simple:2.0.17")
}

repositories {
    mavenCentral()
}
