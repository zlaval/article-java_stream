plugins {
    id("java")
}

group = "com.zlrx.article"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.vavr:vavr:0.10.4")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}