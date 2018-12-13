import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    kotlin("jvm") version "1.3.11"
}

allprojects {

    repositories {
        mavenCentral()
        jcenter()
    }
}



tasks.withType<KotlinCompile>().configureEach {
    println("Configuring $name in project ${project.name}...")
    kotlinOptions {
        suppressWarnings = true
        jvmTarget = "1.8"
        allWarningsAsErrors = false
        noJdk = false
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}

//    compileKotlin {
//        kotlinOptions {
//            jvmTarget = "1.8"
//            allWarningsAsErrors = false
//            noJdk = false
//        }
//    }
//    compileTestKotlin {
//        kotlinOptions {
//            jvmTarget = "1.8"
//            allWarningsAsErrors = false
//            noJdk = false
//        }
//    }

dependencies {
    //        implementation("")
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit5"))
}
//    dependencies {
//        implementation ("org.jetbrains.kotlin:kotlin-stdlib")
//    }

//    if (!name.startsWith("util")) {
//        dependencies {
//            implementation (project(":util"))
//            testImplementation (project(":util-tests"))
//        }
//    }
//
//    repositories {
//        mavenCentral()
//    }

//dependencies {
//    compile(kotlin("stdlib-jdk8"))
//}
//repositories {
//    mavenCentral()
//}
//val compileKotlin: KotlinCompile by tasks
//compileKotlin.kotlinOptions {
//    jvmTarget = "1.8"
//}
//val compileTestKotlin: KotlinCompile by tasks
//compileTestKotlin.kotlinOptions {
//    jvmTarget = "1.8"
//}