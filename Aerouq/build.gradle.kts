plugins {
    id("java")
}

group = "Avansada"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.microsoft.sqlserver:mssql-jdbc:12.2.0.jre11")
}

tasks.test {
    useJUnitPlatform()
}