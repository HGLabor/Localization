plugins {
    id("maven-publish")
    java
}

group = "de.hglabor"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "de.hglabor"
            artifactId = "Localization"
            version = "1.0"

            from(components["java"])
        }
    }
}



dependencies {
    implementation("org.apache.commons", "commons-lang3", "3.0");
    implementation("com.google.guava", "guava", "12.0.1");
    implementation("com.google.code.gson:gson:2.8.6");
    testCompile("junit", "junit", "4.12");
}
