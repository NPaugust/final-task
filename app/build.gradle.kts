plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)

    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
    id ("androidx.navigation.safeargs.kotlin")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.avgust.final_task"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.avgust.final_task"
        minSdk = 28
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
    buildFeatures{
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    annotationProcessor (libs.compiler)
    implementation (libs.androidx.core.ktx)
    implementation (libs.androidx.appcompat)
    implementation (libs.material)
    implementation (libs.androidx.constraintlayout)
    implementation (libs.firebase.auth.ktx)
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.androidx.espresso.core)
    kapt("androidx.room:room-compiler:2.6.1")

    //navigation component for fragments...
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    //room for local database
    implementation (libs.androidx.room.ktx)
    androidTestImplementation (libs.androidx.room.testing)

    //for dataStore instead sharedPref...
    implementation (libs.androidx.datastore.preferences)

    //RecyclerView
    implementation (libs.androidx.recyclerview)

    //retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    //dagger-hilt for dependency injection
    implementation (libs.hilt.android)
    kapt (libs.hilt.android.compiler)

    //implementation 'androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03' //for implementation
    //kapt 'androidx.hilt:hilt-compiler:1.0.0'

    //coroutines
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.kotlinx.coroutines.core)

    //for lifecycles...

    //viewModel
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    // LiveData
    implementation (libs.androidx.lifecycle.livedata.ktx)
    // Lifecycles only (without ViewModel or LiveData)
    implementation (libs.androidx.lifecycle.runtime.ktx)

    implementation (libs.androidx.lifecycle.extensions)



    //coil for images
    implementation (libs.coil.kt.coil)

    //gson for serialisation
    implementation (libs.gson)

    //shimmer for animation effect
    implementation (libs.shimmer)




}