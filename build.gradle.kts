plugins {
    id("java")
    id("org.springframework.boot") version "3.1.5"
}

group = "dev.berkanterdogan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

extra["springBootVersion"] = "3.1.5"
extra["bucket4jVersion"] = "8.6.0"
extra["hazelcastVersion"] = "5.3.0"
extra["cacheApiVersion"] = "1.1.1"
extra["jacksonVersion"] = "2.15.3"
extra["lombokVersion"] = "1.18.30"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:${property("springBootVersion")}")
    implementation("com.bucket4j:bucket4j-core:${property("bucket4jVersion")}")
    implementation("com.bucket4j:bucket4j-hazelcast:${property("bucket4jVersion")}")
    implementation("com.hazelcast:hazelcast:${property("hazelcastVersion")}")
    implementation("javax.cache:cache-api:${property("cacheApiVersion")}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${property("jacksonVersion")}")
    compileOnly("org.projectlombok:lombok:${property("lombokVersion")}")
    annotationProcessor("org.projectlombok:lombok:${property("lombokVersion")}")
}

tasks.jar {
    isEnabled = false
}

tasks.bootJar {
    mainClass.set("dev.berkanterdogan.bucket4j.demo.Bucket4jDemoApplication")
}
