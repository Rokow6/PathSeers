plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "edu.utsa.cs3773.pathseer"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.utsa.cs3773.pathseer"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))

    implementation("com.google.firebase:firebase-auth")

    implementation("com.google.android.gms:play-services-auth:21.2.0")

    implementation("com.google.firebase:firebase-analytics")

    implementation ("androidx.credentials:credentials:1.3.0")
    implementation ("androidx.credentials:credentials-play-services-auth:1.3.0")

}